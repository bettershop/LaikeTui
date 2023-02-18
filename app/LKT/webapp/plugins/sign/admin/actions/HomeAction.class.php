<?php
/**
 * [Laike System] Copyright (c) 2017-2020 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */


class HomeAction extends PluginAction {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
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
        //查询签到配置表，找出活动过期后是否删除
        $sql01 = "select * from lkt_sign_config ";
        $r01 = $db->select($sql01);
        if($r01){
            $activity_overdue = $r01[0]->activity_overdue;
        }else{
            $activity_overdue = 0;
        }

        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        // 查询插件表
        $sql = "select * from lkt_sign_activity ";
        $r_pager = $db->select($sql);
        $total = count($r_pager);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_sign_activity order by add_time desc limit $start,$pagesize";
        $r = $db->select($sql);
        if(!empty($r)){
            foreach ($r as $k => $v) {
                $dtime = date("Y-m-d H:i:s");
                $dtime1 = date('Y-m-d H:i:s',strtotime('-2 day'));//判断过期后多久活动删除

                if($dtime>$v->endtime){//判断该活动是否过期
                    $v->status = '2';
                    if($activity_overdue > 0 && $dtime1>$v->endtime){//过期活动删除
                        $sql = 'delete from lkt_sign_activity where id='.$v->id;
                        $res = $db -> delete($sql);
                    }else{
                        //更新数据表
                        $sql02 = "update lkt_sign_activity set status = 2 where id = ".$v->id;
                        $r02 = $db->update($sql02);

                    }
                }else{
                    if ($v->starttime < $dtime ) {
                        //更新数据表
                        $sql02 = "update lkt_sign_activity set status = 1 where id = ".$v->id;
                        $r02 = $db->update($sql02);
                    }
                }

                if($v->image == ''){
                    $v->image = 'nopic.jpg';
                }
            }
        }
        $url = "index.php?module=pi&p=sign&c=Home&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("list",$r);
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