<?php

// +---------------------------------------------------------------------------+
// | Should we run the system in debug mode? When this is on, there may be     |
// | various side-effects. But for the time being it only deletes the cache    |
// | upon start-up.                                                            |
// |                                                                           |
// | This should stay on while you're developing your application, because     |
// | many errors can stem from the fact that you're using an old cache file.   |
// +---------------------------------------------------------------------------+
define('MO_DEBUG', false);

// +---------------------------------------------------------------------------+
// | The PHP error reporting level.                                            |
// |                                                                           |
// | See: http://www.php.net/error_reporting                                   |
// +---------------------------------------------------------------------------+
define('MO_ERROR_REPORTING', E_ALL | E_STRICT);

// +---------------------------------------------------------------------------+
// | An absolute filesystem path to the mojavi package. This directory         |
// | contains all the Mojavi packages.                                         |
// +---------------------------------------------------------------------------+
define('MO_APP_DIR', dirname(dirname(dirname(__FILE__))).'/mojavi');

// +---------------------------------------------------------------------------+
// | An absolute filesystem path to your web application directory. This       |
// | directory is the root of your web application, which includes the core    |
// | configuration files and related web application data.                     |
// +---------------------------------------------------------------------------+
define('MO_WEBAPP_DIR', dirname(__FILE__));

// +---------------------------------------------------------------------------+
// | An absolute filesystem path to the directory where cache files will be    |
// | stored.                                                                   |
// |                                                                           |
// | NOTE: If you're going to use a public temp directory, make sure this is a |
// |       sub-directory of the temp directory. The cache system will attempt  |
// |       to clean up *ALL* data in this directory.                           |
// +---------------------------------------------------------------------------+
define('MO_CACHE_DIR', MO_WEBAPP_DIR . '/_cache');

// Smarty
define('SMARTY_COMPILE_DIR', MO_WEBAPP_DIR . "/_compile/");

?>
