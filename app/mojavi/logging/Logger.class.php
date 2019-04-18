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
 * Logger provides an easy way to manage multiple log destinations and write
 * to them all simultaneously.
 *
 * @package    mojavi
 * @subpackage logging
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: Logger.class.php 761 2005-01-29 06:20:01Z seank $
 */
class Logger extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | CONSTANTS                                                             |
    // +-----------------------------------------------------------------------+

    /**
     * Debug level.
     *
     * @since 3.0.0
     */
    const DEBUG = 1000;

    /**
     * Error level.
     *
     * @since 3.0.0
     */
    const ERROR = 4000;

    /**
     * Information level.
     *
     * @since 3.0.0
     */
    const INFO = 2000;

    /**
     * Warning level.
     *
     * @since 3.0.0
     */
    const WARN = 3000;

    /**
     * Fatal level.
     *
     * @since 3.0.0
     */
    const FATAL = 5000;

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $appenders    = array(),
        $exitPriority = self::ERROR,
        $priority     = self::WARN;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve an appender.
     *
     * @param string An appender name.
     *
     * @return Appender An Appender, if an appender with the name exists,
     *                  otherwise null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getAppender ($name)
    {

        $retval = null;

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the exit priority level.
     *
     * This is the priority level required in order to immediately exit the
     * request.
     *
     * @return int The exit priority level.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getExitPriority ()
    {

        return $this->exitPriority;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the priority level.
     *
     * This is the priority level required before a message will be written
     * to the log.
     *
     * @return int The priority level.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function getPriority ()
    {

        return $this->priority;

    }

    // -------------------------------------------------------------------------

    /**
     * Log a message.
     *
     * @param Message A Message instance.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function log ($message)
    {

        // get message priority
        $msgPriority = $message->getAttribute('p');

        if ($msgPriority >= $this->priority || $this->priority < 1)
        {

            // loop through our appenders and grab their layouts
            // then format the message and write it to the appender
            foreach ($this->appenders as $appender)
            {

                // grab the layout for the current appender
                $layout = $appender->getLayout();

                // format our message and write it
                $result = $layout->format($message);

                $appender->write($result);

            }

        }

        // to exit or not to exit, that is the question
        if ($this->exitPriority > 0 && $msgPriority >= $this->exitPriority)
        {

            Controller::getInstance()->shutdown();

            exit;

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Remove an appender.
     *
     * This does not shutdown the appender. The shutdown method must be called
     * manually.
     *
     * @param string An appender name.
     *
     * @return Appender An Appender, if one with the name exists, otherwise
     *                  null.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function removeAppender ($name)
    {

        if (isset($this->appenders[$name]))
        {

            $retval = $this->appenders[$name];

            unset($this->appenders[$name]);

            return $retval;

        }

        return null;

    }

    // -------------------------------------------------------------------------

    /**
     * Set an appender.
     *
     * If an appender with the name already exists, an exception will be thrown.
     *
     * @param string   An appender name.
     * @param Appender An Appender instance.
     *
     * @return void
     *
     * @throws <b>LoggingException</b> If an appender with the name already
     *                                 exists.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function setAppender ($name, $appender)
    {

        if (!isset($this->appenders[$name]))
        {

            $this->appenders[$name] = $appender;

            return;

        }

        // appender already exists
        $error = 'An appender with the name "%s" is already registered';
        $error = sprintf($error, $name);

        throw new LoggingException($error);

    }

    // -------------------------------------------------------------------------

    /**
     * Set the exit priority level.
     *
     * @param int An exit priority level.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setExitPriority ($priority)
    {

        $this->exitPriority = $priority;

    }

    // -------------------------------------------------------------------------

    /**
     * Set the priority level.
     *
     * @param int A priority level.
     *
     * @return void
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  1.0.0
     */
    public function setPriority ($priority)
    {

        $this->priority = $priority;

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

        // loop through our appenders and shut them all down
        foreach ($this->appenders as $appender)
        {

            $appender->shutdown();

        }

    }

}

?>