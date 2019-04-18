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
 * User wraps a client session and provides accessor methods for user
 * attributes. It also makes storing and retrieving multiple page form data
 * rather easy by allowing user attributes to be stored in namespaces, which
 * help organize data.
 *
 * @package    mojavi
 * @subpackage user
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: User.class.php 761 2005-01-29 06:20:01Z seank $
 */
class User extends ParameterHolder
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    /**
     * The namespace under which attributes will be stored.
     *
     * @since 3.0.0
     */
    const ATTRIBUTE_NAMESPACE = 'org/mojavi/user/User/attributes';

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $attributes = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Clear all attributes associated with this user.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function clearAttributes ()
    {

        $this->attributes = null;
        $this->attributes = array();

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an attribute.
     *
     * @param string An attribute name.
     * @param string An attribute namespace.
     *
     * @return mixed An attribute value, if the attribute exists, otherwise
     *               null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function & getAttribute ($name, $ns = MO_USER_NAMESPACE)
    {

        $retval = null;

        if (isset($this->attributes[$ns]) &&
            isset($this->attributes[$ns][$name]))
        {

            return $this->attributes[$ns][$name];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an array of attribute names.
     *
     * @param string An attribute namespace.
     *
     * @return array An indexed array of attribute names, if the namespace
     *               exists, otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getAttributeNames ($ns = MO_USER_NAMESPACE)
    {

        if (isset($this->attributes[$ns]))
        {

            return array_keys($this->attributes[$ns]);

        }

        return null;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve all attributes within a namespace.
     *
     * @param string An attribute namespace.
     *
     * @return array An associative array of attributes.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function & getAttributeNamespace ($ns = MO_USER_NAMESPACE)
    {

        $retval = null;

        if (isset($this->attributes[$ns]))
        {

            return $this->attributes[$ns];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an array of attribute namespaces.
     *
     * @return array An indexed array of attribute namespaces.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getAttributeNamespaces ()
    {

        return array_keys($this->attributes);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the current application context.
     *
     * @return Context A Context instance.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getContext ()
    {

        return $this->context;

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not an attribute exists.
     *
     * @param string An attribute name.
     * @param string An attribute namespace.
     *
     * @return bool true, if the attribute exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function hasAttribute ($name, $ns = MO_USER_NAMESPACE)
    {

        if (isset($this->attributes[$ns]))
        {

            return isset($this->attributes[$ns][$name]);

        }

        return false;

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not an attribute namespace exists.
     *
     * @param string An attribute namespace.
     *
     * @return bool true, if the namespace exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function hasAttributeNamespace ($ns)
    {

        return isset($this->attributes[$ns]);

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this User.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this User.
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

        // read data from storage
        $this->attributes = $this->getContext()
                                 ->getStorage()
                                 ->read(self::ATTRIBUTE_NAMESPACE);

        if ($this->attributes == null)
        {

            // initialize our attributes array
            $this->attributes = array();

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a new User implementation instance.
     *
     * @param string A User implementation name
     *
     * @return User A User implementation instance.
     *
     * @throws <b>FactoryException</b> If a user implementation instance cannot
     *                                 be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function newInstance ($class)
    {

        // the class exists
        $object = new $class();

        if (!($object instanceof User))
        {

            // the class name is of the wrong type
            $error = 'Class "%s" is not of the type User';
            $error = sprintf($error, $class);

            throw new FactoryException($error);

        }

        return $object;

    }

    // -------------------------------------------------------------------------

    /**
     * Remove an attribute.
     *
     * @param string An attribute name.
     * @param string An attribute namespace.
     *
     * @return mixed An attribute value, if the attribute was removed,
     *               otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function & removeAttribute ($name, $ns = MO_USER_NAMESPACE)
    {

        $retval = null;

        if (isset($this->attributes[$ns]) &&
            isset($this->attributes[$ns][$name]))
        {

            $retval =& $this->attributes[$ns][$name];

            unset($this->attributes[$ns][$name]);

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Remove an attribute namespace and all of its associated attributes.
     *
     * @param string An attribute namespace.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function removeAttributeNamespace ($ns)
    {

        if (isset($this->attributes[$ns]))
        {

            unset($this->attributes[$ns]);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Set an attribute.
     *
     * If an attribute with the name already exists the value will be
     * overridden.
     *
     * @param string An attribute name.
     * @param mixed  An attribute value.
     * @param string An attribute namespace.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setAttribute ($name, $value, $ns = MO_USER_NAMESPACE)
    {

        if (!isset($this->attributes[$ns]))
        {

            $this->attributes[$ns] = array();

        }

        $this->attributes[$ns][$name] = $value;

    }

    // -------------------------------------------------------------------------

    /**
     * Set an attribute by reference.
     *
     * If an attribute with the name already exists the value will be
     * overridden.
     *
     * @param string An attribute name.
     * @param mixed  A reference to an attribute value.
     * @param string An attribute namespace.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setAttributeByRef ($name, &$value, $ns = MO_USER_NAMESPACE)
    {

        if (!isset($this->attributes[$ns]))
        {

            $this->attributes[$ns] = array();

        }

        $this->attributes[$ns][$name] =& $value;

    }

    // -------------------------------------------------------------------------

    /**
     * Set an array of attributes.
     *
     * If an existing attribute name matches any of the keys in the supplied
     * array, the associated value will be overridden.
     *
     * @param array  An associative array of attributes and their associated
     *               values.
     * @param string An attribute namespace.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setAttributes ($attributes, $ns = MO_USER_NAMESPACE)
    {

        if (!isset($this->attributes[$ns]))
        {

            $this->attributes[$ns] = array();

        }

        $this->attributes[$ns] = array_merge($this->attributes[$ns],
                                             $attributes);

    }

    // -------------------------------------------------------------------------

    /**
     * Set an array of attributes by reference.
     *
     * If an existing attribute name matches any of the keys in the supplied
     * array, the associated value will be overridden.
     *
     * @param array  An associative array of attributes and references to their
     *               associated values.
     * @param string An attribute namespace.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setAttributesByRef (&$attributes, $ns = MO_USER_NAMESPACE)
    {

        if (!isset($this->attributes[$ns]))
        {

            $this->attributes[$ns] = array();

        }

        foreach ($attributes as $key => &$value)
        {

            $this->attributes[$ns][$key] =& $value;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function shutdown ()
    {

        // write attributes to the storage
        $this->getContext()
             ->getStorage()
             ->write(self::ATTRIBUTE_NAMESPACE, $this->attributes);

    }

}

?>