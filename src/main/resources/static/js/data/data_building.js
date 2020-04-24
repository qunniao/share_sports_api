$(function () {
    console.log("data_building!!!!!!!!!!!!!!!!");
    var data_building={

    };
    var displayFunc = (function () {

        var renderList = function (tableId, listData) {
            var $tableWrap = $('#' + tableId);
            var $ul = $('<ul></ul>').addClass('item-list fl-table-list ui-sortable');

            if (listData && listData.length) {
                $.each(listData, function (i, v) {
                    var $liLine = creatLine(v);

                    $liLine.appendTo($ul);
                });

                $ul.appendTo($tableWrap);
            }
        };

        var creatLine = function (v) {
            var propertyArr = [
                ["名称:", v.name, "green"],
                ["状态:", v.status, "green"],
                ["类型:", v.type, "green"],
                ["图片路径:", v.url, "green"],
                ["确认图标路径:", v.urly, "green"]
            ];

            var $liLine = $('<li></li>').addClass('item-orange clearfix');
            var $ulProperty = $('<ul></ul>').addClass('list-unstyled fl-inline-list clearfix');

            var $btnEdit = $('<a></a>').addClass('btn btn-minier btn-yellow pull-right action-buttons').html('编辑');
            $.each(propertyArr, function (index, value) {
                var $liProperty = $('<li></li>');
                $('<strong></strong>').html(value[0]).appendTo($liProperty);
                $('<b></b>').html(value[1]).addClass(value[2]).appendTo($liProperty);
                $liProperty.appendTo($ulProperty);
                if (index == 4) {
                    var span = $('<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>');
                    span.appendTo($liProperty)
                    $btnEdit.appendTo($liProperty)
                }
            });

            $liLine.append($ulProperty);

            $btnEdit.click(function () {
                editLine(v);
            });

            return $liLine;
        };

        return {
            init: function (tableId, listData) {
                $('#' + tableId).html(null);
                renderList(tableId, listData);
            },
            drawLine: function (v) {
                return creatLine(v);
            }
        };
    })();

    com.post("/datab/queryBuildingAll", null, function (res) {
        displayFunc.init('resultList', res.data);
    }, null, false);
    console.log();

    // var searcher = new Searcher();
    // searcher.init([], "condList", "/gymshop/pageGymShop", function (res) {
    //     console.log(res)
    //     // displayFunc.init('resultList', data.content);
    // }, "pagenation");

    function editLine(v) {
        $("#name").val(v.name);
        $("#status").val(v.status);
        $("#type").val(v.type);
        $("#editModal").modal("show");
    }

    $("#saveBtn").on("click", function(){
        var options = {
            "uid": userInfo.userInfoItems.userId.val(),
            "userPhone":  userInfo.userInfoItems.userPhone.val(),
            "userName": userInfo.userInfoItems.userName.val(),
            "userSex": userInfo.userInfoItems.userSex.val(),
            "userPassword":  userInfo.userInfoItems.userPassword.val(),
        };
        com.post("/personal/updateUser", options, successCallback);
        userInfo.modal.modal("hide");
    });

    // if (unitPropertiess == 1) {
    //     $("#unitProperties").find("option[value='1']").attr("selected", true);
    // } else if (unitPropertiess == 2) {
    //     $("#unitProperties").find("option[value='2']").attr("selected", true);
    // } else if (unitPropertiess == 3) {
    //     $("#unitProperties").find("option[value='3']").attr("selected", true);
    // }

});