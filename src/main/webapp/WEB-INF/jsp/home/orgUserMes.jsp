<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>个人页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<%=basePath%>js/bootstrap.min.js"></script>
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <script src="<%=basePath%>js/jq.form.js"></script>
    <link href="<%=basePath%>css/my/home/orgUserMes.css" rel="stylesheet">

    <!--图片上传组件包-->
    <link href="<%=basePath%>css/fileInput/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/fileinput/fileinput.min.js"></script>
</head>
<body style="background-color: #f5f6f7;">
<div class="myTop">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="padding: 0px;margin-left: 0px;" href="#"><img style="height: 100%;" src="<%=basePath%>img/logo_6.png"></a>
            </div>
            <ul class="nav navbar-nav navbar-left">
                <li class="active"><a href="<%=basePath%>">网站首页</a></li>
                <li><a href="/orgManager/org_mes/homeGetList">社团信息</a></li>
                <li><a href="/orgManager/org_news/newsHomeList">社团新闻</a></li>
                <li><a href="/orgManager/org_activity/homeList">社团活动</a></li>
                <li><a href="/orgManager/admin/index">管理网站</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <shiro:guest>
                    <li><a href="<%=basePath%>org_index_ol/goRegister"><span class="glyphicon glyphicon-user"></span> 注册</a></li>
                    <li><a href="<%=basePath%>org_index_ol/login"><span class="glyphicon glyphicon-log-in"></span> 登录</a></li>
                </shiro:guest>
                <shiro:authenticated>
                    <li><a href="<%=basePath%>org_index_ol/goUserMes?userid=<shiro:principal property="id"/>"><span class="glyphicon glyphicon-user"></span>欢迎你：<shiro:principal property="userTrueName"/></a></li>
                    <li><a href="<%=basePath%>org_index_ol/exit"><span class="glyphicon glyphicon-log-in"></span> 注销</a></li>
                </shiro:authenticated>
            </ul>
        </div>
    </nav>
</div><%--<div class="myTheme">
    <div class="logo"><img src="<%=basePath%>img/logo_6.png"></div>
    <div class="themeContent">大学生社团信息网</div>
