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
 * MySQLDatabase provides connectivity for the MySQL brand database.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>database</b>   - [none]      - The database name.
 * # <b>host</b>       - [localhost] - The database host.
 * # <b>method</b>     - [normal]    - How to read connection parameters.
 *                                     Possible values are normal, server, and
 *                                     env. The normal method reads them from
 *                                     the specified values. server reads them
 *                                     from $_SERVER where the keys to retrieve
 *                                     the values are what you specify the value
 *                                     as in the settings. env reads them from
 *                                     $_ENV and works like $_SERVER.
 * # <b>password</b>   - [none]      - The database password.
 * # <b>persistent</b> - [No]        - Indicates that the connection should be
 *                                     persistent.
 * # <b>user</b>       - [none]      - The database user.
 *
 * @package    mojavi
 * @subpackage database
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: MySQLDatabase.class.php 620 2004-12-07 04:28:32Z seank $
 */
class MySQLDatabase extends Database
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Connect to the database.
     *
     * @throws <b>DatabaseException</b> If a connection could not be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function connect ()
    {

        // determine how to get our
        $method = $this->getParameter('method', 'normal');

        switch ($method)
        {

            case 'normal':

                // get parameters normally
                $database = $this->getParameter('database');
                $host     = $this->getParameter('host', 'localhost');
                $password = $this->getParameter('password');
                $user     = $this->getParameter('user');

                break;

            case 'server':

                // construct a connection string from existing $_SERVER values
                // and extract them to local scope
                $parameters =& $this->loadParameters($_SERVER);
                extract($parameters);

                break;

            case 'env':

                // construct a connection string from existing $_ENV values
                // and extract them to local scope
                $string =& $this->loadParameters($_ENV);
                extract($parameters);

                break;

            default:

                // who knows what the user wants...
                $error = 'Invalid MySQLDatabase parameter retrieval method ' .
                         '"%s"';
                $error = sprintf($error, $method);

                throw new DatabaseException($error);

        }

        // let's see if we need a persistent connection
        $persistent = $this->getParameter('persistent', false);
        $connect    = ($persistent) ? 'mysql_pconnect' : 'mysql_connect';

        if ($password == null)
        {

            if ($user == null)
            {

                $this->connection = @$connect($host);

            } else
            {

                $this->connection = @$connect($host, $user);
            }

        } else
        {

            $this->connection = @$connect($host, $user, $password);

        }

        // make sure the connection went through
        if ($this->connection === false)
        {

            // the connection's foobar'd
            $error = 'Failed to create a MySQLDatabase connection';

            throw new DatabaseException($error);

        }

        // select our database
        if ($database != null &&
            !@mysql_select_db($database, $this->connection))
        {

            // can't select the database
            $error = 'Failed to select MySQLDatabase "%s"';
            $error = sprintf($error, $database);

            throw new DatabaseException($error);

        }

        // since we're not an abstraction layer, we copy the connection
        // to the resource
        $this->resource =& $this->connection;

    }

    // -------------------------------------------------------------------------

    /**
     * Load connection parameters from an existing array.
     *
     * @return array An associative array of connection parameters.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    private function & loadParameters (&$array)
    {

        // list of available parameters
        $available = array('database', 'host', 'password', 'user');
        
        $parameters = array();

        foreach ($available as $parameter)
        {

            $$parameter = $this->getParameter($parameter);

            $parameters[$parameter] = ($$parameter != null)
                                      ? $array[$$parameter] : null;

        }

        return $parameters;

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @throws <b>DatabaseException</b> If an error occurs while shutting down
     *                                 this database.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function shutdown ()
    {

        if ($this->connection != null)
        {

            @mysql_close($this->connection);

        }

    }

}

?>