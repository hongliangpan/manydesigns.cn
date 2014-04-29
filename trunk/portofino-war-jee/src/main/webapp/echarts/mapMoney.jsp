<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageHeader">
		客户合同额分布
	</stripes:layout-component>
<stripes:layout-component name="pageBody">

    <script type="text/javascript">

    function fetchXname() {
		var arr = new Array();
		var ss = dataEval.xnames;
		for ( var i = 0; i < ss.length; i++) {
			arr.push(ss[i]);
		}
		console.log('fetchXname：', arr);
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
                    //location: '../asset.echarts/zrender-master/src',
                    main: 'zrender'
                }
            ]
        });
        var option_map_money = {
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
                var myChart = ec.init(document.getElementById('customerMoneyMap'));
                
                var option = option_map_money ; // clone , TODO
                var dataEval = null ;
                
                $.ajax({
        			url : "/actions/echarts/dataProvider/getDatas4CustomerMapMoney",
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
                
                myChart.setOption(option);
            }
        )
    </script>
    
    <div class="container">
        <div class="row">
            <div>
                <div id="customerMoneyMap" style="padding-left:20px; height:500px"></div>
            </div>
        </div>
    </div>
	</stripes:layout-component>

</stripes:layout-render>