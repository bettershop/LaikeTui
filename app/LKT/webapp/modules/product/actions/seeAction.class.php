<?php
/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class seeAction extends Action {
    /*
    时间2018年03月13日
    修改内容：修改产品分类显示
    修改人：苏涛
    主要功能：处理后台所以产品显示
    公司：湖南壹拾捌号网络技术有限公司
     */
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id'))); // 产品id
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 标题
        $url = addslashes(trim($request->getParameter('url'))); // 路径

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $sql = "select * from lkt_configure where pid = '$id'";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k =>$v){
                $attribute_3['rid'] = $v->id;
                $attribute_2 = unserialize($v->attribute); // 属性
                $attribute = array_merge ($attribute_3,$attribute_2);
                $attribute['成本价'] = $v->costprice;
                $attribute['原价'] = $v->yprice;
                $attribute['现价'] = $v->price;
                $attribute['数量'] = $v->num;
                $attribute['单位'] = $v->unit;
                $attribute['图片'] = $v->img;

                $attribute_key = array_keys($attribute); // 属性表格第一栏
                $arr[] =  $attribute;
            }
        }

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("id",$id);
        $request->setAttribute("id",$id);
        $request->setAttribute("product_title",$product_title);
        $request->setAttribute("url",$url);
        $request->setAttribute("attribute_key",$attribute_key);
        $request->setAttribute("attribute_value",$arr);

      return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $id = addslashes(trim($request->getParameter('id'))); // 产品id
        $attribute_id = addslashes(trim($request->getParameter('attribute_id'))); // 属性id
        $num = $request->getParameter('num'); // 数量
        if(floor($num) == $num){
            if($num > 0){
                $sql = "update lkt_configure set num = '$num' where pid = '$id' and id = '$attribute_id'";
                $rr = $db->update($sql);

                $sql = "select num from lkt_configure where pid = '$id'";
                $r = $db->select($sql);
                if($r){
                    $znum = 0;
                    foreach ($r as $k => $v){
                        $znum += $v->num;
                    }
                    $sql = "update lkt_product_list set num = '$znum' where id = '$id'";
                    $db->update($sql);
                }
                if($rr == -1) {
                    $db->admin_record($admin_id,' 修改属性id为 '.$attribute_id.' 的库存失败 ',2);

                    echo 0;
                    exit;
                } else {
                    $db->admin_record($admin_id,' 修改属性id为 '.$attribute_id.' 的库存 ',2);

                    echo 1;
                    exit;
                }
            }else{
                $db->admin_record($admin_id,' 修改属性id为 '.$attribute_id.' 的库存失败 ',2);

                echo 0;
                exit;
            }
        }else{
            $db->admin_record($admin_id,' 修改属性id为 '.$attribute_id.' 的库存失败 ',2);

            echo 0;
            exit;
        }
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

}

?>