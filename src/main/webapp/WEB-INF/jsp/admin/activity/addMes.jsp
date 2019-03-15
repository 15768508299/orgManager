<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
    #addUser td label{
        margin-right: 10px;
    }
    #addUser tr{
        margin-top: 5px;
    }
    #addUser td:nth-child(1){
        width: 15%;
        text-align:right;

    }
    #addUser td:nth-child(2){
        width: 30%;
        height: 50px;
    }
    #addUser td:nth-child(3){
        width: 20%;
        text-align: right;
    }
    #addUser td:nth-child(4){
        width: 30%;
    }
</style>
<!-- 内容部分 -->
<form id="ff" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${id}" />
    <input type="hidden" name="actDetail" id="actdetail">
    <input type="hidden" name="readnum" value="0">
    <table id="addUser"  style="width: 100%;">
        <tr>
            <td><label>活动主题</label></td>
            <td><input class="easyui-textbox" type="text" name="actTheme"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>活动日期</label></td>
            <td><input class="easyui-datebox" name="actDateStr" id="actDate"  data-options="required:true" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>开始时间</label></td>
            <td><input class="easyui-timespinner" name="startTime" id="startTime"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>结束时间</label></td>
            <td><input class="easyui-timespinner" name="endTime" id="endTime"  data-options="required:true" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>发布社团：</label></td>
            <td><input class="easyui-combobox" name="issueBy" id="search_mesnamet" style="width:100%"></td>
        </tr>
        <tr>
            <td><label>活动简介</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="actIntroduction"  data-options="prompt:'填写活动简介',validType:'',multiline:true" style="width: 100%;height: 120px;"/></td>
        </tr>
        <tr>
            <td><label>活动详情</label></td>
            <td colspan="3"><script type="text/plain" id="myEditor" style="width:800px;height:340px;"></script></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    var um = UM.createEditor("myEditor");
    var action = "add";
    var url = '<%=basePath %>org_activity/add';
    <c:choose>
        <c:when test="${id != '' && id != null}">
              url = '<%=basePath %>org_activity/edit';
              action = "edit";
        </c:when>
    </c:choose>
    function submitForm() {
        console.log(url);
        //不知道為什麼用easyui自帶的ajaxt提交文件無法上傳
        $.messager.progress({
            title:'提示消息',
            msg:'正在提交中'
        });
        $("#ff").form('submit', {
            url: url,
            onSubmit: function(param){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// hide progress bar while the form is invalid
                }
                //富文本编辑器处理
                var textDetail = um.getContent();
                $("#actdetail").attr("value",textDetail);


                return isValid;	// return false will stop the form submission
            },
            success:function(data){
                //alert(data);
                var obj =  JSON.parse(data);
                console.log(obj);
                $.messager.show({
                    title:'消息提示',
                    msg:obj.message,
                });
                $.messager.progress('close');
                $("#ff").form("reset");
                $("#dg").datagrid("reload");
                if(action == "edit"){
                    $("#win").window("close");
                    $("#dg").datagrid("uncheckAll");
                }
            }
        });
    }
    $(function () {
        /*加载所有社团*/
        $('#search_mesnamet').combobox({
            url: '<%=path%>/org_mes/getAllName',
            valueField: 'id',
            textField: 'mesName'
        });
    });
</script>
<c:choose>
    <c:when test="${id != '' && id != null}">
        <script>
        setTimeout(function() {
            var data = ${data};
            um.setContent(data.actDetail);
            data.actDateStr = timeStapmToString(data.actDate);
            $("#ff").form("load",data);
        },1000);
        </script>
    </c:when>
</c:choose>