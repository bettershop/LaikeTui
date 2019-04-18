<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $product_class = addslashes(trim($request->getParameter('cid'))); // 分类名称
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 标题
        $sql = "select cid,pname from lkt_product_class where sid = 0 ";
        $rr = $db->select($sql);
        $res = '';
        foreach ($rr as $key => $value) {
            $c = '-'.$value->cid.'-';
            //判断所属类别 添加默认标签
            if ($product_class == $c) {
              $res .= '<option selected="selected" value="'.$c.'">'.$value->pname.'</option>';
            }else{
              $res .= '<option  value="'.$c.'">'.$value->pname.'</option>';
            }
            //循环第一层
            $sql_e = "select cid,pname from lkt_product_class where sid = $value->cid";
            $r_e = $db->select($sql_e);
            if($r_e){
              $hx = '-----';
              foreach ($r_e as $ke => $ve){
                $cone = $c . $ve->cid.'-';
                //判断所属类别 添加默认标签
                if ($product_class == $cone) {
                  $res .= '<option selected="selected" value="'.$cone.'">'.$hx.$ve->pname.'</option>';
                }else{
                  $res .= '<option  value="'.$cone.'">'.$hx.$ve->pname.'</option>';
                }
                //循环第二层
                $sql_t = "select cid,pname from lkt_product_class where sid = $ve->cid";
                $r_t = $db->select($sql_t);
                if($r_t){
                  $hxe = $hx.'-----';
                  foreach ($r_t as $k => $v){
                    $ctow = $cone . $v->cid.'-';
                    //判断所属类别 添加默认标签
                    if ($product_class == $ctow) {
                      $res .= '<option selected="selected" value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                    }else{
                      $res .= '<option  value="'.$ctow.'">'.$hxe.$v->pname.'</option>';
                    }
                  }
                }
              }
            }
        }

        $condition = ' 1=1 ';
        if($product_class != ''){   
            $condition .= " and a.product_class like '%$product_class%' ";
        }

        if($product_title != ''){
            $condition .= " and a.product_title like '%$product_title%' ";
        }
        $sql = 'select a.id,a.product_title,a.sort,a.add_date,c.id as sx_id,c.name,c.color,c.img,c.size,c.bargain_price,c.status,a.product_class,a.brand_id from lkt_product_list AS a  LEFT JOIN lkt_configure AS c ON a.id = c.pid '." where $condition and status = 1".' order by sort ';
        $r = $db->select($sql);
        $list = [];
        foreach ($r as $key => $value) {
            $class =  $value -> product_class;
            $typestr=trim($class,'-');
            $typeArr=explode('-',$typestr);
            //  取数组最后一个元素 并查询分类名称
            $cid = end($typeArr);
            $sql_p = "select pname from lkt_product_class where cid ='".$cid."'";
            $r_p = $db->select($sql_p);
            if($r_p){
              $pname = $r_p['0']->pname;
            }else{
              $pname = '顶级';
            }
            foreach ($value as $k => $v) {
              $arr[$k] = $v;
            }
            $arr['pname'] = $pname;
            $list[$key] = (object)$arr;
        }
        foreach ($list as $key01 => $value01) {
            if(!empty($value01->brand_id)){
                $sql01 = "select brand_name from lkt_brand_class where brand_id ='".$value01->brand_id."'";
                $r01 = $db->select($sql01);
                $list[$key01]->brand_name = $r01[0]->brand_name;
            }
        }
      
        $request->setAttribute("product_title",$product_title);
        $request->setAttribute("class",$res);
        $request->setAttribute("list",$list);
        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>