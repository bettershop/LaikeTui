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
 * Appender allows you to specify a destination for log data and provide
 * a custom layout for it, through which all log messages will be formatted.
 *
 * @package    laiketui
 * @subpackage logging
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class Appender extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $layout = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve the layout.
     *
     * @return Layout A Layout instance, if one has been set, otherwise null.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getLayout ()
    {

        return $this->layout;

    }

    // -------------------------------------------------------------------------

    /**
     * Set the layout.
     *
     * @param Layout A Layout instance.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function setLayout ($layout)
    {

        $this->layout = $layout;

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function shutdown ();

    // -------------------------------------------------------------------------

    /**
     * Write log data to this appender.
     *
     * @param string Log data to be written.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function write (&$data);

}

?>