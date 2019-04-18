<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        $sql = "select * from lkt_product_config where id = '1'";
        $r = $db->select($sql);

        if($r){
            $config = unserialize($r[0]->config);
        }
        $config = (object)$config;

        $request->setAttribute('config', isset($config) ? $config : '');
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $config = serialize(($request->getParameter('config')));

        $sql = "select * from lkt_product_config where id = '1'";
        $rr = $db->select($sql);
        if($rr){
            $sql = "update lkt_product_config set config='$config',add_date=CURRENT_TIMESTAMP where id =1";
            $r = $db->insert($sql);
            if($r > 0){
                $db->admin_record($admin_id,' 修改商品参数信息 ',2);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('修改信息成功！');" .
                    "location.href='index.php?module=product_config';</script>";
            }else{
                $db->admin_record($admin_id,' 修改商品参数信息失败 ',2);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，修改参数失败！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }else{
            $sql = "insert into lkt_product_config(config,add_date) values('$config',CURRENT_TIMESTAMP)";
            $r = $db->insert($sql);
            if($r > 0){
                $db->admin_record($admin_id,' 添加商品参数信息 ',1);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('添加信息成功！');" .
                    "location.href='index.php?module=product_config';</script>";
            }else{
                $db->admin_record($admin_id,' 添加商品参数信息失败 ',1);

                header("Content-type:text/html;charset=utf-8");
                echo "<script type='text/javascript'>" .
                    "alert('未知原因，添加参数失败！');" .
                    "</script>";
                return $this->getDefaultView();
            }
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>