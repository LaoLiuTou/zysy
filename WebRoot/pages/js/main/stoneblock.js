$(document).ready(function(){

    //选取的stoneblock
    var stoneblockList;
    var stoneblockIndex;
    var currentStoneblock;
});


////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////荒料管理////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
/**
 * 添加荒料
 */
function addStoneblock(bodyParam){

    var httpR = new createHttpR(url+'addStoneblock','post','text',bodyParam,'callBack');
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
 * 修改荒料
 * @param id
 */
function updateStoneblock(id){
    var userinfo = JSON.parse(sessionStorage.getItem('userinfo'));
    var bodyParam={'id':id,'name':$('#update_name').val(),'leader':$('#update_leader').val(),
        'comment':$('#update_comment').val(),'state':$('#update_state').val(),'c_id':userinfo['id']};
    var httpR = new createHttpR(url+'updateStoneblock','post','text',bodyParam,'callBack');
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
 * 删除荒料
 * @param id
 */
function deleteStoneblock(id){
    var bodyParam={'id':id};
    var httpR = new createHttpR(url+'deleteStoneblock','post','text',bodyParam,'callBack');
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
 * 查询荒料
 * @param stoneblockname
 * @param currentPage
 * @param pageSize
 */
function  queryStoneblock (stoneblockname,currentPage,pageSize) {

    //分页显示的页码数  必须为奇数
    var showPage=7;
    if(stoneblockname==null||stoneblockname==''){
        var bodyParam={'page':currentPage,'size':pageSize};
    }
    else{
        var bodyParam={'page':currentPage,'size':pageSize,'name':'%'+stoneblockname+'%'};
    }

    var httpR = new createHttpR(url+'listStoneblock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            stoneblockList=msg['data'];
            var html='';
            for(var o in data){
                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+data[o].s_dt+'</td>\n' +
                    '<td>'+data[o].code+'</td>\n' +
                    '<td>'+data[o].source+'</td>\n' +
                    '<td>'+data[o].place+'</td>\n' +
                    '<td>'+data[o].number+'</td>\n'  +
                    '<td>'+data[o].length+'</td>\n'  +
                    '<td>'+data[o].width+'</td>\n'  +
                    '<td>'+data[o].height+'</td>\n'  +
                    '<td>'+data[o].cube+'</td>\n'  +
                    '<td>'+data[o].price+'</td>\n'  +
                    '<td>'+data[o].sum+'</td>\n'  +
                    '<td>'+data[o].platenumber+'</td>\n';
                if(data[o].rz_dt==''){
                    html+='<td>'+'否'+'</td>\n';
                }
                else{
                    html+='<td>'+data[o].rz_dt+'</td>\n';
                }
                if(data[o].yf_dt==''){
                    html+='<td>'+'否'+'</td>\n';
                }
                else{
                    html+='<td>'+data[o].yf_dt+'</td>\n';
                }
                html+= '</tr>';

            }
            $('#stoneblockTbody').html(html);
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
 * 根据编号查询荒料
 * @param code
 * @param currentPage
 * @param pageSize
 */
function  queryStoneblockByCode (code) {

    var bodyParam={'page':1,'size':1,'code':code};

    var httpR = new createHttpR(url+'listStoneblock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            stoneblockList=msg['data'];
            for(var o in data){
                $('#sb_spec').val(data[o].length+'*'+data[o].width+'*'+data[o].height);
                $('#sb_cube').val(data[o].cube);
            }
        }
    });
}

/**
 * 修改库存查询荒料
 * @param id
 */
function  queryStoneblockById (id) {

    var bodyParam={'id':id};

    var httpR = new createHttpR(url+'selectStoneblock','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);

        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var  html='<div class="form-group col-md-6">\n' +
                '    <label>单据编号</label>\n' +
                '    <input type="text" class="form-control" id="update_code" name="code" value="'+msg['code']+'" placeholder="请输入单据编号">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>来源</label>\n' +
                '    <input type="text" class="form-control" id="update_source" name="source" value="'+msg['source']+'" placeholder="请输入来源">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>单据日期</label>\n' +
                '    <input type="text" class="form-control form-control-inline input-medium default-date-picker" id="update_s_dt" name="s_dt" value="'+msg['s_dt']+'" placeholder="请输入单据日期">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>入库地点</label>\n' +
                '    <input type="text" class="form-control" id="update_place" name="place" value="'+msg['place']+'" placeholder="请输入入库地点">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>厂内料号</label>\n' +
                '    <input type="text" class="form-control" id="update_number" name="number" value="'+msg['number']+'" placeholder="请输入厂内料号">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>长</label>\n' +
                '    <input type="text" class="form-control" id="update_length" name="length" value="'+msg['length']+'" placeholder="请输入长度">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>宽</label>\n' +
                '    <input type="text" class="form-control" id="update_width" name="width" value="'+msg['width']+'" placeholder="请输入长度">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>高</label>\n' +
                '    <input type="text" class="form-control" id="update_height" name="height" value="'+msg['height']+'" placeholder="请输入长度">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>立方数</label>\n' +
                '    <input type="text" class="form-control" id="update_cube" name="cube" value="'+msg['cube']+'" placeholder="请输入立方数">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>单价</label>\n' +
                '    <input type="text" class="form-control" id="update_price" name="price" value="'+msg['price']+'" placeholder="请输入单价">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>总块数</label>\n' +
                '    <input type="text" class="form-control" id="update_blocknumber" name="blocknumber" value="'+msg['blocknumber']+'" placeholder="请输入总块数">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>金额</label>\n' +
                '    <input type="text" class="form-control" id="update_sum" name="sum" value="'+msg['sum']+'" placeholder="请输入金额">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>车牌号</label>\n' +
                '    <input type="text" class="form-control" id="update_platenumber" name="platenumber" value="'+msg['platenumber']+'" placeholder="请输入车牌号">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>账差</label>\n' +
                '    <input type="text" class="form-control" id="update_accountdiff" name="accountdiff" value="'+msg['accountdiff']+'" placeholder="请输入账差">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>审核人</label>\n' +
                '    <input type="text" class="form-control" id="update_auditor" name="auditor" value="'+msg['auditor']+'" placeholder="请输入审核人">\n' +
                '</div>\n' +
                '<div class="form-group col-md-6">\n' +
                '    <label>录入人</label>\n' +
                '    <input type="text" class="form-control" id="update_editor" name="editor" value="'+msg['editor']+'" placeholder="请输入录入人">\n' +
                '</div> \n' ;
            $('#updateForms').html(html);
        }
    });
}