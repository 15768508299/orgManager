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
    <link rel="stylesheet" href="<%=basePath%>css/my/home/orgMes.css">
</head>
<!-- 新 Bootstrap 核心 CSS 文件 -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="<%=basePath%>js/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>js/bootstrap.min.js"></script>

<script src="<%=basePath%>js/common.js"></script>
<script>
    $(function () {
        //为模态框绑定刷新数据
        $("#myModal").on("hidden.bs.modal", function() {
            $("#applyReason").val("");
        });

        //绑定详细介绍样式
        $(".mySpan").mouseover(function(){
            $(this).css("color","blue");
            $(this).css("text-decoration","underline");
        }).mouseout(function () {
            $(this).css("color","#555555");
            $(this).css("text-decoration","none");
        });

        //绑定单击提交事件
        $("#commitApply").click(function () {
            $.ajax({
                url:'<%=basePath%>user/userAddMes',
                type:'POST',
                data:$("#myForm").serialize(),
                success:function (data) {
                    if(data.status == "success"){
                        alert(data.message);
                        $("#myModal").modal('hide');
                    }else{
                        alert(data.message);
                    }
                }
            });
        });
        //绑定读取数据事件
        $("#btnSearch").click(function () {
           var searchText = $("#searchText").val();
           $("#where_sql").val(searchText);
           $("#searchForm").submit();
        });

    });

    function goDetail(url){
        location.href = url;
    }

    function apply(mesId,userId){
        $("#mesid").attr("value",mesId);
        $("#userid").attr("value",userId);
    }

    //处理关注社团
    function attention(mesid,userid){
        $.ajax({
            url:'<%=basePath%>org_attention/addAttention',
            type:'POST',
            data:{mesid:mesid,userid:userid},
            success:function (data) {
                if(data.status == "success"){
                    alert(data.message);
                }else{
                    alert(data.message);
                }
            }
        });
    }
    //翻页函数
    function pageTurning(mypage){
        $("#page").val(mypage);
        $("#searchForm").submit();
    }
</script>
<style>
    .adf{
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
    <div style="width: 100%;height: 52px;margin-top: 1px;margin-left: auto;margin-right: auto;">
        <form action="/orgManager/org_mes/homeGetList" method="POST" class="bs-example bs-example-form" role="form" id="searchForm">
            <input type="hidden" name="where_sql" id="where_sql" value="${where_sql}">
            <input type="hidden" name="size" value="4">
            <input type="hidden" name="page" id="page">
            <div class="input-group input-group-lg">
                <span class="input-group-addon">
                    <a href="#"><span class="glyphicon glyphicon-search"></span></a>
                </span>
                <input type="text" id="searchText" class="form-control" placeholder="输入社团名称进行搜索">
                <span class="input-group-btn">
						<button class="btn btn-default" type="button" id="btnSearch">搜索</button>
                </span>
            </div>
            <br>
        </form>
    </div>
    <div class="myContent_list">
        <div class="container" style="width: 80%">
            <div class="row">
                <div class="well">
                    <div class="list-group">
                    <c:forEach items="${mesIPage.getRecords()}" var="item">
                        <a href="javascript:void(0)" class="list-group-item">
                            <div class="media col-md-3">
                                <figure class="pull-left">
                                    <img class="media-object img-rounded img-responsive myImg"  src="<%=basePath%>${item.mesPhoto}" alt="错误，请联系管理员" >
                                </figure>
                            </div>
                            <div class="col-md-6">
                                <h3 class="list-group-item-heading"> ${item.mesName} </h3>
                                <p class="list-group-item-text" style="font-size: 17px;">${item.mesIntroduction}</p>
                            </div>
                            <div class="col-md-3 text-center">
                                <h2><small>总人数：</small><small> ${item.mesNum}</small> </h2>
                                <button type="button" class="btn btn-info btn-lg btn-block <shiro:guest>disabled</shiro:guest>" <shiro:guest>disabled="disabled"</shiro:guest> onclick="attention(${item.id},<shiro:principal property="id"/>)"> 点击关注 </button>
                                <button type="button" class="btn btn-info btn-lg btn-block" onclick="goDetail('<%=basePath%>org_mes/messageDetail?mesId=${item.id}')"> 查看详情 </button>
                                <div class="stars">
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star"></span>
                                    <span class="glyphicon glyphicon-star-empty"></span>
                                </div>
                                <p> 创建日期：<fmt:formatDate value="${item.mesCreateTime}" pattern="yyyy-MM-dd"></fmt:formatDate></p>
                            </div>
                        </a>
                    </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="myContent_pagination">
        <ul class="pagination">
                <c:if test="${actList_nowPage==1}">
                    <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
                </c:if>
                <c:if test="${actList_nowPage!=1}">
                    <li ><a href="#" onclick="pageTurning(${actList_nowPage-1})">&laquo;</a></li>
                </c:if>
            <c:forEach var="i" begin="1" end = "${number}" step="1">
                <c:if test="${i==actList_nowPage}">
                    <li class="active"><a href="javascript:void(0);">${i}</a></li>
                </c:if>
                <c:if test="${i!=actList_nowPage}">
                    <li name="page"><a href="#" onclick="pageTurning(${i})">${i}</a></li>
                </c:if>
            </c:forEach>
                <c:if test="${actList_nowPage==number}">
                    <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
                </c:if>
                <c:if test="${actList_nowPage!=number}">
                    <li ><a href="#" onclick="pageTurning(${actList_nowPage+1})">&raquo;</a></li>
                </c:if>
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <!--内嵌表单-->
            <div class="modal-header">

            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
