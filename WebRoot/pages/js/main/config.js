//后台服务地址
var url = 'http://127.0.0.1:8080/zysy/';
//secret key
var sk = 'TTILY';

$(document).ready(function(){
    $('#logoutBtn').click(function () {
        sessionStorage.clear();
        window.location.href='login.html';
    });

    var userinfo=sessionStorage.getItem('userinfo');
    if(userinfo!=null){
        $('#loginName').text(JSON.parse(userinfo)['nickname']);

    }

});

/**
 * 登录
 */
function login() {
    $.ajax({
        url : url+'login',
        type : 'POST',
        data : {
            'username' : $('#username').val(),
            'password' : $('#password').val()
        },
        success : function(response) {
            console.log(JSON.stringify(response));
            var token = response['token'];
            var userinfo = JSON.stringify(response['msg']);
            //var timestamp = Date.parse(new Date());
            //var hash = md5(token + timestamp + sk);
            sessionStorage.setItem('username',$('#username').val());
            sessionStorage.setItem('userpwd',$('#password').val());
            sessionStorage.setItem('userinfo',userinfo);
            sessionStorage.setItem('token',token);

            //window.location.href='default-page.html?backurl='+window.location.href;
            window.location.href='index.html';
        },
        error : function(response) {
            alert('登录失败！');
        }
    });

}


function createHttpR(url,type,dataType,bodyParam){
    this.url = url;
    this.type = type;
    this.dataType = dataType;
    this.bodyParam = bodyParam;
}
createHttpR.prototype.HttpRequest = function(callBack){

    if(sessionStorage.getItem('username')!=null||sessionStorage.getItem('token')!=null){
        var  token = sessionStorage.getItem('token');
        var timestamp = Date.parse(new Date());
        var hash = md5(token+timestamp+sk);
        $.ajax({
            url:this.url,
            type:this.type,
            cache:false,
            timeout:20,
            dataType:this.dataType,
            data :this.bodyParam,
            async:false,
            headers: {
                'token' : token,
                'timesamp' : timestamp,
                'sign' : hash
            },
            success:function(response) {
                var obj = JSON.parse(response);
                var status = obj['status'];
                var msg = obj['msg'];
                if(status=='mismatch'||status=='expire'){
                    console.log(msg);
                    alert('验证信息错误，请重新登录！');
                    //无用户信息，重新登录
                    window.location.href='login.html';
                    //login();
                }
                else if(status=='0'){
                    callBack(response);
                }
                else{
                    alert(msg);
                }
            },
            error:function(response){
                alert('请求失败！');
            },
            beforeSend:function(){
                //alert('before');
            },
            complete:function(){
                //alert('complete');
            }

        });
    }
    else{
        alert('访问权限已过期，请重新登录！');
        //无用户信息，重新登录
        window.location.href='login.html';
    }

}


function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function  querySys_members (displayname) {
    var param='';
    if(displayname==''){
        param='{"page":"1","size":"10","order":"order by id desc"}';
    }
    else{
        param='{"page":"1","size":"10","order":"order by id desc","displayname":"'+displayname+'"}';
    }
    var bodyParam={'method':'query','tableName':'sys_members','param':param};
    var httpR = new createHttpR(url+'DipService','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            //console.log(data);
            var html='';
            for(var o in data){
                var c_name='';
                for(var p in data){
                    if(data[o].mem_id==data[p].id){
                        c_name=data[p].displayname;
                    }
                }
                html+='<tr id='+data[o].id+'>'+
                    '<td>'+data[o].displayname+'</td>'+
                    '<td>'+data[o].username+'</td>'+
                    //'<td>'+data[o].userpwd+'</td>'+
                    '<td>'+data[o].c_dt+'</td>'+
                    '<td>'+c_name+'</td>'+
                    '</tr>';
            }
            $('#data_tbody').html(html);
        }
    });
}