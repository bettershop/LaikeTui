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
 * ValidatorManager provides management for request parameters and their
 * associated validators.
 *
 * @package    mojavi
 * @subpackage validator
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: ValidatorManager.class.php 656 2004-12-14 20:39:21Z seank $
 */
class ValidatorManager extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $groups  = array(),
        $names   = array(),
        $request = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Clear this validator manager so it can be reused.
     *
     * @retun void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function clear ()
    {

        $this->groups = null;
        $this->groups = array();
        $this->names  = null;
        $this->names  = array();

    }

    // -------------------------------------------------------------------------

    /**
     * Execute all validators and determine the validation status.
     *
     * @return bool true, if validation completed successfully, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function execute ()
    {

        $retval = true;

        // loop through the names and start our validation
        // if 1 or more groups exist, we'll have to do a second pass
        $pass = 1;

        while (true)
        {

            foreach ($this->names as $name => &$data)
            {

                if (isset($data['_is_parent']))
                {

                    // this is a parent
                    foreach ($data as $subname => &$subdata)
                    {

                        if ($subname == '_is_parent')
                        {

                            // this isn't an actual index, but more of a flag
                            continue;

                        }

                        if ($subdata['validation_status'] == true &&
                            !$this->validate($subname, $subdata, $name))
                        {

                            // validation failed
                            $retval = false;

                        }

                    }

                } else
                {

                    // single parameter
                    if ($data['validation_status'] == true &&
                        !$this->validate($name, $data, null))
                    {

                        // validation failed
                        $retval = false;

                    }

                }

            }

            if (count($this->groups) == 0 || $pass == 2)
            {

                break;

            }

            // increase our pass indicator
            $pass++;

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this validator manager.
     *
     * @param Context A context instance.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function initialize ($context)
    {

        $this->request = $context->getRequest();

    }

    // -------------------------------------------------------------------------

    /**
     * Register a file or parameter.
     *
     * @param string  A file or parameter name.
     * @param bool    The required status.
     * @param string  A required error message.
     * @param string  A group name.
     * @param string  A parent array.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function registerName ($name, $required = true,
                                  $message = 'Required', $parent = null,
                                  $group = null, $isFile = false)
    {

        // create the entry
        $entry                      = array();
        $entry['group']             = null;
        $entry['is_file']           = $isFile;
        $entry['required']          = $required;
        $entry['required_msg']      = $message;
        $entry['validation_status'] = true;
        $entry['validators']        = array();

        if ($parent != null)
        {

            // this parameter has a parent array
            if (!isset($this->names[$parent]))
            {

                // create the parent array
                $this->names[$parent] = array('_is_parent' => true);

            }

            // register this parameter
            $this->names[$parent][$name] =& $entry;

        } else
        {

            // no parent

            // register this parameter
            $this->names[$name] =& $entry;

        }

        if ($group != null)
        {

            // set group
            if (!isset($this->groups[$group]))
            {

                // create our group
                $this->groups[$group] = array('_force' => false);

            }

            // add this file/parameter name to the group
            $this->groups[$group][] = $name;

            // add a reference back to the group array to the file/param array
            $entry['group'] =& $this->groups[$group];

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Register a validator for a file or parameter.
     *
     * @param string    A file or parameter name.
     * @param Validator A validator implementation instance.
     * @param string    A parent array name.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function registerValidator ($name, $validator, $parent = null)
    {

        if ($parent != null)
        {

            // this parameter has a parent
            $this->names[$parent][$name]['validators'][] = $validator;

        } else
        {

            // no parent
            $this->names[$name]['validators'][] = $validator;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Validate a file or parameter.
     *
     * @param string A file or parameter name.
     * @param array  Data associated with the file or parameter.
     * @param string A parent name.
     *
     * @return bool true, if validation completes successfully, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    private function validate (&$name, &$data, $parent)
    {

        // get defaults
        $error     = null;
        $errorName = null;
        $force     = ($data['group'] != null) ? $data['group']['_force'] : true;
        $retval    = true;
        $value     = null;

        // get our parameter value
        if ($parent == null)
        {

            // normal file/parameter
            $errorName = $name;

            if ($data['is_file'])
            {

                // file
                $value =& $this->request->getFile($name);

            } else
            {

                // parameter
                $value =& $this->request->getParameter($name);

            }

        } else
        {

            // we have a parent
            $errorName = $parent . '{' . $name . '}';

            if ($data['is_file'])
            {

                // file
                $parent =& $this->request->getFile($parent);

            } else
            {

                // parameter
                $parent =& $this->request->getParameter($parent);

            }

            if ($parent != null && isset($parent[$name]))
            {

                $value =& $parent[$name];

            }

        }

        // now for the dirty work
        if ($value == null || strlen($value) == 0)
        {

            if (!$data['required'] || !$force)
            {

                // we don't have to validate it
                $retval = true;

            } else
            {

                // it's empty!
                $error  = $data['required_msg'];
                $retval = false;

            }

        } else
        {

            // time for the fun
            $error = null;

            // get group force status
            if ($data['group'] != null)
            {

                // we set this because we do have a value for a parameter in
                // this group
                $data['group']['_force'] = true;
                $force                   = true;

            }

            if (count($data['validators']) > 0)
            {

                // loop through our validators
                foreach ($data['validators'] as $validator)
                {

                    if (!$validator->execute($value, $error))
                    {

                        $retval = false;

                    }

                }

            }

        }

        if (!$retval)
        {

            // set validation status
            $data['validation_status'] = false;

            // set the request error
            $this->request->setError($errorName, $error);

        }

        return $retval;

    }

}

?>