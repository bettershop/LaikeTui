<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once (MO_LIB_DIR . '/DBAction.class.php');
require_once (MO_LIB_DIR . '/ShowPager.class.php');
require_once (MO_LIB_DIR . '/Tools.class.php');

class jifenAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this -> getContext() -> getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $user_name = addslashes(trim($request->getParameter('user_name'))); // 用户名
        $mobile = addslashes(trim($request->getParameter('mobile'))); // 手机号码
        $type = $request->getParameter('otype');
        $starttime = $request->getParameter('startdate');//开始时间
        $group_end_time = $request -> getParameter('enddate');//结束时间

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

        $condition = ' 1=1 ';
        if($user_name){
            $condition .= " and b.user_name = '$user_name' ";
        }
        if($mobile){
            $condition .= " and b.mobile = '$mobile' ";
        }
        if($type == ''){
            $type = 'all';
        }else{
            if($type != 'all'){
                $condition .= " and a.type = '$type' ";
            }
        }

        $list = array();

        $sql = "select a.user_id,max(sign_time) as t from lkt_sign_record as a left join lkt_user as b on a.user_id = b.user_id where $condition group by a.user_id";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);
        if($r){
            $b = array_unique($r,SORT_REGULAR);
            $sql = "select a.user_id,max(sign_time) as t from lkt_sign_record as a left join lkt_user as b on a.user_id = b.user_id where $condition group by a.user_id order by t desc limit $start,$pagesize";
            $r1 = $db->select($sql);
            $b1 = array_unique($r1,SORT_REGULAR);
            if($pageto == 'all') { // 导出全部
                $db->admin_record($admin_id,' 导出积分列表 ',4);
            }else if($pageto == 'ne'){ // 导出本页
                $db->admin_record($admin_id,' 导出积分列表第 '.$page.'页'.$pagesize.'条数据',4);
            }else{ // 不导出

            }
            foreach ($b1 as $k => $v){
                $user_id = $v->user_id;
                $sql1 = "select a.*,b.user_name,b.mobile,b.source from lkt_sign_record as a left join lkt_user as b on a.user_id = b.user_id where $condition and a.user_id = '$user_id' order by a.sign_time desc limit 1";
                $rr = $db -> select($sql1);
                if($rr){
                    foreach ($rr as $key => $value) {
                        $user_id = $value->user_id;
                        $sql021 = "select sets from lkt_user_distribution as a ,lkt_distribution_grade as b where a.level = b.id  and user_id = '$user_id' ";
                        $r021 = $db->select($sql021);
                        if(!empty($r021)){
                            $re01=unserialize($r021[0]->sets);
                            $value->typename = $re01['s_dengjiname'];
                        }else{
                            $value->typename = '';
                        }
                    }
                    $list[] = $rr[0];
                }
            }
        }
        $url = "index.php?module=finance&action=jifen&user_name=".urlencode($user_name)."&otype=".urlencode($type)."&mobile=".urlencode($mobile)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request -> setAttribute('pageto', $pageto);
        $request->setAttribute("user_name",$user_name);
        $request->setAttribute("mobile",$mobile);
        $request->setAttribute("type",$type);
        $request->setAttribute("list",$list);
//        $request->setAttribute("starttime",$starttime);
//        $request->setAttribute("group_end_time",$group_end_time);

        $request -> setAttribute('pages_show', $pages_show);
        $request -> setAttribute('pagesize', $pagesize);
        return View::INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods() {
        return Request::NONE;
    }

}
?>