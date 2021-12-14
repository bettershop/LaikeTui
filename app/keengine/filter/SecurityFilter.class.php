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
 * SecurityFilter provides a base class that classifies a filter as one that
 * handles security.
 *
 * @package    laiketui
 * @subpackage filter
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
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
     * @author ketter (ketter@laiketui.com)
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