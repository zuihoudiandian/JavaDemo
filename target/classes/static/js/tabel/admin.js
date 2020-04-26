$(function () {
    $('#table').bootstrapTable({
        idField: "id",
        pagination: true,
        showRefresh: true,
        search: true,
        striped: true,
        cache: false,
        sidePagination:'server',
        pageList: [10, 15, 20],
        clickToSelect: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,
        queryParams: function queryParams(params) {
            var resultParams = {
                "pageSize": params.limit,
                "pageNumber": (params.offset / params.limit) + 1,
            };
            return resultParams;
        },
        url: '/admin/getuserlist',        // 表格数据来源
        method: 'get',
        columns: [{
            field: 'id',
            title: 'id'
        }, {
            field: 'accountId',
            title: '账号'
        }, {
            field: 'gmtCreate',
            title: '创建时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }

        },{
            field: 'gmtModified',
            title: '修改时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }

        },{
            field: 'type',
            title: '类型'
        }, {
            title : '操作',
            field : 'id',
            formatter : option
            }],
        responseHandler: function (res) {  //后台返回的结果
            if (res.code == "200") {
                var data = {
                    total: res.data.TOTAL,
                    rows: res.data.ROWS,
                };
                return data;
            }
        }
    });
    // $('#table').on('click-row.bs.table', function (e, row, element)
    // {
    //     //$(element).css({"color":"blue","font-size":"16px;"});
    //     console.log(row);
    //     questionByuser(row.id);
    // });


});

function questionByuser(id) {
    $("#table").bootstrapTable('destroy');
    $('#table').bootstrapTable({
        idField: "id",
        pagination: true,
        showRefresh: true,
        search: true,
        striped: true,
        cache: false,
        sidePagination:'server',
        pageList: [10, 15, 20],
        clickToSelect: true,
        pageNumber: 1,                       //初始化加载第一页，默认第一页
        pageSize: 10,
        queryParams: function queryParams(params) {
            var resultParams = {
                "pageSize": params.limit,
                "pageNumber": (params.offset / params.limit) + 1,
                "id":id
            };
            return resultParams;
        },
        url: '/admin/getquestionbyluser',        // 表格数据来源
        method: 'get',
        columns: [{
            field: 'id',
            title: 'id'
        }, {
            field: 'title',
            title: '标题',
        },  {
            field: 'description',
            title: '内容'
        },
            {
                field: 'tag',
                title: '标签'
            },
            {
                field: 'viewCount',
                title: '点击数'
            },
            {
            field: 'gmtCreate',
            title: '创建时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }

        },{
            field: 'gmtModified',
            title: '修改时间',
            formatter: function (value, row, index) {
                return changeDateFormat(value)
            }
        },{
            title : '操作',
            field : 'id',
                align : 'center',
            formatter : delquestion
        }],
        responseHandler: function (res) {  //后台返回的结果
            if (res.code == "200") {
                var data = {
                    total: res.data.TOTAL,
                    rows: res.data.ROWS,
                };
                return data;
            }
        }
    });
}

function option(value, row, index) {
    var str=[];
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='questionByuser(\"" + row.id+ "\")'>查看</button>");
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='deleteById(\"" + value + "\",\"" + row.accountId + "\")'>删除</button>");
    return str;
}


function delquestion(value, row, index) {
    var str=[];
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='questioninfo(\"" + row.id+ "\")'>查看</button>");
    str.push("<button  class='btn btn-outline-secondary btn-sm mr-1' onclick='delquesionById(\"" + row.id+ "\")'>删除</button>");
    return str;
}

function questioninfo(id) {
    window.location.href = "/question/"+id;
}
// 删除用户
function deleteById(id,accountId) {
    $.ajax({
        url: "/admin/deleteuser",
        type: "POST",
        data: {"id": id},
        success: function (data) {
            if (data.code == 200) {
                toastr.success(accountId+data.message);
                refreshTable();
            } else {
                toastr.error(data.message);
            }
        },
        error: function (data) {
            toastr.error("请求失败");
        }
    });
}

function delquesionById(id) {
    $.ajax({
        url: "/admin/delquestion",
        type: "POST",
        data: {"id": id},
        success: function (data) {
            if (data.code == 200) {
                toastr.success(data.message);
                refreshTable();
            } else {
                toastr.error(data.message);
            }
        },
        error: function (data) {
            toastr.error("请求失败");
        }
    });
}


function refreshTable() {
    $("#table").bootstrapTable('refresh');
}


function changeDateFormat(cellval) {
    if (cellval != null) {
        return  new Date(+new Date(cellval) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    }
}