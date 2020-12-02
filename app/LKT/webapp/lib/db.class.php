<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 * 数据库操作常用方法
 * 作者：ketter
 */
require_once(MO_LIB_DIR . '/PDOAction.class.php');

function lkt_execute($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> query($sql,$params);
    return $r;
}

/*
 * 获取单行数组
 */
function lkt_row($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> getOne($sql,$params);
    return $r;
}

/*
 * 获取多行数组
 */
function lkt_rows($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> getRows($sql,$params);
    return $r;
}


/*
 * 获取单行对象
 */
function lkt_get($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> selectOne($sql,$params);
    return $r;
}

/*
 * 获取多行对象
 */
function lkt_gets($sql,$params = array()){
    $db=PDOAction::getInstance();
    $r = $db -> select($sql,$params);
    return $r;
}

