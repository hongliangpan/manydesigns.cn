<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageTitle">
	客户维保分析
    </stripes:layout-component>
	<stripes:layout-component name="pageHeader">
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
                    //location: '../../../zrender/src',
                    main: 'zrender'
                }
            ]
        });
        
        var option_chart_warranty = {
       		title : {
       	        text: '维保分析【按照区域统计每个月维保到期客户数量】',
       	        subtext: '',
       	     	position: 'bottom',
       	        x:'center'
       	    },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:new Array(),
                y:'bottom'
            },
            toolbox: {
                show : true,
                orient: 'vertical',
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
                    data : new Array()
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    splitArea : {show : true}
                }
            ],
            series : new Array()
        };
        
        require(
            [
                'echarts',
                'echarts/chart/line',
                'echarts/chart/bar'
            ],
            function(ec) {
                var myChart = ec.init(document.getElementById('warranty'));
                var option = option_chart_warranty ; // clone , TODO
                var dataEval = null ;
                
                $.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4Warranty",
        			async:false,
        			dataType : "text",
        			success : function(data) {
        				dataEval = eval("("+data+")");
        			}
        		});
                
                option.legend.data = dataEval.ynames;
                option.xAxis[0].data = dataEval.xnames;
                option.series = dataEval.seriesList ;
                
                myChart.setOption(option);
            }
        )
    </script>
        <div class="container">
        <div class="row">
            <div id="warranty" style="height:400px;padding-left: 20px;"></div>
        </div>
        <hr>
    </div>
	</stripes:layout-component>

</stripes:layout-render>