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
 * ConfigHandler allows a developer to create a custom formatted configuration
 * file pertaining to any information they like and still have it auto-generate
 * PHP code.
 *
 * @package    mojavi
 * @subpackage config
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: ConfigHandler.class.php 707 2004-12-28 17:48:28Z seank $
 */
abstract class ConfigHandler extends ParameterHolder
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Add a set of replacement values.
     *
     * @param string The old value.
     * @param string The new value which will replace the old value.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function addReplacement ($oldValue, $newValue)
    {

        $this->oldValues[] = $oldValue;
        $this->newValues[] = $newValue;

    }

    // -------------------------------------------------------------------------

    /**
     * Execute this configuration handler.
     *
     * @param string An absolute filesystem path to a configuration file.
     *
     * @return string Data to be written to a cache file.
     *
     * @throws <b>ConfigurationException</b> If a requested configuration file
     *                                       does not exist or is not readable.
     * @throws <b>ParseException</b> If a requested configuration file is
     *                               improperly formatted.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function & execute ($config);

    // -------------------------------------------------------------------------

    /**
     * Initialize this ConfigHandler.
     *
     * @param array An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this ConfigHandler.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function initialize ($parameters = null)
    {

        if ($parameters != null)
        {

            $this->parameters = array_merge($this->parameters, $parameters);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Literalize a string value.
     *
     * @param string The value to literalize.
     *
     * @return string A literalized value.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function literalize ($value)
    {

        static
            $keys = array("\\", "%'", "'"),
            $reps = array("\\\\", "\"", "\\'");

        if ($value == null)
        {

            // null value
            return 'null';

        }

        // lowercase our value for comparison
        $value  = trim($value);
        $lvalue = strtolower($value);

        if ($lvalue == 'on' || $lvalue == 'yes' || $lvalue == 'true')
        {

            // replace values 'on' and 'yes' with a boolean true value
            return 'true';

        } else if ($lvalue == 'off' || $lvalue == 'no' || $lvalue == 'false')
        {

            // replace values 'off' and 'no' with a boolean false value
            return 'false';

        } else if (!is_numeric($value))
        {

            $value = str_replace($keys, $reps, $value);

            return "'" . $value . "'";

        }

        // numeric value
        return $value;

    }

    // -------------------------------------------------------------------------

    /**
     * Replace constant identifiers in a string.
     *
     * @param string The value on which to run the replacement procedure.
     *
     * @return string The new value.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function & replaceConstants ($value)
    {

        static
            $keys = array('%MO_APP_DIR%', '%MO_LIB_DIR%', '%MO_MODULE_DIR%',
                          '%MO_WEBAPP_DIR%'),

            $reps = array(MO_APP_DIR, MO_LIB_DIR, MO_MODULE_DIR,
                          MO_WEBAPP_DIR);

        $value = str_replace($keys, $reps, $value);

        return $value;

    }

    // -------------------------------------------------------------------------

    /**
     * Replace a relative filesystem path with an absolute one.
     *
     * @param string A relative filesystem path.
     *
     * @return string The new path.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function & replacePath ($path)
    {

        if (!Toolkit::isPathAbsolute($path))
        {

            // not an absolute path so we'll prepend to it
            $path = MO_WEBAPP_DIR . '/' . $path;

        }

        return $path;

    }

}

?>