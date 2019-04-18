<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

    <!-- 引入 ECharts 文件 -->
    <script src="assets/js/echarts.js"></script>
    <script src="assets/js/jquery-3.1.1.js"></script>
   
    <link href="assets/css/adminia.css" rel="stylesheet" /> 
    <link href="assets/css/dashboard.css" rel="stylesheet" /> 


<title>我的桌面</title>
</head>
<body>

<div id="main" style="height:400px"></div>
    {literal}
    <script type="text/javascript">
          var  myChart = echarts.init(document.getElementById('main'));

          //获取
          var arr1=[],arr2=[];
              function arrTest(){
                $.ajax({
                  type:"post",
                  async:false,
                  url:"http://localhost/xky/test.php",
                  data:{},
                  dataType:"json",
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
                title: {
                    text: 'Step Line'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['喵喵喵', '汪汪汪', '喳喳喳']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    data: arr1
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'喵喵喵',
                        type:'line',
                        step: 'start',
                        data:arr2
                    },
                    {
                        name:'汪汪汪',
                        type:'line',
                        step: 'middle',
                        data:arr2
                    },
                    {
                        name:'喳喳喳',
                        type:'line',
                        step: 'end',
                        data:arr2
                    }
                ]
            };


            // 为echarts对象加载数据
            myChart.setOption(option);
        // }
    </script>
    {/literal}


</body>
</html>