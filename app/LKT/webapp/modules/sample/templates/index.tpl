<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>商品列表管理</title>

</head>
<body>
<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=product">商品列表</a>
</nav>

<div class="pd-20">

    <div class="text-c">
        <form name="form1" action="index.php" method="get" class="lk_form" >
            <input type="hidden" name="module" value="product" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <select name="cid" class="select" id="cid">
                <option value="0" >请选择商品分类</option>

            </select>
            <select name="brand_id" class="select" id="brand_id">
                <option value="0" >请选择商品品牌</option>

            </select>
            <select name="s_type" class="select" id="s_type">
                <option value="0" >请选择商品类型</option>
                <option value="1" {if $s_type == 1}selected="selected"{/if} >新品</option>
                <option value="2" {if $s_type == 2}selected="selected"{/if}>热销</option>
                <option value="3" {if $s_type == 3}selected="selected"{/if}>推荐</option>
            </select>
            <select name="status" class="select" id="status">
                <option value="0" >请选择商品状态</option>
                <option value="2" {if $status == 2}selected="selected"{/if}>上架</option>
                <option value="1" {if $status == 1}selected="selected"{/if}>下架</option>
                <option value="3" {if $status == 3}selected="selected"{/if}>待上架</option>
            </select>

            <input type="text" name="product_title" size='8' value="{$product_title}" id="product_title" placeholder="请输入商品名称" autocomplete="off" style="width:200px" class="input-text">
            <input name="" id="btn9" class="btn btn-success" type="submit" value="查询">
            <input type="button" value="重 置" id="btn8"  class="btn" onclick="resetButton()"  />

        </form>
    </div>

    <div style="clear:both;margin-top: 10px;" class="btnDiv">

        <div class="btn-group">

            <a class="btn btn-secondary radius"  href="#" >发布商品</a>
            <a class="btn btn-primary radius" href="#"  >商品上架</a>
            <a class="btn btn-success radius" href="#"  >设为新品</a>
            <a class="btn btn-danger radius" href="#"  >设为热销</a>
            <a class="btn btn-warning radius" href="#"  >设为推荐</a>
            <a href="javascript:;"  class="lk_del_btn btn radius" >
                <span style="color:#333">删 除</span>
            </a>

        </div>

    </div>

    <div class="mt-10">

        <div class="mt-10 table-scroll" style="overflow: scroll; width: 100%; height: 495px;">

            <div class="codeView docs-example">
                <table class="table table-border table-bordered table-hover">
                    <thead>
                    <tr>
                        <th class="lk_checkbox">

                            <input name="ipt1" id="ipt1" type="checkbox" value=""   >
                            <label for="ipt1" >全选</label>

                        </th>
                        <th>ID</th>
                        <th>商品名称</th>
                        <th>商品名称</th>
                        <th>商品名称</th>
                        <th>商品名称</th>
                        <th>商品名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td  class="lk_checkbox" >
                            <input id="ipt2" type="checkbox" value=""   >
                            <label for="ipt2" ></label>
                        </td>
                        <td>1</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>
                            <a class="lk_table_a" href="#" ><span class="view_span">查 看</span></a>
                            <a class="lk_table_a" href="#" ><span class="down_span">下 架</span></a>
                            <a class="lk_table_a" href="#" > <span class="up_span">上 架</span></a>
                            <a class="lk_table_a" href="#" ><span class="modify_span">修 改</span></a>
                            <a class="lk_table_a" href="#" ><span class="del_span">删 除</span> </a>
                        </td>
                    </tr>
                    <tr>
                        <td  class="lk_checkbox" >
                            <input id="ipt3" type="checkbox" value=""   >
                            <label for="ipt3" ></label>
                        </td>
                        <td>2</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>
                            <a class="lk_table_a" href="#" ><span class="view_span">查 看</span></a>
                            <a class="lk_table_a" href="#" ><span class="down_span">下 架</span></a>
                            <a class="lk_table_a" href="#" > <span class="up_span">上 架</span></a>
                            <a class="lk_table_a" href="#" ><span class="modify_span">修 改</span></a>
                            <a class="lk_table_a" href="#" ><span class="del_span">删 除</span> </a>
                        </td>
                    </tr>
                    <tr>
                        <td  class="lk_checkbox" >
                            <input id="ipt4" type="checkbox" value=""   >
                            <label for="ipt4" ></label>
                        </td>
                        <td>3</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>表格内容</td>
                        <td>
                            <a class="lk_table_a" href="#" ><span class="view_span">查 看</span></a>
                            <a class="lk_table_a" href="#" ><span class="down_span">下 架</span></a>
                            <a class="lk_table_a" href="#" > <span class="up_span">上 架</span></a>
                            <a class="lk_table_a" href="#" ><span class="modify_span">修 改</span></a>
                            <a class="lk_table_a" href="#" ><span class="del_span">删 除</span> </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

    </div>
    </div>
    <div class="lk_page">{$pages_show}</div>


</div>

{literal}
<script type="text/javascript">

</script>
{/literal}


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

</body>
</html>