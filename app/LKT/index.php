<?php
if(!is_file('../data/install.lock')){
	echo "您还没有安装系统，请安装后重试！";exit;
}
date_default_timezone_set('Asia/Chongqing');
set_time_limit(7200);
// +---------------------------------------------------------------------------+
// | An absolute filesystem path to our webapp/config.php script.              |
// +---------------------------------------------------------------------------+
require_once(dirname(__FILE__)."/webapp/config.php");

// +---------------------------------------------------------------------------+
// | An absolute filesystem path to the mojavi/mojavi.php script.              |
// +---------------------------------------------------------------------------+
require_once(MO_APP_DIR."/mojavi.php");

// +---------------------------------------------------------------------------+
// | Create our controller. For this file we're going to use a front           |
// | controller pattern. This pattern allows us to specify module and action   |
// | GET/POST parameters and it automatically detects them and finds the       |
// | expected action.                                                          |
// +---------------------------------------------------------------------------+
$controller = Controller::newInstance('FrontWebController');

// +---------------------------------------------------------------------------+
// | Dispatch our request.                                                     |
// +---------------------------------------------------------------------------+
$controller->dispatch();
?> 