<!DOCTYPE html>
<%String path = request.getContextPath();String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>后台管理</title>
    <link href="<%=basePath%>css/my/admin/login.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/demo.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
</head>
<body>
<div class="login_box">
    <div class="login_l_img"><img src="<%=basePath%>img/login-img.png" /></div>
    <div class="login">
        <div class="login_logo"><a href="#"><img src="<%=basePath%>img/login_logo.png" /></a></div>
        <div class="login_name">
            <p>社团信息网后台管理</p>
        </div>
        <form id="myForm">
            <input name="username" type="text"  value="用户名" onfocus="if(this.value=='用户名'){this.value=''}" onblur="if(this.value==''){this.value='用户名'}">
            <span id="password_text" onclick="this.style.display='none';document.getElementById('password').style.display='block';document.getElementById('password').focus().select();" >密码</span>
            <input name="password" type="password" id="password" style="display:none;" onblur="if(this.value==''){document.getElementById('password_text').style.display='block';this.style.display='none'};"/>
            <input value="登录" style="width:100%;" type="button" id="login">
        </form>
    </div>
    <div class="copyright">某某有限公司 版权所有©2016-2018 技术支持电话：000-00000000</div>
</div>
</body>
<script type="text/javascript">
    $(function(){
        $("#login").click(function(){
            $.messager.progress({
                title:'提示消息',
                msg:'正在验证'
            });
            $("#myForm").form('submit',{
                url:'<%=basePath%>admin/checkLogin',
                success:function(data){
                    var obj =  JSON.parse(data);
                    if(obj.status != "success"){
                        $.messager.progress('close');
                        $.messager.show({
                            title:'消息提示',
                            msg:obj.message,
                        });
                    }else{
                        location.href = "<%=basePath%>admin/main";
                    }
                }
            });
        });

        $("#reset").click(function(){
            alert("af");
        });
    });
</script>
</html>

