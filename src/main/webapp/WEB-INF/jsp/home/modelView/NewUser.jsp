<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!--内嵌表单-->
<form class="form-horizontal" role="form" id="inviteForm">
    <input type="hidden" name="mesid" value="${mesid}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">新增框</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label class="col-sm-2 control-label">用户名:</label>
            <div class="col-sm-10">
                <input class="form-control" name="username"  placeholder="请输入对方用户名"/>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" id="btnSave" class="btn btn-primary">新增</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
        </button>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#btnSave").click(function () {
            var option = {
                url:'<%=basePath%>manUser/newUser',
                type:'post',
                success:function (data) {
                    if(data.status != "success"){
                        alert(data.message);
                    }else{
                        $("#myModal").modal('hide');
                        setTimeout(function () {
                            //500毫秒以后跳转有过渡的效果
                            jQuery("#list").trigger("reloadGrid");
                        },500);

                    }
                }
            };
            $("#inviteForm").ajaxSubmit(option);
        });
    })
</script>