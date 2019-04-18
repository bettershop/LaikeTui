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
 * Storage allows you to customize the way Mojavi stores its persistent data.
 *
 * @package    mojavi
 * @subpackage storage
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     3.0.0
 * @version   $Id: Storage.class.php 668 2004-12-16 01:16:43Z seank $
 */
abstract class Storage extends ParameterHolder
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE DATA                                                          |
    // +-----------------------------------------------------------------------+

    private
        $context = null;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Retrieve the current application context.
     *
     * @return Context A Context instance.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function getContext ()
    {

        return $this->context;

    }

    // -------------------------------------------------------------------------

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

        $this->context = $context;

        if ($parameters != null)
        {

            $this->parameters = array_merge($this->parameters, $parameters);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve a new Storage implementation instance.
     *
     * @param string A Storage implementation name
     *
     * @return Storage A Storage implementation instance.
     *
     * @throws <b>FactoryException</b> If a storage implementation instance
     *                                 cannot be created.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public static function newInstance ($class)
    {

        // the class exists
        $object = new $class();

        if (!($object instanceof Storage))
        {

            // the class name is of the wrong type
            $error = 'Class "%s" is not of the type Storage';
            $error = sprintf($error, $class);

            throw new FactoryException($error);

        }

        return $object;

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
     * @throws <b>StorageException</b> If an error occurs while reading data from
     *                                 this storage.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function & read ($key);

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
     * @throws <b>StorageException</b> If an error occurs while removing data from
     *                                 this storage.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function & remove ($key);

    // -------------------------------------------------------------------------

    /**
     * Execute the shutdown procedure.
     *
     * @return void
     *
     * @throws <b>StorageException</b> If an error occurs while shutting down
     *                                 this storage.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function shutdown ();

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
     * @throws <b>StorageException</b> If an error occurs while writing to this
     *                                 storage.
     *
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    abstract function write ($key, &$data);

}

?>