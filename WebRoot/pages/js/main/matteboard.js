$(document).ready(function(){

    //选取的matteboard
    var matteboardList;
    var matteboardIndex;
    var currentMatteboard;
});


////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////哑光板管理////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
/**
 * 添加哑光板
 */
function addMatteboard(bodyParam){

    var httpR = new createHttpR(url+'addMatteboard','post','text',bodyParam,'callBack');
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
 * 添加多条亚光板
 */
function addmulMatteboard(bodyParam){

    var httpR = new createJSONHttpR(url+'addMulMatteboard','post','text',JSON.stringify(bodyParam),'callBack');
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
 * 修改哑光板
 * @param id
 */
function updateMatteboard(param){

    var httpR = new createHttpR(url+'updateMatteboard','post','text',param,'callBack');
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
 * 删除哑光板
 * @param id
 */
function deleteMatteboard(id){
    var bodyParam={'id':id};
    var httpR = new createHttpR(url+'deleteMatteboard','post','text',bodyParam,'callBack');
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
 * 查询哑光板
 * @param Matteboardname
 * @param currentPage
 * @param pageSize
 */
function  queryMatteboard (bodyParam) {

    //分页显示的页码数  必须为奇数
    var showPage=7;
    //var bodyParam={'page':currentPage,'size':pageSize};
    currentPage=bodyParam['page'];
    pageSize=bodyParam['size'];
    var httpR = new createHttpR(url+'listMatteboard','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);
        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
            var data=msg['data'];
            matteboardList=msg['data'];
            var html='';
            var sum_1=0,sum_2=0,sum_3=0,sum_4=0,sum_5=0,sum_6=0,sum_7=0,
                sum_8=0,sum_9=0,sum_10=0,sum_11=0,sum_12=0,sum_13=0;
            for(var o in data){


                var sb_specs=data[o].sb_spec.split('*');
                var sb_cube=data[o].sb_cube;
                if(sb_specs[2]>1.25&&(data[o].layer=='上'||data[o].layer=='下')){
                    sb_cube=(sb_cube/2).toFixed(3);
                }

                sum_1=(Number(sum_1)+Number(sb_specs[0])).toFixed(2);
                sum_2=(Number(sum_2)+Number(sb_specs[1])).toFixed(2);
                sum_3=(Number(sum_3)+Number(sb_specs[2])).toFixed(2);
                sum_4=(Number(sum_4)+Number(data[o].sb_cube)).toFixed(3);
                sum_5=(Number(sum_5)+Number(sb_cube)).toFixed(3);
                sum_6=Number(sum_6)+Number(data[o].blocknumber);
                sum_7=(Number(sum_7)+Number(data[o].square)).toFixed(2);
                sum_8=Number(sum_8)+Number(data[o].blocknumber-data[o].belowgradeblock);
                sum_9=(Number(sum_9)+Number(data[o].square-data[o].belowgradesquare)).toFixed(2);
                sum_10=Number(sum_10)+Number(data[o].belowgradeblock);
                sum_11=(Number(sum_11)+Number(data[o].belowgradesquare)).toFixed(2);
                sum_12=(Number(sum_12)+Number(data[o].price)).toFixed(2);
                sum_13=(Number(sum_13)+Number(data[o].sum)).toFixed(2);

                html+='<tr index='+o+' class="gradeX">\n' +
                    '<td>'+(Number(o)+1)+'</td>\n' +
                    '<td>'+data[o].sb_code+'</td>\n' +
                    '<td>'+sb_specs[0]+'</td>\n' +
                    '<td>'+sb_specs[1]+'</td>\n' +
                    '<td>'+sb_specs[2]+'</td>\n' +
                    '<td>'+data[o].sb_cube+'</td>\n' +
                    '<td>'+data[o].layer+'</td>\n' +
                    '<td>'+sb_cube+'</td>\n' +
                    '<td>'+data[o].m_dt+'</td>\n' +
                    '<td>'+data[o].code+'</td>\n' +
                    '<td>'+data[o].msize+'</td>\n' +
                    '<td>'+data[o].height+'</td>\n' +
                    '<td>'+data[o].blocknumber+'</td>\n' +
                    '<td>'+Number(data[o].square).toFixed(2)+'</td>\n' +
                    '<td>'+Number(data[o].blocknumber-data[o].belowgradeblock)+'</td>\n' +
                    '<td>'+Number(data[o].square-data[o].belowgradesquare).toFixed(2)+'</td>\n' +
                    '<td>'+data[o].belowgradeblock+'</td>\n' +
                    '<td>'+Number(data[o].belowgradesquare).toFixed(2)+'</td>\n' +
                    '<td>'+data[o].price+'</td>\n' +
                    '<td>'+data[o].sum+'</td>\n' +
                 '</tr>';
                //alert(html);
            }
            html+='<tr  class="gradeX">\n' +
                '<td>合计</td>\n' +
                '<td></td>\n' +
                '<td>'+sum_1+'</td>\n' +
                '<td>'+sum_2+'</td>\n' +
                '<td>'+sum_3+'</td>\n' +
                '<td>'+sum_4+'</td>\n' +
                '<td></td>\n' +
                '<td>'+sum_5+'</td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td></td>\n' +
                '<td>'+sum_6+'</td>\n' +
                '<td>'+sum_7+'</td>\n' +
                '<td>'+sum_8+'</td>\n' +
                '<td>'+sum_9+'</td>\n' +
                '<td>'+sum_10+'</td>\n' +
                '<td>'+sum_11+'</td>\n' +
                '<td>'+sum_12+'</td>\n' +
                '<td>'+sum_13+'</td>\n' +
                '</tr>';
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
 * 修改库存查询压光板
 * @param id
 */
function  queryMatteboardById (id) {

    var bodyParam={'id':id};

    var httpR = new createHttpR(url+'selectMatteboard','post','text',bodyParam,'callBack');
    httpR.HttpRequest(function(response){
        var obj = JSON.parse(response);

        var status = obj['status'];
        var msg = obj['msg'];
        if(status=='0'){
             var html='<div class="form-group col-md-6">\n' +
                 '    <label>荒料编号</label>\n' +
                 '    <input type="text" class="form-control" id="update_sb_code" name="sb_code" value="'+msg['sb_code']+'" placeholder="请输入荒料编号">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>荒料规格</label>\n' +
                 '    <input type="text" class="form-control" id="update_sb_spec" name="sb_spec" value="'+msg['sb_spec']+'" placeholder="格式为 长*宽*高">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>立方数</label>\n' +
                 '    <input type="text" class="form-control" id="update_sb_cube" name="sb_cube" value="'+msg['sb_cube']+'" placeholder="请输入立方数">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>单据编号</label>\n' +
                 '    <input type="text" class="form-control" id="update_code" name="code" value="'+msg['code']+'" placeholder="请输入单据编号">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>验收人</label>\n' +
                 '    <input type="text" class="form-control" id="update_auditor" name="auditor" value="'+msg['auditor']+'" placeholder="请输入验收人">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>单据日期</label>\n' +
                 '    <input type="text" class="form-control form-control-inline input-medium default-date-picker" id="update_m_dt" name="m_dt" value="'+msg['m_dt']+'" placeholder="请输入单据日期">\n' +
                 '</div>\n' +
                 /*'<div class="form-group col-md-6">\n' +
                 '    <label>工组</label>\n' +
                 '    <input type="text" class="form-control" id="update_workgroup" name="workgroup" value="'+msg['workgroup']+'" placeholder="请输入工组">\n' +
                 '</div>\n' +*/

                 '<div class="form-group col-md-6">\n' +
                 '    <label>板材尺寸</label>\n' +
                 '    <input type="text" class="form-control" id="update_msize" name="msize" value="'+msg['msize']+'" placeholder="格式为 长*宽">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>料层</label>\n' +
                 '    <select class="form-control m-bot15" id="update_layer" name="layer" >\n' +
                 '        <option value="上">上</option>\n' +
                 '        <option value="中">中</option>\n' +
                 '        <option value="下">下</option>\n' +
                 '    </select>\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>厚度</label>\n' +
                 '    <input type="text" class="form-control" id="update_height" name="height" onkeyup="this.value=this.value.replace(/[^0-9-]+/,\'\');" value="'+msg['height']+'" placeholder="请输入板材厚度">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>总块数</label>\n' +
                 '    <input type="text" class="form-control" id="update_blocknumber" onkeyup="this.value=this.value.replace(/[^0-9-]+/,\'\');" name="blocknumber" value="'+msg['blocknumber']+'" placeholder="请输入总块数">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>平方数</label>\n' +
                 '    <input type="text" class="form-control" id="update_square" name="square" value="'+msg['square']+'" placeholder="请输入平方数">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>不合格块数</label>\n' +
                 '    <input type="text" class="form-control" id="update_belowgradeblock" onkeyup="this.value=this.value.replace(/[^0-9-]+/,\'\');" value="'+msg['belowgradeblock']+'" name="belowgradeblock" placeholder="请输入不合格块数">\n' +
                 '</div>\n' +
                 '<div class="form-group col-md-6">\n' +
                 '    <label>不合格平方数</label>\n' +
                 '    <input type="text" class="form-control" id="update_belowgradesquare" value="'+msg['belowgradesquare']+'" name="belowgradesquare" placeholder="请输入不合格平方数">\n' +
                 '</div>\n'   ;

            $('#updateForms').html(html);
            $('#update_layer').val(msg['layer']);
            $('.default-date-picker').datepicker({
                format: 'yyyy-mm-dd'
            });
        }
    });
}