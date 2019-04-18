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
 * FilterChain manages registered filters for a specific context.
 *
 * @package    mojavi
 * @subpackage filter
 *
 * @author    Sean Kerr (skerr@mojavi.org)
 * @copyright (c) Sean Kerr, {@link http://www.mojavi.org}
 * @since     1.0.0
 * @version   $Id: FilterChain.class.php 65 2004-10-26 03:16:15Z seank $
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
     * @author Sean Kerr (skerr@mojavi.org)
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
     * @author Sean Kerr (skerr@mojavi.org)
     * @since  3.0.0
     */
    public function register ($filter)
    {

        $this->chain[] = $filter;

    }

}

?>