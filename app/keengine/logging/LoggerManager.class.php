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
 * LoggerManager provides accessibility and management of all loggers.
 *
 * @package    laiketui
 * @subpackage logging
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class LoggerManager extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private static
        $loggers = array();

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve a logger.
     *
     * @param string A logger name.
     *
     * @return Logger A Logger, if a logger with the name exists, otherwise
     *                null.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function getLogger ($name = 'default')
    {

        if (isset(self::$loggers[$name]))
        {

            return self::$loggers[$name];

        }

        return null;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a list of logger names.
     *
     * @return array An indexed array of logger names.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function getLoggerNames ()
    {

        return array_keys(self::$loggers);

    }

    // -------------------------------------------------------------------------

    /**
     * Indicates that a logger exists.
     *
     * @param string A logger name.
     *
     * @return bool true, if the logger exists, otherwise false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function hasLogger ($name)
    {

        return isset(self::$loggers[$name]);

    }

    // -------------------------------------------------------------------------

    /**
     * Remove a logger.
     *
     * @param string A logger name.
     *
     * @return Logger A Logger, if the logger has been removed, otherwise null.
     *
     * @throws <b>LoggingException</b> If the logger name is 'default', which
     *                                 cannot be removed.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function & removeLogger ($name)
    {

        $retval = null;

        if (isset(self::$loggers[$name]))
        {

            if ($name != 'default')
            {

                $retval = self::$loggers[$name];

                unset(self::$loggers[$name]);

            } else
            {

                // cannot remove the default logger
                $error = 'Cannot remove the default logger';

                throw new LoggingException($error);

            }

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Set a new logger instance.
     *
     * If a logger with the name already exists, an exception will be thrown.
     *
     * @param string A logger name.
     * @param Logger A Logger instance.
     *
     * @return void
     *
     * @throws <b>LoggingException</b> If a logger with the name already exists.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public static function setLogger ($name, $logger)
    {

        if (!isset(self::$loggers[$name]))
        {

            self::$loggers[$name] = $logger;

            return;

        }

        // logger already exists
        $error = 'A logger with the name "%s" is already registered';
        $error = sprintf($error, $name);

        throw new LoggingException($error);

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
    public static function shutdown ()
    {

        // loop through our loggers and shut them all down
        foreach (self::$loggers as $logger)
        {

            $logger->shutdown();

        }

    }

}

?>