$(function () {
    console.log("in scrollimg !!!!!!!")

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
                ["图标路径:", v.url, "green"],
                ["跳转路径:", v.toUrl, "green"],
                ["创建时间:", v.createTime, "green"]
            ];

            var $liLine = $('<li></li>').addClass('item-orange clearfix');
            var $ulProperty = $('<ul></ul>').addClass('list-unstyled fl-inline-list clearfix');

            var $btndelt = $('<a></a>').addClass('btn btn-minier btn-yellow pull-right action-buttons').html('删除');
            $.each(propertyArr, function (index, value) {
                var $liProperty = $('<li></li>');
                $('<strong></strong>').html(value[0]).appendTo($liProperty);
                $('<b></b>').html(value[1]).addClass(value[2]).appendTo($liProperty);
                $liProperty.appendTo($ulProperty);

                if (propertyArr.length - 1 == index) {
                    var span = $('<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>');
                    span.appendTo($liProperty)
                    $btndelt.appendTo($liProperty)
                }
            });

            $liLine.append($ulProperty);

            $btndelt.click(function () {
                deltbtn(v);
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

    init();
    console.log();

    $("#upload").click(function () {
        //获取表单数据传入map
        var map = {
            "name": "jsName",
            "toUrl": "jsTest",
            "type": 1
        }

        planUtils.UploadFile("file1", "/files/uploadScrollimg", map);

        init();
    });

    function deltbtn(v) {
        console.log(v)
        com.post(api.delScrollimg, {"id": v.id}, function () {
            init();
        }, function () {
            alert("删除失败")
        }, false)
    }

    function init() {
        com.post(api.queryScrollImgAll, null, function (res) {
            displayFunc.init('resultList', res.data);
        }, null, false);
    }
});