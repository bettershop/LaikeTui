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
 * CacheException is thrown when ConfigCache fails to execute properly.
 *
 * @package    mojavi
 * @subpackage exception
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: CacheException.class.php 65 2004-10-26 03:16:15Z seank $
 */
class CacheException extends MojaviException
{

    // +-----------------------------------------------------------------------+
    // | CONSTRUCTOR                                                           |
    // +-----------------------------------------------------------------------+

    /**
     * Class constructor.
     *
     * @param string The error message.
     * @param int    The error code.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function __construct ($message = null, $code = 0)
    {

        parent::__construct($message, $code);

        $this->setName('CacheException');

    }

}

?>