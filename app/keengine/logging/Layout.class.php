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
 * Layout allows you to specify a message layout for log messages.
 *
 * @package    laiketui
 * @subpackage logging
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class Layout extends MojaviObject
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
     * Format a message.
     *
     * @param Message A Message instance.
     *
     * @return string A formatted message.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function & format ($message);

    // -------------------------------------------------------------------------

    /**
     * Retrieve the message layout.
     *
     * @return string A message layout.
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
     * Set the message layout.
     *
     * @param string A message layout.
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

}

?>