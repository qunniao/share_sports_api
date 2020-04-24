var mySearcher = function () {
    /*各种设置*/

    var qhistory = {
        pageid: null,
        url: null,
        opt: null,
        condList: null
    };
    var queryfiter = [];
    var pageConf = {
        pageNum: 1,//当前页
        pageSize: 15,//容量
        totalPages: null,//总页数
        totalElements: null//总条数
    };

    var initPage = function (pageid, url, opt, callback) {
        opt = $.extend(opt, pageConf);
        com.post(url, opt, function (res) {
            if (res.data.content.length == 0) {
                emptyData();
                callback(res.data.content)
            } else {
                pageConf.totalPages = res.data.totalPages;
                pageConf.totalElements = res.data.totalElements;
                pages(pageid, url, opt, callback);
                callback(res.data.content)
            }

        }, function () {
        }, false);

    };

    var emptyData = function () {
        var $resultList = $('#' + qhistory.pageid).html('');
        var $zone = $('<div></div>').addClass('dropzone dz-clickable center').css("cursor", "default").css('margin', '20px').css('border', '0px').appendTo($resultList);
        var $span = $('<h1></h1>').text('无可用数据提供').appendTo($zone);
    };

    var pages = function (pageid, url, opt, callback) {
        var data;
        $("#" + pageid).createPage({
            pageNum: pageConf.totalPages,//总页码
            current: pageConf.pageNum,//当前页
            shownum: pageConf.pageSize,//每页显示个数
            backfun: function (e) {
                pageConf.pageNum = e.current;
                initPage(pageid, url, opt, function (res) {
                    callback(res)
                })
            }
        });
    };

    var filter = function (lists,callback) {
        var condList = $("#condList");
        var checkRepeat = [];
        var checkParamName = [];
        var checkType = true;
        $.each(lists, function (i, v) {
            $.each(v, function (j, k) {
                if (checkRepeat.indexOf(k.id) != -1) {
                    console.log(k.id);
                    console.log("id不能重名!!");
                    alert("id不能重名!!");
                    checkType = false;
                    return false;
                }
                if (checkParamName.indexOf(k.paramName) != -1) {
                    console.log(k.paramName);
                    console.log("ParamName不能重名!!");
                    alert("ParamName不能重名!!");
                    checkType = false;
                    return false;
                }
                checkRepeat.push(k.id)
                checkParamName.push(k.paramName)
            });
        });

        if (checkType == false) {
            return
        }


        $.each(lists, function (i, v) {
            var _nbsp = $('<sapn>&nbsp;&nbsp;</sapn>');
            $.each(v, function (j, k) {
                if (j == "text") {
                    var _span = $('<sapn></sapn>').addClass("inline");
                    var _label = $('<label></label>').html(k.name + ":&nbsp;");
                    var _input = $('<input type="text"></input>').attr("paramName", k.paramName).attr("id", k.id)
                    _span.append(_label).append(_input).append(_nbsp);
                    condList.append(_span);
                    queryfiter.push({
                        "type": "text",
                        "id": k.id
                    });
                }

                if (j == "select") {
                    var _span = $('<sapn></sapn>').addClass("inline");
                    var _label = $('<label></label>').html(k.name + ":&nbsp;");
                    var _select = $('<select data-toggle="popover" data-trigger="manual"></select>').attr("id", k.id).attr("paramName", k.paramName);
                    _select.bindData(k.ds, k.keyName, k.valueName, k.flag);
                    _span.append(_label).append(_select).append(_nbsp);
                    condList.append(_span);
                    queryfiter.push({
                        "type": "select",
                        "id": k.id
                    });
                }
            });
        });


        var _sysQbtn = $('<button></button>').addClass("btn btn-xs btn-info btn-search").html("查询");
        var _sysCbtn = $('<button style="margin: 0px 3px;"></button>').addClass("btn btn-xs btn-danger btn-clean").html("重置");
        _sysQbtn.click(function () {
            querypage(callback)
        });
        _sysCbtn.click(function () {
            clearFilter()
        });
        condList.append(_sysQbtn).append(_sysCbtn);
    };

    var querypage = function (callback) {
        // console.log("查询");
        var option = {};
        $.each(queryfiter, function (i, v) {
            var paramName = $("#" + v.id).attr("paramname");
            var value = $("#" + v.id).val();
            if (paramName != null && paramName != "undefined" && paramName != "" && value != null && value != "undefined" && value != "") {
                option[paramName] = value;
            }
        });
        option["pageNum"] = 1;
        option["pageSize"] = 15;
        initPage(qhistory.pageid, qhistory.url, option, callback)
    };

    var clearFilter = function () {
        $.each(queryfiter, function (i, v) {
            if (v.type == "text") {
                $("#" + v.id).val(null);
            }
            if (v.type == "select") {
                $("#" + v.id + " option:first").prop("selected", 'selected');
            }
        })
    };

    return {
        init: function (lists, pageid, condList, url, opt, callback) {
            qhistory.url = url;
            qhistory.pageid = pageid;
            qhistory.opt = opt;
            qhistory.condList = condList;
            filter(lists,callback);
            initPage(pageid, url, opt, callback);
        }
    }

};