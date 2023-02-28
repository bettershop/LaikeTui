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
 * PDODatabase provides connectivity for the PDO database abstraction layer.
 *
 * @package    laiketui
 * @subpackage database
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class PDODatabase extends Database
{

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
    public function connect ()
    {

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
    public function shutdown ()
    {

    }

}

?>