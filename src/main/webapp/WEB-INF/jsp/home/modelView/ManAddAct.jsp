<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!--内嵌表单-->
<form class="form-horizontal" role="form" id="actForm">
    <input type="hidden" name="id" value="${activity.id}">
    <input type="hidden" name="readNum" value="${activity.readNum}">
    <c:choose>
        <c:when test="${activity.id!=null && activity.id!=''}">
            <input type="hidden" name="issueDate" value="${activity.issueDate}">
        </c:when>
    </c:choose>
    <input type="hidden" name="issueBy" value="${activity.issueBy}">
    <input type="hidden" name="actDetail" id="actdetail">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">发布活动</h4>
    </div>
    <div class="modal-body">
        <div class="form-group">
            <label for="theme" class="col-sm-2 control-label">活动主题:</label>
            <div class="col-sm-10">
                <input class="form-control" name="actTheme" value="${activity.actTheme}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="applyReason" class="col-sm-2 control-label">活动简介:</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="5" name="actIntroduction" placeholder="请输入活动简介">${activity.actIntroduction}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="time" class="col-sm-2 control-label">活动时间</label>
            <div  class="input-group date form_date col-sm-3" style="padding-left: 14px;float: left;" data-date="2019-02-14T00:00:00Z" data-date-format="yyyy-mm-dd">
                <input name="actDateStr" id="dtp_input1" class="form-control" placeholder="请选择日期" size="16" type="text" value="<fmt:formatDate value="${activity.actDate}" pattern="yyyy-MM-dd"></fmt:formatDate>" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div  class="input-group date form_time col-sm-3" style="padding-left: 14px;float: left" data-date="2019-02-14T00:00:00Z" data-date-format="hh:ii">
                <input name="startTime" id="dtp_input2" class="form-control" size="16" placeholder="请选择开始时间"  type="text" readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
            <div class="input-group date form_time col-sm-3" style="padding-left: 14px;float: left" data-date="2019-02-14T00:00:00Z" data-date-format="hh:ii">
                <input name="endTime" id="dtp_input3" class="form-control" placeholder="请选择结束时间" size="16"  type="text"  readonly>
                <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
            </div>
        </div>
        <div class="form-group">
            <label for="detail" class="col-sm-2 control-label">活动详情:</label>
            <div class="col-sm-10"><script type="text/plain"  id="myEditor">请输入活动详情</script></div>
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
        initialFrameWidth: 717,
        initialFrameHeight:483
    });

    /*初始化时间控件*/
    $('.form_date').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });
    $('.form_time').datetimepicker({
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
    });

    $(function () {
        var url = '<%=basePath%>manUser/addAct';
        var textDetail;
        <c:choose>
        <c:when test="${edit==1 && edit != null}">
        url = '<%=basePath%>manUser/editAct';
        textDetail = '${activity.actDetail}';
        um.setContent(textDetail);

        /*初始化完再设置进去*/
        $("#dtp_input2").val('${activity.startTime}');
        $("#dtp_input3").val('${activity.endTime}');
        </c:when>
        </c:choose>
        $("#myModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#btnSave").click(function () {
            textDetail = um.getContent();
            $("#actdetail").attr("value",textDetail);
            if($("#dtp_input1").val()!='' && $("#dtp_input2").val()!='' && $("#dtp_input3").val()!=''){
                /*处理时间*/
               /* var actDateStr = $("#dtp_input1").val()+","+$("#dtp_input2").val()+","+$("#dtp_input3").val();
                $("#actDateStr").attr("value",actDateStr);*/
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
                                location.href = "<%=basePath%>org_index_ol/goManMes?mesid=${activity.issueBy}&handleArea=2";
                            },500);

                        }
                    }
                };
                $("#actForm").ajaxSubmit(option);
            }else{
                alert("活动时间必填！");
            }
        });
    })
</script>