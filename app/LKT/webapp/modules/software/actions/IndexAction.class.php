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

        $sql = "select * from lkt_config where id = '1'";

        $r = $db->select($sql);

        $uploadImg = $r[0]->uploadImg; // 图片上传位置

        $upload_file = $r[0]->upload_file . '/'; // 文件上传位置



        // 查询插件表

        $sql = "select * from lkt_software";

        $r = $db->select($sql);

        $array1 = array('2' => '商业版', '1' => '运营版', '0' =>'开源版');

        $array2 = array('0' => '小程序', '1' => 'APP', '2' =>'后台');

        $software = [];

        foreach ($r as $key => $value) {

            foreach ($array2 as $k => $v) {

                if($value->type == $k){

                    $value->type = $array2[$k];

                }

                if($value->software_version ==$k){

                    $value->software_version = $array1[$k];

                }

                $software[$key] = $value;

            }

        }

        $request->setAttribute("uploadImg",$uploadImg);

        $request->setAttribute("upload_file",$upload_file);

        $request->setAttribute("list",$software);

        return View :: INPUT;

    }



    public function execute() {



    }



    public function getRequestMethods(){

        return Request :: NONE;

    }



}



?>