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
 * SecurityUser provides advanced security manipulation methods.
 *
 * @package    mojavi
 * @subpackage user
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: SecurityUser.class.php 690 2004-12-21 20:47:50Z seank $
 */
abstract class SecurityUser extends User
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Add a credential to this user.
     *
     * @param mixed Credential data.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function addCredential ($credential);

    // -------------------------------------------------------------------------

    /**
     * Clear all credentials associated with this user.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function clearCredentials ();

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not this user has a credential.
     *
     * @param mixed Credential data.
     *
     * @return bool true, if this user has the credential, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function hasCredential ($credential);

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not this user is authenticated.
     *
     * @return bool true, if this user is authenticated, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function isAuthenticated ();

    // -------------------------------------------------------------------------

    /**
     * Remove a credential from this user.
     *
     * @param mixed Credential data.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function removeCredential ($credential);

    // -------------------------------------------------------------------------

    /**
     * Set the authenticated status of this user.
     *
     * @param bool A flag indicating the authenticated status of this user.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function setAuthenticated ($authenticated);

}

?>