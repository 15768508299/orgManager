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
    <input type="hidden" name="exId" id="exId"/>
    <input type="hidden" name="id" value="${id}" id="key"/>
    <input type="hidden" name="mesDetail" id="mesdetail">
    <input id="file" name="input-b1" type="file" style="display: none">
    <table id="addMes">
        <tr>
            <td><label>社团名称</label></td>
            <td><input class="easyui-textbox" type="text" name="mesName"  data-options="required:true,validType:'maxWidth'" style="width: 100%;"/></td>
            <td><label>社团创建人</label></td>
            <td><input class="easyui-textbox" name="mesCreate"   data-options="required:true" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>社团创建日期</label></td>
            <td><input class="easyui-datebox" name="mescreatetimeStr" id="mescreatetime"  data-options="required:true" style="width: 100%;"/></td>
            <td><label>社团总人数</label></td>
            <td><input class="easyui-textbox" type="text" name="mesNum"  data-options="promt:'默认是0'" value="0" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>社团负责人1(社长)</label></td>
            <td><input class="easyui-combobox" id="one" style="width: 100%;"/></td>
            <td><label>社团负责人2</label></td>
            <td><input class="easyui-combobox" id="two" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>封面图</label></td>
            <td colspan="3"><input class="easyui-textbox" id="upload" name="mesPhoto" data-options="prompt:'请上传封面图'" style="width: 100%;"/></td>
        </tr>
        <tr>
            <td><label>社团简介</label></td>
            <td colspan="3"><input class="easyui-textbox" type="text" name="mesIntroduction"   data-options="multiline:true,required:'',validType:''" style="height: 120px;width: 100%;"></td>
        </tr>
        <tr>
            <td><label>图文介绍</label></td>
            <td colspan="3"><script type="text/plain" id="myEditor" style="width:800px;height:340px;">请输入图文信息</script></td>
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
    var um = UM.createEditor("myEditor");
    var url = '<%=basePath %>org_mes/add';
    var action = "add";
    <c:choose>
        <c:when test="${id != '' && id != null}">
              url = '<%=basePath %>org_mes/edit';
              action = "edit";
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
                //做下时间处理数据处理
                /*var value = $("#mescreatetime").val();
                if(value!= null && value != ''){
                    $("#mescreatetime").textbox("setValue",stroeTime(value));
                }*/
                //做下id处理
                var ids = [];
                ids.push($("#one").combobox("getValue"));
                ids.push($("#two").combobox("getValue"));
                $("#exId").attr("value",ids.join(","));

                //文本处理
                var textDetail = um.getContent();
                console.log(textDetail);
                $("#mesdetail").attr("value",textDetail);
                return isValid;	// return false will stop the form submission
            },
            success:function(data){
                //alert(data);
                var obj =  JSON.parse(data);
                $.messager.show({
                    title:'消息提示',
                    msg:obj.message,
                });
                $.messager.progress('close');
                $("#dg").datagrid("reload");
                if(action == "edit"){
                    $("#win").window("close");
                    $("#dg").datagrid("uncheckAll");
                }else{
                    $("#ff").form("reset");
                }
            }
        });
    }
    $(function () {
        $('#one,#two,#three').combobox({
            url: '<%=path%>/org_user/getAllName',
            valueField: 'id',
            textField: 'userName'
        });

        /*文件异步上传*/
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
            var data = ${data};
            data.mescreatetimeStr = timeStapmToString(data.mesCreateTime);
            um.setContent(data.mesDetail);
            $.ajax({
            url:'<%=basePath%>org_mes/getByUnion?id='+data.id,
            ayas:false,
            success:function(data){
                $("#one").combobox("setValue",data[0].id);
                $("#two").combobox("setValue",data[1].id);
            }
            });
            $("#ff").form("load",data);
        </script>
    </c:when>
</c:choose>