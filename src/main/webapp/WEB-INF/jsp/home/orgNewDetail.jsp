<%--
  Created by IntelliJ IDEA.
  User: cfx
  Date: 2018/1/4
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<html>
<head>
    <title>$Title$</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>css/my/home/orgNewDetail.css">
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="<%=basePath%>js/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<body style="background-color: #f5f6f7">
<div class="myTop">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="padding: 0px;margin-left: 0px;" href="#"><img style="height: 100%;" src="<%=basePath%>img/logo_6.png"></a>
            </div>
            <ul class="nav navbar-nav navbar-left">
                <li><a href="<%=basePath%>">网站首页</a></li>
                <li><a href="/orgManager/org_mes/homeGetList">社团信息</a></li>
                <li class="active"><a href="/orgManager/org_news/newsHomeList">社团新闻</a></li>
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
</div>
    <div class="myContent">
        <div class="one_content">
            <h1 style="margin-top: 28px;">${news.newTheme}</h1>
            <small><fmt:formatDate value="${news.issueTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></small>
        </div>
        <div class="two_content">${news.newDetail}</div>
        <div class="myBanQuan">
            地址：广东省珠海市*****路***号<br/>
            版权所有：个人创作.<br/>
            互联网信息服务备案编号：京ICP备06001111号.<br/>
            技术支持：bookstrap开源框架，计算机工程技术学院.<br/>
            公司支持：珠海市交通物联网有限公司.<br/>
        </div>
    </div>
</body>
</html>
