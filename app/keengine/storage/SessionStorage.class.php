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
 * SessionStorage allows you to store persistent Mojavi data in the user
 * session.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>auto_start</b>   - [Yes]    - Should session_start() automatically be
 *                                    called?
 * # <b>session_name</b> - [Mojavi] - The name of the session.
 *
 * @package    laiketui
 * @subpackage storage
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class SessionStorage extends Storage
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Initialize this Storage.
     *
     * @param Context A Context instance.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @throws <b>InitializationException</b> If an error occurs while
     *                                        initializing this Storage.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        // initialize parent
        parent::initialize($context, $parameters);

        // set session name
        $sessionName = $this->getParameter('session_name', 'Mojavi');

        session_name($sessionName);

        if ($this->getParameter('auto_start', true))
        {

            // start our session
            session_start();

        }

        $this->over();


    }

    // -------------------------------------------------------------------------

    /**
     * Read data from this storage.
     *
     * The preferred format for a key is directory style so naming conflicts can
     * be avoided.
     *
     * @param string A unique key identifying your data.
     *
     * @return mixed Data associated with the key.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function & read ($key)
    {

        $retval = null;

        if (isset($_SESSION[$key]))
        {

            $retval =& $_SESSION[$key];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Remove data from this storage.
     *
     * The preferred format for a key is directory style so naming conflicts can
     * be avoided.
     *
     * @param string A unique key identifying your data.
     *
     * @return mixed Data associated with the key.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function & remove ($key)
    {

        $retval = null;

        if (isset($_SESSION[$key]))
        {

            $retval =& $_SESSION[$key];

            unset($_SESSION[$key]);

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function shutdown ()
    {

        // don't need a shutdown procedure because read/write do it in real-time

    }

    // -------------------------------------------------------------------------

    /**
     * Execute the over procedure.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function over ()
    {
        if (!isset($_SESSION['LAI_KE'])) {

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Write data to this storage.
     *
     * The preferred format for a key is directory style so naming conflicts can
     * be avoided.
     *
     * @param string A unique key identifying your data.
     * @param mixed  Data associated with your key.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function write ($key, &$data)
    {

        $_SESSION[$key] =& $data;

    }

}

?>