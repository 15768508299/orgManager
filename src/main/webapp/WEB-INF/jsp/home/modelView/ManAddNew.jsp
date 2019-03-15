<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!--内嵌表单-->
<form class="form-horizontal" role="form" id="newsForm">
    <input type="hidden" name="id" value="${news.id}">
    <c:choose>
        <c:when test="${news.id!=null && news.id!=''}">
            <input type="hidden" name="issueTime" value="${news.issueTime}">
        </c:when>
    </c:choose>
    <input type="hidden" name="issueBy" value="${news.issueBy}">
    <input type="hidden" name="newDetail" id="newdetail">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">发布新闻</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label for="theme" class="col-sm-2 control-label">新闻主题:</label>
            <div class="col-sm-10">
                <input class="form-control" name="newTheme" value="${news.newTheme}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">新闻简介:</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="5" name="newIntroduction" placeholder="请输入新闻简介">${news.newIntroduction}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="detail" class="col-sm-2 control-label">新闻详情:</label>
            <div class="col-sm-10"><script type="text/plain" id="myEditor">请输入新闻详情</script></div>
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
        initialFrameWidth: 716,
        initialFrameHeight:483
    });
    $(function () {
        var url = '<%=basePath%>manUser/addNews';
        var textDetail;
        <c:choose>
        <c:when test="${edit==1 && edit != null}">
            url = '<%=basePath %>manUser/editNews';
            textDetail = '${news.newDetail}'
            um.setContent(textDetail);
        </c:when>
        </c:choose>
        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#btnSave").click(function () {
            textDetail = um.getContent();
            $("#newdetail").attr("value",textDetail);
            var option = {
                url:url,
                type:'post',
                success:function (data) {
                    console.log(data);
                    if(data.status != "success"){
                        alert(data.message);
                    }else{
                        alert(data.message);
                        $("#myModal").modal('hide');
                        setTimeout(function () {
                            //500毫秒以后跳转有过渡的效果
                            location.href = "<%=basePath%>org_index_ol/goManMes?mesid=${news.issueBy}&handleArea=1";
                        },500);

                    }
                }
            };
            $("#newsForm").ajaxSubmit(option);
        });
    })
</script>