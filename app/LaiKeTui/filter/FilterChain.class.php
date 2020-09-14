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
 * FilterChain manages registered filters for a specific context.
 *
 * @package    laiketui
 * @subpackage filter
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class FilterChain extends MojaviObject
{

    // +-----------------------------------------------------------------------+
    // | PRIVATE VARIABLES                                                     |
    // +-----------------------------------------------------------------------+

    private
        $chain = array(),
        $index = -1;

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Execute the next filter in this chain.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function execute ()
    {

        // skip to the next filter
        $this->index++;

        if ($this->index < count($this->chain))
        {

            // execute the next filter
            $this->chain[$this->index]->execute($this);

        }

    }

    // -------------------------------------------------------------------------

    /**
     * Register a filter with this chain.
     *
     * @param Filter A Filter implementation instance.
     *
     * @return void
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function register ($filter)
    {

        $this->chain[] = $filter;

    }

}

?>