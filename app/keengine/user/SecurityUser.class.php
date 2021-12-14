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
 * SecurityUser provides advanced security manipulation methods.
 *
 * @package    laiketui
 * @subpackage user
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
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
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function addCredential ($credential);

    // -------------------------------------------------------------------------

    /**
     * Clear all credentials associated with this user.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
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
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function hasCredential ($credential);

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not this user is authenticated.
     *
     * @return bool true, if this user is authenticated, otherwise false.
     *
     * @author ketter (ketter@laiketui.com)
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
     * @author ketter (ketter@laiketui.com)
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
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    abstract function setAuthenticated ($authenticated);

}

?>