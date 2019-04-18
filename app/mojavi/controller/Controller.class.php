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
 * Controller directs application flow.
 *
 * @package    mojavi
 * @subpackage controller
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: Controller.class.php 777 2005-08-17 15:26:07Z seank $
 */
abstract class Controller extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $actionStack     = null,
        $context         = null,
        $databaseManager = null,
        $maxForwards     = 20,
        $renderMode      = View::RENDER_CLIENT,
        $request         = null,
        $securityFilter  = null,
        $storage         = null,
        $user            = null;

    private static
        $instance = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Indicates whether or not a module has a specific action.
     *
     * @param string A module name.
     * @param string An action name.
     *
     * @return bool true, if the action exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function actionExists ($moduleName, $actionName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/actions/' . $actionName .
                'Action.class.php';

        return is_readable($file);

    }

    // -------------------------------------------------------------------------

    /**
     * Forward the request to another action.
     *
     * @param string A module name.
     * @param string An action name.
     *
     * @return void
     *
     * @throws <b>ConfigurationException</b> If an invalid configuration setting
     *                                       has been found.
     * @throws <b>ForwardException</b> If an error occurs while forwarding the
     *                                 request.
     * @throws <b>InitializationException</b> If the action could not be
     *                                        initialized.
     * @throws <b>SecurityException</b> If the action requires security but
     *                                  the user implementation is not of type
     *                                  SecurityUser.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function forward ($moduleName, $actionName)
    {

        // replace periods with slashes for action sub-directories
        $actionName = str_replace('.', '/', $actionName);

        // replace unwanted characters
        $moduleName = preg_replace('/[^a-z0-9\-_]+/i', '', $moduleName);
        $actionName = preg_replace('/[^a-z0-9\-_\/]+/i', '', $actionName);

        if ($this->actionStack->getSize() >= $this->maxForwards)
        {

            // let's kill this party before it turns into cpu cycle hell
            $error = 'Too many forwards have been detected for this request';

            throw new ForwardException($error);

        }

        if (!MO_AVAILABLE)
        {

            // application is unavailable
            $moduleName = MO_UNAVAILABLE_MODULE;
            $actionName = MO_UNAVAILABLE_ACTION;

            if (!$this->actionExists($moduleName, $actionName))
            {

                // cannot find unavailable module/action
                $error = 'Invalid configuration settings: ' .
                         'MO_UNAVAILABLE_MODULE "%s", ' .
                         'MO_UNAVAILABLE_ACTION "%s"';

                $error = sprintf($error, $moduleName, $actionName);

                throw new ConfigurationException($error);

            }

        } else if (!$this->actionExists($moduleName, $actionName))
        {

            // the requested action doesn't exist

            // track the requested module so we have access to the data
            // in the error 404 page
            $this->request->setAttribute('requested_action', $actionName);
            $this->request->setAttribute('requested_module', $moduleName);

            // switch to error 404 action
            $moduleName = MO_ERROR_404_MODULE;
            $actionName = MO_ERROR_404_ACTION;

            if (!$this->actionExists($moduleName, $actionName))
            {

                // cannot find unavailable module/action
                $error = 'Invalid configuration settings: ' .
                         'MO_ERROR_404_MODULE "%s", ' .
                         'MO_ERROR_404_ACTION "%s"';

                $error = sprintf($error, $moduleName, $actionName);

                throw new ConfigurationException($error);

            }

        }

        // create an instance of the action
        $actionInstance = $this->getAction($moduleName, $actionName);

        // add a new action stack entry
        $this->actionStack->addEntry($moduleName, $actionName,
                                     $actionInstance);

        // include the module configuration
        ConfigCache::import('modules/' . $moduleName . '/config/module.ini');

        if (constant('MOD_' . strtoupper($moduleName) . '_ENABLED'))
        {

            // module is enabled

            // check for a module config.php
            $moduleConfig = MO_MODULE_DIR . '/' . $moduleName . '/config.php';

            if (is_readable($moduleConfig))
            {

                require_once($moduleConfig);

            }

            // initialize the action
            if ($actionInstance->initialize($this->context))
            {

                // create a new filter chain
                $filterChain = new FilterChain();

                if (MO_AVAILABLE)
                {

                    // the application is available so we'll register
                    // global and module filters, otherwise skip them

                    // does this action require security?
                    if (MO_USE_SECURITY && $actionInstance->isSecure())
                    {

                        if (!($this->user instanceof SecurityUser))
                        {

                            // we've got security on but the user implementation
                            // isn't a sub-class of SecurityUser
                            $error = 'Security is enabled, but your User ' .
                                     'implementation isn\'t a sub-class of ' .
                                     'SecurityUser';

                            throw new SecurityException($error);

                        }

                        // register security filter
                        $filterChain->register($this->securityFilter);

                    }

                    // load filters
                    $this->loadGlobalFilters($filterChain);
                    $this->loadModuleFilters($filterChain);

                }

                // register the execution filter
                $execFilter = new ExecutionFilter();

                $execFilter->initialize($this->context);
                $filterChain->register($execFilter);

                if ($moduleName == MO_ERROR_404_MODULE &&
                    $actionName == MO_ERROR_404_ACTION)
                {

                    header('HTTP/1.0 404 Not Found');
                    header('Status: 404 Not Found');
                                                
                }
                
                // process the filter chain
                $filterChain->execute();

            } else
            {

                // action failed to initialize
                $error = 'Action initialization failed for module "%s", ' .
                         'action "%s"';

                $error = sprintf($error, $moduleName, $actionName);

                throw new InitializationException($error);

            }

        } else
        {

            // module is disabled
            $moduleName = MO_MODULE_DISABLED_MODULE;
            $actionName = MO_MODULE_DISABLED_ACTION;

            if (!$this->actionExists($moduleName, $actionName))
            {

                // cannot find mod disabled module/action
                $error = 'Invalid configuration settings: ' .
                         'MO_MODULE_DISABLED_MODULE "%s", ' .
                         'MO_MODULE_DISABLED_ACTION "%s"';

                $error = sprintf($error, $moduleName, $actionName);

                throw new ConfigurationException($error);

            }

            $this->forward($moduleName, $actionName);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve an Action implementation instance.
     *
     * @param string A module name.
     * @param string An action name.
     *
     * @return Action An Action implementation instance, if the action exists,
     *                otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getAction ($moduleName, $actionName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/actions/' . $actionName .
                'Action.class.php';

        require_once($file);

        $position = strrpos($actionName, '/');

        if ($position > -1)
        {

            $actionName = substr($actionName, $position + 1);

        }

        $class = $actionName . 'Action';

        // fix for same name classes
        $moduleClass = $moduleName . '_' . $class;

        if (class_exists($moduleClass, false))
        {

            $class = $moduleClass;

        }

        return new $class();

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the action stack.
     *
     * @return ActionStack An ActionStack instance, if the action stack is
     *                     enabled, otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getActionStack ()
    {

        return $this->actionStack;

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
     * Retrieve a global Model implementation instance.
     *
     * @param string A model name.
     *
     * @return Model A Model implementation instance, if the model exists,
     *               otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getGlobalModel ($modelName)
    {

        $file = MO_LIB_DIR . '/models/' . $modelName . 'Model.class.php';

        require_once($file);

        $class = $modelName . 'Model';

        // create model instance and initialize it
        $model = new $class();
        $model->initialize($this->context);

        return $model;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the singleton instance of this class.
     *
     * @return Controller A Controller implementation instance.
     *
     * @throws <b>ControllerException</b> If a controller implementation
     *                                    instance has not been created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function getInstance ()
    {

        if (isset(self::$instance))
        {

            return self::$instance;

        }

        // an instance of the controller has not been created
        $error = 'A Controller implementation instance has not been created';

        throw new ControllerException($error);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a Model implementation instance.
     *
     * @param string A module name.
     * @param string A model name.
     *
     * @return Model A Model implementation instance, if the model exists,
     *               otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getModel ($moduleName, $modelName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/models/' . $modelName .
                'Model.class.php';

        require_once($file);

        $class = $modelName . 'Model';

        // fix for same name classes
        $moduleClass = $moduleName . '_' . $class;

        if (class_exists($moduleClass, false))
        {

            $class = $moduleClass;

        }

        // create model instance and initialize it
        $model = new $class();
        $model->initialize($this->context);

        return $model;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the presentation rendering mode.
     *
     * @return int One of the following:
     *             - View::RENDER_CLIENT
     *             - View::RENDER_VAR
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getRenderMode ()
    {

        return $this->renderMode;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a View implementation instance.
     *
     * @param string A module name.
     * @param string A view name.
     *
     * @return View A View implementation instance, if the model exists,
     *              otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getView ($moduleName, $viewName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/views/' . $viewName .
                'View.class.php';

        require_once($file);

        $position = strrpos($viewName, '/');

        if ($position > -1)
        {

            $viewName = substr($viewName, $position + 1);

        }

        $class = $viewName . 'View';

        // fix for same name classes
        $moduleClass = $moduleName . '_' . $class;

        if (class_exists($moduleClass, false))
        {

            $class = $moduleClass;

        }

        return new $class();

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this controller.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    protected function initialize ()
    {

        if (MO_USE_DATABASE)
        {

            // setup our database connections
            $this->databaseManager = new DatabaseManager();
            $this->databaseManager->initialize();

        }

        // create a new action stack
        $this->actionStack = new ActionStack();

        // create factory implementation instances
        $config = ConfigCache::checkConfig('config/factories.ini');

        // include the factories configuration
        require_once($config);

        // register our shutdown function
        register_shutdown_function(array($this, 'shutdown'));

        // $this->context is created in the factories configuration up above

        // TODO: logging setup

        // set max forwards
        if (defined('MO_MAX_FORWARDS'))
        {

            $this->maxForwards = MO_MAX_FORWARDS;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Load global filters.
     *
     * @param FilterChain A FilterChain instance.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    private function loadGlobalFilters ($filterChain)
    {

        static $list = array();

        // grab our global filter ini and preset the module name
        $config     = MO_CONFIG_DIR . '/filters.ini';
        $moduleName = 'global';

        if (!isset($list[$moduleName]) && is_readable($config))
        {

            // load global filters
            require_once(ConfigCache::checkConfig('config/filters.ini'));

        }

        // register filters
        foreach ($list[$moduleName] as $filter)
        {

            $filterChain->register($filter);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Load module filters.
     *
     * @param FilterChain A FilterChain instance.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    private function loadModuleFilters ($filterChain)
    {

        // filter list cache file
        static $list = array();

        // get the module name
        $moduleName = $this->context->getModuleName();

        if (!isset($list[$moduleName]))
        {

            // we haven't loaded a filter list for this module yet
            $config = MO_MODULE_DIR . '/' . $moduleName . '/config/filters.ini';

            if (is_readable($config))
            {

                require_once(ConfigCache::checkConfig($config));

            } else
            {

                // add an emptry array for this module since no filters
                // exist
                $list[$moduleName] = array();

            }

        }

        // register filters
        foreach ($list[$moduleName] as $filter)
        {

            $filterChain->register($filter);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not a module has a specific model.
     *
     * @param string A module name.
     * @param string A model name.
     *
     * @return bool true, if the model exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function modelExists ($moduleName, $modelName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/models/' . $modelName .
                'Model.class.php';

        return is_readable($file);

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not a module exists.
     *
     * @param string A module name.
     *
     * @return bool true, if the module exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function moduleExists ($moduleName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/config/module.ini';

        return is_readable($file);

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a new Controller implementation instance.
     *
     * @param string A Controller implementation name.
     *
     * @return Controller A Controller implementation instance.
     *
     * @throws <b>FactoryException</b> If a new controller implementation
     *                                 instance cannot be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function newInstance ($class)
    {

        try
        {

            if (!isset(self::$instance))
            {

                // the class exists
                $object = new $class();

                if (!($object instanceof Controller))
                {

                    // the class name is of the wrong type
                    $error = 'Class "%s" is not of the type Controller';
                    $error = sprintf($error, $class);

                    throw new FactoryException($error);

                }

                // set our singleton instance
                self::$instance = $object;

                return $object;

            } else
            {

                $type = get_class(self::$instance);

                // an instance has already been created
                $error = 'A Controller implementation instance has already ' .
                         'been created';

                throw new FactoryException($error);

            }

        } catch (MojaviException $e)
        {

            $e->printStackTrace();

        } catch (Exception $e)
        {

            // most likely an exception from a third-party library
            $e = new MojaviException($e->getMessage());

            $e->printStackTrace();

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Set the presentation rendering mode.
     *
     * @param int A rendering mode.
     *
     * @return void
     *
     * @throws <b>RenderException</b> - If an invalid render mode has been set.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  2.0.0
     */
    public function setRenderMode ($mode)
    {

        if ($mode == View::RENDER_CLIENT || $mode == View::RENDER_VAR ||
            $mode == View::RENDER_NONE)
        {

            $this->renderMode = $mode;

            return;

        }

        // invalid rendering mode type
        $error = 'Invalid rendering mode: %s';
        $error = sprintf($error, $mode);

        throw new RenderException($error);

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

        $this->user->shutdown();

        session_write_close();

        $this->storage->shutdown();
        $this->request->shutdown();

        if (MO_USE_DATABASE)
        {

            $this->databaseManager->shutdown();

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not a module has a specific view.
     *
     * @param string A module name.
     * @param string A view name.
     *
     * @return bool true, if the view exists, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function viewExists ($moduleName, $viewName)
    {

        $file = MO_MODULE_DIR . '/' . $moduleName . '/views/' . $viewName .
                'View.class.php';

        return is_readable($file);

    }

}

?>
