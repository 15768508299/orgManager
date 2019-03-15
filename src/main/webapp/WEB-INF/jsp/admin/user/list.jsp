<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/my/admin/user/list.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/admin/user/list.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jq.form.js"></script>
    <script src="<%=basePath %>js/easyui-lang-zh_CN.js"></script>
    <script src="<%=basePath %>plugin/ajaxfileupload.js" type="text/javascript"></script>
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

    function watchDetail(content){
        alert(content);
        $('#detail').window({
            top:50,
            width:500,
            height:500,
            modal:true,
            title:'详情介绍框',
            reload:true,
            content:'<div>详情介绍</div>'
        });

    }

    function addMes(url,title){
       openWindow(797,510,url,title);
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
            openWindow(797,510,newUrl,title);
        }
    }
    function deleteMes(){
        var row = $('#dg').datagrid('getSelections');
        if(row.length>1){
            $.messager.confirm('确认','确定要删除这些数据，删除数据不可恢复(慎重!)',function(r){
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
                        url:'<%=basePath%>/org_user/delete?ids='+ids,
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
        var username = $("#search_username").val();
        var truename = $("#search_truename").val();
        var identifity =  $("#search_identifity").val()

        var where_sql = '{"userName":"'+ username + '","userTrueName":"' + truename + '","userIdentifity":"' + identifity + '"}';
        $('#dg').datagrid({
            queryParams: {
                where_sql: where_sql
            }
        });
    }

</script>
<body>
<div class="myDiv">
    <table class="easyui-datagrid" id="dg" title="用户信息" style="height:100%;width: 100%;">
    </table>
    <div id="tb" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" onclick="addMes('<%=basePath%>/org_user/add','新增用户信息')" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增用户信息</a>
            <a href="javascript:void(0)" onclick="editMes('<%=basePath%>/org_user/edit','编辑用户信息')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑用户信息</a>
            <a href="javascript:void(0)" onclick="deleteMes()" class="easyui-linkbutton" iconCls="icon-cut" plain="true">删除用户信息(可多选)</a>
        </div>
        <div>
            用户名: <input class="easyui-textbox" id="search_username" style="width:160px">
            真实姓名: <input class="easyui-textbox" id="search_truename" style="width:160px">
            身份证: <input class="easyui-textbox" id="search_identifity" style="width:160px">
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
            url:"<%=basePath%>/org_user/adminGetList",
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
                {field:'userSex',title:'性别',width:50,sortable:true},
                {field:'userAge',title:'年龄',width:50,sortable:true},
                {field:'userPhoto',title:'照片',width:200},
                {field:'userFavour',title:'爱好',width:100},
                {field:'userIdentifity',title:'身份证号',width:200,sortable:true},
                {field:'userIntrod',title:'个人介绍',width:150},
                {field:'userEmail',title:'邮箱',width:200,sortable:true},
                {field:'userQQWei',title:'QQ|微信',width:160,sortable:true},
                {field:'userPhone',title:'电话',width:120},
                {field:'userPassword',title:'登录密码',width:70}
            ]],
            frozenColumns:[[
                {field:'ck',checkbox:true,width:100},
                {field:'id',title:'ID',width:40},
                {field:'userName',title:'登录名',width:100,sortable:true},
                {field:'userTrueName',title:'姓名',width:100}
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