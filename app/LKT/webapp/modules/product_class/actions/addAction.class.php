<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */

require_once MO_LIB_DIR . '/DBAction.class.php';

class addAction extends Action
{

    //递归找上级
    public function str_option($level, $sid, $str_option, $num)
    {
        if ($num > 0) {
            $db   = DBAction::getInstance();
            $sqlc = "select sid,cid,pname from lkt_product_class where recycle = 0 and cid = '$sid'";
            $res  = $db->select($sqlc);
            if ($res) {
                $sidc                     = $res[0]->sid; // 上级id
                $sqlcd                    = "select sid,cid,pname from lkt_product_class where recycle = 0 and sid = '$sidc'";
                $resc                     = $db->select($sqlcd);
                $str_option[$res[0]->cid] = $resc;
                $cnum                     = $num - 1;
                return $this->str_option($level, $sidc, $str_option, $cnum);
            } else {
                return $str_option;
            }
        } else {
            return $str_option;
        }
    }

    public function getDefaultView()
    {

        $db      = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 接收分类id
        $cid       = intval($request->getParameter("val")); // 分类id
        $uploadImg = $this->getContext()->getStorage()->read('uploadImg'); // 图片上传位置

        $level = 0;

        // 根据分类id,查询产品分类表
        $sql = "select * from lkt_product_class where recycle = 0 and cid = '$cid'";
        $r   = $db->select($sql);
        $co  = '';
        if ($cid) {
            $co = " and cid = '$cid'";
        }
        $level001 = $db->select("select max(level) as level  from lkt_product_class where recycle = 0 " . $co);
        $level01  = 0;
        if ($cid) {
            $level01 = $level001[0]->level + 1;
        }
        if ($r) {
            $sid   = $r[0]->sid; // 上级id
            $level = $r[0]->level + 1;
        }

        $str_option = [];
        if ($level >= 1) {
            $cid_r      = $sid;
            $str_option = $this->str_option($level, $cid, $str_option, $level);
        } else {
            $sqlcd            = "select * from lkt_product_class where recycle = 0 and sid = '0'";
            $resc             = $db->select($sqlcd);
            $str_option[$cid] = $resc;
        }
        //获取网页地址
        $dd   = $_SERVER['PHP_SELF'];
        $ddd  = explode('/', $dd); //打散成数组
        $dddd = array_pop($ddd); //去除数组最后一个元素
        if ($ddd) {
            $pic = implode('/', $ddd);

        } else {
            $pic = "/LKT";
        }
        $pic = str_replace('..', '', $pic);
        $request->setAttribute('pic', $pic . '/images');
        $request->setAttribute('cid', $cid);
        $json = json_encode($str_option);
        $request->setAttribute("str_option", $json);
        $request->setAttribute('cid_r', $cid);
        $request->setAttribute('level', $level);
        $request->setAttribute('level01', $level01);
        $request->setAttribute('uploadImg', $uploadImg);
        return View::INPUT;
    }

    public function execute()
    {
        $db      = DBAction::getInstance();
        $request = $this->getContext()->getRequest();

        // 获取分类名称和排序号
        $pname = addslashes(trim($request->getParameter('pname'))); // 分类名称
        $sid   = $request->getParameter('val'); // 产品类别
        $level = $request->getParameter('level'); // 级别
        $image = addslashes(trim($request->getParameter('image'))); // 图片
        $sort  = floatval(trim($request->getParameter('sort'))); // 排序
        $bg    = addslashes(trim($request->getParameter('bg'))); // 展示图片

        $select_c = $request->getParameter('select_c'); // 级别
        $select_1 = $request->getParameter('select_1'); // 级别
        $select_2 = $request->getParameter('select_2'); // 级别
        $select_3 = $request->getParameter('select_3'); // 级别
        $select_4 = $request->getParameter('select_4'); // 级别
        $select_5 = $request->getParameter('select_5'); // 级别
        if ($select_c == 1 && $select_1 == 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('上级分类不能为空！');" .
                "</script>";
            return $this->getDefaultView();

        }
        if ($select_c == 2 && $select_2 == 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('上级分类不能为空！');" .
                "</script>";
            return $this->getDefaultView();

        }
        if ($select_c == 3 && $select_3 == 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('上级分类不能为空！');" .
                "</script>";
            return $this->getDefaultView();

        }
        if ($select_c == 4 && $select_4 == 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('上级分类不能为空！');" .
                "</script>";
            return $this->getDefaultView();

        }
        if ($select_c == 5 && $select_5 == 0) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('上级分类不能为空！');" .
                "</script>";
            return $this->getDefaultView();

        }

        if ($image) {
            $image = preg_replace('/.*\//', '', $image);
        }
        if ($bg) {
            $bg = preg_replace('/.*\//', '', $bg);
        }
        if ($pname == '') {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('产品分类名称不能为空！');" .
                "</script>";
            return $this->getDefaultView();
        }

        //添加分类
        $sql = "insert into lkt_product_class(pname,sid,img,bg,level,sort,add_date) "
            . "values('$pname','$sid','$image','$bg','$level','$sort',CURRENT_TIMESTAMP)";
        $r = $db->insert($sql);

        if ($r == -1) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，添加产品分类失败！');" .
                "</script>";
            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('添加产品分类成功！');" .
                "location.href='index.php?module=product_class&action=Index&cid=$sid';</script>";
            return $this->getDefaultView();
        }
        return;
    }

    public function getRequestMethods()
    {
        return Request::POST;
    }

    public function menu_merge($data, $parent_id = 0, $level = 1)
    {
        $res = array();
        foreach ($data as $v) {
            if ($v['sid'] == $parent_id) {
                $v['level']    = $level;
                $v['disabled'] = false;
                $res[]         = $v;
                $res           = array_merge($res, menu_merge($data, $v['id'], $level + 1));
            }
        }
        return $res;
    }
}
