<!DOCTYPE html>



<html>



	<head>



		<meta charset="utf-8" />



		<title>创建数据库-来客电商 V2.0 系统</title>



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



				<div class="steps  active"><span class="num">4</span>安装



					<span class="arrow"></span>



				</div>



				<div class="steps"><span class="num">5</span>完成



					<span class="arrow"></span>



				</div>



			</div>



			<div class="rule_title">来客电商 V2.0 <span>安装协议</span></div>



			<div class="rules">



                



     <h1>安装中系统将在 <span id="time">10</span> 秒钟后自动安装成功</h1>



      <div id="ul-list" style="position: relative;text-align: center;line-height: 100%;padding-top: 20%;">



  			<img style="position: absolute;top: 50%;left: 50%;margin-top: -25px;margin-left: -25px;" src="style/install/images/5-121204193951.gif">



      </div>



			</div>



		    <div class="btn_group">	



		    	<div class="agree"> 安装中....</div>

		    	<div class="not_agree agree" style="background:#7CCD7C;"><a onclick="$('form').submit()">下一步</a></div>



		    </div>



		</div>



	</body>







	{literal}



	<script type="text/javascript">  



	    delayURL();    



	    function delayURL() { 



	        var delay = document.getElementById("time").innerHTML;



	 var t = setTimeout("delayURL()", 1000);



	        if (delay > 0) {



	            delay--;



	            document.getElementById("time").innerHTML = delay;



	        } else {



	     		clearTimeout(t); 



	            window.location.href = "index.php?action=step4";



	        }        



	    }



	</script>



	{/literal}



</html>



