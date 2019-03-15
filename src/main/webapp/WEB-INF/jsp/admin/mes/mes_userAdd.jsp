<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<!-- 内容部分 -->
<form id="ff" method="post">
    <input type="hidden" name="mesId" value="${mesid}" id="mesid"/>
    <table style="width: 100%;">
        <tr>
            <td>用户名:</td>
            <td><input class="easyui-textbox" type="text" name="username"  data-options="required:true" style="width: 100%;"/></td>
            <td>社团职位:</td>
            <td><input class="easyui-textbox" type="text" name="jobNum" id="jobnum"  data-options="" style="width: 100%;"/></td>
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
<script>
    function submitForm() {
        //不知道為什麼用easyui自帶的ajaxt提交文件無法上傳
        $.messager.progress({
            title:'提示消息',
            msg:'正在提交中'
        });
        $("#ff").form('submit', {
            url: '<%=basePath %>org_mes/mes_userAdd',
            onSubmit: function(param){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// hide progress bar while the form is invalid
                }


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
                $("#win").window("close");
                $("#dg").datagrid("reload");
                $("#dg").datagrid("uncheckAll");
            }
        });
    }
    $(function () {
        $("#jobnum").combobox({
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
    })

</script>