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
 * MojaviException is the base class for all Mojavi related exceptions and
 * provides an additional method for printing up a detailed view of an
 * exception.
 *
 * @package    mojavi
 * @subpackage exception
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: MojaviException.class.php 707 2004-12-28 17:48:28Z seank $
 */
class MojaviException extends Exception
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $name = null;

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

        $this->setName('MojaviException');

    }

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve the name of this exception.
     *
     * @return string This exception's name.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getName ()
    {

        return $this->name;

    }

    // -------------------------------------------------------------------------

    /**
     * Print the stack trace for this exception.
     *
     * @param string The format you wish to use for printing. Options
     *               include:
     *               - html
     *               - plain
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function printStackTrace ($format = 'html')
    {

        // exception related properties
        $class     = ($this->getFile() != null)
                     ? Toolkit::extractClassName($this->getFile()) : 'N/A';

        $class     = ($class != '')
                     ? $class : 'N/A';

        $code      = ($this->getCode() > 0)
                     ? $this->getCode() : 'N/A';

        $file      = ($this->getFile() != null)
                     ? $this->getFile() : 'N/A';

        $line      = ($this->getLine() != null)
                     ? $this->getLine() : 'N/A';

        $message   = ($this->getMessage() != null)
                     ? $this->getMessage() : 'N/A';

        $name      = $this->getName();

        $traceData = $this->getTrace();
        $trace     = array();

        // lower-case the format to avoid sensitivity issues
        $format = strtolower($format);

        if ($trace !== null && count($traceData) > 0)
        {

            // format the stack trace
            for ($i = 0, $z = count($traceData); $i < $z; $i++)
            {

                if (!isset($traceData[$i]['file']))
                {

                    // no file key exists, skip this index
                    continue;

                }

                // grab the class name from the file
                // (this only works with properly named classes)
                $tClass = Toolkit::extractClassName($traceData[$i]['file']);

                $tFile      = $traceData[$i]['file'];
                $tFunction  = $traceData[$i]['function'];
                $tLine      = $traceData[$i]['line'];

                if ($tClass != null)
                {

                    $tFunction = $tClass . '::' . $tFunction . '()';

                } else
                {

                    $tFunction = $tFunction . '()';

                }

                if ($format == 'html')
                {

                    $tFunction = '<strong>' . $tFunction . '</strong>';

                }

                $data = 'at %s in [%s:%s]';
                $data = sprintf($data, $tFunction, $tFile, $tLine);

                $trace[] = $data;

            }

        }

        switch ($format)
        {

            case 'html':

                // print the exception info
                echo '<!DOCTYPE html
                      PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
                      "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
                      <html xmlns="http://www.w3.org/1999/xhtml"
                            xml:lang="en" lang="en">
                      <head>
                      <meta http-equiv="Content-Type"
                            content="text/html; charset=iso-8859-1"/>
                      <title>Mojavi Exception</title>
                      <style type="text/css">

                      #exception {
                          background-color: #EEEEEE;
                          border:           solid 1px #750000;
                          font-family:      verdana, helvetica, sans-serif;
                          font-size:        76%;
                          font-style:       normal;
                          font-weight:      normal;
                          margin:           10px;
                      }

                      #help {
                          color:     #750000;
                          font-size: 0.9em;
                      }

                      .message {
                          color:       #FF0000;
                          font-weight: bold;
                      }

                      .title {
                          font-size:   1.1em;
                          font-weight: bold;
                      }

                      td {
                          background-color: #EEEEEE;
                          padding:          5px;
                      }

                      th {
                          background-color: #750000;
                          color:            #FFFFFF;
                          font-size:        1.2em;
                          font-weight:      bold;
                          padding:          5px;
                          text-align:       left;
                      }

                      </style>
                      </head>
                      <body>

                      <table id="exception" cellpadding="0" cellspacing="0">
                          <tr>
                              <th colspan="2">' . $name . '</th>
                          </tr>
                          <tr>
                              <td class="title">Message:</td>
                              <td class="message">' . $message . '</td>
                          </tr>
                          <tr>
                              <td class="title">Code:</td>
                              <td>' . $code . '</td>
                          </tr>
                          <tr>
                              <td class="title">Class:</td>
                              <td>' . $class . '</td>
                          </tr>
                          <tr>
                              <td class="title">File:</td>
                              <td>' . $file . '</td>
                          </tr>
                          <tr>
                              <td class="title">Line:</td>
                              <td>' . $line . '</td>
                          </tr>';

                if (count($trace) > 0)
                {

                    echo '<tr>
                              <th colspan="2">Stack Trace</th>
                          </tr>';

                    foreach ($trace as $line)
                    {

                        echo '<tr>
                                  <td colspan="2">' . $line . '</td>
                              </tr>';

                    }

                }

                echo     '<tr>
                              <th colspan="2">Info</th>
                          </tr>
                          <tr>
                              <td class="title">Mojavi Version:</td>
                              <td>' . MO_APP_VERSION . '</td>
                          </tr>
                          <tr>
                              <td class="title">PHP Version:</td>
                              <td>' . PHP_VERSION . '</td>
                          </tr>
                          <tr id="help">
                              <td colspan="2">
                                  For help resolving this issue, please visit
                                  <a href="http://www.mojavi.org">www.mojavi.org</a>.
                              </td>
                          </tr>
                      </table>

                      </body>
                      </html>';

                break;

            case 'plain':
            default:

        }

        exit;

    }

    // -------------------------------------------------------------------------

    /**
     * Set the name of this exception.
     *
     * @param string An exception name.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    protected function setName ($name)
    {

        $this->name = $name;

    }

}

?>