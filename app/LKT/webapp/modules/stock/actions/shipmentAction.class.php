<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class shipmentAction extends Action {
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
        $product_title = addslashes(trim($request->getParameter('product_title'))); // 商品名称

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

        $condition = "a.recycle = 0 and b.type = 1 and c.recycle = 0";
        $excel_condition = $condition;

        if($product_title != '') {
            $condition .= " and a.product_title like '%$product_title%' ";
        }

        $sql0 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id where $condition order by b.add_date desc";
        $r0 = $db->select($sql0);
        $total = count($r0);

        $sql1 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id where $condition order by b.add_date desc limit $start,$pagesize";
        // print_r($sql1);die;
        $r1 = $db->select($sql1);
        if($r1){
            foreach ($r1 as $k => $v){
                $attribute = unserialize($v->attribute);
                $specifications = '';
                if($attribute){
                    foreach ($attribute as $ke => $va){
                        $specifications .= $ke .':'.$va.',';
                    }
                }
                $v->specifications = rtrim($specifications, ",");

                $sql2 = "select add_date from lkt_stock where product_id = '$v->pid' and attribute_id = '$v->id' order by add_date desc limit 1";
                $r2 = $db->select($sql2);
                if($r2){
                    $v->add_date = $r2[0]->add_date;
                }
            }
        }
        $pager = new ShowPager($total,$pagesize,$page);

        $url = "index.php?module=stock&action=shipment&pagesize=".urlencode($pagesize)."&product_title=".urlencode($product_title);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $excel_sql = '';
        if($pageto == 'This_page'){ // 导出本页
            $excel_condition = $condition;
            $excel_sql = "where $excel_condition order by a.add_date desc limit $start,$pagesize";
        }else if($pageto == 'whole'){ // 导出全部
            $excel_condition = $excel_condition;
            $excel_sql = "where $excel_condition order by a.add_date desc";

        }else if($pageto == 'inquiry'){ // 导出查询
            $excel_condition = $condition;
            $excel_sql = "where $excel_condition order by a.add_date desc";
        }
        if($excel_sql != ''){
            $sql2 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id $excel_sql";
        }else{
            $sql2 = "select a.product_title,a.status,c.id,c.pid,c.price,c.attribute,c.total_num,b.flowing_num,b.add_date from lkt_stock as b left join lkt_product_list as a on b.product_id = a.id left join lkt_configure as c on b.attribute_id = c.id $condition";
        }
        $r2 = $db->select($sql2);
        if($r2){
            foreach ($r2 as $k => $v){
                $attribute = unserialize($v->attribute);
                $specifications1 = '';
                if($attribute){
                    foreach ($attribute as $ke => $va){
                        $specifications1 .= $ke .':'.$va.',';
                    }
                }
                $v->specifications = rtrim($specifications1, ",");

                $sql2 = "select add_date from lkt_stock where product_id = '$v->pid' and attribute_id = '$v->id' order by add_date desc limit 1";
                $r21 = $db->select($sql2);
                if($r21){
                    $v->add_date = $r21[0]->add_date;
                }
            }
        }

        $request->setAttribute("list",$r1);
        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('product_title', $product_title);

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