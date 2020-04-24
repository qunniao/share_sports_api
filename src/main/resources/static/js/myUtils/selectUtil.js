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