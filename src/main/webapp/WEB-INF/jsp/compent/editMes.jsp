<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!--内嵌表单-->
<style>
    .file-drop-zone{
        height: 42%;
    }
</style>
<form class="form-horizontal" role="form" id="mesForm" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${mesWithBLOBs.id}">
    <input type="hidden" name="mesDetail" id="mesdetail">
    <input type="hidden" name="mesPhoto" id="mesphoto" value="${mesWithBLOBs.mesPhoto}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">社团信息</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">社团名称:</label>
            <div class="col-sm-4">
                <input class="form-control" name="mesName" value="${mesWithBLOBs.mesName}" readonly="readonly"/>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">创建者:</label>
            <div class="col-sm-4">
                <input class="form-control" name="mesCreate" value="${mesWithBLOBs.mesCreate}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">创建日期:</label>
            <div class="col-sm-4">
                <input class="form-control" name="mesCreateTimeStr" value="<fmt:formatDate value="${mesWithBLOBs.mesCreateTime}" pattern="yyyy-MM-dd"></fmt:formatDate>"/>
            </div>
            <label for="applyReason" class="col-sm-2 control-label">总人数:</label>
            <div class="col-sm-4">
                <input class="form-control" name="mesNum" value="${mesWithBLOBs.mesNum}"/>
                ${mesWithBLOBs.mesPhoto}
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">封面图:</label>
            <div class="col-sm-10">
                <input id="input-b1" name="input-b1" type="file" class="file" style="height: 100px;">
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">社团简介:</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="applyReason" rows="5" name="mesIntroduction" placeholder="请输入社团简介">${mesWithBLOBs.mesIntroduction}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">图文详情:</label>
            <div class="col-sm-10"><script type="text/plain" id="myEditor">请输入图文信息</script></div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
        </button>
        <button type="button" id="btnSave" class="btn btn-primary">保存</button>
    </div>
</form>
<script type="text/javascript">
    var um = UM.createEditor("myEditor",{
        initialFrameWidth: 715,
        initialFrameHeight:483
    });
    $(function () {
        $("#input-b1").fileinput({
            title: "请上传封面照",
            language: 'zh', //设置语言
            uploadUrl:"<%=basePath%>compent/uploadImg", //上传的地址
            allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
            showRemove :true, //显示移除按钮
            enctype:'multipart/form-data',
            dropZoneEnabled:true,
            initialPreview: '<%=basePath%>${mesWithBLOBs.mesPhoto}',
            initialPreviewAsData: true,

        }).on("fileuploaded", function (event, data, previewId, index) {
            console.log(data.response.uploadUrl);
            $("#mesphoto").attr("value",data.response.uploadUrl);
        });

        var detail = '${mesWithBLOBs.mesDetail}';
        um.setContent(detail);

        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#btnSave").click(function () {
            var textDetail = um.getContent();
            $("#mesdetail").attr("value",textDetail);
            var option = {
                url:'<%=basePath%>user/editMes',
                type:'post',
                success:function (data) {
                    if(data.status != "success"){
                        alert("未知错误，修改失败");
                    }else{
                        alert(data.message);
                        $("#myModal").modal('hide');
                        setTimeout(function () {
                            //500毫秒以后跳转有过渡的效果
                            location.href = "<%=basePath%>user/goManMes?mesid=${mesWithBLOBs.id}";
                        },500);

                    }
                }
            };
            $("#mesForm").ajaxSubmit(option);
        });
    })
</script>