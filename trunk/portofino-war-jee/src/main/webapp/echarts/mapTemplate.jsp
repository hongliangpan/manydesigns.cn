<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageHeader">
		样板客户分布
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
                    location: '../asset.echarts/zrender/src',
                    //location: '../asset.echarts/zrender-master/src',
                    main: 'zrender'
                }
            ]
        });
        /* require.config({
            paths:{ 
                'echarts':'../asset.echarts/echarts-master/doc/example/www/js/echarts',
                'echarts/chart/map' : '../asset.echarts/echarts-master/doc/example/www/js/echarts-map',       // 把所需图表指向单文件
                'echarts/chart/bar': '../asset.echarts/echarts-master/doc/example/www/js/echarts',
                'echarts/chart/line': '../asset.echarts/echarts-master/doc/example/www/js/echarts'
            }
        }); */
        var option_map_template = {
        	    title : {
        	        text: '',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item'
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        x:'left',
        	        data:new Array()
        	    },
        	    dataRange: {
        	        min: 0,
        	        max: 0,
        	        precision:1,
        	        text:['高','低'],           // 文本，默认为数值文本
        	        calculable : true,
        	        textStyle: {
        	            color: 'orange'
        	        }
        	    },
        	    toolbox: {
        	        show : true,
        	        orient : 'vertical',
        	        x: 'right',
        	        y: 'center',
        	        feature : {
        	            mark : true,
        	            dataView : {readOnly: false},
        	            restore : true,
        	            saveAsImage : true
        	        }
        	    },
        	    series : [
        	        {
        	            name: '',
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700',areaStyle:{color:'#F1FFDD'}},// for legend
        	                
        	                emphasis:{label:{show:true}}
        	            },
        	            data: new Array()
        	        },
        	        {
        	            name: '',
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ff8c00'},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            data: new Array()
        	        },
        	        {
        	            name: '',
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#eeff00'},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            data: new Array()
        	        }
        	    ]
        	};
        
        require(
            [
                'echarts',
                'echarts/chart/map',
                'echarts/chart/bar'
            ],
            function(ec) {
                var myChart = ec.init(document.getElementById('customerTemplateMap'));
                
                var option = option_map_template ; // clone , TODO
                var dataEval = null ;
                
                $.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4CustomerTemplateMap",
        			async:false,
        			dataType : "text",
        			success : function(data) {
        				dataEval = eval("("+data+")");
        			}
        		});
                
                option.legend.data = dataEval.ynames ;
                option.dataRange.max = dataEval.maxValue ;
                option.series[0].name = dataEval.ynames[0];
                option.series[0].data = dataEval.mapValues[0];
                option.series[1].name = dataEval.ynames[1];
                option.series[1].data = dataEval.mapValues[1];
                option.series[2].name = dataEval.ynames[2];
                option.series[2].data = dataEval.mapValues[2];
                
                myChart.setOption(option);
            }
        )
    </script>
    
    <div class="container">
        <div class="row">
        	<div id="customerTemplateMap" style="padding-left:20px; height:500px"></div>
        </div>
    </div>
	</stripes:layout-component>

</stripes:layout-render>