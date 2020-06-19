<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class warningAction extends Action {
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
        $pageto = $request -> getParameter('pageto'); // 导出
        $name = addslashes(trim($request->getParameter('name'))); // 商品编码/商品名称
        $startdate = $request -> getParameter("startdate");
        $enddate = $request -> getParameter("enddate");

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


        $condition = " a.recycle = 0 and c.num <= $min_inventory and c.recycle = 0";
        $excel_condition = $condition;
        if ($name != '') {
            $condition .= " and a.product_title like '%$name%' ";
        }
        if ($startdate != '') {
            $condition .= " and b.add_date >= '$startdate 00:00:00' ";
        }
        if ($enddate != '') {
            $condition .= " and b.add_date <= '$enddate 23:59:59' ";
        }
        $sql0 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,c.num,b.add_date from lkt_configure as c left join lkt_product_list as a on c.pid = a.id left join lkt_stock as b on c.id = b.attribute_id where $condition order by a.sort,c.id ";
        $r0 = $db->select($sql0);
        $total = count($r0);

        $sql1 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,c.num,b.add_date from lkt_configure as c left join lkt_product_list as a on c.pid = a.id left join lkt_stock as b on c.id = b.attribute_id where $condition order by a.sort,c.id limit $start,$pagesize";
        $r1 = $db->select($sql1);
          // print_r($sql1);die;
        if($r1){
            foreach ($r1 as $k => $v){
                $attribute = unserialize($v->attribute);
                $specifications = '';
                foreach ($attribute as $ke => $va){
                    $specifications .= $ke .':'.$va.',';
                }
                $v->specifications = rtrim($specifications, ",");
            }
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=stock&action=warning&pagesize=".urlencode($pagesize)."&name=".urlencode($name)."&startdate=".urlencode($startdate)."&enddate=".urlencode($enddate);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $excel_sql = '';
        if($pageto == 'This_page'){ // 导出本页
            $excel_condition = $condition;
            $excel_sql = "where $excel_condition order by a.sort,c.id limit $start,$pagesize";
        }else if($pageto == 'whole'){ // 导出全部
            $excel_condition = $excel_condition;
            $excel_sql = "where $excel_condition order by a.sort,c.id";

        }else if($pageto == 'inquiry'){ // 导出查询
            $excel_condition = $condition;
            $excel_sql = "where $excel_condition order by a.sort,c.id";
        }
        if($excel_sql != ''){
            $sql2 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,c.num,b.add_date from lkt_configure as c left join lkt_product_list as a on c.pid = a.id left join (select max(add_date) as add_date,type,attribute_id from lkt_stock where type = 2 group by attribute_id) as b on c.id = b.attribute_id $excel_sql";
        }else{
            $sql2 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,c.num,b.add_date from lkt_configure as c left join lkt_product_list as a on c.pid = a.id left join (select max(add_date) as add_date,type,attribute_id from lkt_stock where type = 2 group by attribute_id) as b on c.id = b.attribute_id $condition";
        }
        $r2 = $db->select($sql2);
        if($r2){
            foreach ($r2 as $k => $v){
                $attribute = unserialize($v->attribute);
                $specifications1 = '';
                foreach ($attribute as $ke => $va){
                    $specifications1 .= $ke .':'.$va.',';
                }
                $v->specifications = rtrim($specifications1, ",");
            }
        }

        $request->setAttribute("list",$r1);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('name', $name);
        $request -> setAttribute('startdate', $startdate);
        $request -> setAttribute('enddate', $enddate);

        $request->setAttribute("excel",$r2);
        $request->setAttribute("pageto",$pageto);
        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>