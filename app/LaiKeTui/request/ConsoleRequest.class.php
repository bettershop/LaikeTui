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
 *
 *
 * @package    laiketui
 * @subpackage request
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class ConsoleRequest extends Request
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | PUBLIC VARIABLES                                                      |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | CONSTRUCTOR                                                           |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Initialize this Request.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Request.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

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
    public function shutdown ()
    {

    }

}

?>