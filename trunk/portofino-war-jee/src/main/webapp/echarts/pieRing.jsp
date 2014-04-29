<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>

<head>
    <meta charset="utf-8">
    <title>行业细分环形图</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="linzhifeng@baidu.com">

    <link rel="shortcut icon" href="../asset.echarts/ico/favicon.png">
</head>

<body>

    <script type="text/javascript">

        require.config({
            packages: [
                {
                    name: 'echarts',
                    location: '../asset.echarts/echarts-master/src',
                    main: 'echarts'
                },
                {
                    name: 'zrender',
                    location: '../asset.echarts/zrender/src',
                    //location: '../asset.echarts/zrender-master/src',
                    //location: '../../../zrender/src',
                    main: 'zrender'
                }
            ]
        });
        
        var option_chart_pie_ring = {
        		title : {
        	        text: '客户统计纯属虚构',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        show: true,
        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
        	    },
        	    legend: {
        	        orient : 'vertical',
        	        x : 'left',
        	        data:['华东','华北','东北','华南','华中','西南','辽宁','吉林','黑龙江','其他']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : true,
        	            dataView : {readOnly: false},
        	            restore : true,
        	            saveAsImage : true
        	        }
        	    },
        	    calculable : true,
        	    series : [
        	        {
        	            name:'客户数',
        	            type:'pie',
        	            center : ['35%', 200],
        	            radius : 80,
        	            itemStyle : {
        	                normal : {
        	                    label : {
        	                        position : 'inner',
        	                        formatter : function(a,b,c,d) {return (d - 0).toFixed(0) + ' %'},
        	                    },
        	                    labelLine : {
        	                        show : false
        	                    }
        	                }
        	            },
        	            data:[
        	                {value:335, name:'华东'},
        	                {value:679, name:'华北'},
        	                {
        	                    value:1548,
        	                    name:'东北',
        	                    itemStyle : {
        	                        normal : {
        	                            label : {
        	                                show : false
        	                            }
        	                        },
        	                        emphasis : {
        	                            label : {
        	                                show : true,
        	                                formatter : "{b} : {d}%",
        	                                position : 'inner'
        	                            }
        	                        }
        	                    }
        	                }
        	            ]
        	        },
        	        {
        	            name:'客户数',
        	            type:'pie',
        	            center : ['35%', 200],
        	            radius : [110, 140],
        	            data:[
        	                {value:335, name:'华东'},
        	                {value:310, name:'华南'},
        	                {value:234, name:'华中'},
        	                {value:135, name:'西南'},
        	                {
        	                    value:1048,
        	                    name:'辽宁',
        	                    itemStyle : {
        	                        normal : {
        	                            
        	                            label : {
        	                                textStyle : {
        	                                    color : 'rgba(30,144,255,0.8)',
        	                                    align : 'center',
        	                                    baseline : 'middle',
        	                                    fontFamily : '微软雅黑',
        	                                    fontSize : 30,
        	                                    fontWeight : 'bolder'
        	                                }
        	                            },
        	                            labelLine : {
        	                                length : 40,
        	                                lineStyle : {
        	                                    color : '#f0f',
        	                                    width : 3,
        	                                    type : 'dotted'
        	                                }
        	                            }
        	                        }
        	                    }
        	                },
        	                {value:251, name:'吉林'},
        	                {
        	                    value:102,
        	                    name:'黑龙江',
        	                    itemStyle : {
        	                        normal : {
        	                            label : {
        	                                show : false
        	                            },
        	                            labelLine : {
        	                                show : false
        	                            }
        	                        },
        	                        emphasis : {
        	                            label : {
        	                                show : true
        	                            },
        	                            labelLine : {
        	                                show : true,
        	                                length : 50
        	                            }
        	                        }
        	                    }
        	                },
        	                {value:147, name:'其他'}
        	            ]
        	        },
        	        {
        	            name:'客户数',
        	            type:'pie',
        	            startAngle: 90,
        	            center : ['75%', 200],
        	            radius : [80, 120],
        	            itemStyle :　{
        	                normal : {
        	                    label : {
        	                        show : false
        	                    },
        	                    labelLine : {
        	                        show : false
        	                    }
        	                },
        	                emphasis : {
        	                    
        	                    label : {
        	                        show : true,
        	                        position : 'center',
        	                        formatter : "{d}%",
        	                        textStyle : {
        	                            color : 'red',
        	                            fontSize : '30',
        	                            fontFamily : '微软雅黑',
        	                            fontWeight : 'bold'
        	                        }
        	                    }
        	                }
        	            },
        	            data:[
        	                {value:335, name:'华东'},
        	                {value:310, name:'华南'},
        	                {value:234, name:'华中'},
        	                {value:135, name:'西南'},
        	                {value:1548, name:'东北'}
        	            ]
        	        }
        	    ]
        	};
        
        require(
            [
                'echarts',
                'echarts/chart/pie',
                'echarts/chart/line',
                'echarts/chart/bar'
            ],
            function(ec) {
                var myChart = ec.init(document.getElementById('projectStateRing'));
                var dataEval = null ;
                var option = option_chart_pie_ring ; // clone , TODO
        		$.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4ProjectState",
        			async:false,
        			dataType : "text",
        			success : function(data) {
        				dataEval = eval("("+data+")");
        			}
        		});
                
                // set option value from async result
        		
                myChart.setOption(option);
            }
        )
    </script>
    
    <div class="container">
        <div class="row">
            <div id="projectStateRing" style="height:500px;padding-left: 20px;"></div>
        </div>
    </div>
</body>
</html>