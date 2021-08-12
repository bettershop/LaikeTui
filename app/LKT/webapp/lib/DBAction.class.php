<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once (MO_CONFIG_DIR . '/db_config.php');
class DBAction {

    public $mConnId; //连接标识

    private static $instances = null; //连接实例

    public function DBConnect() {
        $this->mConnId = mysqli_connect(MYSQL_SERVER, MYSQL_USER, MYSQL_PASSWORD,MYSQL_DATABASE,MYSQL_PORT);
        if (!$this->mConnId) {
            print " 连接数据库失败!可能是mysql数据库用户名或密码不正确!<br>";
            return false;
        }
        
    }

    public static function getInstance() {
        if (null == self :: $instances) {
            self :: $instances = new DBAction();
            self :: $instances->DBConnect();
        }
        mysqli_query(self :: $instances->mConnId,"SET NAMES 'UTF8'");
        mysqli_query(self :: $instances->mConnId,"SET SESSION time_zone = '+8:00'");
        return self :: $instances;
    }

    public function query($sql) {
        $result = mysqli_query($this->mConnId,$sql);
        return $result;
    }

    //查询
    public function select($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return;}
        $rs = $this->query($sql);
        if ((!$rs) || empty ($rs)) {return;}
        $data = array();
        while ($rd = mysqli_fetch_object($rs)) {
            $data[] = $rd;
        }
        mysqli_free_result($rs);
        return $data;
    }

    //查询
    public function selectOne($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return;}
        $rs = $this->query($sql);
        if ((!$rs) || empty ($rs)) {return;}
        $rd = mysqli_fetch_object($rs);
        mysqli_free_result($rs);
        return $rd;
    }

    //查询返回数组
    public function selectarray($sql){
        $sql = trim($sql);
        if (empty ($sql)) {return;}
        $rs = $this->query($sql);
        if ((!$rs) || empty ($rs)) {return;}
        $data = array();
        while($row = mysqli_fetch_array($rs)) {
            $data[] = $row;
        }
        mysqli_free_result($rs);
        return $data;
    }

    //查询返回影响行
    public function selectrow($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return -1;}
        $rs = $this->query($sql);
        if ((!$rs) || empty ($rs)) {return 0;}
        $num= mysqli_num_rows($rs);
        mysqli_free_result($rs);
        return $num;
    }

    //插入
    public function insert($sql, $return = "affectedrows") {
        $sql = trim($sql);
        if (empty ($sql)) {return -1;}
        $rs = $this->query($sql);
        if ($rs == false) {return -1;}
        if (strtolower($return) == "last_insert_id") {
            return mysqli_insert_id($this->mConnId);
        } else {
            return mysqli_affected_rows($this->mConnId);
        }
    }

    
    public function insert_array($arr,$database,$xs_sql = '',$return = ''){

        if(is_array($arr)){
            $sql = 'insert into '.$database . '(';

            foreach ($arr as $key => $value) {
                $sql.= $key.',';
            }
            $sql = rtrim($sql, ',').') values (';

            foreach ($arr as $key => $value) {
                if($value == 'CURRENT_TIMESTAMP'){
                    $sql.= $value.',';
                }elseif(is_array($value)){
                    return false;
                }elseif(is_object($value)){
                    return false;
                }else{
                    $sql.= '\''.$value.'\',';
                }
            }
            $sql = rtrim($sql, ',').')';
            if ($xs_sql) {
                echo $sql;
            }else{
                if ($return) {
                    $res = $this->insert($sql,"last_insert_id");
                }else{
                    $res = $this->insert($sql);
                }
                return $res;
            }
        }else{
            return false;
        }
    }


    //更新
    // return 成功，返回执行影响行数，失败，返回-1
    public function update($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return -1;}

        $rs = $this->query($sql);
        if ($rs == false) {return -1;}
        return mysqli_affected_rows($this->mConnId);
    }

    //删除
    // return 成功，返回执行影响行数，失败，返回-1
    public function delete($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return -1;}

        $rs = $this->query($sql);
        if ($rs == false) {return -1;}
        return mysqli_affected_rows($this->mConnId);
    }


    // return 成功，返回执行影响行数，失败，返回-1
    public function modify($arr,$database,$where,$xs_sql = '') {
        if(is_array($arr)){
            $sql = 'update '.$database . ' set ';

            foreach ($arr as $key => $value) {
                if($value == 'CURRENT_TIMESTAMP'){
                    $sql.= $key.' = '.$value;
                }elseif(is_array($value)){
                    return false;
                }elseif(is_object($value)){
                    return false;
                }else{
                    $sql.= $key.' = \''.$value.'\',';
                }
            }
            $sql = rtrim($sql, ',').' where ';
            if($where){
                $sql.= " $where";
            }else{
                $sql.= " 1=1";
            }
            if ($xs_sql) {
                echo $sql;
            }else{
                $res = $this->insert($sql);
                return $res;
            }
        }else{
            return false;
        }
    }


    /*
     * 预处理插入
     * $data 参数一般为数组
     */
    public function preInsert($sql,$data) {
        $mysqli_stmt=$this->mConnId->prepare($sql);
        $callback = array($mysqli_stmt, 'bind_param');
        // 将参数类型描述加入数组
        array_unshift($data, $this->getParamTypeStr($data));
        call_user_func_array($callback, $this->refValues($data));
        if($mysqli_stmt->execute()){
            return $mysqli_stmt->insert_id;
        }else{
            return $mysqli_stmt->error;

        }

    }

    public function preUpdate($sql,$data) {
        $mysqli_stmt=$this->mConnId->prepare($sql);
        $callback = array($mysqli_stmt, 'bind_param');
        // 将参数类型描述加入数组
        array_unshift($data, $this->getParamTypeStr($data));
        call_user_func_array($callback, $this->refValues($data));
        if($mysqli_stmt->execute()){
            return $mysqli_stmt->affected_rows;
        }else{
            return $mysqli_stmt->error;

        }

    }

    private function getParamTypeStr($data){
        $count = count($data);
        $typestr = "";
        for($i = 0; $i<$count; $i++){
            $type = gettype($data[$i]);
            switch($type){
                case "integer":
                    $typestr.= "i";
                    break;
                case "float":
                case "double":
                    $typestr.= "d";
                    break;
                case "string":
                    $typestr.= "s";
                    break;
            }
        }
        return $typestr;
    }


    private function refValues($arr){
        if (version_compare(PHP_VERSION, '5.3.0') >= 0) {
            $refs = array();
            foreach($arr as $key => $value)
                $refs[$key] = &$arr[$key];
            return $refs;
        }
        return $arr;
    }

    /* 无需使用 */
    //使用mysql扩展查询,查询结束需手工释放结果集
    public function i_query($sql) {
        $sql = trim($sql);
        if (empty ($sql)) {return false;}
        $rs = mysqli_query($this->mConnId, $sql);
        return $rs;
    }



    /*关闭连接*/
    public function close() {
        mysqli_close($this->mConnId);
    }

    /* 事务处理函数 */
    public function begin() {
        return $this->query("START TRANSACTION");
    }

    public function commit() {
        return $this->query("COMMIT");
    }

    public function rollback() {
        return $this->query("ROLLBACK");
    }

    public function transaction($q_array) {
        $retval = 1;
        $this->begin();
        foreach ($q_array as $qa) {
            $result = $this->query($qa);
            if ($result == false) {
                $retval = 0;
                break;
            }
        }
        if ($retval == 0) {
            $this->rollback();
            return false;
        } else {
            $this->commit();
            return true;
        }
    }

    // 管理员记录
    function admin_record($admin_name,$event,$type){
        $event = $admin_name . $event;
        $sql = "insert into lkt_admin_record(admin_name,event,type,add_date) values ('$admin_name','$event','$type',CURRENT_TIMESTAMP)";
        $this->insert($sql);
    }
}
?>