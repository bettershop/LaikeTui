<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class numAction extends Action {
    /*
    时间2018年03月13日
    修改内容：修改产品分类显示
    修改人：苏涛
    主要功能：处理后台库存
    公司：湖南壹拾捌号网络技术有限公司
     */
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $status = addslashes(trim($request->getParameter('status'))); // 状态
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 标题

        $pageto = $request -> getParameter('pageto');
        // 导出
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        // 每页显示多少条数据
        $page = $request -> getParameter('page');

        // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }
        $sql = "select * from lkt_product_config where id = 1";
        $r1 = $db->select($sql);
        if($r1){
            $config = unserialize($r1[0]->config);
            $min_inventory = $config['min_inventory'];
        }else{
            $min_inventory = 0;
        }
        $condition = ' 1=1 ';

        if($product_title != ''){
            $condition .= " and a.product_title like '%$product_title%' ";
        }

        $sql = "select a.id from lkt_product_list AS a  LEFT JOIN lkt_configure AS c ON a.id = c.pid where $condition and a.recycle = 0 and c.num <= '$min_inventory' order by a.sort,c.id ";
        $r_pager = $db->select($sql);
        if($r_pager){
            $total = count($r_pager);
        }else{
            $total = 0;
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select a.id,a.product_title,a.imgurl,a.sort,a.add_date,a.status,c.id as attribute_id,c.price,c.num,c.unit,c.img,c.attribute from lkt_product_list AS a  LEFT JOIN lkt_configure AS c ON a.id = c.pid where $condition and a.recycle = 0 and c.num <= '$min_inventory' order by a.sort,c.id limit $start,$pagesize ";
        $r = $db->select($sql);
        $list = [];
        if($r) {
            $res = array();
            foreach ($r as $key => $value) {
                $rew = '[';
                $attribute_2 = unserialize($value->attribute); // 属性
                foreach ($attribute_2 as $k => $v){
                    $rew .= ' ' . $v . ' ';
                }
                $rew .= ']';
                $value->rew = $rew;
                $list[$key] = $value;
            }
        }

        $url = "index.php?module=product&action=num&product_title=".urlencode($product_title)."&pagesize=".urlencode($pagesize);
//        $pages_show = $pager->multipage($url,ceil($total/$pagesize),$page, $para = '');
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("product_title",$product_title);
        $request->setAttribute("list",$list);
        $request -> setAttribute('pages_show', $pages_show);


        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }
}

?>