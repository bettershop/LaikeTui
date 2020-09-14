<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class addAction extends Action {
    /**
     * <p>Copyright (c) 2018-2019</p>
     * <p>Company: www.laiketui.com</p>
     * @author 段宏波
     * @date 2018/12/12  16:07
     * @version 1.0
     */
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = $request->getParameter('id'); // 商品属性id
        $pid = $request->getParameter('pid'); // 商品id
        $sql0 = "select a.product_title,a.product_class,a.brand_id,c.total_num,c.num,c.attribute from lkt_configure as c,lkt_product_list as a where c.pid = a.id and c.id = '$id' and c.pid = '$pid'";
        $r0 = $db->select($sql0);
        if($r0){
            $product_class = rtrim($r0[0]->product_class, "-");
            $product_class_id = trim(strrchr($product_class, '-'),'-');
            $brand_id = $r0[0]->brand_id;
            $product_title = $r0[0]->product_title;
            $total_num = $r0[0]->total_num;
            $num = $r0[0]->num;
            $attribute = unserialize($r0[0]->attribute);
            $specifications = '';
            foreach ($attribute as $ke => $va){
                $specifications .= $ke .':'.$va.',';
            }
            $specifications = rtrim($specifications, ",");

            $sql1 = "select pname from lkt_product_class where   cid = '$product_class_id'";
            $r1 = $db->select($sql1);
            if($r1){
                $product_class_name = $r1[0]->pname;
            }
            $sql2 = "select brand_name from lkt_brand_class where  brand_id = '$brand_id'";
            $r2 = $db->select($sql2);
            if($r2){
                $brand_name = $r2[0]->brand_name;
            }
        }

        $request->setAttribute("id",$id);
        $request->setAttribute("pid",$pid);
        $request->setAttribute("product_class_name",$product_class_name);
        $request->setAttribute("brand_name",$brand_name);
        $request->setAttribute("product_title",$product_title);
        $request->setAttribute("total_num",$total_num);
        $request->setAttribute("num",$num);
        $request->setAttribute("specifications",$specifications);
        return View :: INPUT;
    }
    /**
     * <p>Copyright (c) 2018-2019</p>
     * <p>Company: www.laiketui.com</p>
     * @author 段宏波
     * @date 2018/12/12  16:07
     * @version 1.0
     */
    public function execute(){
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_name = $this->getContext()->getStorage()->read('admin_name');
        $id = addslashes(trim($request->getParameter('id'))); // 商品属性id
        $pid = addslashes(trim($request->getParameter('pid'))); // 商品id
        $total_num = addslashes(trim($request->getParameter('total_num'))); // 总库存
        $num = addslashes(trim($request->getParameter('num'))); // 剩余库存
        $add_num = addslashes(trim($request->getParameter('add_num'))); // 增加库存

        if (floor($add_num) != $add_num){
            echo json_encode(array('status' =>'增加库存请填写整数！' ));exit;
        }else{
            if($add_num <= 0){
                echo json_encode(array('status' =>'增加库存请填写正整数！' ));exit;
            }
            $total_num = $total_num + $add_num; // 总库存
            $x_num = $add_num + $num; // 剩余库存
        }

        $sql0 = "select num from lkt_product_list where id = '$pid'";
        $r0 = $db->select($sql0);
        $z_num = $r0[0]->num + $add_num;
        // 开启事务
        $db->begin();
        $sql4 = "update lkt_product_list set num='$z_num' where id = '$pid'";
        $r4 = $db->update($sql4);
        if($r4 == -1){
            $db->rollback();
            $db->admin_record($admin_name,'增加商品规格ID为'.$id.'的库存失败',2);
            echo json_encode(array('status' =>'未知原因，增加失败！' ));exit;
        }

        $sql1 = "update lkt_configure set total_num = '$total_num',num = '$x_num' where id = '$id' and pid = '$pid'";
        $r1 = $db->update($sql1);

        $sql2 = "insert into lkt_stock(product_id,attribute_id,flowing_num,type,add_date) values('$pid','$id','$add_num',0,CURRENT_TIMESTAMP)";
        $r2 = $db->insert($sql2,'last_insert_id');
        if($r1 != -1 && $r2 > 0){
            $db->commit();
            $db->admin_record($admin_name,'增加商品规格ID为'.$id.'的库存'.$add_num,2);
            echo json_encode(array('status' =>'增加成功！' ,'suc'=>'1'));exit;
        }else{
            $db->rollback();
            $db->admin_record($admin_name,'增加商品规格ID为'.$id.'的库存失败',2);
            echo json_encode(array('status' =>'未知原因，增加失败！' ));exit;
        }
        return;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>