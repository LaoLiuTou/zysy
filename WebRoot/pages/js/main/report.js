$(document).ready(function(){


});


////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////统计管理////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

/**
 * 统计库存
 * @param c_dt
 * @param stocktype
 * @param workshop
 */
function  reportStock (c_dt,stocktype,workshop) {
    var bodyParam={};
    if(c_dt!=''){
        bodyParam['c_dtFrom']=c_dt+' 00:00:00';
        bodyParam['c_dtTo']=c_dt+' 23:59:59';
    }
    if(stocktype!=''){
        bodyParam['stocktype']=stocktype;
    }
    if(workshop!=''){
        bodyParam['workshop']=workshop;
    }
    var httpR = new createHttpR(url+'reportStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];
        if(status=='0'){
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].material_name+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+data[o].sum_number+'</td>\n</tr>' ;
            }
            $('#reportTbody').html(html);
            selectWorkshop(1,100,workshop);
            selectStocktype(1,100,stocktype);
        }
    });
}
/**
 * 出入库明细
 * @param c_dt
 * @param stocktype
 * @param workshop
 */
function  reportStockInOut (stocktype,workshop,material) {
    var bodyParam={};

    if(stocktype!=''){
        bodyParam['stocktype']=stocktype;
    }
    if(workshop!=''){
        bodyParam['workshop']=workshop;
    }
    if(material!=''){
        bodyParam['material']=material;
    }
    var httpR = new createHttpR(url+'reportStockInOut','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];
        if(status=='0'){
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].c_dt+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_in)+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_out)+'</td>\n</tr>' ;
            }
            $('#reportTbody').html(html);
            selectWorkshop(1,100,workshop);
            selectMaterial(1,100,material);
            selectStocktype(1,100,stocktype);
        }
    });
}
/**
 * 车间进出配比
 * @param c_dt
 * @param stocktype
 * @param workshop
 */
function  reportWorkshopInOut (c_dt) {
    var bodyParam={};
    if(c_dt!=''){
        bodyParam['c_dtFrom']=c_dt+' 00:00:00';
        bodyParam['c_dtTo']=c_dt+' 23:59:59';
    }
    var httpR = new createHttpR(url+'reportWorkshopInOut','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];
        if(status=='0'){
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].workshop_name+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_in)+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_out)+'</td>\n' +
                    '<td>'+1+'</td>\n</tr>' ;
            }
            $('#reportTbody').html(html);

        }
    });
}
/**
 * 大锯产量统计
 * @param m_dt
 * @param currentPage
 * @param pageSize
 */
