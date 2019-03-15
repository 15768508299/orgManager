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
    <input type="hidden" name="id" value="0" />
    <input id="file" name="input-b1" type="file" style="display: none">
    <table id="addUser"  style="width: 100%;">
        <tr>
            <td><label>登录名</label></td>
            <td><input class="easyui-textbox" type="text" name="userName"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>密码</label></td>
            <td><input class="easyui-textbox" type="password" name="userPassword"  data-options="required:true" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>真实姓名</label></td>
            <td><input class="easyui-textbox" type="text" name="userTrueName"  data-options="prompt:'姓名必填',required:true,validType:''" style="width: 100%;"/></td>
            <td><label>性别</label></td>
            <td><input class="easyui-textbox" type="text" name="userSex"  data-options="prompt:'性别',validType:''" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>年龄</label></td>
            <td><input class="easyui-textbox" type="text" name="userAge"  data-options="prompt:'年龄',validType:''" style="width: 100%;"/></td>
            <td><label>身份证号</label></td>
            <td><input class="easyui-textbox" type="text" name="userIdentifity"  data-options="prompt:'身份证号',validType:''" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>邮箱</label></td>
            <td><input class="easyui-textbox" type="text" name="userEmail"  data-options="prompt:'填写邮箱',validType:'email'" style="width: 100%;"/></td>
            <td><label>QQ|微信</label></td>
            <td><input class="easyui-textbox" type="text" name="userQQWei"  data-options="prompt:'填写QQ|微信',validType:''" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>电话</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="userPhone"  data-options="prompt:'填写电话',validType:''" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>个人照片</label></td>
            <td colspan="3"><input class="easyui-textbox" id="upload" name="userPhoto" data-options="prompt:'请上传个人照'" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>爱好</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="userFavour"  data-options="prompt:'请输入爱好',validType:'',multiline:true" style="width: 100%;height: 120px;"/></td>
        </tr>
        <tr>
            <td><label>个人介绍</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="userIntrod"  data-options="prompt:'填写个人介绍',validType:'',multiline:true" style="width: 100%;height: 120px;"/></td>
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
    var count = 0;
    var url = '<%=basePath %>org_user/add';
    <c:choose>
        <c:when test="${id != '' && id != null}">
              url = '<%=basePath %>org_user/edit';
              count = 1;
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
                $("#dg").datagrid("reload");
                if(count == 1){
                    $('#win').window("close");
                }else{
                    $("#ff").form("reset");
                }
            }
        });

        <%--  $("#messageForm").ajaxSubmit({
            type:"post",  //提交方式
            url:"<%=path%>/message/add", //请求url
            success:function(data){ //提交成功的回调函数
                $.messager.alert("提示",data);
            }
        });      --%>
    }
    $(function () {
        /*文件异步上传*/
        $("#file").change(function () {
            $("#upload").textbox("setValue",$("#file").val());
        });
        $("#upload").textbox({
            onClickButton:function () {
                return $("#file").click();
            },
            buttonText:"选择文件",
            icons:[{
                iconCls:'icon-search',
                handler:function (e) {
                    watchPicture($("#upload").textbox("getValue"));
                }
            },
                {
                    iconCls:'icon-save',
                    handler:function (e) {
                        $.ajaxFileUpload({
                            type: "POST",
                            url: "<%=basePath%>compent/uploadImg",
                            /*data:{picParams:text},//要传到后台的参数，没有可以不写  */
                            secureuri : false,//是否启用安全提交，默认为false
                            fileElementId:'file',//文件选择框的id属性
                            dataType: 'json',//服务器返回的格式
                            async : false,
                            success: function(data){
                                if(data.status != 'success'){
                                    alert(data.message);
                                }else{
                                    $("#upload").textbox("setValue",data.uploadUrl);
                                    $("#file").change(function () {
                                        $("#upload").textbox("setValue",$("#file").val());
                                    });
                                }
                            }
                        });
                    }
                },
                {
                    iconCls:'icon-cut',
                    handler:function (e) {
                        $(e.data.target).textbox('clear');
                        $("#file").val("");
                    }
                }

            ]
        });

    });
</script>
<c:choose>
    <c:when test="${id != '' && id != null}">
        <script>
            var data = ${data};
            $("#ff").form("load",data);
        </script>
    </c:when>
</c:choose>