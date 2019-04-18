<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {
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
        $product_class = addslashes(trim($request->getParameter('cid'))); // 分类名称
        $brand_id = addslashes(trim($request->getParameter('brand_id'))); // 品牌id
        $status = addslashes(trim($request->getParameter('status'))); // 上下架
        $s_type = addslashes(trim($request->getParameter('s_type'))); // 类型
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 标题

        $pageto = $request -> getParameter('pageto');
        // 导出
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:10;
        // 每页显示多少条数据
        $page = $request -> getParameter('page');

        // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }


        $sql = "select cid,pname from lkt_product_class where recycle = 0 and sid = 0 ";
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
            $sql_e = "select cid,pname from lkt_product_class where recycle = 0 and sid = $value->cid";
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
            $sql_t = "select cid,pname from lkt_product_class where recycle = 0 and sid = $ve->cid";
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
        $sql = "select * from lkt_brand_class where recycle = 0";
        $rr1 = $db->select($sql);
        $rew = '';
        foreach ($rr1 as $key => $value) {
            if ($brand_id == $value->brand_id) {
                $rew .= "<option selected='selected' value='" . $value->brand_id . "'>$value->brand_name</option>";
            } else {
                $rew .= "<option value='" . $value->brand_id . "'>$value->brand_name</option>";
            }
        }
        $sql = "select * from lkt_product_config where id =1";
        $rr = $db->select($sql);

        if($rr){
            $config = unserialize($rr[0]->config);
            $min_inventory = $config['min_inventory'];
        }else{
            $min_inventory = 0;
        }

        $condition = ' recycle = 0 ';
        if($product_class != 0){
            $condition .= " and a.product_class like '%$product_class%' ";
        }
        if ($brand_id != 0) {
            $condition .= " and brand_id like '$brand_id' ";
        }
        if ($status != 0) {
            if($status == 1){
                $condition .= " and status like 1 ";
            }else if($status == 2){
                $condition .= " and status like 0 ";
            }
        }
        if ($s_type != 0) {
            $condition .= " and s_type like '%$s_type%' ";
        }
        if($product_title != ''){
            $condition .= " and a.product_title like '%$product_title%' ";
        }
        $sql = "select * from lkt_product_list as a where $condition" . " order by status asc,a.add_date desc,a.sort desc ";
        $r_pager = $db->select($sql);
        if($r_pager){
            $total = count($r_pager);
        }else{
            $total = 0;
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_product_list as a where $condition" . " order by status asc,a.add_date desc,a.sort desc limit $start,$pagesize ";
        $r = $db->select($sql);
        $list = [];
        $status_num = 0;
        foreach ($r as $key => $value) {
            $pid =  $value -> id;
            $class =  $value -> product_class;
            $num =  $value -> num;
            $value -> s_type = explode(',',$value -> s_type);
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
            if($num == 0 && $value->status == 0){ // 当库存为0 并且商品还为上架状态
                // 根据商品id，修改商品状态（下架）
                $sql = "update lkt_product_list set status = 1 where id = '$pid'";
                $db->update($sql);
                $status_num += 1;
                // 根据商品id，把商品下的属性全部下架
                $sql = "update lkt_configure set status = 4 where pid = '$pid'";
                $db->update($sql);
            }
            $sql = "select id,num,unit,price from lkt_configure where pid = '$pid'";
            $r_s = $db->select($sql);
            if($r_s){
                $price = [];
                $unit = $r_s[0]->unit;
                foreach ($r_s as $k1 => $v1){
                    $price[$k1] = $v1->price;
                    $configure_id = $v1->id;
                    if($v1->num <= $min_inventory && $v1->num > 0){
                        $sql = "update lkt_configure set status = 3 where id = '$configure_id'";
                        $db->update($sql);
                    }else if($v1->num == 0){
                        $sql = "update lkt_configure set status = 4 where id = '$configure_id'";
                        $db->update($sql);
                    }
                }
                $min = min($price);
                $present_price = $min;
            }else{
                $unit = '';
                $present_price = '';
            }
            $value->unit = $unit;
            $value->price = $present_price;
            foreach ($value as $k => $v) {
              $arr[$k] = $v;
            }
            $arr['pname'] = $pname;

            $list[$key] = (object)$arr;
        }
        if($status_num > 0){
            $this->getDefaultView();
        }
        foreach ($list as $key01 => $value01) {
            if(!empty($value01->brand_id)){
                $sql01 = "select brand_name from lkt_brand_class where brand_id ='".$value01->brand_id."'";
                $r01 = $db->select($sql01);
                if($r01){
                    $list[$key01]->brand_name = $r01[0]->brand_name;
                }
            }
        }

        $url = "index.php?module=product&action=Index&cid=".urlencode($product_class)."&brand_id=".urlencode($brand_id)."&status=".urlencode($status)."&s_type=".urlencode($s_type)."&product_title=".urlencode($product_title)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $request->setAttribute("uploadImg", $uploadImg);
        $request->setAttribute("product_title", $product_title);
        $request->setAttribute("class", $res);
        $request->setAttribute("rew", $rew);
        $request->setAttribute("s_type", $s_type);
        $request->setAttribute("status", $status);
        $request->setAttribute("list", $list);
        $request->setAttribute("min_inventory", $min_inventory);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>