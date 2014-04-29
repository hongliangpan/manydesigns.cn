<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageHeader">
		客户投诉
	    <link rel="shortcut icon" href="/asset.echarts/ico/favicon.png">
	</stripes:layout-component>
<stripes:layout-component name="pageBody">

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
                    location: 'http://ecomfe.github.io/zrender/src',
                    //location: '../asset.echarts/zrender-master/src',
                    main: 'zrender'
                }
            ]
        });
        
        var placeHoledStyle = {
        	    normal:{
        	        borderColor:'rgba(0,0,0,0)',
        	        color:'rgba(0,0,0,0)'
        	    },
        	    emphasis:{
        	        borderColor:'rgba(0,0,0,0)',
        	        color:'rgba(0,0,0,0)'
        	    }
        	};
        
        var option_chart_area_complaint = {
        	    title : {
        	        text: '客户投诉分析',
        	        subtext: '',
        	        sublink: ''
        	    },
        	    tooltip : {
        	        trigger: 'axis',
        	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
        	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        	        },
        	        formatter: function(param){
        	            return param[0][1] + '<br/>'
        	                   + param[0][0] + ' : ' + param[0][2] + '<br/>'
        	                   + param[1][0] + ' : ' + param[1][2] +'<br/>' 
        	                   +  '总数 : ' + (param[1][2] + param[0][2]) + 
        	                   '%: ' +Math.round( param[0][2]/(param[1][2] + param[0][2])*1000)/10;
        	        }
        	    },
        	    legend: {
        	        data:new Array()
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
        	    xAxis : [
        	        {
        	            type : 'category',
        	            splitLine : {show: false},
        	            data : new Array()
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value',
        	            boundaryGap: [0, 0.1],
        	            splitArea : {show : true}
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'',
        	            type:'bar',
        	            stack: 'sum',
        	            barCategoryGap: '50%',
        	            itemStyle: {
        	                normal: {
        	                    color: '#ff0',
        	                    borderColor: 'tomato',
        	                    borderWidth: 6,
        	                    label : {
        	                        show: true, position: 'inside',
        	                        textStyle: {
        	                            color: 'tomato'
        	                        }
        	                    }
        	                }
        	            },
        	            data:new Array()
        	        },
        	        {
        	            name:'',
        	            type:'bar',
        	            stack: 'sum',
        	            itemStyle: {
        	                normal: {
        	                    color: 'tomato',
        	                    borderColor: 'tomato',
        	                    borderWidth: 6,
        	                    label : {
        	                        show: true, 
        	                        position: 'top',
        	                        formatter_ignore : function(a, b, c) {
        	                            for (var i = 0, l = option.xAxis[0].data.length; i < l; i++) {
        	                                if (option.xAxis[0].data[i] == b) {
        	                                	console.log(option.xAxis[0].data[i]);
        	                                	console.log("a:"+a);
        	                                	console.log("b:"+b);
        	                                	console.log("c:"+c);
        	                                    return option.series[0].data[i] + c;
        	                                }
        	                            }
        	                        },
        	                        textStyle: {
        	                            color: 'tomato'
        	                        }
        	                    }
        	                }
        	            },
        	            data: new Array()
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
                var myChart = ec.init(document.getElementById('areaComplaint'));
                var option = option_chart_area_complaint ; // clone , TODO
                var dataEval = null ;
                
                $.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4AreaComplaint",
        			async:false,
        			dataType : "text",
        			success : function(data) {
        				dataEval = eval("("+data+")");
        			}
        		});
                
                option.legend.data = dataEval.ynames;
                option.xAxis[0].data = dataEval.xnames;
                option.series[0].name = dataEval.ynames[0];
                option.series[0].data = dataEval.seriesList[0].data ;
                option.series[1].name = dataEval.ynames[1];
                option.series[1].data = dataEval.seriesList[1].data ;

                myChart.setOption(option);
            }
        )
    </script>
    <div class="container">
        <div class="row">
            <div id="areaComplaint" style="height:400px;padding-left: 20px;"></div>
        </div>
    </div>
	</stripes:layout-component>

</stripes:layout-render>