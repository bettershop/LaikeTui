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
 * RegexValidator allows you to match a value against a regular expression
 * pattern.
 *
 * <b>Required parameters:</b>
 *
 * # <b>pattern</b> - [none] - A PCRE, preg_match() style regular expression
 *                             pattern.
 *
 * <b>Optional parameters:</b>
 *
 * # <b>match</b>       - [true]          - Indicates that the pattern must be
 *                                          matched or must not match.
 * # <b>match_error</b> - [Invalid input] - An error message to use when the
 *                                          input does not meet the regex
 *                                          specifications.
 *
 * @package    laiketui
 * @subpackage validator
 *
 * @author ketter (ketter@laiketui.com)
 * @since  3.0.0
 */
class RegexValidator extends Validator
{

    // +-----------------------------------------------------------------------+
    // | METHODS                                                               |
    // +-----------------------------------------------------------------------+

    /**
     * Execute this validator.
     *
     * @param string A parameter value.
     * @param string An error message reference.
     *
     * @return bool true, if this validator executes successfully, otherwise
     *              false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function execute (&$value, &$error)
    {

        $match   = $this->getParameter('match');
        $pattern = $this->getParameter('pattern');

        if (($match && !preg_match($pattern, $value)) ||
            (!$match && preg_match($pattern, $value)))
        {

            $error = $this->getParameter('match_error');

            return false;

        }

        return true;

    }

    // -------------------------------------------------------------------------

    /**
     * Initialize this validator.
     *
     * @param Context The current application context.
     * @param array   An associative array of initialization parameters.
     *
     * @return bool true, if initialization completes successfully, otherwise
     *              false.
     *
     * @author ketter (ketter@laiketui.com)
     * @since  3.0.0
     */
    public function initialize ($context, $parameters = null)
    {

        // set defaults
        $this->setParameter('match',       true);
        $this->setParameter('match_error', 'Invalid input');
        $this->setParameter('pattern',     null);

        // initialize parent
        parent::initialize($context, $parameters);

        // check parameters
        if ($this->getParameter('pattern') == null)
        {

            // no pattern specified
            $error = 'Please specify a PCRE regular expression pattern for ' .
                     'your registered RegexValidator';

            throw new ValidatorException($error);

        }

        return true;

    }

}

?>