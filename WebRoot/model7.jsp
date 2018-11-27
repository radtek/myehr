<%@ page language="java" import="java.util.*" import="com.myehr.common.util.LangUtil" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="../bootstrap2/bootstrap-switch.min.css" type="text/css"></link>
<script type="text/javascript" src="../bootstrap2/bootstrap-switch.min.js"></script>
<style type="text/css">
.switch{float:left;display:none}
select{float:left;margin-right:5px}
.row.cell {margin-bottom:20px}
.content{border: 1px solid #ccc;border-radius: 12px;padding:5px 5px 5px 15px}
.dakuang{padding:5px 15px 5px 5px;overflow:auto}
.chartJs{
	width:100%;height:90%;font-size:14px;resize:none;border-radius: 12px;
}
</style>
</head>
<body>
<div class="container-fluid" style="width:100%;height:100%;padding:1% 0">
	<div class="row" style="width:85%;height:100%;margin:auto auto;">
		
		<div class="col-lg-8 col-md-8 col-sm-8 col-xs-8 dakuang">
			<div class="row content" style="margin-left:0;height:50%;" id="showWall">
				<div id="test_chart" ></div>
			</div>
			<div class="row content" style="margin-left:0;height:50%;" id="chartConfig">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<label style="float:left;font-size:12px">颜色数组: </label>
					<input id="bar-colors" name="bar-colors" type="text" class="form-control paramXName"  style="width:100%;" value="#5BBFE9,#FEC86B, #dd70d9, #34cf34,#6497ef, #85802b, #D7504B, #C6E579,#F4E001, #F0805A, #26C0C0" />
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">图例方向: </label>
					<select id="bar-lo" title="图例方向" name="bar-lo" class="form-control paramSelect paramXName" DictName="SYS_FORM_CHARTS_legend_orient" dictType="dict">
					</select>                                                                             
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">触发器类型: </label>
					<select id="bar-tooltip_trigger" title="触发器类型" name="bar-tooltip_trigger" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_tooltip_trigger" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">是否显示: </label>
					<select id="bar-toolbox_show" title="是否显示" name="bar-toolbox_show" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_toolbox_show" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">指示器配置项类型: </label>
					<select id="tooltip_axisPointer_type" title="指示器配置项类型" name="tooltip_axisPointer_type" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_tooltip_axisPointer_type" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">启用的动态类型: </label>
					<input id="bar-toolbox_feature_magicType_Type" name="bar-toolbox_feature_magicType_Type" type="text" class="form-control paramXName"  style="width:100%;" value="line,bar"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">是否可编辑: </label>
					
					<select id="bar-toolbox_feature_dataView_readOnly" title="是否显示" name="bar-toolbox_feature_dataView_readOnly" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_toolbox_show" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">图标标题: </label>
					<input id="bar-title_text" name="bar-title_text" type="text" class="form-control paramXName"  style="width:100%;"  value="分支行人员分布"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">网格线宽: </label>
					<input id="bar-grid_borderWidth" name="bar-grid_borderWidth" type="text" class="form-control paramXName"  style="width:100%;" value="1" />
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">间隔: </label>
					<input id="bar-barGap" name="bar-barGap" type="text" class="form-control paramXName"  style="width:100%;" value="30%" />
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">最大宽度: </label>
					<input id="bar-barMaxWidth" name="bar-barMaxWidth" type="text" class="form-control paramXName"  style="width:100%;" value="50%" />
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">柱图x位置: </label>
					<input id="bar-grid_x" name="bar-grid_x" type="text" class="form-control paramXName"  style="width:100%;" value="25" />
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">柱图y位置: </label>
					<input id="bar-grid_y" name="bar-grid_y" type="text" class="form-control paramXName"  style="width:100%;" value="45"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">图标x位置: </label>
					<input id="bar-grid_x2" name="bar-grid_x2" type="text" class="form-control paramXName"  style="width:100%;" value="5"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">图标y位置: </label>
					<input id="bar-grid_y2" name="bar-grid_y2" type="text" class="form-control paramXName"  style="width:100%;" value="100"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">x轴是否显示: </label> 
					
					<select id="bar-xAxis_axisLabel_show" title="是否显示" name="bar-xAxis_axisLabel_show" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_toolbox_show" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">离容器下侧距离: </label>
					<input id="bar-legend_bottom" name="bar-legend_bottom" type="text" class="form-control paramXName"  style="width:100%;" value="0"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">坐标刻度显示间隔: </label>
					<input id="bar-xAxis_axisLabel_interval" name="bar-xAxis_axisLabel_interval" type="text" class="form-control paramXName"  style="width:100%;" value="0"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">刻度标是否朝内: </label>
					<select id="bar-xAxis_axisLabel_inside" title="是否显示" name="bar-xAxis_axisLabel_inside" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_toolbox_show" dictType="dict">
					</select>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">刻度旋转的角度: </label>
					<input id="bar-xAxis_axisLabel_rotate" name="bar-xAxis_axisLabel_rotate" type="text" class="form-control paramXName"  style="width:100%;" value="0"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">刻度与轴线距离: </label>
					<input id="bar-xAxis_axisLabel_margin" name="bar-xAxis_axisLabel_margin" type="text" class="form-control paramXName"  style="width:100%;" value="4"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">最小tick的label: </label>
					<input id="bar-xAxis_axisLabel_showMinLabel" name="bar-xAxis_axisLabel_showMinLabel" type="text" class="form-control paramXName"  style="width:100%;" value="0"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">最大tick的label: </label>
					<input id="bar-xAxis_axisLabel_showMaxLabel" name="bar-xAxis_axisLabel_showMaxLabel" type="text" class="form-control paramXName"  style="width:100%;" value="0"/>
				</div>
				<div class="col-lg-2 col-md-4 col-sm-4 col-xs-6">
					<label style="float:left;font-size:12px">对齐方式: </label>
					<select id="bar-title_x" title="是否显示" name="bar-title_x" class="form-control paramSelect paramXName" DictName="SYS_CHARTS_align" dictType="dict">
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">x轴关联字段: </label>
					<select id="x_column" title="x轴关联字段" name="x_column" class="form-control trendSelect paramXName" >
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">x轴数据源: </label>
					<input id="x_column_datas" title="x轴数据源" name="x_column_datas" type="text" class="form-control paramXName"  style="width:100%;" />
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">x轴关联字典: </label>
					<input id="x_column_dict" title="x轴关联字典" name="x_column_dict" type="text" class="form-control paramXName"  style="width:100%;" />
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">图例关联字段: </label>
					<select id="legend_column" title="图例关联字段" name="legend_column" class="form-control trendSelect paramXName" >
					</select>
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">图例数据源: </label>
					<input id="legend_column_datas" title="图例数据源" name="legend_column_datas" type="text" class="form-control paramSelect paramXName"  style="width:100%;" />
				</div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<label style="float:left;font-size:12px">图例关联字典: </label>
					<input id="legend_column_dict" title="图例关联字典" name="legend_column_dict" type="text" class="form-control paramXName"  style="width:100%;" />
				</div>
			</div>
		</div>
		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 dakuang">
			<div class="row content" style="margin-left:0;height:100%;" id="chartJs">
				<input class="btn btn-primary" type="button" value="加载JS" onclick="loadJs()"/>
				<input class="btn btn-primary" type="button" value="运行JS" onclick="yulan()"/>
				<input class="btn btn-primary" type="button" value="保存js" onclick="saveJs()"/>
				<textarea class="chartJs" id="chartJsContent">
				
				</textarea>
			</div>
		</div>
	</div>
	<!--
	<div  style="width:75%;height:4%;margin:auto auto;">
		<div class="BTNGROUP BTNGROUP_1042" style="margin:5px auto; display:inline-block;height: 30px;">
			<div style="margin-right:10px;display:inline-block">
			    <input type="button" id="yulan"  class="btn btn-primary" value="预览" onclick="yulan()"/>
			</div>
			<div style="margin-right:10px;display:inline-block">
			    <input type="button" id="save"  class="btn btn-success" value="保存" onclick="save()"/>
			</div>
			<div style="margin-right:10px;display:inline-block">
			    <input type="button" id="addElement"  class="btn btn-info" value="增加" onclick="addElement(null)"/>
			</div>
		</div>
	</div>
	
	<div id="valueContent" class="form-control" style="width:75%;height:103%;margin:auto auto;position:relative;overflow:auto">
		<div class="row cell" id="divSize">
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control" style="display: inline-block;">
					<lable style="float:left;font-size:12px">大屏显示列数: </lable>
					<input id="large" name="" type="text" class="form-control"  style="width:100px;"/>
					<input id="divSize_id" name="id" type="hidden"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control" style="display: inline-block;">
					<lable style="float:left;font-size:12px">小屏显示列数: </lable>
					<input id="middle" name="" type="text" class="form-control"  style="width:100px;"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control" style="display: inline-block;">
					<lable style="float:left;font-size:12px">扩展字段显示列数: </lable>
					<input id="small" name="" type="text" class="form-control"  style="width:100px;" />
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">值间链接符号: </lable>
					<input id="divSize_linkMark" name="linkMark" type="hidden" class="form-control"  style="width:220px;" value=""/>
					<input id="divSize_linkMarkName" name="linkMarkName" type="text" class="form-control"  style="width:150px;float:left"/>
					<input type="button" id="chooseFormStep2"  class="btn btn-success" style="float:left" value='跳转表单' onclick="chooseFormStep2()"/>
				</div>
			</div>
		</div>
		<div class="row cell" id="title">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">标题元素: </lable>
					<select id="title_columnValue" styleType="select" name="columnValue" class="selectpicker bla bla bli" data-live-search="true" textField="dictName" valueField="dictId" multiSelect="true"  multiple  DictName="SYS_COMPANY_LEVEL" dataField="dict_form_3877" initParam = "_">
					</select>
					<input id="title_id" name="id" type="hidden"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">值间链接符号: </lable>
					<input id="title_linkMark" name="linkMark" type="text" class="form-control"  style="width:50px;" value="-"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">字体大小: </lable>
					<input id="title_fontSize" name="fontSize" type="number" class="form-control"  style="width:150px;float:left" value="12"/><p style="float:left">px</p>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">颜色: </lable>
					<input id="title_fontColor" name="fontColor" type="color" class="form-control"  style="width:150px;" value="#000000"/>
				</div>
			</div>
		</div>
		<div class="row cell" id="address">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">位置元素: </lable>
					<select id="address_columnValue" styleType="select" name="columnValue" class="selectpicker bla bla bli" data-live-search="true" textField="dictName" valueField="dictId" multiSelect="true"  multiple  DictName="SYS_COMPANY_LEVEL" dataField="dict_form_3877" initParam = "_">
					</select>
					<input id="address_id" name="id" type="hidden"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">值间链接符号: </lable>
					<input id="address_linkMark" name="linkMark" type="text" class="form-control"  style="width:50px;" value="-"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">字体大小: </lable>
					<input id="address_fontSize" name="fontSize" type="number" class="form-control"  style="width:150px;float:left" value="12"/><p style="float:left">px</p>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">颜色: </lable>
					<input id="address_fontColor" name="fontColor" type="color" class="form-control"  style="width:150px;" value="#000000"/>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control bs-docs-example" style="float:left">
					<lable style="float:left;font-size:12px">是否标签化: </lable>
					<input id="address_isTag" name="isTag" type="text" class="form-control"  style="width:150px;" value="N"/>
				</div>
			</div>
		</div>	
		<div class="row cell" id="photo">
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control" style="float:left">
					<lable style="float:left;font-size:12px">图片元素: </lable>
					<select id="photo_columnValue"  styleType="select" name="columnValue"  class="form-control" emptyText="请选择..."  style="width:200px;" textField="dictName" valueField="dictID" DictName="SYS_COMPANY_LEVEL" nullItemText="请选择..." dataField="dict_form_3877"initParam = "_">
					</select>
					<input id="photo_id" name="id" type="hidden"/>
				</div>
			</div>
		</div>
		<div class="row cell" id="name">	
			<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
				<div class="ui search selection dropdown entitybox field-control" style="float:left">
					<lable style="float:left;font-size:12px">姓名元素: </lable>
					<select id="name_columnValue" title="" styleType="select" name="columnValue"  class="form-control" emptyText="请选择..."  style="width:200px;" textField="dictName" valueField="dictID" DictName="SYS_COMPANY_LEVEL" nullItemText="请选择..." dataField="dict_form_3877"initParam = "_">
					</select>
					<input id="name_id" name="id" type="hidden"/>
				</div>
			</div>
		</div>
		
	</div>
	-->
</div>
<script id="s1">
	
</script>
<script>
//根据表单ID获取字段信息做下拉项
$(function () { 
	initSelect();
	initData();
})

function initSelect(){
	var data;
	var formDefId = "${param.formDefId}";
	$.ajax({  
        url: '/myehr/formtemplate/getSelect.action?formDefId='+formDefId,  
        async:false, //是否为异步操作  
	    cache:false,    //是否缓存结果  
	    type:"POST",    //请求方式  
	    dataType:"json",    //服务器返回数据是什么类型  
	    success:function(result){   //此函数在服务器执行成功时被调用，参数result是服务器返回的值  
	    	data = result;
	    }  
    });
	$(".trendSelect").each(function(){
		for(var i=0;i<data.length;i++){
			document.getElementById(this.id).options.add(new Option(data[i].text,data[i].id));		
		}
	});
	var classes = [];
	classes = $(".paramSelect");
	var columns = [];
	for(var i=0;i<classes.length;i++){
		var column = {};
		column.formColumnShowType = 'N';//是否空值
		column.formColumnUsName = $(classes[i]).attr("dictname");//字典编码
		column.formColumnGuiType = $(classes[i]).attr("dictType");//字典类型
		columns[i] = column;
	}
	var datas = getselectdatasAll(columns);
	for(var j=0;j<datas.length;j++){
		var data = datas[j];
		for(var i=0;i<data.length;i++){
			document.getElementById(classes[j].id).options.add(new Option(data[i].name,data[i].code));
		}
	}
	$('.selectpicker').selectpicker('refresh');
}
function getselectdatasAll(e){
	var dictDatas;
	$.ajax({ 
	   url:'/myehr/dict/getselectdatasAll.action',
	   type:'post',
	   cache: false,
	   async: false,
	   contentType: 'application/json;charset=utf-8',
	   data: JSON.stringify(e),
	   success: function (datas){ 
		dictDatas = datas;
	   }
   });
   return dictDatas;
}
	var color = [];//颜色
	var toolbox_feature_dataView_readOnly={};
	var tooltip_trigger = "";//默认：‘item’，数据项图形出发；'axis'坐标轴出发；'none'什么都不触发
	var tooltip_axisPointer_type = "";//指示器配置项类型，'line' 直线指示器；'shadow' 阴影指示器；'cross' 十字准星指示器。其实是种简写，表示启用两个正交的轴的 axisPointer。
	var toolbox_show = "";//是否显示
	var legend_orient = "";//图例布局朝向。‘horizontal’水平；'vertical'垂直
	var title_text = "";//图表标题
	var grid_borderWidth = null;//网格线宽
	var series_barGap = null;//间隔
	var barMaxWidth = null;//最大宽度
	var grid_x = null;//
	var grid_y = null;//
	var grid_x2 = null;//
	var grid_y2 = null;//
	var toolbox_feature_magicType_Type = {};//启用的动态类型，包括'line'（切换为折线图）, 'bar'（切换为柱状图）, 'stack'（切换为堆叠模式）, 'tiled'（切换为平铺模式）。
	var legend_bottom = null;//[ default: 'auto' ]图例组件离容器下侧的距离。
	var xAxis_axisLabel_show = "";
	var xAxis_axisLabel_interval = null;//坐标轴刻度标签的显示间隔，在类目轴中有效。
	var xAxis_axisLabel_inside = "";//[ default: false ]刻度标签是否朝内，默认朝外。
	var xAxis_axisLabel_rotate = null;//[ default: 0 ]刻度标签旋转的角度，在类目轴的类目标签显示不下的时候可以通过旋转防止标签之间重叠。旋转的角度从 -90 度到 90 度。
	var xAxis_axisLabel_margin = 4;//[ default: 8 ]刻度标签与轴线之间的距离。
	var xAxis_axisLabel_showMinLabel = null;//是否显示最小 tick 的 label。可取值 true, false, null。默认自动判定（即如果标签重叠，不会显示最小 tick 的 label）。
	var xAxis_axisLabel_showMaxLabel = null;//是否显示最大 tick 的 label。可取值 true, false, null。默认自动判定（即如果标签重叠，不会显示最大 tick 的 label）
	var title_x = "";//x轴对其方式
	var xAxis_data = ['周一','周二','周三','周四','周五','周六','周日'];//x坐标轴的显示
	var legend_data = ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他'];//图标名称
	var toolbox_feature_restore = {};//配置项还原
	var toolbox_feature_saveAsImage = {};//保存为图片
	var seriesDatas = [{
							name:"直接访问",
							type:'bar',
							data:[320, 332, 301, 334, 390, 330, 320]
						},
						{
							name:'邮件营销',
							type:'bar',
							stack: '广告',
							data:[120, 132, 101, 134, 90, 230, 210]
						},
						{
							name:'联盟广告',
							type:'bar',
							stack: '广告',
							data:[220, 182, 191, 234, 290, 330, 310]
						},
						{
							name:'视频广告',
							type:'bar',
							stack: '广告',
							data:[150, 232, 201, 154, 190, 330, 410]
						},
						{
							name:'搜索引擎',
							type:'bar',
							data:[862, 1018, 964, 1026, 1679, 1600, 1570],
							markLine : {
								lineStyle: {
									normal: {
										type: 'dashed'
									}
								},
								data : [
									[{type : 'min'}, {type : 'max'}]
								]
							}
						},
						{
							name:'百度',
							type:'bar',
							barWidth : 5,
							stack: '搜索引擎',
							data:[620, 732, 701, 734, 1090, 1130, 1120]
						},
						{
							name:'谷歌',
							type:'bar',
							stack: '搜索引擎',
							data:[120, 132, 101, 134, 290, 230, 220]
						},
						{
							name:'必应',
							type:'bar',
							stack: '搜索引擎',
							data:[60, 72, 71, 74, 190, 130, 110]
						},
						{
							name:'其他',
							type:'bar',
							stack: '搜索引擎',
							data:[62, 82, 91, 84, 109, 110, 120]
						}]
						
						
						
						
window.onload=function(){
	var objval = $(".paramXName");
	var ur = window.location.href;
	
	
	
	
	var formDefId = ur.substring(ur.indexOf("=")+1,ur.indexOf("&"));
	var templateId = ur.substring(ur.indexOf("templateId=")+11);
	$.ajax({
		url:'${pageContext.request.contextPath }/charts/searchChartsData.action?formDefId='+formDefId+"&templateId="+templateId,
		type:'post',
		cache: true,
		async: false,
		contentType: "application/json",
		dataType:"json",
	   success: function (data){ 
	   
	   alert(data);
	   for(var i=0;i<data.length;i++){
		
		var title = data[i].templateColumnCode;
		$("[name="+title+"]").val(data[i].templateColumnValue);
	}
	alert("回显");
	   }
    });
	loadJs();
	yulan();
	
}
function loadJs(){	
	var options = {};
	
	color = $("#bar-colors").val().split(",");
	tooltip_trigger = $("#bar-tooltip_trigger").val();
	
	legend_orient = $("#bar-lo").val();
	toolbox_show = $("#bar-toolbox_show").val();
	title_text  = $("#bar-title_text").val();
	if(title_text==null||title_text==""){
		title_text = "开发柱状图";//图表标题
	}
	grid_borderWidth = $("#bar-grid_borderWidth").val();
	if(grid_borderWidth==null||grid_borderWidth==""){
		grid_borderWidth = 1;//图表标题
	}
	series_barGap = $("#bar-barGap").val();
	if(series_barGap==null||series_barGap==""){
		series_barGap = '30%';//图表标题
	}
	barMaxWidth = $("#bar-barMaxWidth").val();
	if(barMaxWidth==null||barMaxWidth==""){
		barMaxWidth = '50%';//图表标题
	}
	
	grid_x = $("#bar-grid_x").val();//
	if(grid_x==null||grid_x==""){
		grid_x = 25;//图表标题
	}
	grid_y = $("#bar-grid_y").val();//
	if(grid_y==null||grid_y==""){
		grid_y = 45;//图表标题
	}
	grid_x2 = $("#bar-grid_x2").val();//
	if(grid_x2==null||grid_x2==""){
		grid_x2 = 5;//图表标题
	}
	grid_y2 = $("#bar-grid_y2").val();//
	if(grid_y2==null||grid_y2==""){
		grid_y = 100;//图表标题
	}
	
	legend_bottom = $("#bar-legend_bottom").val();
	if(legend_bottom==null||legend_bottom==""){
		legend_bottom = 0;//图表标题
	}
	xAxis_axisLabel_show = $("#bar-xAxis_axisLabel_show").val();
	if(xAxis_axisLabel_show==null||xAxis_axisLabel_show==""){
		xAxis_axisLabel_show = false;//图表标题
	}else if (xAxis_axisLabel_show=="false"){
		xAxis_axisLabel_show=false;
	}else if (xAxis_axisLabel_show=="true"){
		xAxis_axisLabel_show=true;
	}
	xAxis_axisLabel_interval = $("#bar-xAxis_axisLabel_interval").val();
	if(xAxis_axisLabel_interval==null||xAxis_axisLabel_interval==""){
		xAxis_axisLabel_interval = 0;//图表标题
	}
	xAxis_axisLabel_inside = $("#bar-xAxis_axisLabel_inside").val();
	if(xAxis_axisLabel_inside==null||xAxis_axisLabel_inside==""){
		xAxis_axisLabel_inside = false;//图表标题
	}else if (xAxis_axisLabel_inside=="false"){
		xAxis_axisLabel_inside=false;
	}else if (xAxis_axisLabel_inside=="true"){
		xAxis_axisLabel_inside=true;
	}
	xAxis_axisLabel_rotate = $("#bar-xAxis_axisLabel_rotate").val();
	if(xAxis_axisLabel_rotate==null||xAxis_axisLabel_rotate==""){
		xAxis_axisLabel_rotate = 0;//图表标题
	}
	xAxis_axisLabel_margin = $("#bar-xAxis_axisLabel_margin").val();
	if(xAxis_axisLabel_margin==null||xAxis_axisLabel_margin==""){
		xAxis_axisLabel_margin = 4;//图表标题
	}
	xAxis_axisLabel_showMinLabel = $("#bar-xAxis_axisLabel_showMinLabel").val();
	if(xAxis_axisLabel_showMinLabel==null||xAxis_axisLabel_showMinLabel==""){
		xAxis_axisLabel_showMinLabel = null;//图表标题
	}
	xAxis_axisLabel_showMaxLabel = $("#bar-xAxis_axisLabel_showMaxLabel").val();
	if(xAxis_axisLabel_showMaxLabel==null||xAxis_axisLabel_showMaxLabel==""){
		xAxis_axisLabel_showMaxLabel = null;//图表标题
	}
	
	toolbox_feature_magicType_Type.type =$("#bar-toolbox_feature_magicType_Type").val().split(",");
	if(toolbox_feature_magicType_Type.type==null||toolbox_feature_magicType_Type.type==""){
		toolbox_feature_magicType_Type.type = "line,bar";//图表标题
	}
	toolbox_feature_dataView_readOnly.readOnly=$("#bar-toolbox_feature_dataView_readOnly").val();
	if(toolbox_feature_dataView_readOnly.readOnly==null||toolbox_feature_dataView_readOnly.readOnly==""){
		toolbox_feature_dataView_readOnly.readOnly = false;//图表标题
	}else if (toolbox_feature_dataView_readOnly.readOnly=="false"){
		toolbox_feature_dataView_readOnly.readOnly=false;
	}else if (toolbox_feature_dataView_readOnly.readOnly=="true"){
		toolbox_feature_dataView_readOnly.readOnly=true;
	}
	title_x = $("#bar-title_x").val();//x轴对其方式
	if(title_x==null||title_x==""){
		title_x = "center";//图表标题
	}
	
	if(getChartXDates()!=false){
		xAxis_data = getChartXDates();
	}
	if(getChartLegends()!=false){
		legend_data = getChartLegends();
	}
	
	var Datas = getChartDatas();
	
	
	
	
	
	
	
	
	
	
	

	options = {color: color,
					tooltip : {
						trigger: tooltip_trigger,
						axisPointer : {           
							type : tooltip_axisPointer_type        
						}
					},
					toolbox: {
						show: toolbox_show,
						feature: {
							dataView: toolbox_feature_dataView_readOnly,
							magicType: toolbox_feature_magicType_Type,
							restore: toolbox_feature_restore,
							saveAsImage: toolbox_feature_saveAsImage
						}
					},
				   title:{
					   text:title_text,
					   x: title_x
				   },grid:{
					   x:grid_x,
					   y:grid_y,
					   x2:grid_x2,
					   y2:grid_y2,
					   borderWidth:grid_borderWidth
				   },
					legend:{
						data:legend_data,
						orient: legend_orient,
						bottom: legend_bottom
					},
					xAxis:{
						data : xAxis_data,
						axisLabel: {
							show: xAxis_axisLabel_show,
							interval:xAxis_axisLabel_interval,
							inside: xAxis_axisLabel_inside,
							rotate: xAxis_axisLabel_rotate,
							margin: xAxis_axisLabel_margin,
							showMinLabel: xAxis_axisLabel_showMinLabel,
							showMaxLabel: xAxis_axisLabel_showMaxLabel,
							formatter:function(params){
								var newParamsName = '';
								var paramsNameNumber = params.length;
								var provideNumber = 1;
								var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
								if (paramsNameNumber > provideNumber) {
									for (var p = 0; p < rowNumber; p++) {
										var tempStr = '';
										var start = p * provideNumber;
										var end = start + provideNumber;
										if (p == rowNumber - 1) {
											tempStr = params.substring(start, paramsNameNumber);
										} else {
											tempStr = params.substring(start, end) + '\n';
										}
										newParamsName += tempStr;
									}
								} else {
									newParamsName = params;
								}
								return newParamsName
							}
						}
					},
					yAxis: [
						{
							type : 'value'
						}
					],
					series : seriesDatas
					};
	var chartJs = initJson(JSON.stringify(options));
	$("#chartJsContent").val("");
	$("#chartJsContent").val(chartJs);
}

function getChartXDates(){
	var x_column = $("#x_column").val().split('|')[0];
	if(x_column==''||x_column==null){
		return false;
	}
	var x_column_sql = $("#x_column_datas").val();
	if(x_column_sql==''||x_column_sql==null){
		return false;
	}
	var x_column_dict = $("#x_column_dict").val().split("|");
	var x = myehr_SQLFORBID_sql(x_column_sql);
	var y = [];
	if(x_column_dict[0]!=""){
		var dict = getDictDatas(x_column_dict[0],x_column_dict[1]);//获取字典数组
		//翻译数据
		for(var i=0;i<x.length;i++){
			y[i] = dict[x[i][x_column]];
		}
	}else{
		for(var i=0;i<x.length;i++){
			y[i] = x[i][x_column];
		}
	}
	return y ;
}
function getChartDatas(){
	
	
}
function getChartLegends(){
	var legend_column = $("#legend_column").val().split('|')[0];
	if(legend_column==''||legend_column==null){
		return false;
	}
	var legend_column_sql = $("#legend_column_datas").val();
	if(legend_column_sql==''||legend_column_sql==null){
		return false;
	}
	var legend_column_dict = $("#legend_column_dict").val().split("|");
	var x = myehr_SQLFORBID_sql(legend_column_sql);
	var y = [];
	if(legend_column_dict[0]!=""){
		var dict = getDictDatas(legend_column_dict[0],legend_column_dict[1]);//获取字典数组
		//翻译数据
		for(var i=0;i<x.length;i++){
			y[i] = dict[x[i][legend_column]];
		}
	}else{
		for(var i=0;i<x.length;i++){
			y[i] = x[i][legend_column];
		}
	}
	return y ;
}

function getDictDatas(dictType,dictCode){
	return getDictDatasPublic(dictType,dictCode);//List<Map>
}

function getDictDatasPublic(dictType,dictCode){
	var dictDatas ;
	$.ajax({ 
	   url:'/myehr/dict/selectSqlDictEntryByValue.action?dataField='+dictType+'&DictName='+dictCode,
	   type:'post',
	   cache: false,
	   async: false,
	   success: function (datas){ 
			dictDatas = datas;
	   }
    });
	return dictDatas;
}

function yulan(){
	document.getElementById('test_chart').style.width='95%';
    document.getElementById('test_chart').style.height='90%';
	var myChart = echarts.init(document.getElementById('test_chart'));
	var options = JSON.parse($("#chartJsContent").val());
	myChart.setOption(options);
}
function saveJs(){
	var objval = $(".paramXName");
	var ur = window.location.href;
	
	
	
	
	var formDefId = ur.substring(ur.indexOf("=")+1,ur.indexOf("&"));
	var templateId = ur.substring(ur.indexOf("templateId=")+11);
	var objvalue = [];
	var charts = [];
	for(var i=0;i<objval.length;i++){
		var bar={};
		bar.templateColumnCode = objval.eq(i).attr("id");
		bar.templateColumnValue = objval.eq(i).val();
		bar.templateColumnFormDefId = formDefId;
		bar.templateColumnTemplateId = templateId;
		charts.push(bar);
	}
	$.ajax({
		url:'${pageContext.request.contextPath }/charts/SavechChartsdata.action?formDefId'+formDefId,
		type:'post',
		cache: true,
		async: false,
		contentType: 'application/json;charset=utf-8',
		data :JSON.stringify(charts),
	   success: function (text="000"){ 
			layer.alert('保存成功', {
					  icon: 6,//1:√;2:×;3:问号;4:锁;5:哭脸;6.笑脸;7:！;
					  skin: 'layer-ext-moon' //
					})

	   }
    });
	
	
	
}			

function initJson(json, options) {
            var reg = null,
              formatted = '',
              pad = 0,
              PADDING = '    '; // one can also use '\t' or a different number of spaces
            // optional settings
            options = options || {};
            // remove newline where '{' or '[' follows ':'
            options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
            // use a space after a colon
            options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

            // begin formatting...

            // make sure we start with the JSON as a string
            if (typeof json !== 'string') {
                json = JSON.stringify(json);
            }
            // parse and stringify in order to remove extra whitespace
            json = JSON.parse(json);
            json = JSON.stringify(json);

            // add newline before and after curly braces
            reg = /([\{\}])/g;
            json = json.replace(reg, '\r\n$1\r\n');

            // add newline before and after square brackets
            reg = /([\[\]])/g;
            json = json.replace(reg, '\r\n$1\r\n');

            // add newline after comma
            reg = /(\,)/g;
            json = json.replace(reg, '$1\r\n');

            // remove multiple newlines
            reg = /(\r\n\r\n)/g;
            json = json.replace(reg, '\r\n');

            // remove newlines before commas
            reg = /\r\n\,/g;
            json = json.replace(reg, ',');

            // optional formatting...
            if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                reg = /\:\r\n\{/g;
                json = json.replace(reg, ':{');
                reg = /\:\r\n\[/g;
                json = json.replace(reg, ':[');
            }
            if (options.spaceAfterColon) {
                reg = /\:/g;
                json = json.replace(reg, ': ');
            }

            $.each(json.split('\r\n'), function(index, node) {
                var i = 0,
                  indent = 0,
                  padding = '';

                if (node.match(/\{$/) || node.match(/\[$/)) {
                    indent = 1;
                } else if (node.match(/\}/) || node.match(/\]/)) {
                    if (pad !== 0) {
                        pad -= 1;
                    }
                } else {
                    indent = 0;
                }

                for (i = 0; i < pad; i++) {
                    padding += PADDING;
                }

                formatted += padding + node + '\r\n';
                pad += indent;
            });

            return formatted;
        }


var templateId = "0";
function save(){
	var sList = [];
	var rows = $("#valueContent .row.cell");
	var rightNum=0;
	for(var i=0;i<rows.length;i++){
		var sysGridbycardTemplateColumn={};
		if(rows[i].id=='divSize'){
			sysGridbycardTemplateColumn.templateColumnId=$(rows[i]).find('input[name="id"]').val();
			sysGridbycardTemplateColumn.templateColumnTemplateId=${param.templateId};
			sysGridbycardTemplateColumn.templateColumnCode=rows[i].id;
			sysGridbycardTemplateColumn.templateColumnValue=$("#large").val()+','+$("#middle").val()+','+$("#small").val();
			sysGridbycardTemplateColumn.templateColumnLinkMark=$(rows[i]).find('input[name="linkMark"]').val();
			sysGridbycardTemplateColumn.templateColumnFormDefId=${param.formDefId};
		}else if(rows[i].id=='address'||rows[i].id=='title'){
			var values = "";
			var columnValue = $(rows[i]).find('select[name="columnValue"]').val();
			if(columnValue!=null){
				for(var j = 0; j<columnValue.length ;j++){
					if(j==0){
						values = values + columnValue[0];
					}else{
						values = values +"&"+ columnValue[j];
					}
				}
				sysGridbycardTemplateColumn.templateColumnFontSize=$(rows[i]).find('input[name="fontSize"]').val();
				sysGridbycardTemplateColumn.templateColumnFontColor=$(rows[i]).find('input[name="fontColor"]').val();
				sysGridbycardTemplateColumn.templateIsTag=$(rows[i]).find('input[name="isTag"]').val();
				sysGridbycardTemplateColumn.templateColumnLinkMark=$(rows[i]).find('input[name="linkMark"]').val();
			}
			sysGridbycardTemplateColumn.templateColumnId=$(rows[i]).find('input[name="id"]').val();
			sysGridbycardTemplateColumn.templateColumnTemplateId=${param.templateId};
			sysGridbycardTemplateColumn.templateColumnCode=rows[i].id;
			sysGridbycardTemplateColumn.templateColumnValue=values;
			sysGridbycardTemplateColumn.templateColumnFormDefId=${param.formDefId};
		}else if(rows[i].id=='photo'||rows[i].id=='name'){
			sysGridbycardTemplateColumn.templateColumnId=$(rows[i]).find('input[name="id"]').val();
			sysGridbycardTemplateColumn.templateColumnTemplateId=${param.templateId};
			sysGridbycardTemplateColumn.templateColumnCode=rows[i].id;
			sysGridbycardTemplateColumn.templateColumnValue=$(rows[i]).find('select[name="columnValue"]').val();
			sysGridbycardTemplateColumn.templateColumnFontSize=$(rows[i]).find('input[name="fontSize"]').val();
			sysGridbycardTemplateColumn.templateColumnFontColor=$(rows[i]).find('input[name="fontColor"]').val();
			sysGridbycardTemplateColumn.templateColumnFormDefId=${param.formDefId};
			rightNum++;
		}else{
			sysGridbycardTemplateColumn.templateColumnId=$(rows[i]).find('input[name="id"]').val();
			sysGridbycardTemplateColumn.templateColumnTemplateId=${param.templateId};
			sysGridbycardTemplateColumn.templateColumnCode="right"+rightNum;
			sysGridbycardTemplateColumn.templateColumnValue=$(rows[i]).find('select[name="columnValue"]').val();
			sysGridbycardTemplateColumn.templateColumnFontSize=$(rows[i]).find('input[name="fontSize"]').val();
			sysGridbycardTemplateColumn.templateColumnFontColor=$(rows[i]).find('input[name="fontColor"]').val();
			sysGridbycardTemplateColumn.templateColumnFormDefId=${param.formDefId};
			rightNum++;
		}
		sList[i]=sysGridbycardTemplateColumn;
	}
	
	$.ajax({  
        url: '/myehr/formtemplate/saveCardTemplate.action',  
        async:false, //是否为异步操作  
	    cache:false,    //是否缓存结果  
	    type:"POST",    //请求方式  
	    data:JSON.stringify(sList),
		contentType: 'application/json;charset=utf-8',
	    success:function(result){   //此函数在服务器执行成功时被调用，参数result是服务器返回的值  
			layer.alert('保存成功', {
					  icon: 1,//1:√;2:×;3:问号;4:锁;5:哭脸;6.笑脸;7:！;
					  skin: 'layer-ext-moon' 
					})
	    	closex();
	    }  
    });
}

var cesllObjNum = 0;
function initData(){
	$.ajax({  
        url: '/myehr/formtemplate/getDataInfo.action?formDefId=${param.formDefId}&templateId=${param.templateId}',  
        async:false, //是否为异步操作  
	    cache:false,    //是否缓存结果  
	    type:"POST",    //请求方式  
	    success:function(datas){   //此函数在服务器执行成功时被调用，参数result是服务器返回的值  
			for(var k=0;k<datas.length;k++){
				if(datas[k].templateColumnCode=='divSize'){
					$("#"+datas[k].templateColumnCode).find('input[name="id"]').val(datas[k].templateColumnId);
					$("#large").val(datas[k].templateColumnValue.split(",")[0]);
					$("#middle").val(datas[k].templateColumnValue.split(",")[1]);
					$("#small").val(datas[k].templateColumnValue.split(",")[2]);
					$("#"+datas[k].templateColumnCode).find('input[name="fontColor"]').val(datas[k].templateColumnFontColor);
				}else if(datas[k].templateColumnCode=='title'||datas[k].templateColumnCode=='address'||datas[k].templateColumnCode=='photo'||datas[k].templateColumnCode=='name'){
					$("#"+datas[k].templateColumnCode).find('input[name="id"]').val(datas[k].templateColumnId);
					$("#"+datas[k].templateColumnCode).find('select[name="columnValue"]').val(datas[k].templateColumnValue.split("&"));
					$("#"+datas[k].templateColumnCode).find('input[name="fontSize"]').val(datas[k].templateColumnFontSize);
					$("#"+datas[k].templateColumnCode).find('input[name="fontColor"]').val(datas[k].templateColumnFontColor);
					$("#"+datas[k].templateColumnCode).find('input[name="isTag"]').val(datas[k].templateIsTag);
					$("#"+datas[k].templateColumnCode).find('input[name="linkMark"]').val(datas[k].templateColumnLinkMark);
				}else{
					addElement(datas[k]);
				}
			}
	    }  
    });
}



function chooseFormStep2(e){
	var url = '/myehr/jsp/form/formcreate/formChoose.jsp?fromType=config_form&widget=model5';
	layer.open({
		type:2,
		closeBtn:1,
		shadeClose:true,
		area:['950','700'],
		maxmin:true,
		title:'选择表单',
		content:url,
		success:function(layero,index){
		},
		end:function(){			
			
		}
	 }); 
} 

function closex(){
	var index=parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}

function getFormNameById(formDefId){
	var formDefName;
	$.ajax({
		url:'/myehr/form/getFormNameById.action?formDefId='+formDefId,
		type:'post',
		cache: false,
		async: false,
		contentType: 'application/json;charset=utf-8',
		success: function (data) {
			formDefName = data.formDefName;
			}
		});
	return formDefName;
}



</script>
</body>
</html>