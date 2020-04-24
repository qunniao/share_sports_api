jQuery(function () {

    com = {
        post: function (url, options, callback, failCallback, asyncFlag) {
            if (url == null) {
                return;
            }
            var dfd = $.Deferred();
            var opts = $.extend({}, options || {});
            var optAsync = true;
            if (asyncFlag == undefined || asyncFlag == true) {
                optAsync = true;
            } else {
                optAsync = false;
            }
            var ss = window.location.host;
            console.log("我的" + ss);

            if (ss != "localhost:8080") {
                url = "/gymnasium" + url;
            }
            // var url="http://danbrown.mynatapp.cc/gymnasium"+url;
            // jQuery.support.cors = true;
            $.ajax({
                url: url,
                data: opts,
                type: "POST",
                dataType: "json",
                // contentType: "Access-Control-Allow-Origin:*",
                // headers:{"Access-Control-Allow-Origin:*":null},
                // processData: false,
                // beforeSend: function(xhr) {
                //     xhr.setRequestHeader("Access-Control-Allow-Origin:*","X-Custom-Header1", "Bar");
                // },
                contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                async: optAsync, //设为false就是同步请求
                success: function (res) {
                    if (res == null) {
                        return;
                    }
                    if (callback) {
                        callback(res);
                    }
                }, error: function (msg) {
                    if (failCallback) {
                        failCallback(msg);
                    }
                }
            });

            return dfd.promise();
        }
    }

    $.fn.bindData = function (d, k, v, all, cb, o) {
        if (this[0] != undefined && this[0].nodeName.toLowerCase() == "select" && d != undefined) {
            this.empty();

            var optionHTML = "";

            if (all) {
                optionHTML = "<option value=''>全部</option>";
            }

            for (var i = 0; i < d.length; i++) {
                if (typeof d[i] == 'object') {
                    if (k == undefined || v == undefined || k == "" || v == "") {
                        console.log("该控件无法绑定列表数据");
                        return this;
                    }
                    var _o = o ? "other='" + d[i][o] + "'" : "";
                    optionHTML = optionHTML + "<option value='" + d[i][k] + "'" + _o + ">" + d[i][v] + "</option>";
                } else {
                    optionHTML = optionHTML + "<option value='" + d[i] + "' multikey='multikey'>" + d[i] + "</option>";
                }
            }

            $(optionHTML).appendTo(this);

            if (cb) {
                cb();
            }
        } else {
            console.log("该控件无法绑定列表数据");
        }
        return this;
    };

});
