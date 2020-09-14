<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {

      
		return View :: INPUT;
	}

	public function execute(){
		//获取表单提交的压缩文件
        $file = $_FILES['file'];
        //获取文件名
        $name = $file['name'];
        //获取绝对路径
        $path = getcwd().'/';
        // print_r( getcwd());die;
        //定义文件保存路径
        $filepath= $path.'zip/'.$name;
        //使用PHP函数移动文件
        $res = move_uploaded_file($file['tmp_name'],$filepath);
        //实例化ZipArchive类
        $zip = new ZipArchive();
        //打开压缩文件，打开成功时返回true
        if ($zip->open($filepath) === true) {
            //解压文件到获得的路径a文件夹下
            $zip->extractTo($path.'zip/');
            //关闭
            $zip->close();
                $name =str_replace(strrchr($name, "."),"",$name);//文件夹名称
                $filepath= getcwd().'\zip\\'.$name.'\index.php';
                require_once $filepath;//执行index文件
            // echo 'ok';
        } else {
            // echo 'error';
        }

	    return;
	}








	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>