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
 * BasicSecurityUser will handle any type of data as a credential.
 *
 * @package    mojavi
 * @subpackage user
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: BasicSecurityUser.class.php 690 2004-12-21 20:47:50Z seank $
 */
class BasicSecurityUser extends SecurityUser
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    /**
     * The namespace under which authenticated status will be stored.
     */
    const AUTH_NAMESPACE = 'org/mojavi/user/BasicSecurityUser/authenticated';

    /**
     * The namespace under which credentials will be stored.
     */
    const CREDENTIAL_NAMESPACE = 'org/mojavi/user/BasicSecurityUser/credentials';

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $authenticated = null,
        $credentials   = null;

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
    public function addCredential ($credential)
    {

        if (!in_array($credential, $this->credentials))
        {

            $this->credentials[] = $credential;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Clear all credentials associated with this user.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function clearCredentials ()
    {

        $this->credentials = null;
        $this->credentials = array();

    }

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
    public function hasCredential ($credential)
    {

        return (in_array($credential, $this->credentials));

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this User.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this User.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        // initialize parent
        parent::initialize($context, $parameters);

        // read data from storage
        $storage = $this->getContext()->getStorage();

        $this->authenticated = $storage->read(self::AUTH_NAMESPACE);
        $this->credentials   = $storage->read(self::CREDENTIAL_NAMESPACE);

        if ($this->authenticated == null)
        {

            // initialize our data
            $this->authenticated = false;
            $this->credentials   = array();

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates whether or not this user is authenticated.
     *
     * @return bool true, if this user is authenticated, otherwise false.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function isAuthenticated ()
    {

        return $this->authenticated;

    }

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
    public function removeCredential ($credential)
    {

        if ($this->hasCredential($credential))
        {

            // we have the credential, now we have to find it
            // let's not foreach here and do exact instance checks
            // for future safety
            for ($i = 0, $z = count($this->credentials); $i < $z; $i++)
            {

                if ($credential == $this->credentials[$i])
                {

                    // found it, let's nuke it
                    unset($this->credentials[$i]);
                    return;

                }

            }

        }

    }

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
    public function setAuthenticated ($authenticated)
    {

        if ($authenticated === true)
        {

            $this->authenticated = true;

            return;

        }

        $this->authenticated = false;

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

        $storage = $this->getContext()
                        ->getStorage();

        // write credentials to the storage
        $storage->write(self::AUTH_NAMESPACE,       $this->authenticated);
        $storage->write(self::CREDENTIAL_NAMESPACE, $this->credentials);

        // call the parent shutdown method
        parent::shutdown();

    }

}

?>