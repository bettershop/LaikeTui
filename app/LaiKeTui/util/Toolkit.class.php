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
 * Toolkit provides basic utility methods.
 *
 * @package    laiketui
 * @subpackage util
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class Toolkit extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Extract the class or interface name from filename.
     *
     * @param string A filename.
     *
     * @return string A class or interface name, if one can be extracted,
     *                otherwise null.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function extractClassName ($filename)
    {

        $retval = null;

        if (self::isPathAbsolute($filename))
        {

            $filename = basename($filename);

        }

        $pattern = '/(.*?)\.(class|interface)\.php/i';

        if (preg_match($pattern, $filename, $match))
        {

            $retval = $match[1];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Determine if a filesystem path is absolute.
     *
     * @param path A filesystem path.
     *
     * @return bool true, if the path is absolute, otherwise false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function isPathAbsolute ($path)
    {

        if ($path[0] == '/' || $path[0] == '\\' ||
            (strlen($path) > 3 && ctype_alpha($path[0]) &&
             $path[1] == ':' &&
             ($path[2] == '\\' || $path[2] == '/')
            )
           )
        {

            return true;

        }

        return false;

    }

}

?>