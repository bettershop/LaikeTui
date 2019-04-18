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
        $sql = "select m.name,n.* from (select s.id,u.name from lkt_system_message as s left join lkt_admin as u on s.senderid=u.id) as m left join (select r.title,r.content,r.time,r.id,a.user_name  as r_name ,a.headimgurl from lkt_system_message as r left join lkt_user as a on r.recipientid=a.id) as n on m.id=n.id ORDER BY  time desc";
        $r = $db->select($sql);


		$request->setAttribute("re",$r);

        return View :: INPUT;
    }

    public function execute() {

    }

    public function getRequestMethods(){
        return Request :: NONE;
    }

}

?>