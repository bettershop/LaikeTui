<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    {php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
    {php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
    {literal}
        <style type="text/css">

            .isclick a {
                color: #ffffff;
            }

            .page_bgcolor .status {
                border: 1px solid #ddd;
                border-right: 0;
            }

            .page_bgcolor .status:last-child {
                border-right: 1px solid #ddd;
            }

            .page_bgcolor div {
                font-size: 16px;
            }

            .status {
                width: 80px;
                height: 34px;
                line-height: 34px;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #fff;
                margin-left: 0px;
            }

            .status a:hover {
                text-decoration: none;
                color: #fff;
            }

            .status:hover {
                background-color: #2481e5;
            }

            .status:hover a {
                color: #fFF;
            }

            .isclick {
                width: 80px;
                height: 34px;
                background: #2890FF;
                display: flex;
                flex-direction: row;
                align-items: center;
                justify-content: center;
                border: 1px solid #2890FF !important;
            }

            td a {
                width: 44%;
                margin: 2% !important;
            }

            td button {
                margin: 4px;
                float: left;
                background: white;
                color: #DCDCDC;
                border: 1px #DCDCDC solid;
                width: 56px;
                height: 22px;
            }

            .tc-box {
                width: 100%;
                height: 100vh;
                position: relative;
                position: fixed;
                background: rgba(0, 0, 0, 0.3);
                display: none;
                top: 0;
            }

            .tc-box > div {
                height: 500px;
                width: 70%;
                position: absolute;
                left: 15%;
                top: 10px;
                background: #fff;
            }

            .tc-box-close {
                position: absolute;
                width: 20px;
                height: 20px;
                text-align: center;
                line-height: 20px;
                right: 10px;
                background: #8e8e8e;
                top: 10px;
                color: #fff;
                border-radius: 100%;
                cursor: hand;
            }

            .confirm {
                width: 100%;
                height: 100%;
                z-index: 999;
                position: fixed;
                top: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                background: rgba(0, 0, 0, .5);
            }

            .confirm > div {
                background: #fff;
                width: 460px;
                height: 223px;
            }

            .confirm_tips {
                text-align: center;
                height: 150px;
                line-height: 150px;
                font-size: 16px;
                font-family: MicrosoftYaHei;
                font-weight: 400;
                color: rgba(65, 70, 88, 1);
            }

            .confirm_btn > button {
                width: 112px;
                height: 36px;
                line-height: 36px;
                border: 1px solid #2890FF;
                border-radius: 2px;
                color: #2890FF;
            }

            .confirm_btn {
                display: flex;
                justify-content: space-around;
                width: 60%;
                margin-left: 20%;
                text-align: center;
            }

            .hide {
                display: none;
            }

            .qh {
                width: 112px !important;
                height: 42px !important;
            }

            .table-border th, .table-border td {
                border-bottom: 0;
            }
        </style>
    {/literal}
    <title>拼团活动管理</title>
</head>
<body>


<nav class="breadcrumb">
    拼团管理 <span class="c-gray en">&gt;</span>
    {if $status==0}拼团商品{/if}
    {if $status==4}开团记录{/if}
    {if $status==5}参团记录{/if}
</nav>


<div class="page-container pd-20 page_absolute">
    <div style="display: flex;flex-direction: row;font-size: 16px;" class="page_bgcolor">
        <div class="status qh {if $status==0}isclick{/if}" style="border-radius: 2px 0px 0px 2px !important;"><a
                    href="index.php?module=pi&p=pintuan&c=Home&status=0" onclick="statusclick(0)">拼团商品</a></div>
        <div class="status qh {if $status==4}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=4"
                                                              onclick="statusclick(4)">开团记录</a></div>
        <div class="status qh {if $status==5}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=5"
                                                              onclick="statusclick(5)">参团记录</a></div>
        <div class="status qh {if $status==6}isclick{/if}" style="border-radius: 0px 2px 2px 0px !important;"><a
                    href="index.php?module=pi&p=pintuan&c=config" onclick="statusclick(5)">拼团设置</a></div>
    </div>
    <div class="page_h16"></div>
    <div class="text-c">
        {if  $status==4 || $status==5}
            <form name="form1" action="index.php" method="get" style="display: flex;">
                <input type="hidden" name="module" value="pintuan"/>
                <input type="hidden" name="action" value="Home"/>
                <input type="text" name="proname" size='8' value="{$proname}" id="" placeholder="请输入商品名称"
                       style="width:200px" class="input-text">
                <input type="text" name="username" size='8' value="{$username}" id="" placeholder="请输入会员名称"
                       style="width:200px" class="input-text">

                {if  $status==4}
                    <select style="width: 200px;margin-right: 5px;" name="group_status" id='group_status'>
                        <option value="">请选择活动状态</option>
                        <option value="1" {if $group_status=='1'}selected{/if}>拼团中</option>
                        <option value="2" {if $group_status=='2'}selected{/if}>拼团成功</option>
                        <option value="3" {if $group_status=='3'}selected{/if}>拼团失败</option>
                    </select>
                    <input type="hidden" name="status" value="4"/>
                {else}
                    <input type="hidden" name="status" value="5"/>
                {/if}
                <input type="button" value="重置" id="btn8"
                       style="border: 1px solid #D5DBE8; color: #6a7076; height: 31px;" class="reset"
                       onclick="empty()"/>

                <input name="" id="" class="btn btn-success buttom_hover" type="submit" value="查询">
            </form>
        {else}
            <form name="form1" action="index.php" method="get" style="display: flex;align-items: center;">
                <input type="hidden" name="module" value="pintuan"/>
                <input type="hidden" name="action" value="Home"/>
                <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize"/>
                <input type="text" name="proname" size='8' value="{$proname}" id="" placeholder="请输入商品名称"
                       style="width:200px" class="input-text" autocomplete="off">
                <select style="background: #fff;width:200px;" name="group_status" id='group_status'>
                    <option value="">请选择活动状态</option>
                    <option value="1" {if $group_status=='1'}selected{/if}>未开始</option>
                    <option value="2" {if $group_status=='2'}selected{/if}>进行中</option>
                    <option value="3" {if $group_status=='3'}selected{/if}>已结束</option>
                </select>
                <input type="button" value="重置" id="btn8"
                       style="border: 1px solid #D5DBE8; color: #6a7076; height: 31px;" class="reset"
                       onclick="empty()"/>

                <input name="" id="" class="btn btn-success" type="submit" value="查询">
            </form>
        {/if}
    </div>
    <div class="page_h16"></div>
    <div class="page_bgcolor page_t">
        {if $type == 'record' || $type == 'canrecord'}

        {else}
            <div class="page_h16"></div>
            <a class="btn newBtn radius" href="index.php?module=pi&p=pintuan&c=addproduct">
                <div style="height: 100%;display: flex;align-items: center;">
                    <img src="images/icon1/add.png"/>&nbsp;添加拼团
                </div>
            </a>
        {/if}
    </div>
    <div class="page_h16"></div>
    <div class="mt-20">
        <table class="table-border tab_content">
            {if  $status==4}
                <thead>
                <tr class="text-c tab_tr">
                    <th>
                        序号
                    </th>
                    <th style="width:170px;">活动名称</th>
                    <th style="width:200px;">商品名称</th>
                    <th style="width:5%;">拼团类型</th>
                    <th>零售价</th>
                    <th>团长价格</th>
                    <th>参团价格</th>
                    <th>活动有效时间</th>
                    <th style="width: 80px;">创建人</th>
                    <th style="width: 80px;">活动状态</th>
                    <th style="width: 80px;">活动创建时间</th>
                    <th style="width: 150px;">操作</th>
                </tr>
                </thead>
                <tbody>
                {foreach from=$list item=item}
                    <tr class="text-c tab_td">
                        <td>
                            {$item->id}
                        </td>
                        <td>
                            <div title=" {$item->group_title}"
                                 style="height: 60px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width:160px;line-height: 60px;">
                                {$item->group_title}
                            </div>
                        </td>
                        <td style="display: flex;align-items: center;">
                            <div style="width: 60px;height: 60px;">
                                {if $item->imgurl != ''}
                                    <img onclick="pimg(this)" style="width: 60px;height: 60px;" src="{$item->imgurl}">
                                {else}
                                    <span>暂无图片</span>
                                {/if}
                            </div>
                            <div title="{$item->p_name}"
                                 style="height: 60px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width:160px;line-height: 60px;  text-align: left; margin-left: 10px;">
                                {$item->p_name}
                            </div>
                        </td>
                        <td>
                            {$item->groupman}人团
                        </td>
                        <td>{$item->price} 元</td>
                        <td>￥{$item->openmoney} </td>
                        <td>￥{$item->canmoney} </td>
                        <td>
                            开始时间：{$item->start_time}
                            <br/>
                            结束时间：{$item->end_time}
                        </td>

                        <td>{$item->user_name}</td>
                        <td>
                            <div>
                                {if $item->ptstatus==3}
                                    <span style="color: orange;">拼团失败</span>
                                {elseif $item->ptstatus==2}
                                    <span style="color: red;">拼团成功</span>
                                {elseif $item->ptstatus==1}
                                    <span style="color: green">拼团中</span>
                                {/if}
                            </div>
                        </td>
                        <td style="text-align: center;">
                            {$item->addtime}
                        </td>
                        <td class="tab_editor">
                            <a href="#" onclick="canprecord('{$item->ptcode}')" style="width: 80%;">参团记录详情</a>
                        </td>
                    </tr>
                {/foreach}
                </tbody>
            {elseif $status==5}
                <thead>
                <tr class="text-c tab_tr">
                    <th>
                        序号
                    </th>
                    <th style="width:15%;">拼团活动名称</th>
                    <th style="width:15%;">商品名称</th>
                    <th style="width:15%;">拼团类型</th>
                    <th>零售价</th>
                    <th>参团价格</th>
                    <th style="width:8%;">会员名称</th>
                    <th style="width:8%;">活动状态</th>
                    <th>拼团有效时间</th>
                </tr>
                </thead>
                <tbody>
                {foreach from=$list item=item}
                    <tr class="text-c tab_td">
                        <td>
                            {$item->id}

                        </td>
                        <td>
                            <div style="text-align: left; margin-left: 10px;">
                                {$item->group_title}
                            </div>
                        </td>
                        <td style="display: flex;align-items: center;">
                            <div style="width: 60px;height: 60px;">
                                {if $item->imgurl != ''}
                                    <img onclick="pimg(this)" style="width: 60px;height: 60px;" src="{$item->imgurl}">
                                {else}
                                    <span>暂无图片</span>
                                {/if}
                            </div>
                            <div title="{$item->product_title}"
                                 style="height: 60px; overflow: hidden;text-align:left;padding-left: 10px;white-space: nowrap;text-overflow: ellipsis;width: 200px;line-height: 60px;text-align: left; margin-left: 10px;">
                                {$item->product_title}
                            </div>
                        </td>
                        <td> {$item->groupman}人团</td>
                        <td>￥{$item->price} </td>
                        <td>￥{$item->canmoney}</td>
                        <td>{$item->user_name}</td>
                        <td>
                            <!-- <div>
                                {if $item->group_status==0}
                                    <span style="color: orange;">未开始</span>
                                {elseif $item->group_status==2}
                                    <span style="color: red;">拼团结束</span>
                                {elseif $item->group_status==1}
                                    <span style="color: green">进行中</span>
                                {/if}
                            </div> -->
                            <div>
                                {if $item->ptstatus==3}
                                    <span style="color: orange;">拼团失败</span>
                                {elseif $item->ptstatus==2}
                                    <span style="color: red;">拼团成功</span>
                                {elseif $item->ptstatus==1}
                                    <span style="color: green">拼团中</span>
                                {/if}
                            </div>
                        </td>

                        <td>
                            开始时间：{$item->start_time}
                            <br/>
                            结束时间：{$item->end_time}
                        </td>
                    </tr>
                {/foreach}
                </tbody>
            {else}
                <thead>
                <tr class="text-c tab_tr">

                    <th style="width:20%;">拼团活动名称</th>

                    <th style="width:20%;">商品名称</th>
                    <th style="">状态</th>
                    <th style="">零售价</th>
                    <th style="">拼团价格</th>
                    <th style="width: 80px;">库存</th>
                    <th style="width:15%;">活动时间</th>
                    <th style="width: 30px;">是否显示</th>
                    <th style="width: 80px;">操作</th>
                </tr>
                </thead>
                <tbody>
                {foreach from=$list item=item}
                    <tr class="text-c tab_td">

                        <td>
                            <div style="text-align: left; margin-left: 10px;">
                                {$item->group_title}
                            </div>

                        </td>

                        <td style="display: flex;align-items: center;">
                            <div style="width: 60px;height: 60px;">
                                {if $item->imgurl != ''}
                                    <img onclick="pimg(this)" style="width: 60px;height: 60px;" src="{$item->imgurl}">
                                {else}
                                    <span>暂无图片</span>
                                {/if}
                            </div>
                            <div title="{$item->product_title}"
                                 style="height: 60px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width: 200px;line-height: 60px;text-align: left; margin-left: 10px;">
                                {$item->product_title}
                            </div>
                        </td>

                        <td>

                            <div>
                                {if $item->g_status==1}
                                    <span style="color: orange;">未开始</span>
                                {elseif $item->g_status==2}
                                    <span style="color: green;">进行中</span>
                                {else}
                                    <span style="color: red;">已结束</span>
                                {/if}
                            </div>
                        </td>


                        <td>
                            {$item->price}元
                        </td>
                        <td>
                            <p style="margin: 0 !important;">{$item->min_man}人团</p>
                            <p style="margin: 0 !important;">{$item->min_price}元</p>
                        </td>
                        <td>{$item->num}</td>
                        <td>{$item->actime}</td>

                        <td style="text-align: center;">
                            {if $item->is_show==0}
                                <span>否</span>
                            {else}
                                <span>是</span>
                            {/if}
                        </td>

                        <td class="tab_editor">
                            <div class="tab_block">
                                <div>
                                    <a href="index.php?module=pi&p=pintuan&c=modify&id={$item->group_id}" title="编辑">

                                        <img src="images/icon1/ck.png"/>&nbsp;{if $item->g_status==2&&$item->is_show==1}查看{else}编辑{/if}

                                    </a>

                                    {if $item->is_show==0}
                                        <a onclick="aj({$item->group_id},'1')"
                                           href="javascript:void(0);" title="显示">

                                            <img src="images/icon1/xj.png"/>&nbsp;显示

                                        </a>
                                    {else}
                                        <a onclick="aj({$item->group_id},'0')"
                                           href="javascript:void(0);" title="隐藏">

                                            <img src="images/icon1/sj_g.png"/>&nbsp;隐藏

                                        </a>
                                    {/if}
                                </div>
                                <div>
                                    {if $item->g_status==1||$item->g_status==3}
                                        <a onclick="aj1({$item->group_id},'2','{$item->starttime}','{$item->endtime}','{$item->status}')"
                                           href="javascript:void(0);" title="开始">

                                            <img src="images/icon1/xj.png"/>&nbsp;开始

                                        </a>
                                    {else}
                                        <a onclick="aj1({$item->group_id},'3','{$item->starttime}','{$item->endtime}','{$item->status}')"
                                           href="javascript:void(0);" title="停止">

                                            <img src="images/icon1/sj_g.png"/>&nbsp;停止

                                        </a>
                                    {/if}
                                    {if $item->g_status!=2}
                                        <a title="删除" href="javascript:;"
                                           onclick="delgrouppro1(this,{$item->group_id},'index.php?module=pi&p=pintuan&c=member&m=delpro&id=')"
                                           class="ml-5" data-g_status="{$item->g_status}">

                                            <img src="images/icon1/del.png"/>&nbsp;删除

                                        </a>
                                    {else}
                                        <button type="button" disabled="true">
                                            <div style="align-items: center;font-size: 12px;display: flex;justify-content: center;">
                                                <img src="images/icon1/del.png"/>&nbsp;删除
                                            </div>
                                        </button>
                                    {/if}
                                </div>
                            </div>
                        </td>
                    </tr>
                {/foreach}
                </tbody>
            {/if}
        </table>

    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
    <div class="page_h20"></div>
</div>
<div class="confirm hide">
    <div>
        <div class="confirm_tips">是否删除这个拼团</div>
        <div class="confirm_btn">
            <button class="confirm_cancel" style="background: #fff;">取消</button>
            <button class="confirm_confirm" style="background: #2890FF;color: #fff;">确认</button>
        </div>
    </div>
</div>
<div class="tc-box">
    <div id="ifm-box">
        <div class="tc-box-close">x</div>
        <iframe id="ifm" scrolling="yes" src="" height="100%" width="100%"></iframe>
    </div>
</div>
{literal}
    <script type="text/javascript">

        var aa = $(".pd-20").height() - 32 - $(".page_bgcolor").height() - $(".page_t").height();
        var bb = $(".table-border").height();

        if (aa < bb) {
            $(".page_h20").css("display", "block")
        } else {
            $(".page_h20").css("display", "none")
        }

        function excel(pageto) {
            var pagesize = $("#pagesize").val();
            location.href = location.href + '&pageto=' + pageto + '&pagesize=' + pagesize;
        }

        function delgrouppro1(ev, id, url) {

            var g_status = $(ev).attr('data-g_status');
            if (g_status == '2') {
                layer.msg('正在活动中的产品无法删除!');
                return false;
            }

            console.log('url=================');
            console.log(url);
            confirm('确认要删除吗？', id, url, '删除');
        }

        function empty() {
            $("input[name='username']").val('');
            $("input[name='proname']").val('');
            $("#group_status").val('');
        }

        function statusclick(d) {
            $('.status').each(function (i) {
                if (d == i) {
                    $(this).addClass('isclick');
                } else {
                    $(this).removeClass('isclick');
                }
            })

        }


        /*批量删除*/
        function datadel(url, content) {
            var checkbox = $("input[name='id[]']:checked");//被选中的复选框对象
            var Id = '';
            for (var i = 0; i < checkbox.length; i++) {
                var g_status = checkbox.eq(i).attr('data-g_status');
                if (g_status == '2') {
                    layer.msg('正在活动中的产品无法删除!');
                    return false;
                }
                Id += checkbox.eq(i).val() + ",";
            }
            if (Id == "") {
                layer.msg("未选择数据！");
                return false;
            }
            confirm('确认要删除吗？', Id, url, content)

        }

        function action(ev, id, url) {

        }


        function aj(id, type) {
            var text = '';
            var type_ = '';
            if (type == 1) {
                text = '是否显示所选拼团活动？'
                type_ = '显示';
            } else {
                text = '是否隐藏所选拼团活动？'
                type_ = '隐藏';
            }

            var url = 'index.php?module=pi&p=pintuan&c=member&m=contpro&type=' + type + '&id=';
            console.log(url);
            confirm_aj(text, id, url, type_);
            return false;
            $.post("index.php?module=pi&p=pintuan&c=member&m=contpro", {'id': id, 'type': type}, function (res) {
                $(".maskNew").remove();

                if (res.status == 1) {
                    layer.msg("操作成功！");
                    location.replace(location.href);
                }
                else {
                    layer.msg("操作失败！");
                }

            }, "json");

        }


        //隐藏显示功能选择弹窗
        function confirm_aj(content, id, url, content1) {
            console.log('url=======1')
            console.log(url)
            $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent" style="padding-top:0px;height: 223px !important;">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">删除</div>
                <div style="font-size: 16px;text-align: center;padding:60px 0;">
                    ${content}
                </div>
                <div style="text-align:center;">
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask('${id}','${url}','${content1}')>确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
        }

        function confirm(content, id, url, content1) {
            $("body").append(`
        <div class="maskNew">
            <div class="maskNewContent" style="padding-top:0px;height: 223px !important;">
                <a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
                <div class="maskTitle">删除</div>
                <div style="font-size: 16px;text-align: center;padding:60px 0;">
                    ${content}
                </div>
                <div style="text-align:center;">
                    <button class="closeMask" style="margin-right:20px" onclick=closeMask('${id}','${url}','${content1}')>确认</button>
                    <button class="closeMask" onclick=closeMask1() >取消</button>
                </div>
            </div>
        </div>
    `)
        }


        function aj1(id, type, start, end, status) {
            if (type == '2') {
                var startdate = new Date(start).getTime();
                var enddate = new Date(end).getTime();
                var now = new Date().getTime();
                if (now < startdate || now > enddate) {
                    layer.msg("未在活动时间范围内！");
                    return false;
                }
                if (status == '1') {
                    layer.msg("商品下架，无法开启!");
                    return false;
                }
            }
            var text = '';
            var type_ = '';
            if (type == '2') {
                //开始
                text = '是否开始所选拼团活动？';
                type_ = '开始';
            } else if (type == '3') {
                //结束
                text = '是否结束所选拼团活动？';
                type_ = '结束';
            }
            var url = 'index.php?module=pi&p=pintuan&c=member&m=is_market&type=' + type + '&id=';

            confirm_aj(text, id, url, type_);
        }


        function closeMaskba(id, url, nums) {

            $.ajax({
                type: "post",
                url: url + id + '&type=' + nums,
                async: true,
                success: function (res) {
                    $(".maskNew").remove();

                    res = JSON.parse(res);
                    if (res.status == "1") {
                        layer.msg("修改成功！");
                        location.replace(location.href);
                    } else {
                        layer.msg("修改失败！");
                    }
                },
            });
        }

        function closeMask(id, url, content) {
            $.ajax({
                type: "post",
                url: url + id,
                dataType: "json",
                data: {},
                async: true,
                success: function (res) {
                    $(".maskNew").remove();
                    if (res.status == "1") {
                        layer.msg(content + "成功!");
                        window.location.reload();
                    }
                    else if (res.status == "2") {
                        layer.msg(res.info);
                        location.replace(location.href);
                    }
                    else {
                        layer.msg(content + "失败!");
                        location.replace(location.href);
                    }
                },
                error: function (err) {
                    $(".maskNew").remove();
                    layer.msg(err + "未知错误，请求失败！");
                }

            });
        }

        function closeMask1() {
            $(".maskNew").remove();
        }

        function onshelves() {
            var checkbox = $("input[name='id[]']:checked");//被选中的复选框对象
            var check_text = checkbox.parents(".tab_label").siblings(".tan_status");
            if (checkbox.length == 0) {
                $(".btn_up span").text("商品上架")
            } else {
                for (var j = 0; j < check_text.length; j++) {
                    var ts = check_text.eq(j).text();
                    ts = ts.trim();
                    if (ts == "上架") {
                        $(".btn_up span").text("商品下架")
                    } else {
                        $(".btn_up span").text("商品上架")
                    }

                }
            }

        }
    </script>
    <script>
        $(".btn_sty").click(function () {
            $(".btn_sty").removeClass('sel_btn');
            $(this).addClass('sel_btn');
        })

        function canprecord(ptcode) {
            $("#ifm").attr("src", "index.php?module=pi&p=pintuan&c=canrecord&ptcode=" + ptcode);
            $(".tc-box").show();
        }

        $(".tc-box").click(function () {
            $(".tc-box").hide();
        })
    </script>
{/literal}
</body>
</html>