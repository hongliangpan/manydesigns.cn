<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageHeader">
		<title>客户分布</title>
	</stripes:layout-component>
<stripes:layout-component name="pageBody">

    <script type="text/javascript">
    var dataEval;
    function fetchYname() {
		var arr = new Array();
		$.ajax({
			url : "/actions/echarts/dataProvider/getDatas4CustomerMap200",
			async:false,
			dataType : "text",
			success : function(data) {
				//调用函数获取值，转换成数组模式 
				console.log('response：', data);
				dataEval = eval("("+data+")");
				
				var ss = dataEval.ynames;
				for ( var i = 0; i < ss.length; i++) {
					arr.push(ss[i]);
				}
				console.log('fetchYname：', arr);
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
    function fetchPieValues() {
		var ss = dataEval.pieValues;
		console.log('fetchPieValues：', ss);
		return ss;
	}
    function fetchSeries() {
		var ss = dataEval.seriesList;
		console.log('dataEval.series：', ss);
		return ss;
	}
    function getData(i) {
		var ss = dataEval.mapValues[i];
		console.log('getData '+i+":", ss);
		return ss;
	}
	function getYname(i) {
		var ss = dataEval.ynames[i];
		console.log('getYname '+i+":", ss);
		return ss;
	}
	function getMaxValue(i) {
		var ss = dataEval.maxValues[i];
		console.log('maxValues：'+i+":", ss);
		return ss;
	}
	function getMaxValueTotal() {
		return  dataEval.maxValue;
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
                    location: '../asset.echarts/zrender/src',
                    //location: 'http://ecomfe.github.io/zrender/src',
                    //location: '../../../zrender/src',
                    main: 'zrender'
                }
            ]
        });
        
        option = {
        	    title : {
        	        text: '客户分布',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item'
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        x:'left',
        	        data:fetchYname()
        	    },
        	    dataRange: {
        	        min: 0,
        	        max: 2500,
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
        	            name: getYname(0),
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700',areaStyle:{color:'#F1FFDD'}},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            selectedMode: 'multiple',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700'},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            data: getData(0)
        	        }
        	    ]
        	};
        option1 = {
        	    title : {
        	        text: '客户分布',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item'
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        x:'left',
        	        data: getYname(1)
        	    },
        	    dataRange: {
        	        min: 0,
        	        max: getMaxValueTotal(),
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
        	            name: getYname(1),
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700',areaStyle:{color:'#F1FFDD'}},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            selectedMode: 'multiple',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700'},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            data: getData(1)
        	        }
        	    ]
        	};
        option2 = {
        	    title : {
        	        text: '客户分布',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item'
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        x:'left',
        	        data: getYname(2)
        	    },
        	    dataRange: {
        	        min: 0,
        	        max: 2500,
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
        	            name: getYname(2),
        	            type: 'map',
        	            mapType: 'china',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700',areaStyle:{color:'#F1FFDD'}},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            selectedMode: 'multiple',
        	            itemStyle:{
        	                normal:{label:{show:true}, color:'#ffd700'},// for legend
        	                emphasis:{label:{show:true}}
        	            },
        	            data: getData(2)
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
                var myChart = ec.init(document.getElementById('customerMap'));
                myChart.setOption(option);
                
                var myChart1 = ec.init(document.getElementById('customerMap1'));
                myChart1.setOption(option1);
                
                var myChart2 = ec.init(document.getElementById('customerMap2'));
                myChart2.setOption(option2);
            }
        )
    </script>
    
    <div class="container"  style="padding-left: 25px;">
        <div class="row">
            <div id="graphic">
                <div id="customerMap" style="height:400px"></div>
            </div>
        </div>
        <div class="row">
            <div id="graphic">
                <div id="customerMap1" style="height:400px"></div>
            </div>
        </div>
        <div class="row">
            <div id="graphic">
                <div id="customerMap2" style="height:400px"></div>
            </div>
        </div>
        <hr>
    </div>
	</stripes:layout-component>

</stripes:layout-render>