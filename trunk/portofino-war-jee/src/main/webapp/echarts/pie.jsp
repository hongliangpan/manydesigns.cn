<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">

	<stripes:layout-component name="pageHeader">
		客户状态统计
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
                    location: '../asset.echarts/zrender/src',
                    //location: '/asset.echarts/zrender-master/src',
                    //location: '///zrender/src',
                    main: 'zrender'
                }
            ]
        });
        
        var pie_chart_option_tmpt = {
        	    title : {
        	        text: '',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
        	    },
        	    legend: {
        	        orient : 'vertical',
        	        x : 'bottom',
        	        data: new Array()
        	    },
        	    toolbox: {
        	        show : true,
        	        orient: 'vertical',
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
        	            name:'项目状态',
        	            type:'pie',
        	            radius : '55%',
        	            center: ['50%', 225],
        	            data: new Array()
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
                var myChart = ec.init(document.getElementById('projectState'));
                
                var option = pie_chart_option_tmpt ; // clone , TODO
                var dataEval = null;
                
        		$.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4ProjectState",
        			async:false,
        			dataType : "text",
        			success : function(data) {
        				dataEval = eval("("+data+")");
        			}
        		});
        		
        		// ensure async mode
        		option.legend.data = dataEval.ynames ;
        		option.series[0].data = dataEval.pieValues ;
                myChart.setOption(option);
            }
        );
    </script>
    
    <div class="container">
        <div class="row">
            <div id="projectState" style="padding-left: 20px; height:400px;"></div>
        </div>
    </div>
	</stripes:layout-component>

</stripes:layout-render>