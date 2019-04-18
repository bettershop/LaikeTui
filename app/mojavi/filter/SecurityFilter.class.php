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
 * SecurityFilter provides a base class that classifies a filter as one that
 * handles security.
 *
 * @package    mojavi
 * @subpackage filter
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: SecurityFilter.class.php 689 2004-12-21 20:33:05Z seank $
 */
abstract class SecurityFilter extends Filter
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve a new Controller implementation instance.
     *
     * @param string A Controller implementation name.
     *
     * @return Controller A Controller implementation instance.
     *
     * @throws <b>FactoryException</b> If a security filter implementation
     *                                 instance cannot be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function newInstance ($class)
    {

        // the class exists
        $object = new $class();

        if (!($object instanceof SecurityFilter))
        {

            // the class name is of the wrong type
            $error = 'Class "%s" is not of the type SecurityFilter';
            $error = sprintf($error, $class);

            throw new FactoryException($error);

        }

        return $object;

    }

}

?>