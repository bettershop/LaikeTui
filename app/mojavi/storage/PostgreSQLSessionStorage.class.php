<?php

// +---------------------------------------------------------------------------+
// | This file is part of the Mojavi package.                                  |
// | Copyright (c) 2003, 2004 Sean Kerr.                                       |
// |                                                                           |
// | For the full copyright and license information, please view the LICENSE   |
// | file that was distributed with this source code. You can also view the    |
// | LICENSE file online at http://www.mojavi.org.                             |
// +---------------------------------------------------------------------------+

/**
 * Provides support for session storage using a PostgreSQL brand database.
 *
 * <b>Required parameters:</b>
 *
 * # <b>db_table</b> - [none] - The database table in which session data will be
 *                              stored.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>db_id_col</b>    - [sess_id]   - The database column in which the
 *                                       session id will be stored.
 * # <b>db_data_col</b>  - [sess_data] - The database column in which the
 *                                       session data will be stored.
 * # <b>db_time_col</b>  - [sess_time] - The database column in which the
 *                                       session timestamp will be stored.
 * # <b>session_name</b> - [Mojavi]    - The name of the session.
 *
 * @package    mojavi
 * @subpackage storage
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: PostgreSQLSessionStorage.class.php 697 2004-12-28 04:45:48Z seank $
 */
