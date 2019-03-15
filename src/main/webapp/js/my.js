String.prototype.gblen = function(gbWidth, enWidth) {
    var len = 0;
    if(gbWidth != undefined){
        width = 1;
    }
    if(enWidth != undefined){
        enWidth = 0;
    }
    for (var i=0; i<this.length; i++) {
        if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94) {
            len += gbWidth;
        } else {
            len += enWidth;
        }
    }
    return len;
};

var newDate = new Date();
Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
};
$.extend($.fn.textbox.defaults.rules, {
    number: {
        validator: function (value, param) {
            return /^[0-9]*$/.test(value);
        },
        message: "请输入数字"
    },
    ip: {
        validator: function (value, param) {
            return /^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$/.test(value);
        },
        message: "请输入IP地址"
    },
    vin: {
        validator: function (value, param) {
            return /^[A-Z0-9]+$/.test(value);
        },
        message: "VIN只能包含字符A-Z或0-9"
    },
    plateNo: {
        validator: function (value, param) {
            return /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/.test(value);
        },
        message: "请输入正确的车牌号码"
    },
    chinese: {
        validator: function (value, param) {
            var reg = /^[\u4e00-\u9fa5]+$/i;
            return reg.test(value);
        },
        message: "请输入中文"
    },
    checkLength: {
        validator: function (value, param) {
            return param[0] >= value.length;
        },
        message: '请输入最大{0}位字符'
    },
    specialCharacter: {
        validator: function (value, param) {
            var reg = new RegExp("[`~!@#$^&*()=|{}':;'\\[\\]<>~！@#￥……&*（）——|{}【】‘；：”“'、？]");
            return !reg.test(value);
        },
        message: '不允许输入特殊字符'
    },
    englishLowerCase: {// 验证英语小写
        validator: function (value) {
            return /^[a-z]+$/.test(value);
        },
        message: '请输入小写字母'
    },
    tel: {// 验证英语小写
        validator: function (value) {
            return /^1[3|4|5|7|8][0-9]{9}$/.test(value);
        },
        message: '请输入正确的手机号码'
    },
    numberAndDecimal:{
        validator: function (value) {
            return /^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value);
        },
        message: '请输入整数或小数'
    },
    idno:{
        validator: function (value) {
            return /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/.test(value);
        },
        message: '请输入正确的身份证号'
    }

});

