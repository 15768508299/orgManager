<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/my/admin/indexOL/list.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/admin/indexOL/list.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jq.form.js"></script>
    <script src="<%=basePath %>js/easyui-lang-zh_CN.js"></script>
    <!--umeditor包-->
    <link href="<%=basePath %>umEditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>umEditor/lang/zh-cn/zh-cn.js"></script>
    <!--puload包-->
    <script src="<%=basePath %>plugin/plupload-2.1.9/js/plupload.full.min.js" type="text/javascript"></script>
    <script src="<%=basePath %>plugin/ajaxfileupload.js" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!--add页面-->
    <title>后台管理</title>
</head>
<script type="text/javascript">
    function openWindow(width,height,href,title){
        $('#win').window({
            top:50,
            width:width,
            height:height,
            href:href,
            modal:true,
            title:title,
            reload:true
        });
    }

    function watchPicture(url){
        $('#picture').window({
            top:50,
            width:500,
            height:500,
            modal:true,
            title:'图片框',
            reload:true,
            content:'<img width="100%" height="100%" src="<%=basePath%>'+url+'"/>'
        });
    }

    function addMes(url,title){
        openWindow(978,400,url,title);
    }

    function editMes(url,title){
        //获取id
        var row = $('#dg').datagrid('getSelections');
        if(row.length>1){
            $.messager.alert('提示','编辑只能选择一行');
        }else if(row.length<1){
            $.messager.alert('提示','请选择编辑行');
        }
        else{
            var newUrl = url+"?id="+row[0].id;
            console.log(newUrl);
            openWindow(978,400,newUrl,title);
        }
    }
    function deleteMes(){
        $.messager.confirm('确认','确定要删除这些数据，删除数据不可恢复(慎重!)',function(r){
            if (r){
                var ids = [];
                var row = $('#dg').datagrid('getSelections');
                for(var key in row){
                    ids.push(row[key].id);
                }
                ids = ids.join(",");
                $.messager.progress({
                    title:'提示消息',
                    msg:'正在删除中'
                });
                $.ajax({
                    url:'<%=basePath%>/org_activity/deleteOL?ids='+ids,
                    success:function (data){
                        $.messager.show({
                            title:'消息提示',
                            msg:data.message
                        });
                        $.messager.progress('close');
                        $("#dg").datagrid("reload");
                    }
                });
            }
        });
    }
    function searchMes() {
        var where_sql = "";
        var searchText = $("#search").val();
        if(searchText != ""){
            where_sql = searchText;
        }
        $('#dg').datagrid({
            queryParams: {
                where_sql: where_sql
            }
        });
    }

</script>
<body>
<div class="myDiv">
    <table class="easyui-datagrid" id="dg" title="社团信息" style="height:100%;width: 100%;font-size: 100px;">
    </table>
    <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" onclick="addMes('<%=basePath%>org_activity/actOlAdd','新增轮播信息')" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增轮播信息</a>
            <a href="javascript:void(0)" onclick="editMes('<%=basePath%>org_activity/actOlAddEdit','编辑轮播信息')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑轮播信息</a>
            <a href="javascript:void(0)" onclick="deleteMes()" class="easyui-linkbutton" iconCls="icon-cut" plain="true">删除轮播信息(可多选)</a>
        </div>
        <div>
            活动描述: <input class="easyui-textbox" id="search" style="width:160px">
            <a href="javascript:void(0)" onclick="searchMes()" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
</div>
<div id="win" style="padding: 5px;"></div>
<div id="picture"></div>
<div id="detail"></div>
</body>
<script>
    $(function () {
        $('#dg').datagrid({
            method: "POST",
            url:"<%=basePath%>org_activity/actOlList",
            pageSize: 10,//初始化分页大小
            idField: 'id',//标识数据,唯一字段
            singleSelect: false,//是否允许只选择一行
            checkOnSelect: false,
            selectOnCheck: true,
            pagination: true,//是否显示底部分页工具栏
            fitColumns: true,//宽度自适应
            singleSelect: false,//是否允许只选择一行
            sortName:'id',
            sortOrder:'asc',
            striped:true,
            columns:[[
                {field:'ck',checkbox:true,width:100},
                {field:'id',title:'ID',width:100},
                {field:'description',title:'轮播描述',width:200,sortable:true},
                {field:'photo',title:'轮播图片',width:141,formatter:toWatchButton},
                {field:'target',title:'轮播跳转地址',width:200,sortable:true},
                {field:'olLevel',title:'级别(1为主,2为右侧)',width:200,sortable:true},
            ]],
            toolbar: '#tb',
            onLoadSuccess: function (data) {
                //让其默认选中
                var rowData = data.rows;
                $.each(rowData,function(id,val){//遍历JSON
                    if(val.state==1){
                        $("#dg").datagrid("selectRow", id);//如果后台传过来的数据表示要选中，则此表示一记载就选中
                    }
                });
            }
        });
    })
</script>
</html>