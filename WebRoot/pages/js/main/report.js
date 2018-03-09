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
function  reportListStock (bodyParam) {


    var httpR = new createHttpR(url+'listStock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];

        if(status=='0'){
            var data=msg['data'];
            var html='';
            var sum_1=0,sum_2=0,sum_3=0,sum_4=0;
            for(var o in data){
                var msizes=data[o].msize.split('*');
                sum_1=Number(sum_1)+Number(data[o].number);
                sum_2=(Number(sum_2)+Number(msizes[0]*msizes[1]/1000000)).toFixed(2);
                sum_3=(Number(sum_3)+Number(data[o].sprice)).toFixed(2);
                sum_4=(Number(sum_4)+Number(data[o].ssum)).toFixed(2);
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+(Number(o)+1)+'</td>\n' +
                    '<td>'+data[o].m_dt+'</td>\n' +
                    '<td>'+data[o].code+'</td>\n' +
                    '<td>'+data[o].process+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].number+'</td>\n' +
                    '<td>'+(msizes[0]*msizes[1]/1000000).toFixed(2)+'</td>\n' +
                    '<td>'+data[o].comment+'</td>\n' +
                    '<td>'+data[o].worker+'</td>\n' +
                    '<td>'+data[o].auditor+'</td>\n' +
                    '<td>'+data[o].sprice+'</td>\n' +
                    '<td>'+data[o].ssum+'</td>\n' +
                    '</tr>' ;

            }
            html+='<tr  class="gradeX">\n' +
                '<td>合计</td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td>'+sum_1+'</td>\n' +
                '<td>'+sum_2+'</td>\n' +

                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td>'+sum_3+'</td>\n' +
                '<td>'+sum_4+'</td>\n' +
                '</tr>';
            $('#reportTbody').html(html);


        }
    });
}
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
function  reportWorkshopInOut (c_dt,outtype) {
    var bodyParam={'pid':0};
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
                    '<td>'+data[o].outtype+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_in).toFixed(2)+'</td>\n' +
                    '<td>'+Math.abs(data[o].sum_out).toFixed(2)+'</td>\n' +
                    '<td>'+(Math.abs(data[o].sum_in)-Math.abs(data[o].sum_out)).toFixed(2)+'</td>\n</tr>' ;
            }
            $('#reportTbody').html(html);
            selectDistinctOuttype(outtype);
        }
    });
}
/**
 * 大锯产量统计
 * @param m_dt
 * @param currentPage
 * @param pageSize
 */
function  reportMatteboard (m_dt) {

    //分页显示的页码数  必须为奇数
    var showPage=7;
    var bodyParam={};
    if(m_dt!=''){
        bodyParam['m_dt']=m_dt+' 00:00:00';
    }

    var httpR = new createHttpR(url+'reportMatteboard','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            var html='';
            var sum_1=0,sum_2=0,sum_3=0,sum_4=0,sum_5=0,sum_6=0;
            for(var o in data){
                sum_1=Number(sum_1)+Number(data[o].sum_blocknumber);
                sum_2=Number(sum_2)+Number(data[o].sum_square);
                sum_3=Number(sum_3)+Number(data[o].sum_belowgradeblock);
                sum_4=Number(sum_4)+Number(data[o].sum_belowgradesquare);
                sum_5=Number(sum_5)+Number(data[o].sum_gradeblock);
                sum_6=Number(sum_6)+Number(data[o].sum_gradesquare);
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].m_dt+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].sum_blocknumber+'</td>\n' +
                    '<td>'+data[o].sum_square+'</td>\n' +
                    '<td>'+data[o].sum_belowgradeblock+'</td>\n' +
                    '<td>'+data[o].sum_belowgradesquare+'</td>\n' +
                    '<td>'+data[o].sum_gradeblock+'</td>\n' +
                    '<td>'+data[o].sum_gradesquare+'</td>\n' +
                    '</tr>';
            }
            html+='<tr  class="gradeX">\n' +
                '<td>合计</td>\n' +
                '<td></td>\n' +
                '<td>'+sum_1+'</td>\n' +
                '<td>'+sum_2+'</td>\n' +
                '<td>'+sum_3+'</td>\n' +
                '<td>'+sum_4+'</td>\n' +
                '<td>'+sum_5+'</td>\n' +
                '<td>'+sum_6+'</td>\n' +
                '</tr>';

            $('#reportTbody').html(html);


        }
    });
}


/**
 * 日报
 * @param c_dt
 * @param workshop
 */
function  reportYield (c_dt,outtype) {
    var bodyParam={'pid':0,'state':1,'damage':'否'};
    if(c_dt!=''){
        bodyParam['c_dtFrom']=c_dt+' 00:00:00';
        bodyParam['c_dtTo']=c_dt+' 23:59:59';
    }
    if(outtype!=''){
        outtype['outtype']=outtype;
    }
    var httpR = new createHttpR(url+'reportYield','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];

        if(status=='0'){
            var html='';
            for(var o in data){

                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].outtype+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+(data[o].sum_in*data[o].msize.split('*')[0]*data[o].msize.split('*')[1]/1000000).toFixed(2)+'</td>\n' +
                    '<td>'+(data[o].sum_out*data[o].msize.split('*')[0]*data[o].msize.split('*')[1]/1000000).toFixed(2)+'</td>\n' +
                    '<td>'+data[o].sum_number+'</td>\n' +
                    '</tr>' ;
            }

            $('#reportTbody').html(html);
            selectDistinctOuttype(outtype);
            //selectWorkshop(1,100,workshop);
        }
    });
}
/**
 * 破损
 * @param c_dt
 * @param workshop
 */
function  reportDamage (c_dt,workshop) {
    var bodyParam={};
    if(c_dt!=''){
        bodyParam['c_dtFrom']=c_dt+' 00:00:00';
        bodyParam['c_dtTo']=c_dt+' 23:59:59';
    }
    if(workshop!=''){
        bodyParam['workshop']=workshop;
    }
    bodyParam['damage']='是';
    var httpR = new createHttpR(url+'reportDamage','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];
        if(status=='0'){
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].unit+'</td>\n' +
                    '<td>'+data[o].sum_out+'</td></tr>' ;
            }

            $('#reportTbody').html(html);
            selectWorkshop(1,100,workshop);
        }
    });
}
/**
 * 车间select

 * @param currentPage
 * @param pageSize
 */
function  selectWorkshop (currentPage,pageSize,workshop) {
    var bodyParam={'page':currentPage,'size':pageSize,'state':'0'};
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
    var bodyParam={'page':currentPage,'size':pageSize,'state':'0'};
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
    var bodyParam={'page':currentPage,'size':pageSize,'state':'0'};
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
}/**
 * 唯一类型
 */
function  selectDistinctOuttype (outtype) {
    var bodyParam={};
    var httpR = new createHttpR(url+'distinctType','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var data = obj['msg'];

        if(status=='0'){
            var html='<option value="">全部</option>';
            for(var o in data){
                if(data[o].outtype!=''){
                    html+='<option value="'+data[o].outtype+'">'+data[o].outtype+'</option>\n';
                }
            }
            $('#distinctOuttype').html(html);
            $('#distinctOuttype').val(outtype);
        }
    });
}