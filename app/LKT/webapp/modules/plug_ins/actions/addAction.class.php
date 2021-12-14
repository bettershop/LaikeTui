<?php
require_once(MO_LIB_DIR . '/DBAction.class.php');

class addAction extends Action {

	public function getDefaultView() {
        return View :: INPUT;
	}

	public function execute(){
		$file = $_FILES['file'];
        $name = $file['name'];
        $path = getcwd().'/';
        $filepath= $path.'zip/'.$name;
        $res = move_uploaded_file($file['tmp_name'],$filepath);
        $zip = new ZipArchive();
        if ($zip->open($filepath) === true) {
            $zip->extractTo($path.'zip/');
            $zip->close();
            $name =str_replace(strrchr($name, "."),"",$name);
            $filepath= getcwd().'\zip\\'.$name.'\index.php';
            require_once $filepath;
        } 
	    return;
	}


	public function getRequestMethods(){
		return Request :: POST;
	}

}

?>