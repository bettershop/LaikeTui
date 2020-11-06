<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
    {php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
    <title>添加活动</title>
    {literal}
        <style type="text/css">
            body{background-color: #edf1f5;height: 100vh;}
            　　　/*隐藏掉我们模型的checkbox*/
            .my_protocol .input_agreement_protocol {
                appearance: none;
                -webkit-appearance: none;
                outline: none;
                display: none;
            }
            /*未选中时*/
            .my_protocol .input_agreement_protocol+span {
                width: 16px;
                height: 16px;
                background-color: red;
                display: inline-block;
                background: url(../../Images/TalentsRegister/icon_checkbox.png) no-repeat;
                background-position-x: 0px;
                background-position-y: -25px;
                position: relative;
                top: 3px;
            }
            /*选中checkbox时,修改背景图片的位置*/
            .my_protocol .input_agreement_protocol:checked+span {
                background-position: 0 0px
            }
            .inputC:checked +label::before{
                top: -3px;
                position: relative;
            }

            #addpro{
                background: #ccc;
                padding: 20px;
                border-radius: 4px;
            }
            .protitle{
                overflow: hidden;
            }
            .barginprice{
                border-radius: 5px;
                width: 120px;
            }
            .redport{
                border:2px red solid;
            }
            .sysyattr{
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                z-index: 9999;
                background: rgba(0,0,0,0.5);

            }
            .sysyattr_div{
                width: 100%;
                height: 100%;
                display:flex;
            }
            .sys_c{
                width:70%;
                height: 70%;
                margin:auto;
                background: white;
                border-radius: 20px;
            }
            .modalshow{
                display: none;
            }
            .modaltitle{
                padding:10px;
                width:100%;
                height:7%;
            }
            .breadcrumb{padding: 0;margin: 0;margin-left: 10px;background-color: #edf1f5;}
            #form-category-add{padding:0 14px;}
            .text-c th{border: none;border-top: 1px solid #E9ECEF!important;vertical-align:middle!important;}
            .text-c td{border: none;border-bottom: 1px solid #E9ECEF!important;vertical-align:middle;}
            .text-c th:first-child{border-left: 1px solid #E9ECEF!important;}
            .text-c th:last-child{border-right: 1px solid #E9ECEF!important;}
            .text-c td:first-child{border-left: 1px solid #E9ECEF!important;}
            .text-c td:last-child{border-right: 1px solid #E9ECEF!important;}
            .text-c input{border: 1px solid #E9ECEF;margin: auto;padding: 2px 10px;}

            .ra1{
                /*width: 8%!important;*/
                float: left;
            }
            .ra1 label{
                width: 100px!important;
                padding-left: 20px;
                margin: auto;
                height: 36px;
                display: block;
                line-height: 36px;
            }
            input[type=text],.select{
                width: 190px;padding-left: 10px;
            }
            .fo_btn2{
                margin-right: 10px!important;
                color: #fff!important;
                background-color: #2890FF!important;
                border: 1px solid #fff;
                float: right;
                display: block;
                margin: 16px 0;
                width: 112px!important;
            }
            .inputC:checked + label::before {
                display: -webkit-inline-box;
            }
            .tab_label{padding-left: 15px!important;border-left: 1px solid #E9ECEF!important;}
            .scrolly{
                height: 300px;
                overflow-y: scroll;
            }

              .manlevel{
                margin: 11px 0;
            }
            select{
                background: #fff;
            }
            .cont{
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
                width: 250px;
                text-align: left;
            }
        </style>
    {/literal}
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe6ca;</i>
    插件管理
    <span class="c-gray en">&gt;</span>
    <a style="margin-top: 10px;" onclick="location.href='index.php?module=pi&p=pintuan&c=Home';">拼团 </a>
    <span class="c-gray en">&gt;</span>
    编辑拼团商品
</nav>
<div id="addpro" class="pd-20" style="background-color: white;">
    <p style="font-size: 15px;" class="page_title">编辑拼团商品</p>

    <form name="form1" id="form1" class="form form-horizontal" style="padding: 10px 10px 0 10px" method="post" enctype="multipart/form-data" >
        <div class="row cl">
            <label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">拼团标题：</label>
            <div class="formControls col-10">
                <input type="text" name="title" value="{$list[0]->group_title}" {if $g_status == 2 && $is_show == 0}disabled{/if}>
                <span style="margin-left: 10px;font-size: 14px;color:#A8B0CB;">（活动标题如果不填写，默认为商品名称）</span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2" style="line-height: 25px;margin-top: 0px;padding-right: 0px;">选择拼团商品：</label>
            <div class="formControls col-10">
                <select name="class" class="select" {if $g_status == 2}disabled{/if}>
                    <option value="0" selected="selected" >请选择商品分类</option>
                    {$class}
                </select>
                <select name="brand" class="select" {if $g_status == 2 }disabled{/if}>

                    <option value="0" selected="selected" >请选择品牌</option>
                    {foreach from = $brandres item = item }
                        <option value="{$item->brand_id}"  >{$item->brand_name}</option>
                    {/foreach}
                </select>
                <input type="text" name="p_name" value="" placeholder="请输入商品名称" {if $g_status == 2}disabled{/if}>
                <input  id="my_query" class="btn btn-success" type="button" style="margin-left: 5px;background-color: #2890ff!important;" value="查询" {if $g_status == 2 }disabled{/if}>
                <input  id="huanyuan" class="btn btn-success" type="button" style="margin-left: 5px;background-color: #2890ff!important;" value="还原">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2" style="line-height: 25px;margin-top: 0px;"></label>
            <div class="formControls col-10">
                <div class="tabCon1">
                    <div class="mt-20" id="prolist">
                        <table class="table table-border table-bordered table-bg table-hover" style="margin:0 auto;border: 0;">
                            <thead>
                            <tr class="text-c">
                                <th></th>
                                <th>序号</th>
                                {if $g_status == 2 }
                                <th>商品名称及规格</th>
                                {else}
                                <th>商品名称</th>
                                {/if}
                                
                                <th>商品id</th>
                                <!-- <th>商家名称</th> -->
                                <th>库存</th>
                                <!-- <th>库存预警值</th> -->
                                <th>零售价</th>
                            </tr>
                            </thead>
                            <tbody id="proattr">
                            {foreach from=$proattr item=item1 key=key}
                                <tr class="text-c add-tr" style="height:20px;">
                                <tr class="text-c" style="height:60px!important;">
                                    <input type="hidden" name="attr_id"  value="{$item1->attr_id}">
                                    <td class="tab_label">
                                        <input id="ptradio" type="radio" class="inputC input_agreement_protocol" {if $item1->select}checked{/if} attr-data="{$item1->attr_id}" id = "{$item1->id}{$key}" targ="{$item1->id + $key}" name="id[]"  value = "{$item1->id + $key}">
                                    </td>
                                    <td>{$key+1}</td>

                               {if $g_status == 2}
                                <td style="display: flex;align-items: center;">
                                        <img src ="{$item1->imgurl}" style="width: 40px;height:40px;"> <span class="cont" title="'+element.product_title+'">{$item1->product_title}    【{ $item1->attrtype}】</span>
                                    </td>
                                {else}
                                <td style="display: flex;align-items: center;">
                                        <img src ="{$item1->imgurl}" style="width: 40px;height:40px;"> <span class="cont" title="'+element.product_title+'">{$item1->product_title}    </span>
                                    </td>
                                {/if}
                                     <td>{$item1->id}</td>
                                     <!-- <td>{$item1->name}</td> -->
                                    <td>{$item1->num}</td>
                                    <!-- <td>{$item1->min_inventory}</td> -->
                                    <td>{$item1->price}</td>
                                    </tr>
                                </tr>
                            {/foreach}
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>

          <div class="row cl" >
            <label class="form-label col-xs-4 col-sm-3"  style="margin-top: 9px;padding-right: 0px!important;height: 36px;line-height: 36px;"><span class="c-red">*</span>拼团人数设置：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="formControls col-xs-10 col-sm-10" >
                <div id="setlevel">
                    <div class="manlevel">
                        <input type="number" max="50" min="1" class="input-text" value="{$lastset[2]}" name="min_man" style="width:60px;" onkeyup="onkeyup1(this)" {if $g_status == 2}disabled{/if}>&nbsp;&nbsp;人团&nbsp;&nbsp;
                        <span style="margin-left:17px;">折扣价: 参团</span>
                        <input type="number" class="input-text" value="{$lastset[0]}"  name="canprice" style="width:80px;" onkeyup="onkeyup1(this)" {if $g_status == 2}disabled{/if}>
                        %
                        <span style="margin-left: 5px;">开团</span>
                        <input type="number" class="input-text" value="{$lastset[1]}"  name="memberprice" style="width:80px;" onkeyup="onkeyup1(this)" {if $g_status == 2 }disabled{/if}>
                        %
                        <span style="margin-left: 10px;font-size: 10px;color:#97A0B4;">（拼团人数要大于1，不能设置1人团）</span>
                    </div>
                    {$levelstr}
                </div>
            
            </div>
            </div>
            <div class="col-3">
            </div>
        </div>

        <div class="row cl" >
            <label class="form-label col-xs-4 col-sm-3"style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;"><span class="c-red">*</span>开始时间：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" {if $g_status == 2 && $is_show == 1}disabled{/if}{if $g_status == 2 && $is_show == 0}disabled{/if} value="{$group_data->starttime}" autocomplete="off" placeholder="" id="group_start_time" name="starttime" style="width:180px;">
            </div>
            <div class="col-3">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-3" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;"><span class="c-red">*</span>结束时间：</label>
            <div class="formControls col-xs-8 col-sm-9">
                {if $g_status==2&&$is_show==1}
                    <input type="text" {if $g_status == 2 && $is_show == 1}disabled{/if} class="input-text" style="width:180px;" value='{$group_data->endtime}' disabled>
                {else}
                    <div style="margin-top: 0px;">
                        <input type="hidden" {if $group_data->overtype==1}value='{$group_data->endtime}'{else}value=""{/if} name='ischang'>

                        <input type="radio" value="2" placeholder="" id="endtime" name="endtime" onchange="radioChange(2)" {if $group_data->overtype==2}checked{/if}><span style="margin-left: 10px;">定期结束</span><input type="text" class="input-text" {if $group_data->overtype==2}value="{$group_data->endtime}"{else}value=""{/if} placeholder="" id="group_end_time" name="group_end_time" style="width:180px;margin-left: 10px;">
                    </div>
                    <div style="margin-top: 15px;">
                        <input type="radio" value="1" placeholder="" id="ctime" {if $group_data->overtype==1}checked{/if} name="endtime" onchange="radioChange(1)" >
                        <span style="margin-left: 10px;">长期</span>
                        <input type="text" {if $g_status == 2 && $is_show == 1}disabled{/if} class="input-text" autocomplete="off" {if $group_data->overtype==1}value='{$group_data->endtime}'{else}value=""{/if} placeholder="" id="end_year" name="end_year" style="width:180px;margin-left: 30px;" disabled>
                    </div>
                {/if}
            </div>
            <div class="col-3">
            </div>
        </div>


        <div class="page_h10" style="height: 68px!important;margin-top: 140px;border-top: 1px solid #E9ECEF;">
            {if $g_status == 2 && $is_show == 1}
            <input type="button" name="reset" value="返回" class="fo_btn1" onclick="javascript :history.back(-1);">
            {/if}
              {if $g_status != 2 || $is_show != 1}
            <input class="fo_btn2" type="button" name="Submit" value="保存" onclick="baocungroup()">
             <input type="button" name="reset" value="取消" class="fo_btn1" onclick="javascript :history.back(-1);">
            {/if}
           

        </div>
    </form>

</div>
{literal}

    <script type="text/javascript">

        function radioChange(i){
            var group_end_time = $('#group_end_time');
            if(i == 1){
                console.log(1);
                group_end_time.attr('disabled','disabled');
                group_end_time.val('');
                radio = 1;
                var startdate = $("#group_start_time").val();
                // console.log(11);

                if(startdate != '' && startdate.length == 19){
                    // console.log(111);

                    var day = startdate.split(' ');
                    var str = startdate.replace(/-/g,'/');
                    var d = new Date(str);
                    var oneYear = oneYearPast(d);
                    // console.log(1111);

                    oneYear = oneYear + ' ' + day[1];
                    console.log(oneYear);

                    $("#end_year").val(oneYear)
                }
            }else{
                // console.log(2);
                group_end_time.removeAttr('disabled');
                $("#end_year").val('');
                radio = 2;
            }
        }

        //一年后的今天的前一天
        function oneYearPast(time)
        {
            //var time=new Date();
            var year=time.getFullYear()+1;
            var month=time.getMonth()+1;
            var day=time.getDate();

            if(month<10){
                month="0"+month;
            }

            if(day>1){
                day = day;
            }else{
                month = month-1;
                if(month<10){
                    month="0"+month;
                }
                if(month==0){
                    month = 12;
                }
                day=new Date(year,month,0).getDate();
            }

            var v2=year+'-'+month+'-'+day;
            return v2;
        }

        function baocungroup(){
            var pList = '{';
            var goods_id = '';
            var each_times = 0;

            $("#ptradio").each(function () {
                // console.log('each');
                //如果选中状态
                if ($(this).is(':checked')) {
                    //判断每一次的id是否相等
                    if(goods_id == ''){
                        goods_id = $(this).val();
                        // console.log(goods_id);
                    }else if (goods_id != $(this).val()){
                        alert('请选择同一个产品id号的商品');
                        pList = [];
                        goods_id = '';
                        checkdata = false;
                        return;
                    }
                    let attr = $(this).attr('attr-data');
                    pList +='"'+attr+'":"~",';
                    each_times++;
                }
            });
            // console.log(each_times);
            if(each_times == 0){
                alert('请选择拼团商品');
                checkdata = false;
                return;
            }
            pList=pList.substring(0,pList.length-1);
            pList +='}';
            // console.log(goods_id);
            var checkdata = true;
            var gdata = {};
            var timehour = gdata['timehour'] = $("input[name=timehour]").val();
            var starttime = gdata['starttime'] = $("input[name=starttime]").val();
            var groupnum = gdata['groupnum'] = $("input[name=groupnum]").val();
            var productnum = gdata['productnum'] = $("input[name=productnum]").val();
            var overtype = gdata['overtype'] = $("input[name=endtime]:checked").val();
            var endtime = $("input[name=group_end_time]").val();

            var manlevel = $('.manlevel');
            var glevel = {};

            $.each(manlevel,function (index,element){
                var min_man = $(element).children('input[name=min_man]').val();
                var canprice = $(element).children('input[name=canprice]').val();
                var memberprice = $(element).children('input[name=memberprice]').val();
                if(glevel[min_man]){
                    alert('人数设置参数重复');
                    checkdata = false;
                    return;
                }
                  if(min_man == '' ||min_man == 'NaN'){
                    alert('人数设置参数不能为空');
                    checkdata = false;
                    return;
                }
                if(min_man < 2 ){
                    alert('拼团人数设置不合理');
                    checkdata = false;
                    return;
                }
                if(canprice == '' || memberprice == ''){
                    alert('价格设置参数不能为空');
                    checkdata = false;
                    return;
                }
                if(parseInt(canprice) < parseInt(memberprice)){
                    alert('参团价格不能小于开团价格');
                    checkdata = false;
                    return;
                }
                glevel[min_man] = canprice+'~'+memberprice;
            })
            // console.log('rule_end1');

            if(checkdata == true){
                if(timehour == ''){
                    alert('拼团倒计时设置不能为空');
                    checkdata = false;
                    return;
                }else if(starttime == ''){

                    alert('开始时间设置不能为空');
                    checkdata = false;
                    return;
                }else if($("input[name=endtime]:checked").val() == 2 && endtime == ''){

                    alert('结束时间设置不能为空');
                    checkdata = false;
                    return;
                }else if(groupnum == ''){

                    alert('团数限制不能为空');
                    checkdata = false;
                    return;
                }else if(productnum == ''){

                    alert('产品购买件数限制不能为空');
                    checkdata = false;
                    return;
                }
            }
            var g_status = "{/literal}{$list[0]->g_status}{literal}";
            var is_show = "{/literal}{$list[0]->is_show}{literal}";
            var old_goods_id = "{/literal}{$goods_id}{literal}";
             // console.log(g_status);
            // console.log(g_status);
            // console.log(is_show);
            // console.log('rule_end');
            if(checkdata == true){
                var stime = new Date(starttime).getTime();
                var now = new Date().getTime();
                  now1 = now-(3600 * 1000);
                if(overtype == '2'){
                    gdata['endtime'] = endtime;
                    var etime = new Date(endtime).getTime();
                    if(is_show !=0 || g_status!=2){
                        if(stime < now1 || etime < now || stime >= etime){
                            alert('时间设置不合格!');
                            return false;
                        }
                    }
                    
                }else{
                    gdata['endtime'] = 'changqi';

                    if(stime < now1 &&(is_show !=0 ||g_status!=2)){
                        alert('时间设置不合格!');
                        return false;
                    }
                }
                var title = $("input[name='title']").val();

                gdata = JSON.stringify(gdata);
                $.ajax({
                    url: "index.php?module=pi&p=pintuan&c=modify",
                    type: "post",
                    data: {glevel:glevel,gdata:gdata,tuanZ:pList,goods_id:goods_id,g_status:g_status,group_title:title,old_goods_id:old_goods_id},
                    dataType: "json",
                    success:function(data) {
                        if(data.code == 1){
                            alert('修改成功!');
                                window.location.href = 'index.php?module=pi&p=pintuan&c=Home';
                           
                        }else{
                            alert('未知原因,修改失败!');
                        }
                    },
                })
                return  false;
                $.ajax({
                    url:"index.php?module=pi&p=pintuan&c=addgroup",
                    type:"post",
                    data:{gdata:gdata,glevel:glevel,tuanZ:pList,goods_id:goods_id},
                    dataType:"json",
                    success:function(data) {
                        if(data.code == 1){
                            alert('添加成功!');
                                window.location.href = 'index.php?module=pi&p=pintuan&c=Home';
                            
                        }else{
                            alert('发布失败!');
                        }
                    },
                })
            }
        }
        

        window.onload = function(){

            var old_pro_html = '';

            //ajax请求--获取拼团商品列表
            $("#my_query").click(function(){

                var my_brand = $('select[name=brand] option:selected').val();
                var my_class = $('select[name=class] option:selected').val();
                var pro_name =$('input[name=p_name]').val();
                $("#prolist").removeClass('scrolly');


                $.ajax({
                    type:"POST",
                    dataType:"json",
                    data:{my_brand:my_brand,my_class:my_class,pro_name:pro_name},
                    url:"index.php?module=pi&p=pintuan&c=addproduct&m=pro_query",
                    success:function(data){

                        var res = data.res;
                        var table = '';
                        if(old_pro_html == ''){
                            old_pro_html = $("#proattr").html();
                            console.log('设置old_pro_html');
                            console.log(old_pro_html);
                        }
                        if(res.length > 0){//有拼团商品数据

                            $.each(res,function(index,element){
                                table += '<tr class="text-c" style="height:60px!important;">' +
                                    '<input type="hidden" name="attr_id"  value="'+element.attr_id+'">' +
                                    '<td class="tab_label">' +
                                    '<input  type="checkbox" class="inputC input_agreement_protocol" attr-data="'+element.attr_id+'" id = "'+element.id+index+'" targ="'+element.id+index+'" name="id[]" onchange="check_one('+element.id+index+')" value = "'+element.id+'" style="position: absolute;">' +

                                    ' <label for="'+element.id+index+'">' +
                                    '</label>' +
                                    '</td>' +
                                    '<td>'+(index+1)+'</td>' +
                                    '<td>' +
                                    '<img src ="'+element.image+'" style="width: 40px;height:40px;">'+element.product_title+' </td>' +
                                    ' <td>'+element.id+'</td> ' +
                                    // ' <td>'+element.name+'</td> ' +
                                    '<td>'+element.num+'</td> ' +
                                    // '<td>'+element.min_inventory+'</td> ' +
                                    '<td>'+element.price+'</td>' +
                                    '</tr>';
                                $("#proattr").html(table);
                            })

                        }else{//无拼团数据
                            $("#proattr").empty();
                            alert('没有拼团商品',{time:2000})
                        }

                        var table_height = $("#prolist").css('height');//单位为px
                        table_height = table_height.slice(0,-2);
                        if(table_height >= 200){
                            $("#prolist").addClass('scrolly');
                        }
                    }

                })



            })

            $("#huanyuan").click(function () {
                if(old_pro_html != ''){
                    $("#proattr").html(old_pro_html);
                }
                $("select[name='class']").val(0);
                $("select[name='brand']").val(0);
                $("input[name='p_name']").val('');
            })

        }

        //复选框唯一限制函数
        function check_one(i){

            if($("input[id="+i+"]").prop("checked")==true){
                $("input[id!="+i+"][name='id[]']").removeAttr("checked");
            }
        }

        function addlever() {

            var node = '<div class="manlevel">' +
                '<input type="number"  max="50" min="1" class="input-text ct-rs" value="3" name="min_man" style="width:60px;" onkeyup="onkeyup1(this)" >&nbsp;&nbsp;人团&nbsp;&nbsp;' +
                '<span style="margin-left:20px;">折扣价: 参团' +
                '</span><input type="number" class="input-text" value=""  name="canprice" style="width:80px;margin-left:5px;" onkeyup="onkeyup1(this)">&nbsp;%' +
                '<span style="margin-left: 10px;">开团</span>' +
                '<input type="number" class="input-text" value=""  name="memberprice" style="width:80px;margin-left:5px;" onkeyup="onkeyup1(this)">&nbsp;%' +
                '<input class="btn btn-primary radius" type="button" onclick="removepro(event)" value="删除" style="margin-left:10px;height: 36px!important;">' +
                '</div>';

            var setlevel = $('#setlevel');
            setlevel.append(node);
        }

        function onkeyup1(ob){
            if(ob.value.length==1){ob.value=ob.value.replace(/[^1-9]/g,'')}else{ob.value=ob.value.replace(/\D/g,'')}
        }

        function removepro(event){
            var goods = event.srcElement.parentNode;
            goods.parentNode.removeChild(goods);

        }

        //ajax请求--添加拼团商品
        function check() {

            //时间合理判断
            var starttime = $('input[name=starttime]').val();
            var endtime = $('input[name=endtime]').val();
            if(starttime == ''|| endtime == ''){

                alert('时间不能为空！',{time:2000});
                return false;
            }

            var start = new Date(starttime).getTime();
            var end = new Date(endtime).getTime();
            var now = new Date().getTime();
              now1 = now-(3600 * 1000);
            if(start < now1 ){

                alert('开始时间不能小于当前时间前1小时！');
                return false;
            }
            if(end < now ){
                alert('结束时间不能小于当前时间！');
                return false;
            }
            if(start > end){
                alert('开始时间不能大于结束时间！');
                return false;
            }

            //商品唯一性判断
            var product_id = $("input[name='id[]']:checked");
            if(product_id.length < 1){
                alert('请选择商品',{time:2000});
            }
            if(product_id.length > 1){
                alert('只能选择一个商品',{time:2000});
            }

            //获取选中的规格id
            var attr_id_check = $("input[name='id[]']:checked").parent().prev().val();
            //如果拼团标题为空，则取商品标题
            var title = $("input[name='title']").val();
            var product_title = '';
            if(title == 0){
                product_title = $("input[name='id[]']:checked").parent().next().next().text();
            }


            console.log(attr_id_check);
            console.log(product_title)
            var rr = $('#form1').serialize();
            $.ajax({
                cache: true,
                type: "POST",
                dataType:"json",
                url:'index.php?module=auction&action=addauc&m=pro_add',
                data:'attr_id_check='+attr_id_check+'&product_title='+product_title+'&'+$('#form1').serialize(),// 你的formid
                async: true,
                success: function(data) {

                    alert(data.status);
                    if(data.suc){
                        location.href="index.php?module=auction";
                    }
                }
            });
        }


        document.onkeydown = function (e) {
            if (!e) e = window.event;
            if ((e.keyCode || e.which) == 13) {
                $("[name=Submit]").click();
            }
        }

        laydate.render({
            elem: '#group_start_time', //指定元素
            trigger: 'click',
            type: 'datetime',
            done: function(value, date){
                var radio = $("input[name=endtime]:checked").val();

                if(radio == 1){
                    var day = value.split(' ');
                    var str = value.replace(/-/g,'/');
                    var d = new Date(str);
                    var oneYear = oneYearPast(d);
                    oneYear = oneYear + ' ' + day[1];
                    $("#end_year").val(oneYear)
                }

            }
        });

        laydate.render({
            elem: '#group_end_time',
            trigger: 'click',
            type: 'datetime'
        });





    </script>

{/literal}
</body>
</html>