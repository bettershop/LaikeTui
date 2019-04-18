<!DOCTYPE html>



<html>



	<head>



		<meta charset="utf-8" />



		<title>环境检测-来客电商 V2.0  商城管理系统</title>



	<link type="text/css" href="style/install/css/index.css" rel="stylesheet">



	<link type="text/css" href="style/install/css/style.css" rel="stylesheet">



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



				<div class="steps"><span class="num">3</span>创建数据库



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



			 <h1 style="text-align:center;">环境检测</h1>



       <table class="table">



        <caption><h2>运行环境检查</h2></caption>



        <thead>



            <tr>



                <th>项目</th>



                <th>所需配置</th>



                <th>当前配置</th>



            </tr>



        </thead>



        <tbody>



            {foreach from=$functions item=item name=f1}



                <tr>



                    <td>{$item[0]}</td>



                    <td>{$item[1]}</td>



                    <td><i class="iconfont ico-{$item[4]}">&nbsp;</i>{$item[3]}</td>       



                </tr>



            {/foreach}



        </tbody>



    </table>



    <!-- /需要判断/ -->



    <table class="table">



        <caption><h2>目录、文件权限检查</h2></caption>



        <thead>



            <tr>



                <th>目录/文件</th>



                <th>所需状态</th>



                <th>当前状态</th>



            </tr>



        </thead>



        <tbody>



             {foreach from=$files item=item name=f1}



                <tr>



                    <td>{$item[3]}</td>



                    <td><i class="iconfont ico-success">&nbsp;</i>可写</td>



                    <td><i class="iconfont ico-{$item[2]}">&nbsp;</i>{$item[1]}</td>   



                </tr>



            {/foreach}



        </tbody>



    </table>



	<!-- /需要判断/ -->



    <table class="table">



        <caption><h2>函数依赖性检查</h2></caption>



        <thead>



            <tr>



                <th>函数名称</th>



                <th>检查结果</th>



            </tr>



        </thead>



        <tbody>



            {foreach from=$func item=item name=f1}



                <tr>



                    <td>{$item[0]}()</td>



                    <td><i class="iconfont ico-{$item[2]}">&nbsp;</i>{$item[1]}</td>



                </tr>



            {/foreach}



        </tbody>



    </table>



			</div>



            <div class="btn_group">     



                <div class="agree">   <a onClick="javascript :history.back(-1);">返回上一页</a></div>



                <div class="not_agree agree" style="background:#7CCD7C;"><a href="index.php?action=step2">下一步</a></div>



            </div>



		</div>



	</body>



</html>



