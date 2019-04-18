<?php
ini_set("display_errors", 0);
ini_set("error_reporting", FALSE);
require_once(MO_APP_DIR . "/smarty/Smarty.class.php");
ini_restore("error_reporting");
ini_restore("display_errors");

// +---------------------------------------------------------------------------+
// | This file is part of the Mojavi package.                                  |
// | Copyright (c) 2003, 2004 Sean Kerr.                                       |
// |                                                                           |
// | For the full copyright and license information, please view the LICENSE   |
// | file that was distributed with this source code. You can also view the    |
// | LICENSE file online at http://www.mojavi.org.                             |
// +---------------------------------------------------------------------------+

/**
 * $Id: SmartyView.class.php 65 2004-10-26 03:16:15Z seank $
 *
 *
 * @package    mojavi
 * @subpackage view
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Rev$
 */
abstract class SmartyView extends View
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | PUBLIC VARIABLES                                                      |
    // +-----------------------------------------------------------------------+

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+
    private $smarty;
    // +-----------------------------------------------------------------------+
    // | CONSTRUCTOR                                                           |
    // +-----------------------------------------------------------------------+
    public function __construct() {
        $this->smarty = new Smarty();
        $this->smarty->config_dir = MO_CONFIG_DIR;
        $this->smarty->cache_dir = MO_CACHE_DIR;
    }
    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+
    public function clearAttributes() {
        $this->smarty->clear_all_assign();
    }

    public function getAttributeNames() {
        return array_keys($this->smarty->get_template_vars());
    }

    public function &getAttribute($name) {
        return $this->smarty->get_template_vars($name);
    }

    public function &removeAttribute($name) {
        $retval = $this->smarty->get_template_vars($name);
        $this->smarty->clear_assign($name);
        return $retval;
    }

    public function setAttribute($name, $value) {
        $this->smarty->assign($name, $value);
    }

    public function setAttributeByRef($name, &$value) {
        $this->smarty->assign_by_ref($name, $value);
    }

    public function setAttributes($attributes) {
        $this->smarty->assign($attributes);
    }

    public function setAttributesByRef(&$attributes) {
        $this->smarty->assign_by_ref($attributes);
    }

    public function &getEngine () {
        return $this->smarty;
    }

    public function &render() {
        $retval = null;

        // execute pre-render check
        $this->preRenderCheck();

        // get the render mode
        $mode = $this->getContext()->getController()->getRenderMode();

        $module = $this->getContext()->getModuleName();

        $this->getEngine()->template_dir = $this->getDirectory();
        $this->getEngine()->compile_dir  = SMARTY_COMPILE_DIR . $module;

		if(!file_exists(SMARTY_COMPILE_DIR)) mkdir (SMARTY_COMPILE_DIR);
        if(!file_exists($this->getEngine()->compile_dir)) mkdir($this->getEngine()->compile_dir);
        if(!file_exists(MO_CACHE_DIR)) mkdir(MO_CACHE_DIR);
        if(!file_exists($this->getEngine()->cache_dir)) mkdir($this->getEngine()->cache_dir);

        if ($mode == View::RENDER_CLIENT) {
            $this->getEngine()->display($this->getTemplate());
        } elseif ($mode == View::RENDER_VAR) {
            $retval = $this->getEngine()->fetch($this->getTemplate());
        }
        return $retval;
    }
}

?>