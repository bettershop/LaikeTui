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
 * StringValidator allows you to apply string-related constraints to a
 * parameter.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>insensitive</b>  - [false]              - Whether or not the value check
 *                                                against the array of values is
 *                                                case-insensitive. <b>Note:</b>
 *                                                When using this option, values
 *                                                in the values array must be
 *                                                entered in lower-case.
 * # <b>max</b>          - [none]               - Maximum string length.
 * # <b>max_error</b>    - [Input is too long]  - An error message to use when
 *                                                input is too long.
 * # <b>min</b>          - [none]               - Minimum string length.
 * # <b>min_error</b>    - [Input is too short] - An error message to use when
 *                                                input is too short.
 * # <b>values</b>       - [none]               - An array of values the input
 *                                                is allowed to match.
 * # <b>values_error</b> - [Invalid selection]  - An error message to use when
 *                                                input does not match a value
 *                                                listed in the values array.
 *
 * @package    mojavi
 * @subpackage validator
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: StringValidator.class.php 492 2004-11-26 03:56:47Z seank $
 */
class StringValidator extends Validator
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Execute this validator.
     *
     * @param mixed A parameter value.
     * @param error An error message reference.
     *
     * @return bool true, if this validator executes successfully, otherwise
     *              false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function execute (&$value, &$error)
    {

        $min = $this->getParameter('min');

        if ($min != null && strlen($value) < $min)
        {

            // too short
            $error = $this->getParameter('min_error');

            return false;

        }

        $max = $this->getParameter('max');

        if ($max != null && strlen($value) > $max)
        {

            // too long
            $error = $this->getParameter('max_error');

            return false;

        }

        $values = $this->getParameter('values');

        if ($values != null)
        {

            $insensitive = $this->getParameter('insensitive');
            $lvalue      = ($insensitive) ? strtolower($value) : $value;

            if (!in_array($lvalue, $values))
            {

                // can't find a match
                $error = $this->getParameter('values_error');

                return false;

            }

        }

        return true;

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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        // set defaults
        $this->setParameter('insensitive',  false);
        $this->setParameter('max',          null);
        $this->setParameter('max_error',    'Input is too long');
        $this->setParameter('min',          null);
        $this->setParameter('min_error',    'Input is too short');
        $this->setParameter('values',       null);
        $this->setParameter('values_error', 'Invalid selection');

        // initialize parent
        parent::initialize($context, $parameters);

        return true;

    }

}

?>