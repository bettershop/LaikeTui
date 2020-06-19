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
 * ParameterParser is used to take parameter lists in Mojavi configuration
 * files and return them in literal array form.
 *
 * @package    laiketui
 * @subpackage config
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class ParameterParser extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Parse an array of key-value pairs and return an array of specified
     * parameters.
     *
     * @param array  An array of parameters.
     * @param string A parameter identifer.
     *
     * @return string A string representation of an array of initialization
     *                parameters, if any parameters exists, otherwise the string
     *                form of null.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function & parse (&$array, $identifer = 'param')
    {

        $parameters = array();

        // get the identifer length
        $identifer = $identifer . '.';
        $length    = strlen($identifer);

        foreach ($array as $key => &$value)
        {

            if (strlen($key) > $length &&
                substr($key, 0, $length) == $identifer)
            {

                // literalize our value
                $value = ConfigHandler::literalize($value);

                // we found a parameter, let's add this baby to our list
                $parameter = substr($key, $length);

                if (preg_match('/\.[0-9]+$/', $parameter))
                {

                    // we have an array of parameter values, get the actual
                    // parameter name
                    preg_match('/(.*?)\.[0-9]+$/', $parameter, $match);

                    $parameter = $match[1];

                    if (!isset($parameters[$parameter]))
                    {

                        // create our parameter's sub-array
                        $parameters[$parameter] = array();

                    }

                    // add our parameter to the array
                    $parameters[$parameter][] =& $value;

                } else
                {

                    // just a boring 'ol string value
                    $parameters[$parameter] =& $value;

                }

            }

        }

        if (count($parameters) == 0)
        {

            // no parameters
            $retval = 'null';

            return $retval;

        }

        // init our return value
        $retval = array();

        // loop through the parameters list
        foreach ($parameters as $parameter => &$value)
        {

            if (is_array($value))
            {

                // we have an array value
                $tmp      = "'%s' => array(%s)";
                $retval[] = sprintf($tmp, $parameter, implode(', ', $value));

            } else
            {

                // we have a normal value
                $tmp      = "'%s' => %s";
                $retval[] = sprintf($tmp, $parameter, $value);

            }

        }

        // complete the return value
        $tmp    = 'array(%s)';
        $retval = sprintf($tmp, implode(', ', $retval));

        return $retval;

    }

}

?>