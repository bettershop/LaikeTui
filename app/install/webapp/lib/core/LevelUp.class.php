<?php


//更新升级**************************************************************************
function levelUp($lt,$rt,$db){			
			$sql_uplevel="select a.node,b.node_left,b.node_center,b.node_right,(select uplevel from ntb_user c where c.user_id=a.node) as uplevel,"
			."(select pv from ntb_duipeng d where d.userid=a.node) as pv from ntb_anzhi a left join "
			." ntb_board_face b on a.node=b.node where a.lt <= '$lt' and a.rt >= '$rt' order by a.rt" ;
			$r_uplevel = $db->select($sql_uplevel);
			if($r_uplevel)
			{
			    foreach($r_uplevel as $list)
				{
				   $node= $list->node;
				   $pv= $list->pv;
				   $uplevel= $list->uplevel;
			       $node_left= $list->node_left;
				   $node_center= $list->node_center;
				   $node_right= $list->node_right;

				   $left_num1=0;
				   $left_num2=0;
				   $left_num3=0;
				   $left_num4=0;
				   $left_num5=0;
				   $left_num6=0;
				   $left_num7=0;
				   $left_num8=0;
				   $left_num9=0;
				   $left_num10=0;

				   $center_num1=0;
				   $center_num2=0;
				   $center_num3=0;
				   $center_num4=0;
				   $center_num5=0;
				   $center_num6=0;
				   $center_num7=0;
				   $center_num8=0;
				   $center_num9=0;
				   $center_num10=0;

				   $right_num1=0;
				   $right_num2=0;
				   $right_num3=0;
				   $right_num4=0;
				   $right_num5=0;
				   $right_num6=0;
				   $right_num7=0;
				   $right_num8=0;
				   $right_num9=0;
				   $right_num10=0;
				   
					  
                  
				   if($node_left!="")
				   {
				      $sql="select lt,rt from ntb_anzhi where node='$node_left'";
					  $r= $db->select($sql);
					  
					  $sql2=Get_level($r[0]->lt,$r[0]->rt);
					 
					  $r= $db->select($sql2);

                       $left_num1=$r[0]->num1;
					   $left_num2=$r[0]->num2;
					   $left_num3=$r[0]->num3;
					   $left_num4=$r[0]->num4;
					   $left_num5=$r[0]->num5;
					   $left_num6=$r[0]->num6;
					   $left_num7=$r[0]->num7;
					   $left_num8=$r[0]->num8;
					   $left_num9=$r[0]->num9;
					   $left_num10=$r[0]->num10;

				   }

				   if($node_center!="")
				   {
				      $sql="select lt,rt from ntb_anzhi where node='$node_center'";
					  $r= $db->select($sql);
					  
					  $sql2=Get_level($r[0]->lt,$r[0]->rt);
					  $r= $db->select($sql2);

                       $center_num1=$r[0]->num1;
					   $center_num2=$r[0]->num2;
					   $center_num3=$r[0]->num3;
					   $center_num4=$r[0]->num4;
					   $center_num5=$r[0]->num5;
					   $center_num6=$r[0]->num6;
					   $center_num7=$r[0]->num7;
					   $center_num8=$r[0]->num8;
					   $center_num9=$r[0]->num9;
					   $center_num10=$r[0]->num10;

				   }

				   if($node_right!="")
				   {
				      $sql="select lt,rt from ntb_anzhi where node='$node_right'";
					  $r= $db->select($sql);
					  
					  $sql2=Get_level($r[0]->lt,$r[0]->rt);
					   
					  $r= $db->select($sql2);

                       $right_num1=$r[0]->num1;
					   $right_num2=$r[0]->num2;
					   $right_num3=$r[0]->num3;
					   $right_num4=$r[0]->num4;
					   $right_num5=$r[0]->num5;
					   $right_num6=$r[0]->num6;
					   $right_num7=$r[0]->num7;
					   $right_num8=$r[0]->num8;
					   $right_num9=$r[0]->num9;
					   $right_num10=$r[0]->num10;

				   }
                   
                   $level=0;
				   if($left_num10==1 && $center_num10==1 && $right_num10==1)
				   {
				      $level=12;
				   }
				   else  if($left_num9==1 && $center_num9==1 && $right_num9==1)
				   {
				      $level=11;
				   }
				   else  if($left_num8==1 && $center_num8==1 && $right_num8==1 && $pv>=45000000)
				   {
				      $level=10;
				   }
				   else  if($left_num7==1 && $center_num7==1 && $right_num7==1 && $pv>=15000000)
				   {
				      $level=9;
				   }
				   else  if($left_num6==1 && $center_num6==1 && $right_num6==1 && $pv>=5400000)
				   {
				      $level=8;
				   }
				   else  if($left_num5==1 && $center_num5==1 && $right_num5==1 && $pv>=1800000)
				   {
				      $level=7;
				   }
				   else  if($left_num4==1 && $center_num4==1 && $right_num4==1 && $pv>=600000)
				   {
				      $level=6;
				   }
				   else  if(($left_num3+$center_num3) ==2 && $right_num2=1 && $pv>=288000)
				   {
				      $level=5;
				   }
				   else  if(($right_num3+$center_num3) ==2 && $left_num2=1 && $pv>=288000)
				   {
				      $level=5;
				   }
				   else  if(($left_num3+$right_num3) ==2 && $center_num2=1 && $pv>=288000)
				   {
				      $level=5;
				   }
				   else if($left_num3==1 && $center_num3==1 && $pv>=96000)
				   {
				      $level=4;
				   }
				   else if($left_num2==1 && $center_num2==1 && $pv>=19200)
				   {
				      $level=3;
				   }
				   else if($left_num1==1 && $center_num1==1 && $pv>=4800)
				   {
				      $level=2;
				   }
				   else if($pv>=2400)
				   {
				      $level=1;
				   }
				   else
				   {
				     $level=0;
				   }
				  
                   //echo $node." ".$level." ".$pv." ".$left_num1." ".$center_num1."<br />";
				   if($level>$uplevel)
				   {
					   
				        $sql4 = "update ntb_user set uplevel = ".$level." where user_id = '$node'";
					    $r = $db->update($sql4);
						
				   }
			    }
				return true;
			}
			else
			{
			   return false;
			}
}


 //获得从主任 到七星董事 num1 - num10
	 function Get_level($lt,$rt)
	{
		     return "select (select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$rt' and a.rt<= '$rt' and b.uplevel=10) as num10,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=9) as num9,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=8) as num8,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=7) as num7,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=6) as num6,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=5) as num5,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=4) as num4,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=3) as num3,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=2) as num2,
(select count(b.id) as uplevel from ntb_anzhi  a left join ntb_user b on a.node=b.user_id where a.lt >= '$lt' and a.rt<= '$rt' and b.uplevel=1) as num1";
	}

?>
