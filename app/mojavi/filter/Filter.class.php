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
 * Filter provides a way for you to intercept incoming requests or outgoing
 * responses.
 *
 * @package    mojavi
 * @subpackage filter
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: Filter.class.php 569 2004-12-05 06:25:47Z seank $
 */
abstract class Filter extends ParameterHolder
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
     * Execute this filter.
     *
     * @param FilterChain A FilterChain instance.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    abstract function execute ($filterChain);

    // -------------------------------------------------------------------------

    /**
     * Retrieve the current application context.
     *
     * @return Context The current Context instance.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public final function getContext ()
    {

        return $this->context;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this Filter.
     *
     * @param Context The current application context.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Filter.
     *
     * @author Sean Kerr (skerr@mojavi.org)
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