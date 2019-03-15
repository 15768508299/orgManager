<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<style>
    .file-drop-zone{
        height: 42%;
    }
</style>
<!--内嵌表单-->
<form class="form-horizontal" role="form" id="userForm" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${user.id}">
    <input type="hidden" name="userPhoto" id="userphoto" value="${user.userPhoto}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">个人信息</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">用户名:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userName" value="${user.userName}" readonly="readonly"/>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">密码:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userPassword" value="${user.userPassword}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">真实姓名:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userTrueName" value="${user.userTrueName}"/>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">年龄:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userAge" value="${user.userAge}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label ">性别:</label>
            <div class="col-sm-4">
                <label class="radio-inline">

                    <input type="radio" name="userSex" value="男" <c:choose><c:when test="${user.userSex=='男'}">checked</c:when></c:choose>>男
                </label>
                <label class="radio-inline">
                    <input type="radio" name="userSex" value="女" <c:choose><c:when test="${user.userSex=='女'}">checked</c:when></c:choose>>女
                </label>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">身份证:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userIdentifity" value="${user.userIdentifity}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">邮箱:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userEmail" value="${user.userEmail}"/>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">QQ|微信:</label>
            <div class="col-sm-4">
                <input class="form-control" name="userQQWei" value="${user.userQQWei}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">电话:</label>
            <div class="col-sm-10">
                <input class="form-control" type="text" name="userPhone" value="${user.userPhone}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">图片:</label>
            <div class="col-sm-10">
                <input id="input-b1" name="input-b1" type="file" class="file" style="height: 50px;">
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">兴趣爱好:</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="applyReason" rows="5" name="userFavour" placeholder="请输入兴趣爱好">${user.userFavour}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">自我介绍:</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="10" name="userIntrod" placeholder="请输入自我介绍">${user.userIntrod}</textarea>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" id="btnSave" class="btn btn-primary">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
        </button>
    </div>
</form>
<script>
    $(function () {
        $("#input-b1").fileinput({
            title: "请上传照片",
            language: 'zh', //设置语言
            uploadUrl:"<%=basePath%>compent/uploadImg", //上传的地址
            allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
            showRemove :true, //显示移除按钮
            enctype:'multipart/form-data',
            dropZoneEnabled:true,
            initialPreview: '<%=basePath%>${user.userPhoto}',
            initialPreviewAsData: true,

        }).on("fileuploaded", function (event, data, previewId, index) {
            console.log(data.response.uploadUrl);
            $("#userphoto").attr("value",data.response.uploadUrl);
        });


        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#btnSave").click(function () {
            var option = {
                url:'<%=basePath%>org_index_ol/h_editUser',
                type:'post',
                success:function (data) {
                    if(data.status != "success"){
                        alert("未知错误，修改失败");
                    }else{
                        alert(data.message);
                        $("#myModal").modal('hide');
                        setTimeout(function () {
                            //500毫秒以后跳转有过渡的效果
                            location.href = "<%=basePath%>org_index_ol/goUserMes?userid=${user.id}";
                        },500);

                    }
                }
            };
            $("#userForm").ajaxSubmit(option);
        });
    })
</script>