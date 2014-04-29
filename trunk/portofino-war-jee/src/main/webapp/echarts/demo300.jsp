<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>

<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="linzhifeng@baidu.com">

    <link rel="shortcut icon" href="../asset.echarts/ico/favicon.png">
</head>

<body>

    <div class="container">
        <div class="row">
            <div id="graphic" class="span12">
                <div id="main" style="height:400px"></div>
                
            </div>
        </div>
        <hr>
    </div>

    <script type="text/javascript">
    
    function fetchXname() {
		var arr = new Array();
		$.ajax({
			url : "/actions/echarts/dataProvider",
			dataType : "text",
			success : function(data) {
				//调用函数获取值，转换成数组模式 
				console.log('对象数组1：', data);
				var ss = eval("("+data+")");
				console.log('对象数组2：', ss);
				for ( var i = 0; i < ss.length; i++) {
					arr.push(ss[i]);
				}
				console.log('对象数组3：', arr);
			}
		});
		return arr;
	}
	function dataXRand() {
		var dataarr = new Array();
		$.ajax({
			url : "/actions/echarts/dataProvider",
			dataType : "text",
			success : function(data) {
				var ss = eval(data);
				for ( var i = 0; i < ss.length; i++) {
					dataarr.push(ss[i]);
					//alert(dataarr[i]); 
				}
			}
		});
		return dataarr;
	}
        require.config({
            packages: [
                {
                    name: 'echarts',
                    location: '../asset.echarts/echarts-master/src',
                    main: 'echarts'
                },
                {
                    name: 'zrender',
                    location: 'http://ecomfe.github.io/zrender/src',
                    //location: '../asset.echarts/zrender-master/src',
                    //location: '../../../zrender/src',
                    main: 'zrender'
                }
            ]
        });
        
        var option = {
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['蒸发量','降水量']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : true,
                    dataView : {readOnly: false},
                    magicType:['line', 'bar'],
                    restore : true,
                    saveAsImage : true
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : fetchXname()
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    splitArea : {show : true}
                }
            ],
            series : [
                {
                    "name":"蒸发量",
                    "type":"bar",
                    "data":[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
                },
                {
                    name:'降水量',
                    type:'bar',
                    data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
                }
            ]
        };
        
        require(
            [
                'echarts',
                'echarts/chart/line',
                'echarts/chart/bar'
            ],
            function(ec) {
                var myChart = ec.init(document.getElementById('main'));
                myChart.setOption(option);
            }
        )
    </script>
</body>
</html>