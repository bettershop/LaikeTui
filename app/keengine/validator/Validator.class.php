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
 * Validator allows you to apply constraints to user entered parameters.
 *
 * @package    laiketui
 * @subpackage validator
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class Validator extends ParameterHolder
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
     * Execute this validator.
     *
     * @param mixed A file or parameter value/array.
     * @param string An error message reference.
     *
     * @return bool true, if this validator executes successfully, otherwise
     *              false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function execute (&$value, &$error);

    // -------------------------------------------------------------------------

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
     * Initialize this validator.
     *
     * @param Context The current application context.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        $this->context = $context;

        if ($parameters != null)
        {

            $this->parameters = array_merge($this->parameters, $parameters);

        }

        return true;

    }

}

?>