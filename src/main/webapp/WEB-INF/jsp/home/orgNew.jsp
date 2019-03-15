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
	<title>社团新闻首页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<%=basePath%>css/my/home/orgNew.css">
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="<%=basePath%>js/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<style>
	.blogShort{ border-bottom:1px solid #ddd;}
	.btn-blog {
		color: #ffffff;
		background-color: #37d980;
		border-color: #37d980;
		border-radius:0;
		margin-bottom:10px
	}
	.btn-blog:hover,
	.btn-blog:focus,
	.btn-blog:active,
	.btn-blog.active,
	.open .dropdown-toggle.btn-blog {
		color: white;
		background-color:#34ca78;
		border-color: #34ca78;
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
<div style="margin: 0 90px;height: auto;width: auto;">
<div class="myContent">
	<div class="myContent_ol">
		<div class="myContent_ol_one">
			<div id="myCarousel" style="height: 100%;" class="carousel slide">
				<!-- 轮播（Carousel）指标 -->
				<ol class="carousel-indicators">
					<c:forEach items="${newOLS}" var="one" varStatus="status">
						<li data-target="#carousel-example-generic" data-slide-to="${status.index}" class="<c:choose><c:when test="${status.index==0}">active</c:when></c:choose>"></li>
					</c:forEach>
				</ol>
				<!-- 轮播（Carousel）项目 -->
				<div class="carousel-inner" style="height: 100%;">
					<c:forEach items="${newOLS}" var="one" varStatus="status">
						<div class="item <c:choose><c:when test="${status.index==0}">active</c:when></c:choose>" style="height: 100%;" onclick="location.href ='${one.target}'">
							<img style="height: 100%;" src="<%=basePath%>${one.photo}" alt="社团">
							<div class="carousel-caption"><span style="font-weight: bold;font-size: 20px;">${one.description}</span></div>
						</div>
					</c:forEach>
				</div>
				<!-- 轮播（Carousel）导航 -->
				<a class="carousel-control left" href="#myCarousel"
				   data-slide="prev">&lsaquo;
				</a>
				<a class="carousel-control right" href="#myCarousel"
				   data-slide="next">&rsaquo;
				</a>
			</div>
		</div>
		<c:forEach items="${newOLS2}" var="one" varStatus="status">
			<div onclick="location.href='${one.target}'" class="myContent_ol_two"><img style="width: 100%;height: 100%;" src="<%=basePath%>${one.photo}" alt=""><div class="myContent_ol_point">${one.description}</div></div>
		</c:forEach>

	</div>
	<div class="myContent_list">
			<div id="blog" class="row" style="width: 100%;">
				<c:forEach items="${newsList}" var="myNew">
					<div class="col-md-10 blogShort" style="width: 100%;">
						<h3>${myNew.newTheme}</h3>
						<em><fmt:formatDate value="${myNew.issueTime}" pattern="yyyy-MM-dd"></fmt:formatDate></em>
						<article><p>
							${myNew.newIntroduction}
						</p></article>
						<a class="btn btn-blog pull-right marginBottom10" href="<%=basePath%>org_news/newsListDetail?newsid=${myNew.id}">查看详情</a>
					</div>
				</c:forEach>
			</div>
	</div>
</div>
<div class="myContent_pagination">
	<ul class="pagination">
		<c:if test="${actList_nowPage==1}">
			<li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
		</c:if>
		<c:if test="${actList_nowPage!=1}">
			<li ><a href="<%=basePath%>org_news/newsHomeList?page=${actList_nowPage-1}">&laquo;</a></li>
		</c:if>
		<c:forEach var="i" begin="1" end = "${actList_AllPage}" step="1">
			<c:if test="${i==actList_nowPage}">
				<li class="active"><a href="javascript:void(0);">${i}</a></li>
			</c:if>
			<c:if test="${i!=actList_nowPage}">
				<li name="page"><a href="<%=basePath%>org_news/newsHomeList?page=${i}">${i}</a></li>
			</c:if>
		</c:forEach>
		<c:if test="${actList_nowPage==actList_AllPage}">
			<li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
		</c:if>
		<c:if test="${actList_nowPage!=actList_AllPage}">
			<li ><a href="<%=basePath%>org_news/newsHomeList?page=${actList_nowPage+1}">&raquo;</a></li>
		</c:if>
	</ul>
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
</html>
