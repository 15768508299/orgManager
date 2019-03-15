<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<html>
<head>
    <meta charset="utf-8">
    <title>注册界面</title>
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/reset.css" />
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/common.css" />
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/font-awesome.min.css" />
</head>
<body>
<div class="wrap login_wrap">
    <div class="content">
        <div class="logo"></div>
        <div class="login_box" style="height: 300px;">
            <div class="login_form">
                <div class="login_title">
                    注册
                </div>
                <form id="myForm">
                    <div class="form_text_ipt">
                        <input name="userName" id="username" type="text" placeholder="手机号/邮箱/自定义用户名">
                    </div>
                    <div class="ececk_warning"><span>用户名不能为空</span></div>
                    <div class="form_text_ipt">
                        <input name="userPassword" id="password" type="password" placeholder="密码" >
                    </div>
                    <div class="form_check_ipt">
                        <div class="right check_right">
                            <a href="javascript:void(0);" onclick="alert('暂未开放！');">忘记密码</a>
                        </div>
                    </div>
                    <div class="form_btn">
                        <button type="button" id="login">马上注册</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/my/common.js"></script>
<script>
    $(function(){
        $("#login").click(function(){
            if($("#username").val()!= "" && $("#password").val()!= "") {
                $.ajax({
                    url: '<%=basePath%>org_index_ol/register',
                    type: 'post',
                    data: $("#myForm").serialize(),
                    success: function (data) {
                        if (data.status != "success") {
                            alert(data.message);
                        } else {
                            alert(data.message)
                            location.href = "<%=basePath%>org_index_ol/login";
                        }
                    }
                });
            }else{
                alert("请填写用户名和密码进行注册");
            }
        });
    });
</script>
</body>
</html>

