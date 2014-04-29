<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>

<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="linzhifeng@baidu.com">

    <link href="../asset.echarts/css/bootstrap.css" rel="stylesheet">
    <link href="../asset.echarts/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="../asset.echarts/css/codemirror.css" rel="stylesheet">
    <link href="../asset.echarts/css/monokai.css" rel="stylesheet">
    <link href="../asset.echarts/css/echartsHome.css" rel="stylesheet">
    <link rel="shortcut icon" href="../asset.echarts/ico/favicon.png">
</head>

<body>

    <div class="container">
        <div class="row">
            <div id="graphic" class="span10">
                <div id="main" style="height:400px"></div>
            </div>
        </div>
        <hr>
    </div>

    <script src="../asset.echarts/js/jquery.js"></script>
    <script src="../asset.echarts/js/bootstrap-transition.js"></script>
    <script src="../asset.echarts/js/bootstrap-alert.js"></script>
    <script src="../asset.echarts/js/bootstrap-modal.js"></script>
    <script src="../asset.echarts/js/bootstrap-dropdown.js"></script>
    <script src="../asset.echarts/js/bootstrap-scrollspy.js"></script>
    <script src="../asset.echarts/js/bootstrap-tab.js"></script>
    <script src="../asset.echarts/js/bootstrap-tooltip.js"></script>
    <script src="../asset.echarts/js/bootstrap-popover.js"></script>
    <script src="../asset.echarts/js/bootstrap-button.js"></script>
    <script src="../asset.echarts/js/bootstrap-collapse.js"></script>
    <script src="../asset.echarts/js/bootstrap-carousel.js"></script>
    <script src="../asset.echarts/js/bootstrap-typeahead.js"></script>
    
    <!--echarts demo-->
    <script src="../asset.echarts/js/esl/esl.js"></script>
    <script type="text/javascript">
    var dataEval;
    function fetchYname() {
		var arr = new Array();
		$.ajax({
			url : "/actions/echarts/dataProvider/getDatas4AreaComplaint",
			async:false,
			dataType : "text",
			success : function(data) {
				//调用函数获取值，转换成数组模式 
				console.log('对象数组1：', data);
				dataEval = eval("("+data+")");
				console.log('对象数组2：', dataEval);
				
				var ss = dataEval.ynames;
				console.log('对象数组2：', ss);
				for ( var i = 0; i < ss.length; i++) {
					arr.push(ss[i]);
				}
				console.log('对象数组3：', arr);
			}
		});
		return arr;
	}
    function fetchXname() {
		var arr = new Array();
		var ss = dataEval.xnames;
		console.log('对象数组2：', ss);
		for ( var i = 0; i < ss.length; i++) {
			arr.push(ss[i]);
		}
		console.log('对象数组3：', arr);
		return arr;
	}
    function fetchSeries() {
		var ss = dataEval.seriesList;
		console.log('dataEval.series：', ss);
		return ss;
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
                    location: '../asset.echarts/zrender-master/src',
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
                data:fetchYname()
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
            series : fetchSeries()
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