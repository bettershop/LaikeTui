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
 * $Id: MojaviObject.class.php 65 2004-10-26 03:16:15Z seank $
 *
 * MojaviObject provides useful methods that all Mojavi classes inherit.
 *
 * @package    mojavi
 * @subpackage core
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Rev$
 */
abstract class MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve a string representation of this object.
     *
     * @return string A string containing all public variables available in
     *                this object.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function toString ()
    {

        $output = '';
        $vars   = get_object_vars($this);

        foreach ($vars as $key => &$value)
        {

            if (strlen($output) > 0)
            {

                $output .= ', ';

            }

            $output .= $key . ': ' . $value;

        }

        return $output;

    }

}

?>