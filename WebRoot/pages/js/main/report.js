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
        alert(material);
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