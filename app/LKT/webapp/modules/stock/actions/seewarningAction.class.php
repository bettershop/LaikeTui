<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class seewarningAction extends Action {
    /**
     * <p>Copyright (c) 2018-2019</p>
     * <p>Company: www.laiketui.com</p>
     * @author 段宏波
     * @date 2018/12/12  17:50
     * @version 1.0
     */
    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $id = addslashes(trim($request->getParameter('id'))); // 商品属性id
        $pid = addslashes(trim($request->getParameter('pid'))); // 商品id

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

        $sql0 = "select a.product_title,a.status,c.price,c.attribute,c.total_num,c.num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id where a.recycle = 0 and c.num <= $min_inventory and b.product_id = '$pid' and b.attribute_id = '$id' and c.recycle = 0 order by b.add_date desc";
        $r0 = $db->select($sql0);
        $total = count($r0);

        $sql1 = "select a.product_title,a.status,c.price,c.attribute,c.total_num,c.num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id where a.recycle = 0 and c.num <= $min_inventory  and b.product_id = '$pid' and b.attribute_id = '$id' and c.recycle = 0 order by b.add_date desc limit $start,$pagesize";
        $r1 = $db->select($sql1);
        if($r1){
            foreach ($r1 as $k => $v){
                $attribute = unserialize($v->attribute);
                $specifications = '';
                foreach ($attribute as $ke => $va){
                    $specifications .= $ke .':'.$va.',';
                }
                $v->specifications = rtrim($specifications, ",");
                if($v->status == 0){
                    $v->status = '上架';
                }else if($v->status == 1){
                    $v->status = '下架';
                }
            }
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=stock&action=seewarning&pagesize=".urlencode($pagesize)."&id=".urlencode($id)."&pid=".urlencode($pid);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$r1);
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