<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>前台管理页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<%=basePath%>js/bootstrap.min.js"></script>
    <link href="<%=basePath%>css/my/home/orgManMes.css" rel="stylesheet">
    <link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
    <!--jqgrid包-->
    <link rel="stylesheet" href="<%=basePath%>jqgrid/jqgrid/css/ui.jqgrid.css"/>
    <link rel="stylesheet" href="<%=basePath%>jqgrid/jqgrid/css/css/redmond/jquery-ui-1.8.16.custom.css"/>
    <script type="text/javascript" src="<%=basePath%>jqgrid/jqgrid/js/jquery.jqGrid.src.js"></script>
    <script type="text/javascript" src="<%=basePath%>jqgrid/jqgrid/js/i18n/grid.locale-cn.js"></script>

    <!--umeditor包-->
    <link href="<%=basePath %>umEditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>umEditor/umeditor.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>umEditor/lang/zh-cn/zh-cn.js"></script>

    <!--图片上传组件包-->
    <link href="<%=basePath%>css/fileInput/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/fileinput/fileinput.min.js"></script>
    <script src="<%=basePath%>js/jq.form.js"></script>

    <!--时间框组件包-->
    <link href="<%=basePath%>css/timepick/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="<%=basePath%>js/timepick/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=basePath%>js/timepick/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>
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
<div class="myContent">
    <div class="mes_title">${mesWithBLOB.mesName}</div>
    <div class="mes_show">
        <div class="img_show"><img style="width: 100%;height: 100%;" src="<%=basePath%>${mesWithBLOB.mesPhoto}"/></div>
        <div class="bas_mes">
            <div class="bas_mes_right">
                <div class="right_bas"><p style="margin-left: 10px;font-size: large;"><strong>基本信息</strong></p></div>
                <div style="height: 150px;">
                    <div class="kong_div"></div>
                    <div class="mes_div">
                        <table class="table" style="width: 100%;height: 100%;margin-top: -21px;">
                            <tr>
                                <td>社团名称</td>
                                <td>${mesWithBLOB.mesName}</td>
                            </tr>
                            <tr>
                                <td>社团创建者</td>
                                <td>${mesWithBLOB.mesCreate}</td>
                            </tr>
                            <tr>
                                <td>社团创建日期</td>
                                <td><fmt:formatDate value="${mesWithBLOB.mesCreateTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
                            </tr>
                            <tr>
                                <td>总人数</td>
                                <td>${mesWithBLOB.mesNum}</td>
                            </tr>
                            <br/>
                        </table>
                    </div>
                </div>
                <div style="margin-top: 3px;margin-left: 22px;">
                    <p style="font-size: large"><strong>社团简介</strong></p>
                    <p style="margin-left: -1px;">${mesWithBLOB.mesIntroduction}</p>
                </div>
                <div style="margin-top: 3px;margin-left: 22px;">
                    <p style="font-size: large"><strong>图文详情</strong></p>
                    <p style="margin-left: -1px;">${mesWithBLOB.mesDetail}</p>
                </div>
            </div>
            <input style="float: right;" id="editBtn" type="button" class="btn btn-primary <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>"<shiro:hasRole name="普通成员">disabled='disabled'</shiro:hasRole> value="编辑信息"/>
        </div>
    </div>
    <div class="user_list">
        <table id="list"></table>
        <div id="pager2"></div>
    </div>
    <!--社团新闻-->
    <div class="panel panel-default editNew">
        <div class="panel-heading" style="font-weight: bold;color:#ffffff;border:1px solid #cceff5;background:#5c9ccc;">
            <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                <a href="javascript:void(0)">社团新闻</a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse <c:choose><c:when test="${handleArea==1}">in</c:when></c:choose>">
            <div class="panel-body">
                <div style="border:1px solid #cceff5;background:#fafcfd;margin-bottom: 3px;">
                    <button type="button" onclick="AddNews()" class="btn btn-info btn-lg <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>" <shiro:hasRole name="普通成员">disabled='disabled'</shiro:hasRole>><span class="glyphicon glyphicon-envelope"></span>发布新闻</button>
                </div>
                <ul class="list-group">
                    <c:forEach items="${newsList}" var="news">
                        <li class="list-group-item"><a href="<%=basePath%>news/newsListDetail?newsid=${news.id}">${news.newTheme}</a><a href="javascript:void(0)" onclick="deleteNews(${news.id})" <shiro:hasRole name="普通成员">disabled='disabled'</shiro:hasRole> style="float: right;margin-top: -5px;background-color: #5c9ccc;" class="btn btn-info btn-sm <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>"><span class="glyphicon glyphicon-remove"></span>删除</a><a href="javascript:void(0)" onclick="editNews(${news.id})" style="float: right;margin-right:8px;margin-top: -5px;background-color: #5c9ccc;" <shiro:hasRole name='普通成员'>disabled='disabled'</shiro:hasRole> class="btn btn-info btn-sm <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>"><span class="glyphicon glyphicon-pencil"></span>编辑</a></li>
                    </c:forEach>
                </ul>
                <ul class="pager">
                    <li><a href="<%=path%>/org_index_ol/goManMes?mesid=${mesWithBLOB.id}&page1=${newsList_nowPage-1<1?1:newsList_nowPage-1}&size1=10&handleArea=1">上一页</a></li>
                    <li><a href="<%=path%>/org_index_ol/goManMes?mesid=${mesWithBLOB.id}&page1=${newsList_nowPage+1>newsList_AllPage?newsList_AllPage:newsList_nowPage+1}&size1=10&handleArea=1">下一页</a></li>
                </ul>
            </div>
        </div>
    </div>
    <!--社团活动-->
    <div class="panel panel-default editActivity">
        <div class="panel-heading" style="font-weight: bold;color:#ffffff;border:1px solid #cceff5;background:#5c9ccc;">
            <h4 class="panel-title" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                <a href="javascript:void(0)">社团活动</a>
            </h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse <c:choose><c:when test="${handleArea==2}">in</c:when></c:choose>">
            <div class="panel-body">
                <div style="border:1px solid #cceff5;background:#fafcfd;margin-bottom: 3px;">
                    <button type="button" onclick="addActivity()" class="btn btn-info btn-lg <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>" <shiro:hasRole name="普通成员">disabled='disabled'</shiro:hasRole>><span class="glyphicon glyphicon-envelope"></span>发布活动</button>
                </div>
                <ul class="list-group">
                    <c:forEach items="${actList}" var="act">
                        <li class="list-group-item"><a href="<%=basePath%>org_activity/homeList_detail?actid=${act.id}">${act.actTheme}</a><a href="javascript:void(0)" onclick="deleteAct(${act.id})" <shiro:hasRole name="普通成员">disabled='disabled'</shiro:hasRole> style="float: right;margin-top: -5px;background-color: #5c9ccc;" class="btn btn-info btn-sm <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>"><span class="glyphicon glyphicon-remove"></span>删除</a><a href="javascript:void(0)" onclick="editAct(${act.id})" style="float: right;margin-right:8px;margin-top: -5px;background-color: #5c9ccc;" <shiro:hasRole name='普通成员'>disabled='disabled'</shiro:hasRole> class="btn btn-info btn-sm <shiro:hasRole name='普通成员'>disabled</shiro:hasRole>"><span class="glyphicon glyphicon-pencil"></span>编辑</a></li>
                    </c:forEach>
                </ul>
                <ul class="pager">
                    <li><a href="<%=path%>/org_index_ol/goManMes?mesid=${mesWithBLOB.id}&page2=${actList_nowPage-1<1?1:actList_nowPage-1}&size2=10&handleArea=2">上一页</a></li>
                    <li><a href="<%=path%>/org_index_ol/goManMes?mesid=${mesWithBLOB.id}&page2=${actList_nowPage+1>actList_AllPage?actList_AllPage:actList_nowPage+1}&size2=10&handleArea=2">下一页</a></li>
                </ul>
            </div>
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

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 900px;">
        <div class="modal-content">
            <!--内嵌表单-->
            <form class="form-horizontal" role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">信息</h4>
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
</body>
<script>
    function openMoTai(url){
        $("#myModal").modal({
            remote: url,
            backdrop: 'static',
            keyboard: false
        });
    }

    function editMes(id) {
        var url =  '<%=basePath%>compent/editMes?mesid='+id;
        openMoTai(url);
    }

    function NewUser(id){
        var url = '<%=basePath%>manUser/newUserShow?mesid='+id;
        openMoTai(url);
    }

    function AddNews(){
        var url = '<%=basePath%>manUser/addNewsShow?mesid=${mesWithBLOB.id}';
        openMoTai(url);
    }

    function addActivity(){
        var url = '<%=basePath%>manUser/addActShow?mesid=${mesWithBLOB.id}';
        openMoTai(url);
    }

    function editNews(id){
        var url = '<%=basePath%>manUser/editNewsShow?newid='+id;
        openMoTai(url);
    }

    function editAct(id){
        var url = '<%=basePath%>manUser/editActShow?actid='+id;
        openMoTai(url);
    }

    function deleteNews(id){
       if(confirm("确定要删除该条新闻？")){
           $.ajax({
               url:'<%=basePath%>manUser/deleteNews?newsid='+id,
               type:'get',
               success:function (data) {
                   if(data.status != "success"){
                       alert(data.message);
                   }else{
                       $("#myModal").modal('hide');
                       setTimeout(function () {
                           //500毫秒以后跳转有过渡的效果
                           location.href = "<%=basePath%>org_index_ol/goManMes?mesid=${mesWithBLOB.id}&handleArea=1";
                       },500);
                   }
               }
           });
       }
    }

    function deleteAct(id){
        if(confirm("确定要删除该活动？")){
            $.ajax({
                url:'<%=basePath%>manUser/deleteAct?actid='+id,
                type:'get',
                success:function (data) {
                    if(data.status != "success"){
                        alert(data.message);
                    }else{
                        $("#myModal").modal('hide');
                        setTimeout(function () {
                            //500毫秒以后跳转有过渡的效果
                            location.href = "<%=basePath%>org_index_ol/goManMes?mesid=${mesWithBLOB.id}&handleArea=2";
                        },500);
                    }
                }
            });
        }
    }

    function watchPicture(url){
        $("#imgView").modal({
            remote: '<%=basePath%>compent/ImgView',
            backdrop: 'static',
            keyboard: false
        });

    }

    function fmtImg(cellvalue, options, rowObject){
        var myValue = cellvalue;
        if(myValue != null) {
            myValue = myValue.split("\\").join("\\\\");
            return '<span data-toggle="modal" href="javascript:void(0);" onclick="watchPicture(\''+myValue+'\')">查看图片</span>';
        }else{
            return "";
        }
    }

    $(function () {
        //页面加载完成之后执行
        var id = ${mesWithBLOB.id};
        pageInit(id);
        $("#editBtn").click(function () {
            editMes(id);
        });


    });

    function pageInit(id) {
        //创建jqGrid组件
        jQuery("#list").jqGrid(
            {
                url: '<%=basePath%>org_mes/getMes_user?mesId='+id,//组件创建完成之后请求数据的url
                datatype: "json",//请求数据返回的类型。可选json,xml,txt
                colNames: ['ID', '用户名', '真实姓名', '年龄', '性别', '邮箱', 'QQ|微信', '电话', '图片'],//jqGrid的列显示名字
                colModel: [ //jqGrid每一列的配置信息。包括名字，索引，宽度,对齐方式.....
                    {name: 'id', index: 'id', width: 55, align: "center"},
                    {name: 'userName', index: 'userName', width: 200, align: "center"},
                    {name: 'userTrueName', index: 'userTrueName', width: 100, align: "center"},
                    {name: 'userAge', index: 'userAge', width: 80, align: "center"},
                    {name: 'userSex', index: 'userSex', width: 80, align: "center"},
                    {name: 'userEmail', index: 'userEmail', width: 300, sortable: false,align: "center"},
                    {name: 'userQQWei', index: 'userQQWei', width: 300, sortable: false, align: "center"},
                    {name: 'userPhone', index: 'userPhone', width: 150, align: "center", sortable: false},
                    {name: 'userPhoto', index: 'userPhoto', width: 150, align: "center", sortable: false, formatter: fmtImg},
                ],
                rowNum: 10,//一页显示多少条
                rowList: [10, 20, 30],//可供用户选择一页显示多少条
                pager: '#pager2',//表格页脚的占位符(一般是div)的id
                sortname: 'id',//初始化的时候排序的字段
                sortorder: "asc",//排序方式,可选desc,asc
                mtype: "post",//向后台请求数据的ajax的类型。可选post,get
                viewrecords: true,
                caption: "社团成员列表",//表格的标题名字
                autowidth: true,
                editurl : "<%=basePath%>message/delete_user?mesid="+id
            });
        /*创建jqGrid的操作按钮容器*/
        /*可以控制界面上增删改查的按钮是否显示*/
        jQuery("#list").jqGrid('navGrid', '#pager2', {edit: false, add: false, del:false});
        jQuery("#list").navButtonAdd('#pager2',{

            caption:"新增成员",

            buttonicon:"ui-icon-add",

            onClickButton: function(){
                <shiro:hasRole name='社团管理员'>
                    NewUser(id);
                </shiro:hasRole>
            },

            position:"last"

        });
        jQuery("#list").navButtonAdd('#pager2',{

            caption:"删除成员",

            buttonicon:"ui-icon-del",

            onClickButton: function(){
                <shiro:hasRole name='社团管理员'>
                var gr = jQuery("#list").jqGrid('getGridParam', 'selrow');
                if (gr != null) {
                    if (confirm("确定要删除该成员（慎重）？")) {
                        console.log(gr);
                        $.ajax({
                            type:'POST',
                            url:'<%=basePath%>org_mes/delete_user?userid='+gr+"&mesid="+id,
                            success:function (data) {
                                if(data.status != 'success'){
                                    alert(data.message);
                                }
                                else{
                                    jQuery("#list").trigger("reloadGrid");
                                }
                            }
                        });
                    }
                }
                else{
                    alert("请选择要删除的行");
                }
                </shiro:hasRole>

            },
            position:"last"
        });
    }
</script>
</html>
