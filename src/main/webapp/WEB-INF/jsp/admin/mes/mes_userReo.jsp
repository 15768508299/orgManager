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
<form id="ff" method="post">
    <input type="hidden" name="userid" value="${userid}">
    <input type="hidden" name="mesid" value="${mesid}">
    <table id="addUser"  style="width: 100%;">
        <tr>
            <td><label>社团权限</label></td>
            <td colspan="3"><input class="easyui-combobox" id="jobNum"  name="jobNum"  style="width: 100%;"/></td>
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
    var url = '<%=basePath %>org_mes/mes_userReo';
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

                return isValid;	// return false will stop the form submission
            },
            success:function(data){
                var obj =  JSON.parse(data);
                $.messager.show({
                    title:'消息提示',
                    msg:obj.message,
                });
                $.messager.progress('close');
                $("#dg").datagrid("reload");
                $('#win').window("close");
                $("#dg").datagrid("uncheckAll");
            }
        });

    }
    $(function () {
        $("#jobNum").combobox({
            valueField: 'id',
            textField: 'name',
            data: [{
                id: '1',
                name: '社长'
            },{
                id: '2',
                name: '负责人'
            },{
                id: '3',
                name: '普通成员'
            }]
        });

        setTimeout(function () {
        <c:choose>
            <c:when test="${id != '' && id != null}">
                var data = ${data};
                $("#ff").form("load",data);
            </c:when>
        </c:choose>
        }, 100);
    });
</script>
