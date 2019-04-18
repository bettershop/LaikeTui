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
 * CreoleDatabase provides connectivity for the Creole database abstraction
 * layer.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>classpath</b>      - [none]   - An absolute filesystem path to the main
 *                                      Creole class file.
 * # <b>database</b>       - [none]   - The database name.
 * # <b>dsn</b>            - [none]   - The DSN formatted connection string.
 * # <b>hostspec</b>       - [none]   - The database host specifications.
 * # <b>method</b>         - [normal] - How to read connection parameters.
 *                                      Possible values are dsn, normal,
 *                                      server, and env. The dsn method reads
 *                                      them from the dsn parameter. The
 *                                      normal method reads them from the
 *                                      specified values. server reads them
 *                                      from $_SERVER where the keys to
 *                                      retrieve the values are what you
 *                                      specify the value as in the settings.
 *                                      env reads them from $_ENV and works
 *                                      like $_SERVER.
 * # <b>no_assoc_lower</b> - [Off]    - Turn off portabilty of resultset
 *                                      field names.
 * # <b>password</b>       - [none]   - The database password.
 * # <b>persistent</b>     - [No]     - Indicates that the connection should
 *                                      persistent.
 * # <b>phptype</b>        - [none]   - The type of database (mysql, pgsql,
 *                                      etc).
 * # <b>username</b>       - [none]   - The database user.
 *
 * @package    mojavi
 * @subpackage database
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: CreoleDatabase.class.php 743 2005-01-06 15:53:16Z seank $
 */
class CreoleDatabase extends Database
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

        try
        {

            // determine how to get our settings
            $method = $this->getParameter('method', 'normal');

            switch ($method)
            {

                case 'normal':

                    // get parameters normally, and all are required
                    $database = $this->getParameter('database', null);
                    $hostspec = $this->getParameter('hostspec', null);
                    $password = $this->getParameter('password', null);
                    $phptype  = $this->getParameter('phptype', null);
                    $username = $this->getParameter('username', null);

                    $dsn = array('database' => $database,
                                 'hostspec' => $hostspec,
                                 'password' => $password,
                                 'phptype'  => $phptype,
                                 'username' => $username);

                    break;

                case 'dsn':

                    $dsn = $this->getParameter('dsn');

                    if ($dsn == null)
                    {

                        // missing required dsn parameter
                        $error = 'Database configuration specifies method ' .
                                 '"dsn", but is missing dsn parameter';

                        throw new DatabaseException($error);

                    }

                    break;

                case 'server':

                    // construct a DSN connection string from existing $_SERVER
                    // values
                    $dsn =& $this->loadDSN($_SERVER);

                    break;

                case 'env':

                    // construct a DSN connection string from existing $_ENV
                    // values
                    $dsn =& $this->loadDSN($_ENV);

                    break;

                default:

                    // who knows what the user wants...
                    $error = 'Invalid CreoleDatabase parameter retrieval ' .
                             'method "%s"';
                    $error = sprintf($error, $method);

                    throw new DatabaseException($error);

            }

            // get creole class path
            $classPath = $this->getParameter('classpath');

            // include the creole file
            if ($classPath == null)
            {

                require_once('creole/Creole.php');

            } else
            {

                require_once($classPath);

            }

            // set our flags
            $noAssocLower = $this->getParameter('no_assoc_lower', false);
            $persistent   = $this->getParameter('persistent', false);

            $flags  = 0;
            $flags |= ($noAssocLower) ? Creole::NO_ASSOC_LOWER : 0;
            $flags |= ($persistent) ? Creole::PERSISTENT : 0;

            // do the duuuurtay work, right thurr
            if ($flags > 0)
            {

                $this->connection = Creole::getConnection($dsn, $flags);

            } else
            {

                $this->connection = Creole::getConnection($dsn);

            }

            // get our resource
            $this->resource =& $this->connection->getResource();

        } catch (SQLException $e)
        {

            // the connection's foobar'd
            throw new DatabaseException($e->toString());

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Load a DSN connection string from an existing array.
     *
     * @return array An associative array of connection parameters.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    private function & loadDSN (&$array)
    {

        // determine if a dsn is set, otherwise use separate parameters
        $dsn = $this->getParameter('dsn');

        if ($dsn == null)
        {

            // list of available parameters
            $available = array('database', 'hostspec', 'password', 'phptype',
                               'username');

            $dsn = array();

            // yes, i know variable variables are ugly, but let's avoid using
            // an array for array's sake in this single spot in the source
            foreach ($available as $parameter)
            {

                $$parameter = $this->getParameter($parameter);

                $dsn[$parameter] = ($$parameter != null)
                                   ? $array[$$parameter] : null;

            }

        } else
        {

            $dsn = $array[$dsn];

        }

        return $dsn;

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

        if ($this->connection !== null)
        {

            @$this->connection->close();

        }

    }

}

?>