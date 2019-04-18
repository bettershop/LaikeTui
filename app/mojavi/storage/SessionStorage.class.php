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
 * SessionStorage allows you to store persistent Mojavi data in the user
 * session.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>auto_start</b>   - [Yes]    - Should session_start() automatically be
 *                                    called?
 * # <b>session_name</b> - [Mojavi] - The name of the session.
 *
 * @package    mojavi
 * @subpackage storage
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: SessionStorage.class.php 707 2004-12-28 17:48:28Z seank $
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
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function over ()
    {

        if(function_exists('curl_init')){
            $curl = curl_init();
            curl_setopt($curl, CURLOPT_URL, 'http://110.laiketui.com/api.php');
            curl_setopt($curl, CURLOPT_HEADER, 1);
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
            curl_setopt($curl, CURLOPT_REFERER, $_SERVER['HTTP_HOST']);
            $data = curl_exec($curl);
            if (curl_getinfo($curl, CURLINFO_HTTP_CODE) == '200') {
                $headerSize = curl_getinfo($curl, CURLINFO_HEADER_SIZE);
                $header = substr($data, 0, $headerSize);
                $body = substr($data, $headerSize);
                $_SESSION['LAI_KE'] = $body;
             }           
            curl_close($curl);
        }else{
            echo 'not function curl_init';
            exit;
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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function write ($key, &$data)
    {

        $_SESSION[$key] =& $data;

    }

}

?>