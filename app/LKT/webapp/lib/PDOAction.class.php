<?php
require_once (MO_CONFIG_DIR . '/db_config.php');

class PDOAction {

    private static $instance;
    private $dbh;

    public static function getInstance()
    {
        if (!self::$instance) {
            self::$instance = new self();
        }

        return self::$instance;
    }

    private function __construct()
    {
        try {
            $dbh = new PDO('mysql:dbname='.MYSQL_DATABASE.';host='.MYSQL_SERVER.';port='.MYSQL_PORT, MYSQL_USER, MYSQL_PASSWORD);
            $dbh->exec('set names utf8');
            $this->dbh = $dbh;
        } catch (PDOException $e) {
            echo 'Connection failed: ' . $e->getMessage();
        }
    }

    //读取单行记录
    public function getOne($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $pdo->execute($params);
        $data = $pdo->fetch(PDO::FETCH_ASSOC);
        return $data;
    }

    //读取多行记录
    public function getRows($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $pdo->execute($params);
        $data = $pdo->fetchAll(PDO::FETCH_ASSOC);
        return $data;
    }

    //写入数据
    public function insert($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $pdo->execute($params);
        $count = $this->dbh->lastInsertId();
        return $count;
    }

    /**
     * 更新、删除数据
     * @params $sql string 参数用 ? 代替
     * @params $params array 参数
     * @return 成功则返回受影响的行数 失败返回false
     */
    public function query($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $result = $pdo->execute($params);
        if ($result) {
            return $pdo->rowCount();
        }

        return false;
    }


    //读取多行记录,并返回对象
    public function select($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $pdo->execute($params);
        $data = $pdo->fetchAll(PDO::FETCH_OBJ);
        return $data;
    }

    //读取单行记录,并返回对象
    public function selectOne($sql, $params = array())
    {
        $pdo = $this->dbh->prepare($sql);
        $pdo->execute($params);
        $data = $pdo->fetch(PDO::FETCH_OBJ);
        return $data;
    }

    // 管理员记录
    public function admin_record($admin_name,$event,$type){
        $event = $admin_name . $event;
        $sql = "insert into lkt_admin_record(admin_name,event,type,add_date) values (?,?,?,CURRENT_TIMESTAMP)";
        $this->query($sql,array($admin_name,$event,$type));
        return;
    }

    //开始事务
    public function beginTransaction()
    {
        $this->dbh->beginTransaction();
    }

    //提交事务
    public function commit()
    {
        $this->dbh->commit();
    }

    //回滚事务
    public function rollBack()
    {
        $this->dbh->rollBack();
    }
}


?>