class PostgreSQLSessionStorage extends SessionStorage
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $resource = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Initialize this Storage.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Storage.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        // disable auto_start
        $this->setParameter('auto_start', false);

        // initialize the parent
        parent::initialize($context, $parameters);

        if (!$this->hasParameter('db_table'))
        {

            // missing required 'db_table' parameter
            $error = 'Factory configuration file is missing required ' .
                     '"db_table" parameter for the Storage category';

            throw new InitializationException($error);

        }

        // use this object as the session handler
        session_set_save_handler(array($this, 'sessionOpen'),
                                 array($this, 'sessionClose'),
                                 array($this, 'sessionRead'),
                                 array($this, 'sessionWrite'),
                                 array($this, 'sessionDestroy'),
                                 array($this, 'sessionGC'));

        // start our session
        session_start();

    }

    // -------------------------------------------------------------------------

    /**
     * Close a session.
     *
     * @return bool true, if the session was closed, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionClose ()
    {

        // do nothing
        return true;

    }

    // -------------------------------------------------------------------------

    /**
     * Destroy a session.
     *
     * @param string A session ID.
     *
     * @return bool true, if the session was destroyed, otherwise an exception
     *              is thrown.
     *
     * @throws <b>DatabaseException</b> If the session cannot be destroyed.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionDestroy ($id)
    {

        // get table/column
        $db_table  = $this->getParameter('db_table');
        $db_id_col = $this->getParameter('db_id_col', 'sess_id');

        // cleanup the session id, just in case
        $id = addslashes($id);

        // delete the record associated with this id
        $sql = 'DELETE FROM ' . $db_table . ' ' .
               'WHERE ' . $db_id_col . ' = \'' . $id . '\'';

        if (@pg_query($this->resource, $sql))
        {

            return true;

        }

        // failed to destroy session
        $error = 'PostgreSQLSessionStorage cannot destroy session id "%s"';
        $error = sprintf($error, $id);

        throw new DatabaseException($error);

    }

    // -------------------------------------------------------------------------

    /**
     * Cleanup old sessions.
     *
     * @param int The lifetime of a session.
     *
     * @return bool true, if old sessions have been cleaned, otherwise an
     *              exception is thrown.
     *
     * @throws <b>DatabaseException</b> If any old sessions cannot be cleaned.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionGC ($lifetime)
    {

        // determine deletable session time
        $time = time() - $lifetime;

        // get table/column
        $db_table    = $this->getParameter('db_table');
        $db_time_col = $this->getParameter('db_time_col', 'sess_time');

        // delete the record associated with this id
        $sql = 'DELETE FROM ' . $db_table . ' ' .
               'WHERE ' . $db_time_col . ' < ' . $lifetime;

        if (@pg_query($this->resource, $sql))
        {

            return true;

        }

        // failed to cleanup old sessions
        $error = 'PostgreSQLSessionStorage cannot delete old sessions';

        throw new DatabaseException($error);

    }

    // -------------------------------------------------------------------------

    /**
     * Open a session.
     *
     * @param string
     * @param string
     *
     * @return bool true, if the session was opened, otherwise an exception is
     *              thrown.
     *
     * @throws <b>DatabaseException</b> If a connection with the database does
     *                                  not exist or cannot be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionOpen ($path, $name)
    {

        // what database are we using?
        $database = $this->getParameter('database', 'default');

        // get the database resource
        $this->resource = $this->getContext()
                               ->getDatabaseManager()
                               ->getDatabase($database)
                               ->getResource();

        return true;

    }

    // -------------------------------------------------------------------------

    /**
     * Read a session.
     *
     * @param string A session ID.
     *
     * @return bool true, if the session was read, otherwise an exception is
     *              thrown.
     *
     * @throws <b>DatabaseException</b> If the session cannot be read.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionRead ($id)
    {

        // get table/column
        $db_table    = $this->getParameter('db_table');
        $db_data_col = $this->getParameter('db_data_col', 'sess_data');
        $db_id_col   = $this->getParameter('db_id_col', 'sess_id');
        $db_time_col = $this->getParameter('db_time_col', 'sess_time');

        // cleanup the session id, just in case
        $id = addslashes($id);

        // delete the record associated with this id
        $sql = 'SELECT ' . $db_data_col . ' ' .
               'FROM ' . $db_table . ' ' .
               'WHERE ' . $db_id_col . ' = \'' . $id . '\'';

        $result = @pg_query($this->resource, $sql);

        if ($result != false && @pg_num_rows($result) == 1)
        {

            // found the session
            $data = pg_fetch_row($result);

            return $data[0];

        } else
        {

            // session does not exist, create it
            $sql = 'INSERT INTO ' . $db_table . ' (' . $db_id_col . ', ' .
                   $db_data_col . ', ' . $db_time_col . ') VALUES (' .
                   '\'' . $id . '\', \'\', ' . time() . ')';

            if (@pg_query($this->resource, $sql))
            {

               return '';

            }

            // can't create record
            $error = 'PostgreSQLSessionStorage cannot create new record for ' .
                     'id "%s"';
            $error = sprintf($error, $id);

            throw new DatabaseException($error);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Write session data.
     *
     * @param string A session ID.
     * @param string A serialized chunk of session data.
     *
     * @return bool true, if the session was written, otherwise an exception is
     *              thrown.
     *
     * @throws <b>DatabaseException</b> If the session data cannot be written.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function sessionWrite ($id, &$data)
    {

        // get table/column
        $db_table    = $this->getParameter('db_table');
        $db_data_col = $this->getParameter('db_data_col', 'sess_data');
        $db_id_col   = $this->getParameter('db_id_col', 'sess_id');
        $db_time_col = $this->getParameter('db_time_col', 'sess_time');

        // cleanup the session id and data, just in case
        $id   = addslashes($id);
        $data = addslashes($data);

        // delete the record associated with this id
        $sql = 'UPDATE ' . $db_table . ' ' .
               'SET ' . $db_data_col . ' = \'' . $data . '\', ' .
               $db_time_col . ' = ' . time() . ' ' .
               'WHERE ' . $db_id_col . ' = \'' . $id . '\'';

        if (@pg_query($this->resource, $sql))
        {

            return true;

        }

        // failed to write session data
        $error = 'PostgreSQLSessionStorage cannot write session data for id ' .
                 '"%s"';
        $error = sprintf($error, $id);

        throw new DatabaseException($error);

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function shutdown ()
    {

    }

}

?>