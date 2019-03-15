<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/my/admin/activity/list.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jq.form.js"></script>
    <script src="<%=basePath %>js/easyui-lang-zh_CN.js"></script>

    <!--umeditor包-->
    <link href="<%=basePath %>umEditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>umEditor/lang/zh-cn/zh-cn.js"></script>
    <!--add页面-->
    <title>后台管理</title>
</head>
<script type="text/javascript">
    function openWindow(width,height,href,title){
        $('#win').window({
            top:0,
            width:width,
            height:height,
            href:href,
            modal:true,
            title:title,
            reload:true
        });
    }

    function add(url,title){
       openWindow(950,510,url,title);
    }

    function edit(url,title){
        //获取id
        var row = $('#dg').datagrid('getSelections');
        if(row.length>1){
            $.messager.alert('提示','编辑只能选择一行');
        }else if(row.length<1){
            $.messager.alert('提示','请选择编辑行');
        }
        else{
            var newUrl = url+"?id="+row[0].id;
            openWindow(950,510,newUrl,title);
        }
    }
    function deletes(){
        var row = $('#dg').datagrid('getSelections');
        if(row.length>0){
            $.messager.confirm('确认','确定要删除这些新闻，删除新闻不可恢复(慎重!)',function(r){
                if (r){
                    var ids = [];
                    for(var key in row){
                        ids.push(row[key].id);
                    }
                    ids = ids.join(",");
                    $.messager.progress({
                        title:'提示消息',
                        msg:'正在删除中'
                    });
                    $.ajax({
                        url:'<%=basePath%>/org_news/delete?ids='+ids,
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
        }else{
            $.messager.alert('提示','请选中要删除的数据');
        }
    }

    function searchMes() {
        var newTheme = $("#search_acttheme").val();
        var issueTime = $("#search_actdate").val();

        var where_sql = '{"newTheme":"'+ newTheme + '","issueTime":"' + issueTime + '"}';

        $('#dg').datagrid({
            queryParams: {
                where_sql: where_sql
            }
        });
    }

</script>
<body>
<div class="myDiv">
    <table class="easyui-datagrid" id="dg" title="活动信息" style="height:100%;width: 100%;">
    </table>
    <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" onclick="add('<%=basePath%>org_news/add','新增新闻信息')" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增新闻信息</a>
            <a href="javascript:void(0)" onclick="edit('<%=basePath%>org_news/edit','编辑新闻信息')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑新闻信息</a>
            <a href="javascript:void(0)" onclick="deletes()" class="easyui-linkbutton" iconCls="icon-cut" plain="true">删除活动信息(可多选)</a>
        </div>
        <div>
            <%--社团名称: <input class="easyui-textbox" id="search_username" style="width:160px">--%>
            新闻主题: <input class="easyui-textbox" id="search_acttheme" style="width:160px">
            发布日期: <input class="easyui-datebox" id="search_actdate" style="width:160px">
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
            url:"<%=basePath%>org_news/adminGetList",
            pageSize: 10,//初始化分页大小
            idField: 'id',//标识数据,唯一字段
            singleSelect: false,//是否允许只选择一行
            checkOnSelect: true,
            selectOnCheck: true,
            pagination: true,//是否显示底部分页工具栏
            fitColumns: false,//宽度自适应
            singleSelect: false,//是否允许只选择一行
            sortName:'id',
            sortOrder:'asc',
            columns:[[
                {field:'issueTime',title:'发布日期',width:122,sortable:true,formatter:timeStapmToString},
                {field:'newDetail',title:'新闻详情',width:115,sortable:true,formatter:toWatchDetail},
                {field:'readNum',title:'阅读量',width:64}
            ]],
            frozenColumns:[[
                {field:'ck',checkbox:true,width:100},
                {field:'id',title:'ID',width:100},
                {field:'newTheme',title:'新闻标题',width:301,sortable:true},
                {field:'newIntroduction',title:'新闻简介',width:91,formatter:toWatchDetail}
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