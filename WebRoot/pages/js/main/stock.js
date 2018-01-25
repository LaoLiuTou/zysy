$(document).ready(function(){

    //选取的stock
    var stockList;
    var stockIndex;
    var currentStock;
});


////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////库存管理////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
/**
 * 添加用户
 */
function addStock(){
    var userinfo = JSON.parse(sessionStorage.getItem('userinfo'));
    var bodyParam={'name':$('#name').val(),'leader':$('#leader').val(),
        'comment':$('#comment').val(),'state':$('#state').val(),'c_id':userinfo['id']};
    var httpR = new createHttpR(url+'addStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        //var msg = obj['msg'];
        if(status=='0'){
            alert("新建成功！");
            window.location.reload();
            //window.location.href="interface.html?index="+interfaceIndex;
        }
    });
}

/**
 * 修改用户
 * @param id
 */
function updateStock(id){
    var userinfo = JSON.parse(sessionStorage.getItem('userinfo'));
    var bodyParam={'id':id,'name':$('#update_name').val(),'leader':$('#update_leader').val(),
        'comment':$('#update_comment').val(),'state':$('#update_state').val(),'c_id':userinfo['id']};
    var httpR = new createHttpR(url+'updateStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        //var msg = obj['msg'];
        if(status=='0'){
            alert("修改成功！");
            window.location.reload();
            //window.location.href="interface.html?index="+interfaceIndex;
        }
    });
}

/**
 * 删除用户
 * @param id
 */
function deleteStock(id){
    var bodyParam={'id':id};
    var httpR = new createHttpR(url+'deleteStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        //var msg = obj['msg'];
        if(status=='0'){
            alert("修改成功！");
            window.location.reload();
        }
    });
}
/**
 * 查询用户
 * @param stockname
 * @param currentPage
 * @param pageSize
 */
function  queryStock (stockname,currentPage,pageSize) {

    //分页显示的页码数  必须为奇数
    var showPage=7;
    if(stockname==null||stockname==''){
        var bodyParam={'page':currentPage,'size':pageSize};
    }
    else{
        var bodyParam={'page':currentPage,'size':pageSize,'name':stockname};
    }

    var httpR = new createHttpR(url+'listStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            stockList=msg['data'];
            var html='';
            for(var o in data){

                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].id+'</td>\n' +
                    '<td>'+data[o].material+'</td>\n' +
                    '<td>'+data[o].stocktype+'</td>\n' +
                    '<td>'+data[o].workshop+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+data[o].number+'</td>\n' +
                    '<td>'+data[o].comment+'</td>\n'  ;
                if(data[o].state=='0'){
                    html+='<td><span class="label label-success label-mini">入库</span></td>\n';
                }
                else if(data[o].state=='1'){
                    html+='<td><span class="label label-warning label-mini">出库</span></td>\n';
                }
                else if(data[o].state=='2'){
                    html+='<td><span class="label label-danger label-mini">破损</span></td>\n';
                }
                html+='<td><a class="updateStock" href="" index='+o+' data-toggle="modal" data-target="#update-box"><span class="label label-info label-mini">修改</span></a>   ' +
                    '<a class="deleteStock" href="" index='+o+' data-toggle="modal" data-target="#delete-box"><span class="label label-info label-mini">删除</span></a></td>\n';
                html+='</tr>';
            }
            $('#stockTbody').html(html);
            var num=msg['num'];
            if(num>0){
                var pageHtml='';
                var totalPage=0;
                if(num%pageSize==0){
                    totalPage=num/pageSize;
                }
                else{
                    totalPage=Math.ceil(num/pageSize);
                }

                if(currentPage==1){
                    pageHtml+='<li class="disabled"><a href="#">|&laquo;</a></li>';
                    pageHtml+='<li class="disabled"><a href="#">&laquo;</a></li>';
                }
                else{
                    pageHtml+='<li ><a href="#" class="pageBtn" index="1">|&laquo;</a></li>';
                    pageHtml+='<li ><a href="#" class="prevBtn" index="">&laquo;</a></li>';
                }
                if(totalPage<=showPage){
                    for(var i=1;i<Number(totalPage)+1;i++){
                        if(currentPage==i){
                            pageHtml+='<li class="active"><a href="#" >'+i+'</a></li>';
                        }
                        else{
                            pageHtml+='<li><a href="#" class="pageBtn" index="'+i+'">'+i+'</a></li>';
                        }
                    }
                }
                else{
                    if(currentPage<=(showPage-1)/2){
                        for(var i=1;i<=showPage;i++){
                            if(currentPage==i){
                                pageHtml+='<li class="active"><a href="#" >'+i+'</a></li>';
                            }
                            else{
                                pageHtml+='<li><a href="#" class="pageBtn" index="'+i+'">'+i+'</a></li>';
                            }
                        }
                    }
                    else if(totalPage-currentPage<(showPage-1)/2){
                        for(var i=Number(totalPage)-showPage;i<=totalPage;i++){
                            if(currentPage==i){
                                pageHtml+='<li class="active"><a href="#" >'+i+'</a></li>';
                            }
                            else{
                                pageHtml+='<li><a href="#" class="pageBtn" index="'+i+'">'+i+'</a></li>';
                            }
                        }
                    }
                    else{
                        for(var i=Number(currentPage)-(showPage-1)/2;i<=Number(currentPage)+(showPage-1)/2;i++){
                            if(currentPage==i){
                                pageHtml+='<li class="active"><a href="#" >'+i+'</a></li>';
                            }
                            else{
                                pageHtml+='<li><a href="#" class="pageBtn" index="'+i+'">'+i+'</a></li>';
                            }
                        }
                    }


                }

                if(currentPage==totalPage){
                    pageHtml+='<li class="disabled"><a href="#">&raquo;</a></li>';
                    pageHtml+='<li class="disabled"><a href="#">&raquo;|</a></li>';
                }
                else{
                    pageHtml+='<li class="nextBtn" index=""><a href="#">&raquo;</a></li>';
                    pageHtml+='<li class="pageBtn" index="'+totalPage+'"><a href="#">&raquo;|</a></li>';
                }
                /* pageHtml+='<li><input type="text" id="jumpPageText" class="paging-inpbox form-control"></li>\n' +
                     '<li><button type="button" id="jumpBtn" class="paging-btnbox btn btn-primary">跳转</button></li>\n' +
                     '<li><span class="number-of-pages">共'+totalPage+'页</span></li>';*/

                $('.pagination').html(pageHtml);
            }

        }
    });
}
