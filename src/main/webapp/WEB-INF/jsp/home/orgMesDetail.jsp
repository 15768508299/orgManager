<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>查看详情页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>css/my/home/orgMesDetail.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script type="text/javascript">
    function addComment() {
        var mesid = ${mesWithBLOBs.id!=null?mesWithBLOBs.id:0};
        if(mesid != 0){
            var content = myEncodeUrl($("#review").val());
            var param = "&mesid="+mesid+"&tempContent="+content;
            location.href = '<%=basePath%>org_word/comment?'+param;
        }
    }

    function applyMes() {
        //入社申请：返回函数。。后台

        var i;
        <shiro:guest>
            i = 0;
        </shiro:guest>
        <shiro:authenticated>
            i = <shiro:principal property="id"/>;
        </shiro:authenticated>
        //var i = ;
        //console.log(i);
        $.ajax({
            url:'<%=basePath%>org_send_rece/applymes?userid=' + i + '&mesid=${mesWithBLOBs.id}',
            type:'get',
            success:function (data) {
                alert(data.message);
            }


        });
       // console.log(i+"---------------"+${mesWithBLOBs.id});
    }
</script>
<body>
<div class="myTop">
    <nav class="navbar navbar-default" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" style="padding: 0px;margin-left: 0px;" href="#"><img style="height: 100%;" src="<%=basePath%>img/logo_6.png"></a>
            </div>
            <ul class="nav navbar-nav navbar-left">
                <li><a href="<%=basePath%>">网站首页</a></li>
                <li class="active"><a href="/orgManager/org_mes/homeGetList">社团信息</a></li>
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
<div class="myContent">
    <div class="myC">
        <div class="myC_base">
            <div class="myC_base_element">
                <div style="width: 100%;height: 36px;padding-top: 10px;padding-left: 5px"><span style="width: 70%;font-size: 30px"><b>${mesWithBLOBs.mesName}</b> </span><button onclick="applyMes()">申请入社</button>
                </div><br><br><br>
                <p style="width: 96%;height: 100px;overflow: auto;margin-top: -23px;margin-left: 25px;font-size: 15px;">
                    ${mesWithBLOBs.mesIntroduction}
                </p>
                <h3><span class="glyphicon glyphicon-screenshot"></span>基本信息</h3>
                <table style="margin-left: 25px;width: 97%;" class="table table-hover table-condensed">
                    <tr>
                        <td>社团名称</td>
                        <td>${mesWithBLOBs.mesName}</td>
                        <td>创建者</td>
                        <td>${mesWithBLOBs.mesCreate}</td>
                    </tr>
                    <tr>
                        <td>创建日期</td>
                        <td><fmt:formatDate value="${mesWithBLOBs.mesCreateTime}"
                                            pattern="yyyy-MM-dd"></fmt:formatDate></td>
                        <td>总人数</td>
                        <td>${mesWithBLOBs.mesNum}</td>
                    </tr>
                </table>
            </div>
            <div class="myC_base_element">
                <h3><span class="glyphicon glyphicon-screenshot"></span>图文介绍</h3>
                <div class="myC_textAndImg">
                    ${mesWithBLOBs.mesDetail}
                </div>
            </div>
        </div>
        <div class="myC_img"><img src="<%=basePath%>/${mesWithBLOBs.mesPhoto}"/></div>
        <div class="liuYan">
            <div class="well" style="height: 100%;overflow: auto;">
                <h4>留言区</h4>
                <div class="input-group" style="width: 100%;">
                    <textarea class="form-control col-sm-10" style="resize:none;" id="review" rows="5" name="userfavour"
                              placeholder="请发表你的看法"></textarea>
                    <br>
                    <span style="float: right;display: block;" onclick="addComment()"><a href="#" class="btn btn-primary btn-sm"><span
                            class="glyphicon glyphicon-comment"></span> 发表</a></span>
                </div>
                <hr data-brackets-id="12673">
                <ul data-brackets-id="12674" id="sortable" class="list-unstyled ui-sortable">
                    <c:forEach items="${words}" var="word">
                    <strong class="pull-left primary-font">${word.username}</strong>
                    <small class="pull-right text-muted">
                        <span class="glyphicon glyphicon-time"></span>
                        <fmt:formatDate value="${word.wordTime}"
                                        pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </small>
                    </br>
                    <li class="ui-state-default">${word.content}
                    </li>
                    </br>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <div class="myC_ownMes">
        <div class="container">
            <div class="row">
                <div class="col-md-12" data-wow-delay="0.2s">

                    <div class="carousel slide" data-ride="carousel" id="quote-carousel">
                        <!-- Bottom Carousel Indicators -->
                        <ol class="carousel-indicators">
                            <c:forEach items="${users}" var="user" varStatus="one">
                                <li data-target="#quote-carousel" data-slide-to="${one.index}"
                                    <c:if test="${one.index==0}">class="active"</c:if>><img class="img-responsive "
                                                                                            src="<%=basePath%>/${user.userPhoto}"
                                                                                            alt="">
                                </li>
                            </c:forEach>
                        </ol>
                        <!-- Carousel Slides / Quotes -->
                        <div class="carousel-inner text-center">
                            <!-- Quote 1 -->
                            <c:forEach items="${users}" var="user" varStatus="one">
                                <div class="item <c:if test="${one.index==0}">active</c:if>">
                                    <blockquote>
                                        <div class="row">
                                            <div class="col-sm-8 col-sm-offset-2">
                                                <strong>负责人信息</strong>
                                                <table class="table table-condensed">
                                                    <tr>
                                                        <td>姓名</td>
                                                        <td>${user.userName}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>社团职位</td>
                                                        <td>${user.jobNumStr}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>年龄</td>
                                                        <td>${user.userAge}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>爱好</td>
                                                        <td>${user.userFavour}</td>
                                                    </tr>
                                                    <tr>
                                                        <td>QQ|微信号</td>
                                                        <td>${user.userQQWei}</td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </blockquote>
                                </div>
                            </c:forEach>
                            <!-- Quote 2 -->
                        </div>
                        <!-- Carousel Buttons Next/Prev -->
                        <a data-slide="prev" href="#quote-carousel" class="left carousel-control"><i
                                class="fa fa-chevron-left"></i></a>
                        <a data-slide="next" href="#quote-carousel" class="right carousel-control"><i
                                class="fa fa-chevron-right"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="myBanQuan">
        地址：广东省珠海市*****路***号<br/>
        版权所有：个人创作.<br/>
        互联网信息服务备案编号：京ICP备00000000号.<br/>
        技术支持：bookstrap开源框架，广科计算机工程技术学院.<br/>
        公司支持：珠海市交通物联网有限公司.<br/>
    </div>
</div>
</body>
</html>
