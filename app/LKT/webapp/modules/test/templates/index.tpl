<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

    <!-- 引入 ECharts 文件 -->
    <script src="style/js/echarts.js"></script>
    <script src="style/js/jquery-3.1.1.js"></script>
   
    <link href="assets/css/adminia.css" rel="stylesheet" /> 
    <link href="assets/css/dashboard.css" rel="stylesheet" /> 


<title>我的桌面</title>
</head>
<body>

<div id="main" style="height:400px;"></div>


   {literal}
    <script type="text/javascript">
          var  myChart = echarts.init(document.getElementById('main'));

          //获取
          var arr1=[],arr2=[];
              function arrTest(){
                $.ajax({
                  type:"post",
                  async:false,
                  url:"index.php?module=test&action=test",
                  data:{},
                  dataType:"json",
                    result.push("type","number"),
                  success:function(result){
                    if (result) {
                      for (var i = 0; i < result.length; i++) {
                          arr1.push(result[i].type);
                          arr2.push(result[i].number);
                      }
                    }
                  }
                })
                return arr1,arr2;
              }
              arrTest();
          var option = {
                tooltip: {
                    show: true
                },
                legend: {
                   data:['number']
                },
                xAxis : [
                        {
                            type : 'category',
                            data : arr1
                        }
                    ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                        {
                            "name":"number",
                            "type":"bar",
                            "data":arr2
                        }
                    ]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        // }
    </script>
    {/literal}


  <!--   {literal}
    <script type="text/javascript">
          var  myChart = echarts.init(document.getElementById('main'));


            var option = 
            {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: [{
                    orient: 'vertical',//列表朝向
                    x: 'left',
                    data:(function(){
                        var arr=[];
                            $.ajax({
                            type : "post",
                            async : false, //同步执行
                            url : "test.tpl",
                            data : {$data},
                            dataType : "json", //返回数据形式为json
                            success : function(result) {
                            if (result) {
                                console.log(result);
                                   for(var i=0;i<result.length;i++){
                                      console.log(result[i].type);
                                      arr.push(result[i].type);
                                    }
                            }

                        },
                        error : function(errorMsg) {
                            alert("sorry，请求数据失败");
                            myChart.hideLoading();
                        }
                       })
                       return arr;
                    })()
                }],
                series: [
                    {
                        name:'访问来源',
                        type:'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data:(function(){
                                    var arr=[];
                                    $.ajax({
                                    type : "post",
                                    async : false, //同步执行
                                    url : "test.tpl",
                                    data : {},
                                    dataType : "json", //返回数据形式为json
                                    success : function(result) {
                                    if (result) {
                                        for(var i=0;i<result.length;i++){
                                            arr.push({
                                            name: result[i].type,
                                            value: result[i].number
                                            });
                                        }
                                    }
                                },
                                error : function(errorMsg) {
                                    alert("sorry，请求数据失败");
                                    myChart.hideLoading();
                                }
                               })
                              return arr;
                        })()
                    }
                ]
            };


            // 为echarts对象加载数据
            myChart.setOption(option);
        // }
    </script>
    {/literal} -->


</body>
</html>