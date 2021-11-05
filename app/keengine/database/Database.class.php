<?php

// +---------------------------------------------------------------------------+
// | This file is part of the core package.                                    |
// | Copyright (c) laiketui.com                                                |
// |                                                                           |
// | For the full copyright and license information, please view the LICENSE   |
// | file that was distributed with this source code. You can also view the    |
// | LICENSE file online at http://www.laiketui.com                            |
// +---------------------------------------------------------------------------+

/**
 * Database is a base abstraction class that allows you to setup any type of
 * database connection via a configuration file.
 *
 * @package    laiketui
 * @subpackage database
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class Database extends ParameterHolder
{

    // +-----------------------------------------------------------------------+
    // | PROTECTED VARIABLES                                                   |
    // +-----------------------------------------------------------------------+

    protected
        $connection = null,
        $resource   = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Connect to the database.
     *
     * @throws <b>DatabaseException</b> If a connection could not be created.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function connect ();

    // -------------------------------------------------------------------------

    /**
     * Retrieve the database connection associated with this Database
     * implementation.
     *
     * When this is executed on a Database implementation that isn't an
     * abstraction layer, a copy of the resource will be returned.
     *
     * @return mixed A database connection.
     *
     * @throws <b>DatabaseException</b> If a connection could not be retrieved.
     */
    public function getConnection ()
    {

        if ($this->connection == null)
        {

            $this->connect();

        }

        return $this->connection;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a raw database resource associated with this Database
     * implementation.
     *
     * @return mixed A database resource.
     *
     * @throws <b>DatabaseException</b> If a resource could not be retrieved.
     */
    public function getResource ()
    {

        if ($this->resource == null)
        {

            $this->connect();

        }

        return $this->resource;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this Database.
     *
     * @param array An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Database.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($parameters = null)
    {

        if ($parameters != null)
        {

            $this->parameters = array_merge($this->parameters, $parameters);

        }

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
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function shutdown ();

}

?>