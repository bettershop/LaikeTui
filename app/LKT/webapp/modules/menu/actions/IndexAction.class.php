<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $cart_id = $request->getParameter('cart_id'); // 菜单id
        $title = $request->getParameter('title'); // 菜单名称

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

        $condition = ' 1 = 1 ';

        if($cart_id != ''){
            $condition .= " and id = '$cart_id' ";
        }
        if($title != ''){
            $condition .= " and title = '$title'  ";
        }
        $rew = [];
        $res = '';

        $num = 0;
        $sql = "select * from lkt_core_menu where $condition and recycle = 0 order by sort,id ";
        $r_pager = $db->select($sql);


        $sql = "select * from lkt_core_menu where $condition and recycle = 0 and s_id = 0 order by id";
        $r = $db->select($sql);
        if($r){
            foreach ($r as $k => $v){
                $r_title = $v->title;
                if($r_title == '商品管理'){
                    $res = 'SP_';
                }elseif ($r_title == '订单管理'){
                    $res = 'DD_';
                }elseif ($r_title == '财务管理'){
                    $res = 'CW_';
                }elseif ($r_title == '用户管理'){
                    $res = 'YH_';
                }elseif ($r_title == '配置管理'){
                    $res = 'PZ_';
                }elseif ($r_title == '系统管理'){
                    $res = 'XT_';
                }elseif ($r_title == '插件管理'){
                    $res = 'CJ_';
                }elseif ($r_title == '小程序'){
                    $res = 'XCX_';
                }elseif ($r_title == 'app'){
                    $res = 'APP_';
                }
                $id = $v->id;
                $v->id_id = $res . $v->id;
                $v->s_id = $res . $v->s_id;
                $rew[$num++] = $v;

                $sql = "select * from lkt_core_menu where s_id = '$id' and recycle = 0 order by id";
                $rr = $db->select($sql);
                if($rr){
                    foreach ($rr as $ke => $va) {
                        $id1 = $va->id;
                        $va->id_id = $res . $va->id;
                        $va->s_id = $res . $va->s_id;

                        $rew[$num++] = $va;
                        if($va->level != 4){
                            $sql = "select * from lkt_core_menu where s_id = '$id1' and recycle = 0 order by id";
                            $rrr = $db->select($sql);
                            if($rrr){
                                foreach ($rrr as $key => $val){
                                    $id2 = $val->id;
                                    $val->id_id = $res . $val->id;
                                    $val->s_id = $res . $val->s_id;

                                    $rew[$num++] = $val;
                                    if($val->level != 4){
                                        $sql = "select * from lkt_core_menu where s_id = '$id2' and recycle = 0 order by id";
                                        $rrrr = $db->select($sql);
                                        if($rrrr){
                                            foreach ($rrrr as $key1 => $val1){
                                                $val1->id_id = $res . $val1->id;
                                                $val1->s_id = $res . $val1->s_id;

                                                $rew[$num++] = $val1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            $sql = "select * from lkt_core_menu where $condition and recycle = 0 order by id";
            $r1 = $db->select($sql);
            if($r1){
                $s_id = $r1[0]->s_id;
                $level = $r1[0]->level;
                $sql = "select * from lkt_core_menu where id = '$s_id'";
                $r2 = $db->select($sql);
                if($r2[0]->s_id == 0){
                    $r_title = $r2[0]->title;
                }else{
                    $s_id = $r2[0]->s_id;

                    $sql = "select * from lkt_core_menu where id = '$s_id'";
                    $r3 = $db->select($sql);

                    if($r3[0]->s_id == 0){
                        $r_title = $r3[0]->title;
                    }else{
                        $s_id = $r3[0]->s_id;
                        $sql = "select * from lkt_core_menu where id = '$s_id'";
                        $r4 = $db->select($sql);
                        if($r4[0]->s_id == 0){
                            $r_title = $r4[0]->title;
                        }
                    }
                }
                if($r_title == '商品管理'){
                    $res = 'SP_';
                }elseif ($r_title == '订单管理'){
                    $res = 'DD_';
                }elseif ($r_title == '财务管理'){
                    $res = 'CW_';
                }elseif ($r_title == '用户管理'){
                    $res = 'YH_';
                }elseif ($r_title == '配置管理'){
                    $res = 'PZ_';
                }elseif ($r_title == '系统管理'){
                    $res = 'XT_';
                }elseif ($r_title == '插件管理'){
                    $res = 'CJ_';
                }elseif ($r_title == '小程序'){
                    $res = 'XCX_';
                }elseif ($r_title == 'app'){
                    $res = 'APP_';
                }
                $id = $r1[0]->id;
                $r1[0]->id_id = $res . $r1[0]->id;
                $r1[0]->s_id = $res . $r1[0]->s_id;
                $rew[0] = $r1[0];
                $num = 1;
                if($level == 2){
                    $sql = "select * from lkt_core_menu where s_id = '$id' and recycle = 0 order by id";
                    $rr = $db->select($sql);
                    if($rr){
                        foreach ($rr as $ke => $va) {
                            $id1 = $va->id;
                            $va->id_id = $res . $va->id;
                            $va->s_id = $res . $va->s_id;

                            $rew[$num++] = $va;
                            if($va->level != 4){
                                $sql = "select * from lkt_core_menu where s_id = '$id1' and recycle = 0 order by id";
                                $rrr = $db->select($sql);
                                if($rrr){
                                    foreach ($rrr as $key => $val){
                                        $id2 = $val->id;
                                        $val->id_id = $res . $val->id;
                                        $val->s_id = $res . $val->s_id;

                                        $rew[$num++] = $val;
                                        if($val->level != 4){
                                            $sql = "select * from lkt_core_menu where s_id = '$id2' and recycle = 0 order by id";
                                            $rrrr = $db->select($sql);
                                            if($rrrr){
                                                foreach ($rrrr as $key1 => $val1){
                                                    $val1->id_id = $res . $val1->id;
                                                    $val1->s_id = $res . $val1->s_id;

                                                    $rew[$num++] = $val1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if($level == 3){
                    $sql = "select * from lkt_core_menu where s_id = '$id' and recycle = 0 order by id";
                    $rr = $db->select($sql);
                    if($rr){
                        foreach ($rr as $ke => $va) {
                            $id1 = $va->id;
                            $va->id_id = $res . $va->id;
                            $va->s_id = $res . $va->s_id;

                            $rew[$num++] = $va;
                            if($va->level != 4){
                                $sql = "select * from lkt_core_menu where s_id = '$id1' and recycle = 0 order by id";
                                $rrr = $db->select($sql);
                                if($rrr){
                                    foreach ($rrr as $key => $val){
                                        $id2 = $val->id;
                                        $val->id_id = $res . $val->id;
                                        $val->s_id = $res . $val->s_id;

                                        $rew[$num++] = $val;
                                        if($val->level != 4){
                                            $sql = "select * from lkt_core_menu where s_id = '$id2' and recycle = 0 order by id";
                                            $rrrr = $db->select($sql);
                                            if($rrrr){
                                                foreach ($rrrr as $key1 => $val1){
                                                    $val1->id_id = $res . $val1->id;
                                                    $val1->s_id = $res . $val1->s_id;

                                                    $rew[$num++] = $val1;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        $total = count($rew);
        $pager = new ShowPager($total,$pagesize,$page);

        $list = array_slice($rew,$start,$pagesize);

        $url = "index.php?module=menu&action=Index&cart_id=".urlencode($cart_id)."&title=".urlencode($title)."&pagesize=".urlencode($pagesize);
        $pages_show = $pager->multipage($url,$total,$page,$pagesize,$start,$para = '');

        $request->setAttribute("cart_id",$cart_id);
        $request->setAttribute("title",$title);
        $request->setAttribute("list",$list);
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