;(function($){
    in_array = function(stringToSearch, arrayToSearch) {
        for (s = 0; s < arrayToSearch.length; s++) {
            thisEntry = arrayToSearch[s].toString();
            if (thisEntry == stringToSearch) {
                return true;
            }
        }
        return false;
    };
    idFilter = function(str, arr){
        var ids = str.toString().split(",");
        var values = [];
        for(i=0; i< ids.length; i++){
            if( in_array(ids[i], arr) ){
                values.push(ids[i]);
            }
        }
        return values.join(",");
    };
    strlen = function(str){
        var len = 0;
        for (var i=0; i<str.length; i++) {
            var c = str.charCodeAt(i);
            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
                len++;
            }
            else {
                len+=2;
            }
        }
        return len;
    };
    strtotime = function(stringTime){
        var timestamp2 = 0;
        if( /^\d{4}(\-)\d{2}\1\d{2}( ?\d{2}\:\d{2}| ?\d{2}\:\d{2}\:\d{2})?$/.test(stringTime) ){
            if(stringTime.length == 10){
                stringTime += " 00:00:00";
            }
            timestamp2 = Date.parse(new Date(stringTime))/1000;
        }
        return timestamp2;
    };
    fmtDateTime = function(value, row, index) {
        var timestamp = value;
        newDate.setTime(timestamp * 1000);
        return newDate.format('yyyy-MM-dd hh:mm:ss');
    };
    fmtDate = function(value, row, index) {
        var timestamp = value;
        newDate.setTime(timestamp * 1000);
        return newDate.format('yyyy-MM-dd');
    };
    fmtDate1 = function(value, row, index){
        var val = "<font color='#e2e2e2'>不限制</font>"
        if(value != 0 && value != null){
            val = fmtDate(value, row, index);
        }
        return val;
    };
    fmtStatus = function(value, row, index) {
        var Status = "有效";
        if (value == 0) {
            Status = "<font color='#aaa'>无效</font>";
        }
        return Status;
    };
    fmtType = function(value, row, indfex) {
        if (value == "1") {
            return "<font color='#8b0000'>超级管理员</font>";
        } else {
            return "普通用户";
        }
    };
    fmtImg = function(value, row, indfex){
        var val = "";
        if(value != ""){
            val ="<img onclick='viewPic(\""+BasePath+"/upload/"+value+"\")' style='cursor:pointer' src='"+BasePath+"/easyui/themes/insdep/icons/more/picture.png'>";
        }
        return val;
    };
    getByDict = function(value, fun) {
        var dict = fun();
        return dict[value.toString()];
    };
    getCombobox = function(fun) {
        var dict = fun();
        var ret = [];
        $.each(fun(), function (key, val) {
            ret.push({id: key, text: val});
        });
        return ret;
    };
    doSubmit = function ($url, $act, $ckFun, $succFun) {
        if ($('#editForm').form('enableValidation').form('validate')) {
            var $json = {};
            $("#editForm input,#editForm select,#editForm textarea").each(function () {
                var name = $(this).attr("name");
                if (name != undefined && name != "") {
                    if ($(this).attr("type") == "checkbox") {
                        if ($(this).attr("checked")) {
                            $json[name] = $(this).val();
                        } else {
                            $json[name] = 0;
                        }
                    } else if ($(this).attr("type") == "radio") {
                        //console.log( $("input[name='state0'][checked]").val() );
                        //$json[name] = $("input[name='" + name + "'][checked]").val();
                        if( $(this).attr("checked") ){
                            $json[$(this).attr("name")] = $(this).val();
                        }
                    } else if ($(this).attr("type") != "checkbox") {
                        if( $json[name] == undefined ){
                            $json[name] = $(this).val();
                        }else {
                            $json[name] += ","+$(this).val();
                        }
                    }
                }
            });
            if ($ckFun != undefined && !$ckFun($json)) {
                return false;
            }
            $.messager.progress({
                title: '',
                msg: '<span id="progressMsg" style="line-height:18px;">数据提交中...</span>'
            });
            $.post($url, $json, function (rsp) {
                $.messager.progress('close');
                if (rsp.status == 1 || rsp.status == 2) {
                    $.messager.show({title: "操作提示", msg: rsp.message, timeout: 5000, showType: 'slide'});
                    if( $succFun != undefined ){
                        $succFun();
                    }else {
                        closeEditWin($act);
                    }
                } else {
                    $.messager.alert("操作提示", '<span style="line-height:25px;padding-left:10px;padding-top:5px;">' + rsp.message + '</span>', "error");
                }
            }, "JSON").error(function (err) {
                $.messager.progress('close');
                $.messager.alert("提示", "提交错误了！" + JSON.stringify(err));
            });
        }
    };
    selectAll = function(obj){
        if(obj != undefined){
            obj.children("input[type='checkbox']").attr("checked","true");
        }
    };
    registerListener = function(handleFunc){
        window.parent.addSubscribe(window.location.href,handleFunc);
    };
    getVehicleInfoByIds = function(ids, callback){
        var $url = BasePath+"/vehicle/getInfoByIds.shtml";
        var $json = {};
        $json["ids"] = ids;
        $.post($url, $json, function (rsp) {
            if( callback != undefined ) {
                callback(rsp);
            }
        }, "JSON").error(function (err) {
            $.messager.alert("提示", "出现错误了！" + JSON.stringify(err));
        });
    };

    getVehicleInfoByPlateNo = function(plateNo, callback){
        var $url = BasePath+"/vehicle/getInfoByPlateNo.shtml";
        var $json = {};
        $json["plateNo"] = plateNo;
        $.post($url, $json, function (data) {
            if( callback != undefined ) {
                var ret = {};
                if( data.length > 0 ){
                    ret = data[0];
                }
                if(callback != undefined){
                    callback(ret);
                }
            }
        }, "JSON").error(function (err) {
            $.messager.alert("提示", "出现错误了！" + JSON.stringify(err));
        });
    };

    getVehicleInfo = function(id, callback){
        getVehicleInfoByIds(id, function(data){
            var ret = {};
            if( data.length > 0 ){
                ret = data[0];
            }
            if(callback != undefined){
                callback(ret);
            }
        })
    };


    viewPic = function(img){
        if($("#viewWIn").length<=0){
            $("body").append('<div id="viewWIn"></div>');
        }
        $('#viewWIn').window({
            top:0,
            title: '预览',
            width: 610,
            modal: true,
            shadow: true, inline: false, resizable: true, maximizable: false, minimizable: false,
            content: '<img width="100%" src="'+img+'" />'
        });
    };

    initUploadTools = function(container, upladBtn, uploadInput, callback){
        $("."+container+" .icon-folder-search").attr("id", "pickfiles");
        $("."+container+" .textbox-text").attr("readonly","true");
        var uploader = new plupload.Uploader({
            runtimes : 'html5,flash,silverlight,html4',
            browse_button : "pickfiles", // you can pass an id...
            //container: document.getElementById('container'), // ... or DOM Element itself
            url : BasePath+'/open/importPicFile.shtml',
            flash_swf_url : '${ex:getBasePath()}/plupload/js/Moxie.swf',
            silverlight_xap_url : '${ex:getBasePath()}/plupload/js/Moxie.xap',
            multi_selection: false,//只能单选
            file_data_name: 'file',//数据名称
            //multiple_queues : true,//是否可以多次上传
            //resize : { width : 320, height : 240, quality : 90 },//压缩图片大小
            //指定要浏览的文件类型
            filters : {
                max_file_size : '10mb',
                mime_types: [
                    {title : "Image files", extensions : "jpg,gif,png"},
                    {title : "Zip files", extensions : "zip"}
                ]
            },
            multipart_params: {
                picParams: '1'
            },
            init: {
                PostInit: function() {
                    document.getElementById(upladBtn).onclick = function() {
                        uploader.start();
                        return false;
                    };
                },
                FilesAdded: function(up, files) {
                    if(uploader.files.length>1) { // 最多上传1张图
                        for(var i=0; i<uploader.files.length-files.length;i++) {
                            uploader.files.splice(uploader.files[i].id, 1);
                        }
                    }
                    plupload.each(files, function(file) {
                        $("#"+uploadInput).textbox("setValue", file.name + ' (' + plupload.formatSize(file.size) + ')');
                    });
                },
                UploadProgress: function(up, file) {
                    $("#"+uploadInput).textbox("setValue", file.name + ' (' + plupload.formatSize(file.size) + ') ' + file.percent + '%');
                },
                FileUploaded: function(up,file,responseObject){
                    if(callback != undefined){
                        callback(JSON.parse(responseObject.response));
                    }else {
                        console.log(responseObject.response);
                    }
                },
                Error: function(up, err) {
                    console.log("Error #" + err.code + ": " + err.message);
                }
            }
        });

        remove = function(id){
            uploader.files.splice(id, 1);
        };

        uploader.init();
    };

    $.fn.extend({
        // 获取当前光标位置的方法
        getCurPos:function() {
            var getCurPos = '';
            if ( navigator.userAgent.indexOf("MSIE") > -1 ) {  // IE
                // 创建一个textRange,并让textRange范围包含元素里所有内容
                var all_range = document.body.createTextRange();all_range.moveToElementText($(this).get(0));$(this).focus();
                // 获取当前的textRange,如果当前的textRange是一个具体位置而不是范围,则此时textRange的范围是start到end.此时start等于end
                var cur_range = document.selection.createRange();
                // 将当前textRange的start,移动到之前创建的textRange的start处,这时,当前textRange范围就变成了从整个内容的start处,到当前范围end处
                cur_range.setEndPoint("StartToStart",all_range);
                // 此时当前textRange的Start到End的长度,就是光标的位置
                curCurPos = cur_range.text.length;
            } else {
                // 获取当前元素光标位置
                curCurPos = $(this).get(0).selectionStart;
            }
            // 返回光标位置
            return curCurPos;
        }
    });
})(jQuery);

//判断是否
function isTrueFalse(value){
    if(value == true){
        return "是";
    }else{
        return "否";
    }
}

