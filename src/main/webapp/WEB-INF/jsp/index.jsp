<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%--
  Created by IntelliJ IDEA.
  User: cfx
  Date: 2018/1/4
  Time: 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<!DOCTYPE html>
<html>
<head>
	<title>社团首页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
	<link href="<%=basePath%>css/my/home/index.css" rel="stylesheet">
</head>

<!-- 新 Bootstrap 核心 CSS 文件 -->
<script src="<%=basePath%>js/jquery.min.js"></script>
<script src="<%=basePath%>js/jq.form.js"></script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script>
	function  goUrl(url) {
		location.href = url;
    }
</script>
<style>
	a:hover{
		color: black;
		text-decoration: underline;
	}


</style>
<body>
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
</div>
<div class="myBody">
<div class="par_lun_rig">
	<!--轮播图-->
	<div class="lunbo" style="background: cyan;">
		<section class="section-white">
			<div class="container">
				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<c:forEach items="${indexOL}" var="one" varStatus="status">
							<li data-target="#carousel-example-generic" data-slide-to="${status.index}" class="<c:choose><c:when test="${status.index==0}">active</c:when></c:choose>"></li>
						</c:forEach>
					</ol>
					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<c:forEach items="${indexOL}" var="one" varStatus="status">
							<div class="item hei <c:choose><c:when test="${status.index==0}">active</c:when></c:choose> " onclick="goUrl('${one.url}')">
								<img src="<%=basePath%>${one.photoPath}" alt="社团">
								<div class="carousel-caption">
									<h2>${one.descrption}</h2>
								</div>
							</div>
						</c:forEach>
					</div>
					<!-- Controls -->
					<a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left"></span>
					</a>
					<a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
						<span class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</div>
		</section>
	</div>
	<div class="rightMes">
		<a href="javascript:void(0)"><span class="glyphicon glyphicon-fire"></span></a>
		热点新闻
		<ul class="list-group">
			<c:forEach items="${newsList}" var="news" begin="0" end="10">
				<li class="list-group-item"><a href="<%=basePath%>org_news/newsListDetail?newsid=${news.id}">${news.newTheme}</a></li>
			</c:forEach>
		</ul>
	</div>
</div>
<div class="myContent">
	<div class="myContent_left">
		<a href="javascript:void(0);"><span class="glyphicon glyphicon-send"></span></a>
		最新活动
		<ul class="list-group">
			<c:forEach items="${activityList}" var="activity" begin="0" end="10">
				<li class="list-group-item"><a href="<%=basePath%>org_activity/homeList_detail?actid=${activity.id}">${activity.actTheme}</a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="myContent_right">
		<a href="javascript:void(0);"><span class="glyphicon glyphicon-tag"></span></a>
		热门社团
		<ul class="list-group">
			<c:forEach items="${mesWithBLOBsList}" var="mesOne" begin="0" end="10">
				<li class="list-group-item"><a href="<%=basePath%>org_mes/messageDetail?mesId=${mesOne.id}">${mesOne.mesName}</a></li>
			</c:forEach>
		</ul>
	</div>
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
        remote: '<%=basePath%>/compent/editUserMes',
        backdrop: 'static',
        keyboard: false
    });
}
</script>
</html>
