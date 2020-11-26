<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/PDOAction.class.php');

function lkt_execute($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> query($sql,$params);
    return $r;
}

function lkt_get($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> getOne($sql,$params);
    return $r;
}