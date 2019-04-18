<!DOCTYPE html>



<html>



	<head>



		<meta charset="utf-8" />



		<title>创建数据库-laiketui</title>



	<link type="text/css" href="style/install/css/index.css" rel="stylesheet">



	<link type="text/css" href="style/install/css/style.css" rel="stylesheet">



	<script type="text/javascript" src="style/install/js/jquery.min.js" ></script>



	</head>



	<body>



		<div class="border">



			<div class="header">



				<span class="logo"></span>



				<span class="intro">安装向导</span>



			</div>



			<div class="install_steps">



				<div class="steps  active"><span class="num">1</span>安装协议



					<span class="arrow"></span>



				</div>



				<div class="steps  active"><span class="num">2</span>环境检测



					<span class="arrow"></span>



				</div>



				<div class="steps active"><span class="num">3</span>创建数据库



					<span class="arrow"></span>



				</div>



				<div class="steps"><span class="num">4</span>安装



					<span class="arrow"></span>



				</div>



				<div class="steps"><span class="num">5</span>完成



					<span class="arrow"></span>



				</div>



			</div>



			<div class="rule_title">来客电商 V2.0 <span>安装协议</span></div>



			<div class="rules">



	



                



        <h1 style="text-align:center;">创建数据库</h1>



    <form action="index.php?action=step3" method="post">



        <div class="item">



            <h2>数据库连接信息</h2>



			<div>数据库连接类型，服务器支持的情况下建议使用mysqli</div>



                <select name="db[]">



				<option>mysql</option>



                    <option>mysqli</option>



	                



                </select>



               



           



          



			    <div>数据库服务器，数据库服务器IP，一般为127.0.0.1</div>



                <input name="db[]" id='localhost' value="127.0.0.1" type="text">



              



          



           



			    <div>数据库名</div>



                <input name="db[]" value="" type="text">



           



			    <div>数据库用户名</div>



                <input name="db[]" id='user' value="root" type="text">



          



       			<div>数据库密码</div>



                <input name="db[]" id='password' value="" type="password">



               



            



			<div>数据库端口，数据库服务连接端口，一般为3306</div>



                <input name="db[]" value="3306" type="text">



              



           



			   <!-- <div>数据表前缀，同一个数据库运行多个系统时请修改为不同的前缀</div> -->



                <input name="db[]" value="lkt_" type="hidden">



            



        </div>







        <div class="item">



            <h2>管理员帐号信息</h2>



          



  			    <div>用户名</div>



                <input name="admin[]" value="laiketui" type="text">



              



           



			    <div>密码</div>



                <input name="admin[]" value="laiketui" type="text">



             



			    <div>确认密码</div>



                <input name="admin[]" value="laiketui" type="password">



          



			    <div>邮箱，请填写正确的邮箱便于收取提醒邮件</div>



                <input name="admin[]" value="admin@laiketui.com" type="text">



        </div>



        <div class="item">



            <h2>系统信息</h2>



  			    <div>域名地址</div>



                <input name="url" value="{$url}" type="text"> 



        </div>







    </form>



			</div>



		    <div class="btn_group">    	



		    	<div class="agree">   <a  href="index.php?action=step2">上一步</a></div>



		    	<div class="not_agree agree" style="background:#7CCD7C;"><a onclick="submit(this)">下一步</a></div>



		    </div>



		</div>



	</body>



</html>



<script>



{literal}



function submit(obj){



	$(".load").show();



	$(obj).text("安装中,大概用时30秒.............");



    $('form').submit();



}







function test(obj){



	$(".load").show();



	var localhost = $("#localhost").val();



	var user = $("#user").val();



	var pass = $("#password").val()



	$.ajax({ 



		type: "POST", 



		url: "index.php?action=step3&m=test", 



		data: 'localhost='+localhost+'&user='+user+'&pass='+pass, 



		success: function(e){ 



			if(e){



				alert( "OK " + e ); 



			}else{



				alert( "NO " + e ); 



			}



		} 



	});



}



{/literal}







</script>