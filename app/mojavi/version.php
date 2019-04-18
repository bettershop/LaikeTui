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
 * Version initialization script.
 *
 * @package mojavi
 * 
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: version.php 91 2004-11-06 01:25:44Z seank $
 */

define('MO_APP_NAME',          'Mojavi');

define('MO_APP_MAJOR_VERSION', '3');

define('MO_APP_MINOR_VERSION', '0');

define('MO_APP_MICRO_VERSION', '0');

define('MO_APP_BRANCH',        'dev-3.0.0');

define('MO_APP_STATUS',        'DEV');

define('MO_APP_VERSION',       MO_APP_MAJOR_VERSION . '.' .
                               MO_APP_MINOR_VERSION . '.' .
                               MO_APP_MICRO_VERSION . '-' . MO_APP_STATUS);

define('MO_APP_URL',           'http://www.mojavi.org');

define('MO_APP_INFO',          MO_APP_NAME . ' ' . MO_APP_VERSION .
                               ' (' . MO_APP_URL . ')');

?>