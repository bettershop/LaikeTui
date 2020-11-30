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
 * Model provides a convention for separating business logic from application
 * logic. When using a model you're providing a globally accessible API for
 * other modules to access, which will boost interoperability among modules in
 * your web application.
 *
 * @package    laiketui
 * @subpackage model
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class Model extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+
    private
        $context = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve the current application context.
     *
     * @return Context The current Context instance.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public final function getContext ()
    {

        return $this->context;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this model.
     *
     * @param Context The current application context.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($context)
    {

        $this->context = $context;

        return true;

    }

}

?>