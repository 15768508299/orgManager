<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!--内嵌表单-->
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">消息详细</h4>
    </div>
    <div class="modal-body">
        <h1>${sendReceEx.mesTypeTheme}</h1>
        <table class="table">
            <tr>
                <td width="10%;">发送人</td>
                <td>${sendReceEx.sendName}</td>
            </tr>
            <tr>
                <td>时间</td>
                <td><fmt:formatDate value="${sendReceEx.mesTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            </tr>
            <tr>
                <td>内容</td>
                <td>${sendReceEx.content}</td>
            </tr>
        </table>
    </div>
<script>
    $(function () {
        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

    })
</script>