<?php

/**
 * [Laike System] Copyright (c) 2018 laiketui.com
 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.
 */

require_once(MO_LIB_DIR . '/DBAction.class.php');

class pageaddAction extends Action
{

    public function getDefaultView()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $sql = "select * from lkt_config where id = '1'";
        $r = $db->select($sql);
        $uploadImg = $r[0]->uploadImg; // 图片上传位置
        //产品显示选择
        $sql = 'select a.id,a.product_title,a.sort,a.add_date from lkt_product_list AS a order by a.sort,a.id ';
        $products = $db->select($sql);

        // 查询分类表，根据sort顺序排列
        $sql = "select * from lkt_product_class where sid = 0 order by sort desc";
        $rr = $db->select($sql);

        $list = [];
        foreach ($rr as $key => $value) {
            $dd = $this->sel($db, $value->cid);
            if ($dd) {
                unset($rr[$key]);
            } else {
                array_push($list, $value);
                $list = $this->category($list, $value->cid, $key);
            }

        }

        // 获取文章信息
        $sql_2 = "select Article_id,Article_prompt,Article_title from lkt_article";
        $article = $db->select($sql_2);
        $request->setAttribute('article', $article);
        $request->setAttribute("list", $list);
        $request->setAttribute('products', $products);
        $request->setAttribute("uploadImg", $uploadImg);
        return View :: INPUT;


    }


    public function sel($db, $cid)
    {
        $r = $db->select("select * from lkt_index_page where type ='category' and url =$cid");
        if ($r) {
            return 1;
        } else {
            return;
        }

    }

    public function category($list, $cid, $k, $num = 0)
    {
        $num++;
        $db = DBAction::getInstance();
        // 查询分类表，根据sort顺序排列
        $sql = "select * from lkt_product_class where sid = '$cid' order by sort,cid";
        $rr = $db->select($sql);
        foreach ($rr as $key => $value) {
            $dd = $this->sel($db, $value->cid);
            if ($dd) {
                unset($rr[$key]);
            } else {

                $str = '|—';
                for ($i = 0; $i < $num; $i++) {
                    $str .= '—';
                }
                $value->str = $str;
                array_push($list, $value);
                $sql = "select * from lkt_product_class where sid = '$value->cid' order by sort,cid";
                $rs = $db->select($sql);
                if ($rs) {
                    $list = $this->category($list, $value->cid, $k, $num + 1);
                }
            }
        }

        return $list;
    }


    public function execute()
    {


        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 接收数据 
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $product_class = trim($request->getParameter('product_class')); // 分类

        if (!$sort) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('排序号不能为空！');" .
                "location.href='index.php?module=software&action=pageadd';</script>";
            return $this->getDefaultView();
        }
        $sql = "insert into lkt_index_page(type,url,sort,add_date) values('category','$product_class','$sort',CURRENT_TIMESTAMP)";

        $r = $db->insert($sql);
        if ($r == -1) {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('未知原因，添加失败！');" .
                "</script>";
            return $this->getDefaultView();
        } else {
            header("Content-type:text/html;charset=utf-8");
            echo "<script type='text/javascript'>" .
                "alert('添加成功！');" .
                "location.href='index.php?module=software&action=pageindex';</script>";
            return $this->getDefaultView();
        }
        return;

    }

    public function getRequestMethods()
    {


        return Request :: POST;


    }


}


?>