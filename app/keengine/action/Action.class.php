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
 * Action allows you to separate application and business logic from your
 * presentation. By providing a core set of methods used by the framework,
 * automation in the form of security and validation can occur.
 *
 * @package    laiketui
 * @subpackage action
 *
 */
abstract class Action extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+
    
    private
        $context = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Execute any application/business logic for this action.
     *
     * In a typical database-driven application, execute() handles application
     * logic itself and then proceeds to create a model instance. Once the model
     * instance is initialized it handles all business logic for the action.
     *
     * A model should represent an entity in your application. This could be a
     * user account, a shopping cart, or even a something as simple as a
     * single product.
     *
     * @return mixed A string containing the view name associated with this
     *               action.
     *
     *               Or an array with the following indices:
     *
     *               - The parent module of the view that will be executed.
     *               - The view that will be executed.
     *
     */
    abstract function execute ();

    // -------------------------------------------------------------------------

    /**
     * Retrieve the current application context.
     *
     * @return Context The current Context instance.
     *
     */
    public final function getContext ()
    {

        return $this->context;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the credential required to access this action.
     *
     * @return mixed Data that indicates the level of security for this action.
     *
     */
    public function getCredential ()
    {

        return null;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the default view to be executed when a given request is not
     * served by this action.
     *
     * @return mixed A string containing the view name associated with this
     *               action.
     *
     *               Or an array with the following indices:
     *
     *               - The parent module of the view that will be executed.
     *               - The view that will be executed.
     *
     */
    public function getDefaultView ()
    {

        return View::INPUT;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the request methods on which this action will process
     * validation and execution.
     *
     * @return int One of the following values:
     *
     *             - Request::GET
     *             - Request::POST
     *             - Request::NONE
     *
     */
    public function getRequestMethods ()
    {

        return Request::GET | Request::POST;

    }

    // -------------------------------------------------------------------------

    /**
     * Execute any post-validation error application logic.
     *
     * @return mixed A string containing the view name associated with this
     *               action.
     *
     *               Or an array with the following indices:
     *
     *               - The parent module of the view that will be executed.
     *               - The view that will be executed.
     *
     */
    public function handleError ()
    {

        return View::ERROR;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this action.
     *
     * @param Context The current application context.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     */
    public function initialize ($context)
    {

        $this->context = $context;

        return true;

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates that this action requires security.
     *
     * @return bool true, if this action requires security, otherwise false.
     *
     */
    public function isSecure ()
    {

        return false;

    }

    // -------------------------------------------------------------------------

    /**
     * Manually register validators for this action.
     *
     * @param ValidatorManager A ValidatorManager instance.
     *
     * @return void
     *
     */
    public function registerValidators ($validatorManager)
    {

    }

    // -------------------------------------------------------------------------

    /**
     * Manually validate files and parameters.
     *
     * @return bool true, if validation completes successfully, otherwise false.
     *
     */
    public function validate ()
    {

        return true;

    }

}

?>