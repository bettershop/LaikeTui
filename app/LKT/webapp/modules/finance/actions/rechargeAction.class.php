<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class rechargeAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $admin_id = $this->getContext()->getStorage()->read('admin_id');

        $zhanghao = addslashes(trim($request->getParameter('zhanghao'))); // 账号
        $mobile = addslashes(trim($request->getParameter('mobile'))); // 手机号码
        $user_name = addslashes(trim($request->getParameter('user_name'))); // 用户昵称

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

		$condition = '(a.type = 1 or a.type = 14)';
		if($zhanghao != ''){
			$condition .= " and b.zhanghao = '$zhanghao' ";
		}
        if($mobile != ''){
            $condition .= " and b.mobile = '$mobile' ";
        }
        if($user_name != ''){
            $condition .= " and b.user_name = '$user_name' ";
        }
        $sql = "select a.*,b.user_name from lkt_record as a LEFT JOIN lkt_user as b ON a.user_id = b.user_id where $condition";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);
		$offset = $pager->offset;
       
        if($pageto == 'all'){ // 导出
            $sql = "select a.*,b.user_name,b.mobile,b.source from lkt_record as a LEFT JOIN lkt_user as b ON a.user_id = b.user_id where $condition order by a.add_date desc limit $start,$pagesize";
            $list = $db->select($sql);

            $db->admin_record($admin_id,' 导出充值列表 ',4);

        }else if($pageto == 'ne'){ // 导出本页
            $sql = "select t1.*,t2.user_name from (select * from lkt_record a where $condition limit $offset,$pagesize) t1, lkt_user t2 where t1.user_id = t2.user_id ";
            $list = $db->select($sql);

            $db->admin_record($admin_id,' 导出充值列表第 '.$page.'页'.$pagesize.'条数据',4);

        }else{ // 不导出
            // 根据用户id,查询充值记录和用户昵称
            $sql = "select a.*,b.user_name,b.mobile,b.source from lkt_record as a LEFT JOIN lkt_user as b ON a.user_id = b.user_id where $condition order by a.add_date desc limit $start,$pagesize";
            $list = $db->select($sql);
        }

        $url = "index.php?module=finance&action=recharge&zhanghao=".urlencode($zhanghao)."&mobile=".urlencode($mobile)."&user_name=".urlencode($user_name)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("zhanghao",$zhanghao);
        $request->setAttribute("mobile",$mobile);
        $request->setAttribute("user_name",$user_name);
        $request->setAttribute("list",$list);
        $request->setAttribute('pageto',$pageto);
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