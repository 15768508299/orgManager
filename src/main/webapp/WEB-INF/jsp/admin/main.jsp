<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/demo.css">
<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理</title>
</head>
<style type="text/css">
body,html{
	padding:0;
	margin:0;
	height:100%;
	width:100%;
}
.myDiv{
	height: 100%;
    width: 100%;
}
*{
	box-sizing: border-box;
}
ul{
	margin:0px;
	padding: 0px;
}
ul li{
	list-style: none;
	border-bottom:1px solid red;
	height: 30px;
}
ul li a{
	display: inline-block;
	width:100%;
	height:100%;
	text-decoration: none;
	font-size: 18px;
}
.panel-title{
	font-size: 15px;
}
</style>
<script type="text/javascript">
function addTab(title,url){
	var content = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
	$('#myTab').tabs('add', {
    	title: title,
        content: content,
        closable: true,
    });
}
</script>
<body>
	<div class="myDiv">
	<div style="height100%;width: 100%;" class="easyui-layout" data-options="fit:true">
		<div id="north" region="north" style="height:100px;margin-bottom:5px;">
    		<div style="float: left;width: 70%;height: 100%;">
				<img src="<%=basePath%>img/logo_6.png" style="height: 94px;float: left;"/>
				<div style="float: left;font-size: 37px;margin-top: 17px;">社团信息网后台管理系统</div>
			</div>

			<div style="float: right;height: 100%;width: 13%;margin-right: 10px;border-color:#95B8E7;border-style: solid;border-width: 0 0 1px 2px;">
				<div style="border-color:#95B8E7;border-style: solid;border-width: 0 0 1px 0;height: 70%;">管理员</br>
					admin
				</div>
				<div style="height: 30%;border-color:#95B8E7;border-style: solid;border-width: 0 0 0px 0;"><span style="display: inline-block;float: left;">修改资料</span><span style="display: inline-block;float: right;"><a href="<%=basePath%>">注销</a></span></div>
			</div>
    	</div>
    	<div region="west" split="true" title="导航栏" style="width:15%;">
    		<div class="easyui-accordion" style="width:100%;">
				<div title="首页管理" data-options="iconCls:'icon-ok'" style="overflow:auto;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'"  style="width:100%;text-align: left;" onclick="addTab('轮播列表','<%=basePath%>org_index_ol/adminGetList')">轮播列表</a><br/>
				</div>
				<div title="社团管理" data-options="iconCls:'icon-ok'" style="overflow:auto;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('查看社团','<%=basePath%>org_mes/adminGetList')">查看社团</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('社团用户','<%=basePath%>org_mes/getUserList')">社团用户</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" href="javascript:void(0)" onclick="showcontent('java')">后续开放</a><br/>
				</div>
				<div title="用户管理" data-options="iconCls:'icon-ok'" style="overflow:auto;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('查看用户','<%=basePath%>org_user/adminGetList')">查看用户</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" href="javascript:void(0)" onclick="showcontent('java')">后续开放</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" href="javascript:void(0)" onclick="showcontent('java')">后续开放</a><br/>
				</div>
				<div title="活动管理" data-options="iconCls:'icon-ok'" style="overflow:auto;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('查看活动','<%=basePath%>org_activity/adminGetList')">查看活动</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('活动导航','<%=basePath%>org_activity/actOlList')">活动页导航管理</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" href="javascript:void(0)" onclick="showcontent('java')">后续开放</a><br/>
				</div>
				<div title="新闻管理" data-options="iconCls:'icon-ok'" style="overflow:auto;">
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('查看新闻','<%=basePath%>org_news/adminGetList')">查看新闻</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" onclick="addTab('新闻页导航','<%=basePath%>org_news/newOlList')">新闻页导航管理</a><br/>
					<a class="easyui-linkbutton" data-options="iconCls:'icon-sum'" style="width:100%;text-align: left;" href="javascript:void(0)" onclick="showcontent('java')">后续开放</a><br/>
				</div>
			</div>
    	</div>
    	<!-- 内容部分 -->
    	<div id="content" region="center" style="width: 100%;height: 100%;">
	    	<div class="easyui-tabs" id="myTab" style="width:100%;height:100%">
    			<div title="首页" style="width: 85%;height:100%;">

                </div>
    		</div>
    	</div>
    </div>
    </div>
</body>
</html>