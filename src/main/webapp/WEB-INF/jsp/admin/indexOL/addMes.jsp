<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
    #addMes td label{
        margin-right: 10px;
    }
    #addMes tr{
        margin-top: 5px;
    }
    #addMes td:nth-child(1){
        width: 20%;
        text-align:right;

    }
    #addMes td:nth-child(2){
        width: 30%;
        height: 50px;
    }
    #addMes td:nth-child(3){
        width: 20%;
        text-align: right;
    }
    #addMes td:nth-child(4){
        width: 30%;
    }
</style>
<!-- 内容部分 -->
<form id="ff" method="post" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${id}" id="key"/>
    <input id="file" name="input-b1" type="file" style="display: none">
    <input type="hidden" name="url" id="url"/>
    <table id="addMes">
        <tr>
            <td><label>轮播描述</label></td>
            <td><input class="easyui-textbox" type="text" name="descrption"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>轮播类型</label></td>
            <td>
                <input class="easyui-combobox" name="type" id="type" style="width: 100%;"></input>
            </td>
        </tr>
        <tr>
            <td><label>轮播链接至</label></td>
            <td colspan="3"><input class="easyui-combobox" name="choseTarget" id="chosetarget" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>轮播图片</label></td>
            <td colspan="3"><input class="easyui-textbox" id="upload" name="photoPath" data-options="prompt:'请使用1200*800规格图片,达到最大美化效果'" style="width: 122%;"/></td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    var url = '<%=basePath %>org_index_ol/add';
    <c:choose>
        <c:when test="${id != '' && id != null}">
              url = '<%=basePath %>org_index_ol/edit';
        </c:when>
    </c:choose>
    function submitForm() {
        console.log(url);
        //不知道為什麼用easyui自帶的ajaxt提交文件無法上傳
        $.messager.progress({
            title:'提示消息',
            msg:'正在提交中'
        });
        $("#ff").form('submit', {
            url: url,
            onSubmit: function(param){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// hide progress bar while the form is invalid
                }
                //做下处理数据
                var value;
                if($("#type").val()==1){
                    value = '<%=basePath%>org_activity/homeList_detail?actid='+$("#chosetarget").combobox("getValue");
                }else{
                    value = '<%=basePath%>org_news/newsListDetail?newsid='+$("#chosetarget").combobox("getValue");
                }
                $("#url").val(value);


                return isValid;	// return false will stop the form submission
            },
            success:function(data){
                //alert(data);
                var obj =  JSON.parse(data);
                console.log(obj);
                $.messager.show({
                    title:'消息提示',
                    msg:obj.message,
                });
                $.messager.progress('close');
                $("#win").window("close");
                $("#dg").datagrid("reload");
                $("#dg").datagrid("uncheckAll");
            }
        });

        <%--  $("#messageForm").ajaxSubmit({
            type:"post",  //提交方式
            url:"<%=path%>/message/add", //请求url
            success:function(data){ //提交成功的回调函数
                $.messager.alert("提示",data);
            }
        });      --%>
    }
    $(function () {
        /*为类型设置初始值*/
        $("#type").combobox({
            valueField:'value',
            textField:'option',
            data: [{
                option: '活动',
                value: '1'
            },{
                option: '新闻',
                value: '2'
            }]
        });

        /*为轮播级联绑定事件*/
        $("#type").combobox({
            onChange:function () {
               if($(this).val()==1){
                   //活动
                   $('#chosetarget').combobox({
                       url:'<%=basePath%>org_activity/getAllActivity',
                       valueField:'id',
                       textField:'actTheme',
                   });
               }else{
                   //新闻
                   $('#chosetarget').combobox({
                       url:'<%=basePath%>org_news/getAllNews',
                       valueField:'id',
                       textField:'newTheme',
                   });
               }
            }
        });




        $("#file").change(function () {
            $("#upload").textbox("setValue",$("#file").val());
        });
        $("#upload").textbox({
            onClickButton:function () {
               return $("#file").click();
            },
            buttonText:"选择文件",
            icons:[{
                iconCls:'icon-search',
                handler:function (e) {
                    watchPicture($("#upload").textbox("getValue"));
                }
            },
                {
                    iconCls:'icon-save',
                    handler:function (e) {
                        $.ajaxFileUpload({
                            type: "POST",
                            url: "<%=basePath%>compent/uploadImg",
                            /*data:{picParams:text},//要传到后台的参数，没有可以不写  */
                            secureuri : false,//是否启用安全提交，默认为false
                            fileElementId:'file',//文件选择框的id属性
                            dataType: 'json',//服务器返回的格式
                            async : false,
                            success: function(data){
                                if(data.status != 'success'){
                                    alert(data.message);
                                }else{
                                    $("#upload").textbox("setValue",data.uploadUrl);
                                    $("#file").change(function () {
                                        $("#upload").textbox("setValue",$("#file").val());
                                    });
                                }
                            }
                        });
                    }
                },
                {
                    iconCls:'icon-cut',
                    handler:function (e) {
                        $(e.data.target).textbox('clear');
                        $("#file").val("");
                    }
                }

            ]
        });
    });
</script>
<c:choose>
    <c:when test="${id != '' && id != null}">
        <script>
            setTimeout(function() {
                var data = ${data};
                $("#ff").form("load",data);
            },200);
        </script>
    </c:when>
</c:choose>