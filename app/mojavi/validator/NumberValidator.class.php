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
 * NumberValidator verifies a parameter is a number and allows you to apply
 * size constraints.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>max</b>        - [none]                  - Maximum number size.
 * # <b>max_error</b>  - [Input is too large]    - An error message to use when
 *                                                 input is too large.
 * # <b>min</b>        - [none]                  - Minimum number size.
 * # <b>min_error</b>  - [Input is too small]    - An error message to use when
 *                                                 input is too small.
 * # <b>nan_error</b>  - [Input is not a number] - Default error message when
 *                                                 input is not a number.
 * # <b>type</b>       - [Any]                   - Type of number (Any, Float).
 * # <b>type_error</b> - [Input is not a number] - An error message to use when
 *                                                 input is not a number.
 *
 * @package    mojavi
 * @subpackage validator
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: NumberValidator.class.php 513 2004-12-01 14:41:30Z seank $
 */
class NumberValidator extends Validator
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Execute this validator.
     *
     * @param mixed A file or parameter value/array.
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

        if (!is_numeric($value))
        {

            // it's NaN, what nerve!
            $error = $this->getParameter('nan_error');

            return false;

        }

        $type = strtolower($this->getParameter('type'));

        if ($type == 'float')
        {

            if (substr_count($value, '.') != 1)
            {

                // value isn't a float, shazbot!
                $error = $this->getParameter('type_error');

                return false;

            }

            // cast our value to a float
            $value = (float) $value;

        }

        $min = $this->getParameter('min');

        if ($min != null && $value < $min)
        {

            // too small
            $error = $this->getParameter('min_error');

            return false;

        }

        $max = $this->getParameter('max');

        if ($max != null && $value > $max)
        {

            // too large
            $error = $this->getParameter('max_error');

            return false;

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
        $this->setParameter('max',        null);
        $this->setParameter('max_error',  'Input is too large');
        $this->setParameter('min',        null);
        $this->setParameter('min_error',  'Input is too small');
        $this->setParameter('nan_error',  'Input is not a number');
        $this->setParameter('type',       'Any');
        $this->setParameter('type_error', 'Input is not a number');

        // initialize parent
        parent::initialize($context, $parameters);

        // check user-specified parameters
        $type = strtolower($this->getParameter('type'));

        if ($type != 'any' && $type != 'float')
        {

            // unknown type
            $error = 'Unknown number type "%s" in NumberValidator';
            $error = sprintf($error, $this->getParameter('type'));

            throw new ValidatorException($error);

        }

        return true;

    }

}

?>