</div>--%>
<div style="margin: 0 90px">
    <div class="mynav">
        <ul class="nav nav-tabs nav-justified">
            <li <c:choose><c:when test="${active==1 || active==null}">class="active"</c:when></c:choose>><a href="#home" data-toggle="tab">个人信息</a></li>
            <li <c:choose><c:when test="${active==2}">class="active"</c:when></c:choose>><a href="#socalMes" data-toggle="tab" onclick="onoeSpan()">消息通知<span id="iscount" class="badge">${count}</span></a></li>

            <%--<c:choose><c:when test="${count > 0}"><span class="badge">${count}</span></c:when></c:choose>--%>
        </ul>
    </div>
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade  <c:choose><c:when test="${active==1 || active==null}">in active</c:when></c:choose>" id="home">
            <div class="myContent">
                <div class="user_img">
                    <img src="<%=basePath%>${user.userPhoto}" style="width: 100%;height: 100%;"></img>
                </div>
                <div class="user_mes">
                    <div style="margin-left: 5px;margin-top: 20px;font-size:30px;">${user.userName}
                        <button id="editUser" type="button" style="float: right;" class="btn btn-default btn-sm">
                            <span class="glyphicon glyphicon-edit"></span> 编辑
                        </button>
                    </div><br/>
                    <div style="margin-left: 15px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <c:choose><c:when test="${user.userTrueName==null || user.userTrueName==''}">未填写</c:when><c:otherwise>${user.userTrueName}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${user.userAge==null|| user.userAge==''}">未填写</c:when><c:otherwise>${user.userAge}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${user.userSex==null || user.userSex==''}">未填写</c:when><c:otherwise>${user.userSex}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${user.userQQWei==null || user.userQQWei==''}">未填写</c:when><c:otherwise>${user.userQQWei}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${user.userQQWei==null || user.userPhone==''}">未填写</c:when><c:otherwise>${user.userPhone}</c:otherwise></c:choose>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div><br/>
                    <div style="margin-left: 5px;">个人简介
                        <div style="margin-left: 25px;overflow: auto;height: 104px;">${user.userIntrod}</div>
                    </div>
                </div><br/>
            </div>
            <div class="myMes">
                <div class="panel panel-default editNew">
                    <div class="panel-heading" style="font-weight: bold;color:#ffffff;border:1px solid #cceff5;background:#5c9ccc;">
                        <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                            <a href="javascript:void(0)">我的社团</a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <c:forEach items="${mesWithBLOBs}" var="mesWithBLOB">
                                <div class="one"><span style="margin-left: 20px;display:inline-block;margin-top: 8px;">${mesWithBLOB.mesName}</span>
                                    <button id="mesDeleteBtn" type="button" style="float: right;" class="btn btn-default">退出社团</button><button onclick="watchDetailMes(${mesWithBLOB.id})" style="float: right;" type="button" class="btn btn-default" ><span class="glyphicon glyphicon-sort-by-attributes-alt"></span></span> 查看详情</button>
                                </div>
                                <hr/>
                            </c:forEach>
                    </div>
                </div>
                </div>
            </div>
        </div>
        <div class="tab-pane fade <c:choose><c:when test="${active==2 }">in active</c:when></c:choose>" id="socalMes">
            <div class="socalMes_rece">
                <div class="socalMes_rece_title">
                    <a href="<%=basePath%>org_index_ol/goUserMes?userid=${user.id}&isRead=2">全部</a><% for(int i=0;i<10;i++) { %>&nbsp;<% } %>|<% for(int i=0;i<10;i++) { %>&nbsp;<% } %><a href="<%=basePath%>org_index_ol/goUserMes?userid=${user.id}&isRead=1">已读</a><% for(int i=0;i<10;i++) { %>&nbsp;<% } %>|<% for(int i=0;i<10;i++) { %>&nbsp;<% } %><a href="<%=basePath%>org_index_ol/goUserMes?userid=${user.id}&isRead=0">未读</a><% for(int i=0;i<10;i++) { %>&nbsp;<% } %>|<% for(int i=0;i<10;i++) { %>&nbsp;<% } %><a href="<%=basePath%>org_index_ol/deleteAllInform?userid=${user.id}&isRead=2">清空所有通知</a>
                </div>
                <div class="socalMes_rece_content">
                    <div class="btn-group btn-group-lg">
                        <button type="button" id="deleteMesBtn" class="btn btn-default">删除</button>
                        <%--<button type="button" class="btn btn-default">回复</button>
                        <button type="button" class="btn btn-default">Button 3</button>--%>
                    </div>
                    <table id="oneTable" class="table table-bordered table-hover" style="background-color: #ffffff">
                        <tr>
                            <td style="width: 5%;text-align: center;">勾选</td>
                            <td style="width:20%;">发件人</td>
                            <td style="width:55%">主题</td>
                            <td style="width: 15%" >时间</td>
                        </tr>
                        <c:forEach items="${sendRecesExes}" var="sendRecesEx">
                            <tr class="itea" title="${sendRecesEx.id}">
                                <td style="width: 5%;"><input type="checkbox" name="chose" style="width: 71px;height: 19px;"></td>
                                <td style="width:20%;">${sendRecesEx.sendName}<c:choose><c:when test="${sendRecesEx.isread == 0}"><span class="badge">未读</span></c:when></c:choose></td>
                                <td style="width:55%">${sendRecesEx.mesTypeTheme}</td>
                                <td style="width: 15%" ><fmt:formatDate value="${sendRecesEx.mesTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <ul class="pager">
                    <c:if test="${sendRecePage == 1}">
                        <li><a href="javascript:void(0);">上一页</a></li>
                    </c:if>
                    <c:if test="${sendRecePage != 1}">
                        <li><a href="<%=basePath%>org_index_ol/goUserMes?userid=${user.id}&isRead=2&page=${sendRecePage-1}">上一页</a></li>
                    </c:if>
                    <c:if test="${sendRecePage == number}">
                        <li><a href="javascript:void(0);">下一页</a></li>
                    </c:if>
                    <c:if test="${sendRecePage != number}">
                        <li><a href="<%=basePath%>org_index_ol/goUserMes?userid=${user.id}&isRead=2&page=${sendRecePage+1}">下一页</a></li>
                    </c:if>

                </ul>
            </div>
        </div>
    </div>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 903px;">
            <div class="modal-content">
                <!--内嵌表单-->
                <form class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel"></h4>
                    </div>
                    <div class="modal-body">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                        </button>
                        <button type="button" class="btn btn-primary">保存</button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
    <div class="myBanQuan">
        地址：广东省珠海市*****路***号<br/>
        版权所有：个人创作.<br/>
        互联网信息服务备案编号：京ICP备06001111号.<br/>
        技术支持：bookstrap开源框架，计算机工程技术学院.<br/>
        公司支持：珠海市交通物联网有限公司.<br/>
    </div>
</div>
</body>
<script>
    function editUserMes() {
        $("#myModal").modal({
            remote: '<%=basePath%>compent/editUserMes?userid=<shiro:principal property="id"/>',
            backdrop: 'static',
            keyboard: false
        });
    }

    function  watchDetailMes(id) {
        location.href = "<%=basePath%>org_index_ol/goManMes?mesid="+id;
    }

    function onoeSpan(){
        $("#iscount").css("display","none");
    }

    $(function () {
        //为查看和退出社团绑定事件
        $("#")

        $("#editUser").click(function () {
            editUserMes();
        });

        //为除开第一个td绑定事件
        $(".itea td:not(:nth-child(1))").click(function () {
            var id = this.parentNode.getAttribute("title");
            $(this).siblings().find("span").css("display","none");
            $(this).find("span").css("display","none");
            $("#myModal").modal({
                remote: '<%=basePath%>org_send_rece/goInformDetail?id='+id,
                backdrop: 'static',
                keyboard: false
            });
        });

        //为删除按钮绑定事件
        $("#deleteMesBtn").click(function () {
            var ids = [];
            var array = $(".itea input:checkbox:checked");
            console.log(array.length);
            if(array.length==0){
                alert("请选择要删除的行");
            }else{
                for(var i=0;i<array.length;i++){
                    var id = $(array[i]).parent().parent();
                    ids.push(id.attr("title"));
                }
                var valueIds = ids.join(",");
                location.href = '<%=basePath%>org_send_rece/deleteInform?userid=<shiro:principal property="id"/>&ids='+valueIds;
            }
        });
    })
</script>
</html>
