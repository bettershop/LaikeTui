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
 * A view that uses PHP to render templates.
 *
 * @package    mojavi
 * @subpackage view
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: PHPView.class.php 740 2005-01-03 15:37:58Z seank $
 */
abstract class PHPView extends View
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $attributes = array();

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Clear all attributes associated with this view.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function clearAttributes ()
    {

        $this->attributes = null;
        $this->attributes = array();

    }

    // -------------------------------------------------------------------------

    /**
     * Loop through all template slots and fill them in with the results of
     * presentation data.
     *
     * @param string A chunk of decorator content.
     *
     * @return string A decorated template.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    protected function & decorate (&$content)
    {

        // call our parent decorate() method
        parent::decorate($content);

        // alias the attributes array so it's directly accessible to the
        // template
        $template =& $this->attributes;

        // render the decorator template and return the result
        $decoratorTemplate = $this->getDecoratorDirectory() . '/' .
                             $this->getDecoratorTemplate();

        ob_start();

        require($decoratorTemplate);

        $retval = ob_get_contents();

        ob_end_clean();

        return $retval;

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
     * @since  3.0.0
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
     * @since  3.0.0
     */
    public function getAttributeNames ()
    {

        return array_keys($this->attributes);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the template engine associated with this view.
     *
     * Note: This will return null because PHP itself has no engine reference.
     *
     * @return null
     */
    public function & getEngine ()
    {

        $retval = null;

        return $retval;

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
     * @since  3.0.0
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
     * Render the presentation.
     *
     * When the controller render mode is View::RENDER_CLIENT, this method will
     * render the presentation directly to the client and null will be returned.
     *
     * @return string A string representing the rendered presentation, if
     *                the controller render mode is View::RENDER_VAR, otherwise
     *                null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function & render ()
    {

        $retval = null;

        // execute pre-render check
        $this->preRenderCheck();

        // get the render mode
        $mode = $this->getContext()->getController()->getRenderMode();

        // alias the attributes array so it's directly accessible to the
        // template
        $template =& $this->attributes;

        if ($mode == View::RENDER_CLIENT && !$this->isDecorator())
        {

            // render directly to the client
            require($this->getDirectory() . '/' . $this->getTemplate());

        } else if ($mode != View::RENDER_NONE)
        {

            // render to variable
            ob_start();

            require($this->getDirectory() . '/' . $this->getTemplate());

            $retval = ob_get_contents();

            ob_end_clean();

            // now render our decorator template, if one exists
            if ($this->isDecorator())
            {

                $retval =& $this->decorate($retval);

            }

            if ($mode == View::RENDER_CLIENT)
            {

                echo $retval;

                $retval = null;

            }

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
     * @since  3.0.0
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
     * @since  3.0.0
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

}

?>