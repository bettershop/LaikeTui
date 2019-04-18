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
 * Request provides methods for manipulating client request information such
 * as attributes, errors and parameters. It is also possible to manipulate the
 * request method originally sent by the user.
 *
 * @package    mojavi
 * @subpackage request
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: Request.class.php 640 2004-12-10 14:10:30Z seank $
 */
abstract class Request extends ParameterHolder
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    /**
     * Process validation and execution for only GET requests.
     *
     * @since 3.0.0
     */
    const GET = 2;

    /**
     * Skip validation and execution for any request method.
     *
     * @since 3.0.0
     */
    const NONE = 1;

    /**
     * Process validation and execution for only POST requests.
     *
     * @since 3.0.0
     */
    const POST = 4;

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $attributes = array(),
        $errors     = array(),
        $method     = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Clear all attributes associated with this request.
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
     * Extract parameter values from the request.
     *
     * @param array An indexed array of parameter names to extract.
     *
     * @return array An associative array of parameters and their values. If
     *               a specified parameter doesn't exist an empty string will
     *               be returned for its value.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function & extractParameters ($names)
    {

        $array = array();

        foreach ($this->parameters as $key => &$value)
        {

            if (in_array($key, $names))
            {

                $array[$key] =& $value;

            }

        }

        return $array;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an attribute.
     *
     * @param string An attribute name.
     *
     * @return mixed An attribute value, if the attribute exists, otherwise
     *               null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function & getAttribute ($name)
    {

        $retval = null;

        if (isset($this->attributes[$name]))
        {

            return $this->attributes[$name];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an array of attribute names.
     *
     * @return array An indexed array of attribute names.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getAttributeNames ()
    {

        return array_keys($this->attributes);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an error message.
     *
     * @param string An error name.
     *
     * @return string An error message, if the error exists, otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getError ($name)
    {

        $retval = null;

        if (isset($this->errors[$name]))
        {

            $retval = $this->errors[$name];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an array of error names.
     *
     * @return array An indexed array of error names.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getErrorNames ()
    {

        return array_keys($this->errors);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an array of errors.
     *
     * @return array An associative array of errors.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getErrors ()
    {

        return $this->errors;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve this request's method.
     *
     * @return int One of the following constants:
     *             - Request::GET
     *             - Request::POST
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getMethod ()
    {

        return $this->method;

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not an attribute exists.
     *
     * @param string An attribute name.
     *
     * @return bool true, if the attribute exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function hasAttribute ($name)
    {

        return isset($this->attributes[$name]);

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not an error exists.
     *
     * @param string An error name.
     *
     * @return bool true, if the error exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function hasError ($name)
    {

        return isset($this->errors[$name]);

    }


    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not any errors exist.
     *
     * @return bool true, if any error exist, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  2.0.0
     */
    public function hasErrors ()
    {

        return (count($this->errors) > 0);

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this Request.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Request.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function initialize ($context, $parameters = null);

    // -------------------------------------------------------------------------

    /**
     * Retrieve a new Request implementation instance.
     *
     * @param string A Request implementation name.
     *
     * @return Request A Request implementation instance.
     *
     * @throws <b>FactoryException</b> If a request implementation instance
     *                                 cannot be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function newInstance ($class)
    {

        // the class exists
        $object = new $class();

        if (!($object instanceof Request))
        {

            // the class name is of the wrong type
            $error = 'Class "%s" is not of the type Request';
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
     *
     * @return mixed An attribute value, if the attribute was removed,
     *               otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function & removeAttribute ($name)
    {

        $retval = null;

        if (isset($this->attributes[$name]))
        {

            $retval =& $this->attributes[$name];

            unset($this->attributes[$name]);

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Remove an error.
     *
     * @param string An error name.
     *
     * @return string An error message, if the error was removed, otherwise
     *                null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function & removeError ($name)
    {

        $retval = null;

        if (isset($this->errors[$name]))
        {

            $retval =& $this->errors[$name];

            unset($this->errors[$name]);

        }

        return $retval;

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
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setAttribute ($name, $value)
    {

        $this->attributes[$name] = $value;

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
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setAttributeByRef ($name, &$value)
    {

        $this->attributes[$name] =& $value;

    }

    // -------------------------------------------------------------------------

    /**
     * Set an array of attributes.
     *
     * If an existing attribute name matches any of the keys in the supplied
     * array, the associated value will be overridden.
     *
     * @param array An associative array of attributes and their associated
     *              values.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setAttributes ($attributes)
    {

        $this->attributes = array_merge($this->attributes, $attributes);

    }

    // -------------------------------------------------------------------------

    /**
     * Set an array of attributes by reference.
     *
     * If an existing attribute name matches any of the keys in the supplied
     * array, the associated value will be overridden.
     *
     * @param array An associative array of attributes and references to their
     *              associated values.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setAttributesByRef (&$attributes)
    {

        foreach ($attributes as $key => &$value)
        {

            $this->attributes[$key] =& $value;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Set an error.
     *
     * @param name    An error name.
     * @param message An error message.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setError ($name, $message)
    {

        $this->errors[$name] = $message;

    }


    // -------------------------------------------------------------------------

    /**
     * Set an array of errors
     *
     * If an existing error name matches any of the keys in the supplied
     * array, the associated message will be overridden.
     *
     * @param array An associative array of errors and their associated
     *              messages.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  2.0.0
     */
    public function setErrors ($errors)
    {

        $this->errors = array_merge($this->errors, $errors);

    }

    // -------------------------------------------------------------------------

    /**
     * Set the request method.
     *
     * @param int One of the following constants:
     *            - Request::GET
     *            - Request::POST
     *
     * @return void
     *
     * @throws <b>MojaviException</b> - If the specified request method is
     *                                  invalid.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  2.0.0
     */
    public function setMethod ($method)
    {

        if ($method == self::GET || $method == self::POST)
        {

            $this->method = $method;

            return;

        }

        // invalid method type
        $error = 'Invalid request method: %s';
        $error = sprintf($error, $method);

        throw new MojaviException($error);

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
    abstract function shutdown ();

}

?>