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
 * WebController provides web specific methods to Controller such as, url
 * redirection.
 *
 * @package    laiketui
 * @subpackage controller
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
abstract class WebController extends Controller
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $contentType = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Generate a formatted Mojavi URL.
     *
     * @param string An existing URL for basing the parameters.
     * @param array  An associative array of URL parameters.
     *
     * @return string A URL to a Mojavi resource.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function genURL ($url = null, $parameters = array())
    {

        if ($url == null)
        {

            $url = $_SERVER['SCRIPT_NAME'];

        }

        if (MO_URL_FORMAT == 'PATH')
        {

            // use PATH format
            $divider  = '/';
            $equals   = '/';
            $url     .= '/';

        } else
        {

            // use GET format
            $divider  = '&';
            $equals   = '=';
            $url     .= '?';

        }

        // loop through the parameters
        foreach ($parameters as $key => &$value)
        {

            $url .= urlencode($key) . $equals . urlencode($value) . $divider;

        }

        // strip off last divider character
        $url = rtrim($url, $divider);

        // replace &'s with &amp;
        $url = str_replace('&', '&amp;', $url);

        return $url;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the requested content type.
     *
     * @return string A content type.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getContentType ()
    {

        return $this->contentType;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this controller.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    protected function initialize ()
    {

        // initialize parent
        parent::initialize();

        // set our content type
        $this->contentType = $this->getContext()
                                  ->getRequest()
                                  ->getParameter('ctype', MO_CONTENT_TYPE);

    }

    // -------------------------------------------------------------------------

    /**
     * Redirect the request to another URL.
     *
     * @param string An existing URL.
     * @param int    A delay in seconds before redirecting. This only works on
     *               browsers that do not support the PHP header.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function redirect ($url, $delay = 0)
    {

        // shutdown the controller
        $this->shutdown();

        // redirect
        /* delete to use output to client before redirect
		header('Location: ' . $url);
		*/

        $echo = '<html>' .
                '<head>' .
                '<meta http-equiv="refresh" content="%d;url=%s"/>' .
                '</head>' .
                '</html>';

        $echo = sprintf($echo, $delay, $url);

        echo $echo;

        exit;

    }

    // -------------------------------------------------------------------------

    /**
     * Set the content type for this request.
     *
     * @param string A content type.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function setContentType ($type)
    {

        $this->contentType = $type;

    }

}

?>