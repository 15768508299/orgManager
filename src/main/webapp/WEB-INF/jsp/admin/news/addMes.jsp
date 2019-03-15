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
    <input type="hidden" name="id" value="0" />
    <input type="hidden" name="newDetail" id="newdetail">
    <input type="hidden" name="readNum" value="0">
    <table id="addUser"  style="width: 100%;">
        <tr>
            <td><label>新闻标题</label></td>
            <td><input class="easyui-textbox" type="text" name="newTheme"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>发布日期</label></td>
            <td><input class="easyui-datebox" name="issueTimeStr" id="issueTimeStr"  data-options="required:true" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>发布社团：</label></td>
            <td><input class="easyui-combobox" name="issueBy" id="search_mesname_s" style="width:100%"></td>
        </tr>
        <tr>
            <td><label>新闻简介</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="newIntroduction"  data-options="prompt:'填写新闻简介',validType:'',multiline:true" style="width: 100%;height: 120px;"/></td>
        </tr>
        <tr>
            <td><label>新闻详情</label></td>
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
    var url = '<%=basePath %>org_news/add';
    var action = "add";
    <c:choose>
        <c:when test="${id != '' && id != null}">
              url = '<%=basePath %>org_news/edit';
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
                $("#newdetail").attr("value",textDetail);


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
                if(action == "edit"){
                    $("#win").window("close");
                }
                $("#dg").datagrid("uncheckAll");
                $("#dg").datagrid("reload");
            }
        });
    }
    $(function () {
        /*加载所有社团*/
        $('#search_mesname_s').combobox({
            url: '<%=path%>/org_mes/getAllName',
            valueField: 'id',
            textField: 'mesName'
        });
    });
</script>
<c:choose>
    <c:when test="${id != '' && id != null}">
        <script>
            var data = ${data};
            um.setContent(data.newDetail);
            data.issueTimeStr = timeStapmToString(data.issueTime);
            $("#ff").form("load",data);
        </script>
    </c:when>
</c:choose>