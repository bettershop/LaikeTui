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
 * ActionStack keeps a list of all requested actions and provides accessor
 * methods for retrieving individual entries.
 *
 * @package    laiketui
 * @subpackage action
 *
 */
class ActionStack extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $stack = array();

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Add an entry.
     *
     * @param string A module name.
     * @param string An action name.
     * @param Action An action implementation instance.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function addEntry ($moduleName, $actionName, $actionInstance)
    {

        // create our action stack entry and add it to our stack
        $actionEntry = new ActionStackEntry($moduleName, $actionName,
                                            $actionInstance);

        $this->stack[] = $actionEntry;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the entry at a specific index.
     *
     * @param int An entry index.
     *
     * @return ActionStackEntry An action stack entry implementation.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getEntry ($index)
    {

        $retval = null;

        if ($index > -1 && $index < count($this->stack))
        {

            $retval = $this->stack[$index];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the first entry.
     *
     * @return ActionStackEntry An action stack entry implementation.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getFirstEntry ()
    {

        $count  = count($this->stack);
        $retval = null;

        if ($count > 0)
        {

            $retval = $this->stack[0];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the last entry.
     *
     * @return ActionStackEntry An action stack entry implementation.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getLastEntry ()
    {

        $count  = count($this->stack);
        $retval = null;

        if ($count > 0)
        {

            $retval = $this->stack[$count - 1];

        }

        return $retval;

    }

    // -------------------------------------------------------------------------

    /**
     * Retrieve the size of this stack.
     *
     * @return int The size of this stack.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function getSize ()
    {

        return count($this->stack);

    }

}

?>
