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
 * Layout allows you to specify a message layout for log messages.
 *
 * @package    mojavi
 * @subpackage logging
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: Layout.class.php 762 2005-01-29 06:30:25Z seank $
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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    abstract function & format ($message);

    // -------------------------------------------------------------------------

    /**
     * Retrieve the message layout.
     *
     * @return string A message layout.
     *
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setLayout ($layout)
    {

        $this->layout = $layout;

    }

}

?>