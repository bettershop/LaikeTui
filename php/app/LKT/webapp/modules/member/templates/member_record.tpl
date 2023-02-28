<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>管理员列表</title>
</head>
<body>
    
<nav class="breadcrumb">
    系统管理 <span class="c-gray en">&gt;</span> 
    日志列表 
</nav>


<div class="pd-20">
    <div class="text-c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="member" />
            <input type="hidden" name="action" value="member_record" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <input type="text" name="admin_name" size='8' value="{$admin_name}" id="" placeholder="管理员账号" autocomplete="off" style="width:200px" class="input-text">
            <input placeholder="请输入开始时间" id="startdate" name="startdate" value="{$startdate}" size="8" readonly class="scinput_s" style="width: 140px; height:26px;font-size: 14px;vertical-align: middle;" />
           
            至
            <input placeholder="请输入结束时间"  id="enddate" name="enddate" value="{$enddate}" size="8" readonly  class="scinput_s" style="width: 140px; height:26px;font-size: 14px;vertical-align: middle;"/>
            
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
            <input type="button" value="导出" class="btn btn-success" onclick="excel('all')">
            <input type="button" class="btn btn-warning radius" onclick="multiple_del('onekey')" value="一键清空" />
        	<input type="button" class="btn btn-danger radius" onclick="multiple_del('batch')" value="批量删除" />
        </form>
    </div>
    <div class="mt-20">
        <div class="mt-20 table-scroll" style="overflow: scroll; width: 100%; height: 495px;">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th width="25">
                        <div style="display: flex;align-items: center;height: 60px;">
                            <input  style="display:block" name="allAndNotAll" id="allAndNotAll" type="checkbox" value="" class="inputC"  >
                            <label for="allAndNotAll" ></label>
                        </div>


                    </th>
                    <th>管理员账号</th>
                    <th>事件</th>
                    <th>时间</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>
                        <div style="display: flex;align-items: center;height: 60px;">
                            <input name="id[]" style="display:block" id="{$item->id}" type="checkbox" class="inputC orders_select" value="{$item->id}">
                            <label for="{$item->id}"></label>
                        </div>
                    </td>
                    <td>{$item->admin_name}</td>
                    <td>{$item->event}</td>
                    <td>{$item->add_date}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


{literal}
<script type="text/javascript">


$("#allAndNotAll").bind("click",
            function () {
                $(".orders_select").prop("checked", $(this).prop("checked"));
            });




laydate.render({
          elem: '#startdate', //指定元素
           trigger: 'click',
          type: 'date',

        });
       
        laydate.render({
          elem: '#enddate',
          trigger: 'click',
          type: 'date'
        });


function excel(pageto) {
    var pagesize = $("#pagesize").val();
    location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize;
}

/*批量删除*/
function multiple_del($type){
    if($type == 'onekey'){
        confirm("确认要一键清空吗？",$type);
    }else{
        var checkbox=$("input[name='id[]']:checked");//被选中的复选框对象
        var Id = '';
        for(var i=0;i<checkbox.length;i++){
            Id+=checkbox.eq(i).val()+",";
        }
        console.log(Id)
        if(Id==""){
            confirm('未选择数据!');
            return false;
        }
        confirm("确认要批量删除吗？",$type,Id);
    }
}
function confirm (content,type,id){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">提示</div>
                <div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
                <div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
                    ${content}
                </div>
                <div style="text-align:center;margin-top:30px">
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask('${type}','${id}') >确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
}
function appendMask(content,src){
    $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">删除</div>
                <div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
                <div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
                    ${content}
                </div>
                <div style="text-align:center;margin-top:30px">
                    <button class="closeMask" onclick=closeMask1() >确认</button>
                </div>
            </div>
        </div>
    `)
}
function closeMask1(){
    $(".maskNew").remove();
    //location.replace(location.href);
}
function closeMask(type,Id){
    $.get("index.php?module=member&action=member_record_del",{'id':Id,'type':type},function(res){
        if(res.status=="1"){
            appendMask("删除成功","cg");
            location.replace(location.href);
        }else{
            appendMask("删除失败","ts");
        }
    },"json");
    $(".maskNew").remove();
}
</script>
{/literal}
</body>
</html>