<?php

/**

 * [Laike System] Copyright (c) 2018 laiketui.com

 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.

 */
require_once(MO_LIB_DIR . '/DBAction.class.php');
require_once(MO_LIB_DIR . '/ShowPager.class.php');
require_once(MO_LIB_DIR . '/Tools.class.php');

class IndexAction extends Action {

    public function getDefaultView() {
        $db = DBAction::getInstance();

        $request = $this->getContext()->getRequest();

        $m =$request->getParameter('m');

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

        if(!empty($m)){
            if($m == 'del_simg'){
                 $res = $this-> del_simg();
                 echo $res;
                 exit;
            }
        }
        // 查询推广图，根据sort顺序排列
        $sql = "select * from lkt_extension order by sort";
        $r_pager = $db->select($sql);
        $total = count($r_pager);
        $pager = new ShowPager($total,$pagesize,$page);

        $sql = "select * from lkt_extension order by add_date limit $start,$pagesize ";
        $r = $db->select($sql);

        // 查询配置表信息
        $sql = "select * from lkt_config where id = 1";
        $r_1 = $db->select($sql);
        $uploadImg_domain = $r_1[0]->uploadImg_domain; // 图片上传域名
        $uploadImg = $r_1[0]->uploadImg; // 图片上传位置
        if(strpos($uploadImg,'../') === false){ // 判断字符串是否存在 ../
            $img = $uploadImg_domain . $uploadImg; // 图片路径
        }else{ // 不存在
            $img = $uploadImg_domain . substr($uploadImg,2); // 图片路径
        }
        $url = "index.php?module=extension&action=Index&pagesize=".urlencode($pagesize);
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

    public function del_simg()
    {
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        $imgDir = 'product_share_img/';
        $sql = "select * from lkt_config where id=1";
        $r = $db->select($sql);
        if($r){
            $uploadImg = $r[0]->uploadImg; // 图片上传位置
        }else{
            $uploadImg = '';
        }

        $path  = $uploadImg.$imgDir;
        //如果是目录则继续
        if(is_dir($path)){
        //扫描一个文件夹内的所有文件夹和文件并返回数组
        $p = scandir($path);
        foreach($p as $val){
        //排除目录中的.和..
        if($val !="." && $val !=".."){
         //如果是目录则递归子目录，继续操作
         if(is_dir($path.$val)){
          //子目录中操作删除文件夹和文件
          deldir($path.$val.'/');
          //目录清空后删除空文件夹
          @rmdir($path.$val.'/');
         }else{
          //如果是文件直接删除
          unlink($path.$val);
         }
        }
        }
        }
        return 1;
    }

}

?>