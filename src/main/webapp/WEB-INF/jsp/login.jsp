<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<html>
<head>
    <meta charset="utf-8">
    <title>登录界面</title>
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/reset.css" />
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/common.css" />
    <link rel="stylesheet" href="<%=basePath%>css/my/home/register/font-awesome.min.css" />
</head>
<body>
<div class="wrap login_wrap">
    <div class="content">
        <div class="logo"></div>
        <div class="login_box">
            <div class="login_form">
                <div class="login_title">
                    登录
                </div>
                <form id="myForm">
                    <div class="form_text_ipt">
                        <input name="username" type="text" placeholder="手机号/邮箱/自定义用户名" value="${cookie.username.value}">
                    </div>
                    <div class="ececk_warning"><span>用户名不能为空</span></div>
                    <div class="form_text_ipt">
                        <input name="password" type="password" placeholder="密码" value="${cookie.password.value}">
                    </div>
                    <div class="ececk_warning"><span>密码不能为空</span></div>
                    <div class="form_check_ipt">
                        <div class="left check_left">
                            <label><input name="" type="checkbox"> 下次自动登录</label>
                        </div>
                        <div class="right check_right">
                            <a href="javascript:void(0);" onclick="alert('暂未开放！');">忘记密码</a>
                        </div>
                    </div>
                    <div class="form_btn">
                        <button type="button" id="login">登录</button>
                    </div>
                    <div class="form_reg_btn">
                        <span>还没有帐号？</span><a href="/orgManager/org_index_ol/goRegister">马上注册</a>
                    </div>
                </form>
                <div class="other_login">
                    <div class="left other_left">
                        <span>其它登录方式</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/my/common.js"></script>
<script>
    $(function(){
        $("#login").click(function(){
           $.ajax({
               url:'<%=basePath%>org_index_ol/checkLogin',
               type:'post',
               data:$("#myForm").serialize(),
               success:function(data){
                   if(data.status != "success"){
                       alert(data.message);
                   }else{
                       if(data.message == "社团管理员登录成功"){
                           //跳转一个单独的处理社团信息的页面
                            location.href="<%=basePath%>org_index_ol/goManMes?mesid="+data.mesid+"&userid="+data.userid;
                       }else{
                           location.href = "<%=basePath%>";
                       }

                   }
               }
           });
        });
    });
</script>
</body>
</html>