function  reportMatteboard (m_dt,currentPage,pageSize) {

    //分页显示的页码数  必须为奇数
    var showPage=7;
    var bodyParam={};
    if(m_dt!=''){
        bodyParam['m_dt']=m_dt+' 00:00:00';
    }
    bodyParam['page']=currentPage;
    bodyParam['size']=pageSize;

    var httpR = new createHttpR(url+'listMatteboard','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].m_dt+'</td>\n' +
                    '<td>'+data[o].code+'</td>\n' +
                    '<td>'+data[o].workgroup+'</td>\n' +
                    '<td>'+data[o].layer+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].blocknumber+'</td>\n' +
                    '<td>'+data[o].square+'</td>\n' +
                    '<td>'+data[o].belowgradeblock+'</td>\n' +
                    '<td>'+data[o].belowgradesquare+'</td>\n' +
                    '<td>'+(data[o].blocknumber-data[o].belowgradeblock)+'</td>\n' +
                    '<td>'+(data[o].square-data[o].belowgradesquare)+'</td>\n' +
                    '<td>'+data[o].comment+'</td>\n' +
                    '</tr>';
            }

            $('#reportTbody').html(html);
            var num=msg['num'];
            if(num>0) {
                var pageHtml = '';
                var totalPage = 0;
                if (num % pageSize == 0) {
                    totalPage = num / pageSize;
                }
                else {
                    totalPage = Math.ceil(num / pageSize);
                }

                if (currentPage == 1) {
                    pageHtml += '<li class="disabled"><a href="#">|&laquo;</a></li>';
                    pageHtml += '<li class="disabled"><a href="#">&laquo;</a></li>';
                }
                else {
                    pageHtml += '<li ><a href="#" class="pageBtn" index="1">|&laquo;</a></li>';
                    pageHtml += '<li ><a href="#" class="prevBtn" index="">&laquo;</a></li>';
                }
                if (totalPage <= showPage) {
                    for (var i = 1; i < Number(totalPage) + 1; i++) {
                        if (currentPage == i) {
                            pageHtml += '<li class="active"><a href="#" >' + i + '</a></li>';
                        }
                        else {
                            pageHtml += '<li><a href="#" class="pageBtn" index="' + i + '">' + i + '</a></li>';
                        }
                    }
                }
                else {
                    if (currentPage <= (showPage - 1) / 2) {
                        for (var i = 1; i <= showPage; i++) {
                            if (currentPage == i) {
                                pageHtml += '<li class="active"><a href="#" >' + i + '</a></li>';
                            }
                            else {
                                pageHtml += '<li><a href="#" class="pageBtn" index="' + i + '">' + i + '</a></li>';
                            }
                        }
                    }
                    else if (totalPage - currentPage < (showPage - 1) / 2) {
                        for (var i = Number(totalPage) - showPage; i <= totalPage; i++) {
                            if (currentPage == i) {
                                pageHtml += '<li class="active"><a href="#" >' + i + '</a></li>';
                            }
                            else {
                                pageHtml += '<li><a href="#" class="pageBtn" index="' + i + '">' + i + '</a></li>';
                            }
                        }
                    }
                    else {
                        for (var i = Number(currentPage) - (showPage - 1) / 2; i <= Number(currentPage) + (showPage - 1) / 2; i++) {
                            if (currentPage == i) {
                                pageHtml += '<li class="active"><a href="#" >' + i + '</a></li>';
                            }
                            else {
                                pageHtml += '<li><a href="#" class="pageBtn" index="' + i + '">' + i + '</a></li>';
                            }
                        }
                    }


                }

                if (currentPage == totalPage) {
                    pageHtml += '<li class="disabled"><a href="#">&raquo;</a></li>';
                    pageHtml += '<li class="disabled"><a href="#">&raquo;|</a></li>';
                }
                else {
                    pageHtml += '<li class="nextBtn" index=""><a href="#">&raquo;</a></li>';
                    pageHtml += '<li class="pageBtn" index="' + totalPage + '"><a href="#">&raquo;|</a></li>';
                }
                /* pageHtml+='<li><input type="text" id="jumpPageText" class="paging-inpbox form-control"></li>\n' +
                     '<li><button type="button" id="jumpBtn" class="paging-btnbox btn btn-primary">跳转</button></li>\n' +
                     '<li><span class="number-of-pages">共'+totalPage+'页</span></li>';*/

                $('.pagination').html(pageHtml);
            }

        }
    });
}
/**
 * 车间select

 * @param currentPage
 * @param pageSize
 */
function  selectWorkshop (currentPage,pageSize,workshop) {
    var bodyParam={'page':currentPage,'size':pageSize};
    var httpR = new createHttpR(url+'listWorkshop','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            var html='<option value="">全部</option>';
            for(var o in data){
                html+='<option value="'+data[o].id+'">'+data[o].name+'</option>\n';
            }
            $('#searchWorkshop').html(html);
            $('#searchWorkshop').val(workshop);
        }
    });
}
/**
 * 物品select
 * @param currentPage
 * @param pageSize
 */
function  selectMaterial (currentPage,pageSize,material) {
    var bodyParam={'page':currentPage,'size':pageSize};
    var httpR = new createHttpR(url+'listMaterial','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            var html='<option value="">全部</option>';
            for(var o in data){
                html+='<option value="'+data[o].id+'">'+data[o].name+'</option>\n';
            }
            $('#searchMaterial').html(html);
            $('#searchMaterial').val(material);
        }
    });
}

/**
 * 库别select
 * @param currentPage
 * @param pageSize
 */
function  selectStocktype (currentPage,pageSize,stocktype) {
    var bodyParam={'page':currentPage,'size':pageSize};
    var httpR = new createHttpR(url+'listStocktype','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            var html='<option value="">全部</option>';
            for(var o in data){
                html+='<option value="'+data[o].id+'">'+data[o].name+'</option>\n';
            }
            $('#searchStocktype').html(html);
            $('#searchStocktype').val(stocktype);
        }
    });
}