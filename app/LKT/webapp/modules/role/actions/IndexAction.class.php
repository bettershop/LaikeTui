<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

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
        // 查询管理员信息
        $sql = "select * from lkt_role";
        $r = $db->select($sql);
        $total = count($r);
        $pager = new ShowPager($total,$pagesize,$page);

        // 查询管理员信息
        $sql = "select * from lkt_role order by add_date desc limit $start,$pagesize";
        $rr = $db->select($sql);
        if($rr){
            foreach ($rr as $k1 => $v1) {
                $list_3[$k1] = '';
                $permission = unserialize($v1->permission);
                $arr_1 = [];
                $arr_2 = [];
                $arr_3 = [];
                if($permission){
                    foreach ($permission as $a => $b){
                        $res = substr($b,0,strpos($b, '/')); // 截取第一个'/'之前的内容
                        $rew = substr($b,strpos($b,'/')+1); // 截取第一个'/'之后的内容
                        if($res == 1){
                            $arr_1[] = explode('/',$rew); // 第一级数组
                        }else if($res == 2){
                            $arr_2[] = explode('/',$rew); // 第二级数组
                        }else if($res == 3){
                            $arr_3[] = explode('/',$rew); // 第三级数组
                        }
                    }
                    foreach ($arr_1 as $k => $v){
                        $list_1 = '';
                        $list_2 = '';
                        // 查询模块表(模块名称、模块标识、模块描述)
                        $sql = "select id,title from lkt_core_menu where recycle = 0 and s_id = 0 and name = '$v[0]' order by sort,id";
                        $r = $db->select($sql);
                        if($r){
                            $list_1 .= $r[0]->title . '('; // 一级菜单名称拼接
                            $id_1 = $r[0]->id;
                            foreach ($arr_2 as $ke => $va){
                                // 根据上级id、权限信息，查询菜单名称
                                $sql = "select id,title from lkt_core_menu where recycle = 0 and s_id = '$id_1' and name = '$va[0]' and module = '$va[1]' and action = '$va[2]' order by sort,id";
                                $r_1 = $db->select($sql);
                                if($r_1){
                                    $list_2 .= $r_1[0]->title . ','; // 二级菜单名称拼接
                                }
                            }
                            $list_1 .= rtrim($list_2,','); // 一级菜单名称拼接
                            $list_1 .= ')'; // 一级菜单名称拼接
                            $list_3[$k1] .= $list_1 . ',';
                        }
                    }
                }
                $v1->permission = rtrim($list_3[$k1], ',');
            }
        }
        $url = "index.php?module=role&action=Index&pagesize=".urlencode($pagesize);;
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("list",$rr);
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