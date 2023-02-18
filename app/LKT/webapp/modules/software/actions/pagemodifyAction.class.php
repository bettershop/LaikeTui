<?php



/**



 * [Laike System] Copyright (c) 2018 laiketui.com



 * Laike is not a free software, it under the license terms, visited http://www.laiketui.com/ for more details.



 */



require_once(MO_LIB_DIR . '/DBAction.class.php');



class pagemodifyAction extends Action {


	public function getDefaultView() {



        $db = DBAction::getInstance();



        $request = $this->getContext()->getRequest();



        // 接收信息



        $id = intval($request->getParameter("id")); // 轮播图id



        $yimage = addslashes(trim($request->getParameter('yimage'))); // 原图片路径带名称



        $uploadImg = substr($yimage,0,strripos($yimage, '/')) . '/'; // 图片路径
        // 根据轮播图id，查询轮播图信息
        $sql = "select * from lkt_index_page where id = '$id'";
        $r = $db->select($sql);
        if($r){
            $image = $r[0]->image; // 轮播图
            $url = $r[0]->url ; // 链接
            $sort = $r[0]->sort; // 排序
            $type = $r[0]->type;

        }
        if($url == ''){
            $url = '#';
        }
// print_r($url);die;
        //产品显示选择
        $sql = 'select a.id,a.product_title,a.sort,a.add_date from lkt_product_list AS a order by a.sort,a.id ';
        $products = $db->select($sql);

        // 查询分类表，根据sort顺序排列
        $sql = "select * from lkt_product_class where sid = 0 order by sort desc";
        $rr = $db->select($sql);

        $list = [];
        // $list['url']
        foreach ($rr as $key => $value) {
             $dd = $this->sel($db,$value->cid);
               if($dd && $url !=$value->cid){
                unset($rr[$key]);
               }else{
                   array_push($list, $value);
                   // print_r($list);die;
                   $list = $this->category($list,$value->cid,$key,$num = 0,$url);
            }
        }

        // 获取文章信息
        $sql_2 = "select Article_id,Article_prompt,Article_title from lkt_article";
        $article = $db->select($sql_2);

        $request->setAttribute('article', $article);      
        $request->setAttribute("list",$list);
        $request->setAttribute('products', $products);
        $request->setAttribute("uploadImg",$uploadImg);
        $request->setAttribute("image",$image);
        $request->setAttribute('id', $id);
        $request->setAttribute('url', $url);
        $request->setAttribute('sort', $sort);
        $request->setAttribute('type', $type);
        return View :: INPUT;
	}

    public function sel($db,$cid){
        // print_r("select * from lkt_index_page where type ='category' and url =$cid");
        $r = $db->select("select * from lkt_index_page where type ='category' and url =$cid");
        if($r){
            return 1;
        }else{
            return;
        }

    }


    public function category($list,$cid,$k,$num = 0,$url)
    {
        $num++;
        $db = DBAction::getInstance();
        $request = $this->getContext()->getRequest();
        // 查询分类表，根据sort顺序排列
        $sql = "select * from lkt_product_class where sid = '$cid' order by sort,cid";
        $rr = $db->select($sql);
        foreach ($rr as $key => $value) {
            $dd = $this->sel($db,$value->cid);
               if($dd && $url !=$value->cid){
                unset($rr[$key]);
               }else{
                $str = '|—';
                   for ($i=0; $i < $num; $i++) { 
                      $str .= '—';
                   }
               $value->str = $str;
               array_push($list, $value);
               $sql = "select * from lkt_product_class where sid = '$value->cid' order by sort,cid";
               $rs = $db->select($sql);
               if($rs){
                   $list = $this->category($list,$value->cid,$k,$num+1,$url);
               }
           }
        }

        return $list;
    }



	public function execute(){
		$db = DBAction::getInstance();
		$request = $this->getContext()->getRequest();
        // 接收信息
		    $id = intval($request->getParameter('id'));
        $sort = floatval(trim($request->getParameter('sort'))); // 排序
        $product_class = trim($request->getParameter('product_class')); // 分类
        $sql = "update lkt_index_page set url = '$product_class', sort = '$sort',type = 'category' where id = '$id'";
		//更新数据表
		
		$r = $db->update($sql);
		if($r == -1) {
		echo "<script type='text/javascript'>" .
				"alert('未知原因，修改失败！');" .
				"location.href='index.php?module=software&action=pageindex';</script>";
			return $this->getDefaultView();

		}else {

			header("Content-type:text/html;charset=utf-8");

			echo "<script type='text/javascript'>" .

				"alert('修改成功！');" .

				"location.href='index.php?module=software&action=pageindex';</script>";
		}
		return;
	}







	public function getRequestMethods(){



		return Request :: POST;



	}







}







?>