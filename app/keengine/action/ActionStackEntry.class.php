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
 * ActionStackEntry represents information relating to a single Action request
 * during a single HTTP request.
 *
 * @package    laiketui
 * @subpackage action
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class ActionStackEntry extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+
    
    private
        $actionInstance = null,
        $actionName     = null,
        $microtime      = null,
        $moduleName     = null,
        $presentation   = null;
    
    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+
    
    /**
     * Class constructor.
     *
     * @param string A module name.
     * @param string An action name.
     * @param Action An action implementation instance.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function __construct ($moduleName, $actionName, $actionInstance)
    {
        
        $this->actionName     = $actionName;
        $this->actionInstance = $actionInstance;
        $this->microtime      = microtime();
        $this->moduleName     = $moduleName;
        
    }
    
    /**
     * Retrieve this entry's action name.
     *
     * @return string An action name.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getActionName ()
    {
        
        return $this->actionName;
    
    }
    
    /**
     * Retrieve this entry's action instance.
     *
     * @return Action An action implementation instance.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getActionInstance ()
    {
        
        return $this->actionInstance;
    
    }
    
    /**
     * Retrieve this entry's microtime.
     *
     * @return string A string representing the microtime this entry was
     *                created.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getMicrotime ()
    {
        
        return $this->microtime;
    
    }
    
    /**
     * Retrieve this entry's module name.
     *
     * @return string A module name.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getModuleName ()
    {
        
        return $this->moduleName;
    
    }
    
    /**
     * Retrieve this entry's rendered view presentation.
     *
     * This will only exist if the view has processed and the render mode
     * is set to View::RENDER_VAR.
     *
     * @return string An action name.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function & getPresentation ()
    {
        
        return $this->presentation;
    
    }

    /**
     * Set the rendered presentation for this action.
     *
     * @param string A rendered presentation.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function setPresentation (&$presentation)    
    {
        
        $this->presentation =& $presentation;

    }

}

?>
