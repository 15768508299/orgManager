var detailArray = [];

$.extend($.fn.validatebox.defaults.rules, {
    maxWidth: {
        validator: function(value,param){

            return value.length > 2 ;
        },
        message: '社团名称长度不能小于2个字符'
    }
});

//将字符串转化为时间戳
function stroeTime(value){
    if(value != undefined && value != null){
        var myDate = new Date(value);
        return myDate.getTime()/1000;
    }
}

function toWatchButton(value,row,index){
    var myValue = value;
    if(value != null) {
        myValue = myValue.split("\\").join("\\\\");
        return '<a href="javascript:void(0);" onclick="watchPicture(\''+myValue+'\')">查看图片</a>';
    }else{
        return "";
    }
}


function toWatchDetail(value,row,index){
    //console.log(row);
    detailArray[row.id] = value;
    if(value != null){
        return '<a href="javascript:void(0);" onclick="watchDetail('+row.id+')">详情</a>';
    }
}

function watchDetail(id){
    $('#detail').window({
        top:50,
        width:1200,
        height:600,
        modal:true,
        title:'详情介绍框',
        reload:true,
        content:'<div>'+detailArray[id]+'</div>'
    });
}


function timeStapmToString(value){
    //console.log(value);
    var date = new Date(value);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    return Y+M+D;
}



function getRealPath(){

    //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp

    var curWwwPath=window.document.location.href;

    //获取主机地址之后的目录，如： myproj/view/my.jsp

    var pathName=window.document.location.pathname;

    var pos=curWwwPath.indexOf(pathName);

    //获取主机地址，如： http://localhost:8083

    var localhostPaht=curWwwPath.substring(0,pos);

    //获取带"/"的项目名，如：/myproj

    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);


    //得到了 http://localhost:8083/myproj

    var realPath=localhostPaht+projectName;

    return realPath+"/";

}