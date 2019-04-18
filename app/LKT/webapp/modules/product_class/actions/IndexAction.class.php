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
        $uploadImg = $this->getContext()->getStorage()->read('uploadImg');

        $cid = $request->getParameter("cid"); // 分类id
        $array = ['顶级','一级','二级','三级','四级','五级'];
        $pagesize = $request -> getParameter('pagesize');
        $pagesize = $pagesize ? $pagesize:'10';
        $page = $request->getParameter('page'); // 页码
        if($page){
            $start = ($page-1)*$pagesize;
        }else{
            $start = 0;
        }

        $con = '';
        foreach ($_GET as $key => $value001) {
            $con .= "&$key=$value001";
        }
        
        if($cid){ // 上级id
            // 根据分类id,查询所有下级
            $sql = "select * from lkt_product_class where recycle = 0 and sid = '$cid' order by sort desc limit $start,$pagesize";
            $rr = $db->select($sql);
            if($rr){
                // 有数据
                $level = $rr[0]->level;
                // 循环查询该分类是否有商品
                foreach ($rr as $k => $v){
                    $product_class = '-' . $v->cid . '-';
                    $sql = "select id from lkt_product_list where product_class like '%$product_class%' order by sort desc";
                    $rr1 = $db->select($sql);
                    if($rr1){
                        $v->status = 1; // 有商品，隐藏删除按钮
                    }else{
                        $v->status = 0; // 没商品，显示删除按钮
                    }
                }
            }else{ // 没数据，查询当前分类级别
                $sql = "select level from lkt_product_class where recycle = 0 and cid = '$cid' order by sort desc limit $start,$pagesize";
                $rrr = $db->select($sql);
                $level = $rrr[0]->level+1;
            }
            $sid_1 = $cid;
            $request->setAttribute("cid",$sid_1);
        }else{
            // 查询分类表，根据sort顺序排列
            $sql = "select * from lkt_product_class where recycle = 0 and sid = 0 order by sort desc limit $start,$pagesize";
            $rr = $db->select($sql);
            $level = 0;
            foreach ($rr as $k => $v){
                $product_class = '-' . $v->cid . '-';
                $sql = "select id from lkt_product_list where product_class like '%$product_class%' order by sort desc";
                $rr1 = $db->select($sql);
                if($rr1){
                    $v->status = 1;
                }else{
                    $v->status = 0;
                }
            }
        }
        $sid = $cid ? $cid:0;
        $total = $db->selectrow("select * from lkt_product_class where recycle = 0 and sid = '$sid'");
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=product_class&pagesize=".urlencode($pagesize).'&cid='.urlencode($cid).'&con='.urlencode($con);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');


        $level= $level ? $level:0;
        $newlerevl = $array[$level];

        $request->setAttribute("level_xs",$newlerevl);
        $request->setAttribute("level",$level);
        $request->setAttribute("list",$rr);

        $request->setAttribute("pages_show",$pages_show);
        $request->setAttribute("uploadImg",$uploadImg);
        return View :: INPUT;
    }

    public function execute() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $cid = $request->getParameter("cid"); // 分类id
        $sid = $request->getParameter("sid"); // 上级分类sid
        $sql = "select MAX(sort) as sort from lkt_product_class where recycle = 0 and sid = '$sid' ";
        $rr = $db->select($sql);
        // var_dump($rr);exit;
        $sort = $rr[0]->sort;
        $sort= $sort +1 ;
        $sql = "update lkt_product_class set sort = '$sort' where recycle = 0 and cid = '$cid'";
        $r = $db->update($sql);
        echo $r;
    }

    public function getRequestMethods(){
        return Request :: POST;
    }

    public function found($sid = 0,$level = 1){
        $db = DBAction::getInstance();
        $sql = "select * from lkt_product_class where recycle = 0 and sid = '$sid' ";
        $rr = $db->select($sql);
        foreach ($rr as $k => $v){
            $cid = $v->cid;
            $sql = "update lkt_product_class set level='$level' where recycle = 0 and cid = '$cid'";
            $db->update($sql);
            $uplevel = $level+1;
            $this->found($cid,$uplevel);
        }
        echo 'OK';
    }
}

?>