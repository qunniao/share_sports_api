$(function () {
    var asicConf = {
        $Module: $(".nav,.nav-list"),
        $PageContent: $(".page-content"),
        $ModulePath: $("#breadcrumb"),
        $ModulePathList: {}
    }


    var userId = getCookie("userId");

    com.post(api.queryManageUserByUserId, {"userId": userId}, function (res) {
        params.UserManag = res.data;
        console.log(params.UserManag);
        console.log(res.data);
    }, null, false);

    var url = "";

    if (params.UserManag.roleList != null) {
        $.each(params.UserManag.roleList, function (i, v) {
            if (v.roleName == "root") {
                url = "/modules/findAllModule"
            }
        });
    }

    com.post("/modules/findAllModule", {"userId": "aaa"}, function (res) {
        displayFunc.init(res)
    });

    var displayFunc = (function () {

        var firstModule = function (index, data) {
            var v = data
            var i = index;
            if (v.level == 1) {
                var html = renderModule(v.level, data)
                asicConf.$Module.append(html)
            } else {
                var $fatherLi = asicConf.$Module.find("[level=" + (v.level - 1) + "][moduleId=" + v.fatherModuleId + "]");
                var $NewUl = $fatherLi.children("ul").last();
                var html = renderModule(i, v)
                $NewUl.append(html)
                if ($fatherLi.children("a").first().children("b").length == 0) {
                    $fatherLi.children("a").first().append('<b class="arrow icon-angle-down"></b>')
                }
                if (!$fatherLi.children("a").hasClass("dropdown-toggle")) {
                    $fatherLi.children("a").addClass("dropdown-toggle")
                }
            }
        }

        var renderModule = function (level, v) {
            var $li = $("<li></li>").attr("level", v.level).attr("moduleId", v.mid).attr("moduleName", v.name);
            var $i = $("<i></i>").addClass(v.icon);
            var $span = $("<span></span>").text(v.name)
            var $ul = $("<ul></ul>").addClass("submenu").attr("style", "display:none;");
            var $a = $("<a></a>").attr("href", v.url).on("click", function (e) {
                var html;
                //$li.siblings().removeClass("active open").find('li').removeClass("active open");
                $li.siblings(".active").removeClass('active open').find("ul").attr("style", "display:none");
                $li.siblings().find(".active").removeClass("active");
                $li.addClass("active");
                if (!(v.url == null || v.url == '' || v.url == "#")) {
                    asicConf.$PageContent.load('/' + v.url);
                }
                asicConf.$ModulePathList[v.level] = "<li moduleId='" + v.mid + "'><i class='" + (v.level == 1 ? "slight-right-shift " + v.icon : "") + "'></i><a href=\"#\">" + v.name + "</a></li>";
                for (var i = 1; i <= v.level; i++) {
                    if (html == null) {
                        html = asicConf.$ModulePathList[i]
                    } else {
                        html += asicConf.$ModulePathList[i]
                    }
                }
                asicConf.$ModulePath.html(html)
                e.preventDefault();
            })
            level == 1 ? $span.addClass("menu-text") : ""
            return $li.append($a.append($i, $span), $ul);
        }


        return {
            init: function (data) {
                $.each(data, function (i, v) {
                    firstModule(i, v)
                })
            }
        }
    })();

    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null) document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }

    function setCookie(name, value, time) {
        var strsec = getsec(time);
        var exp = new Date();
        exp.setTime(exp.getTime() + strsec * 1);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }

    function getsec(str) {
        alert(str);
        var str1 = str.substring(1, str.length) * 1;
        var str2 = str.substring(0, 1);
        if (str2 == "s") {
            return str1 * 1000;
        } else if (str2 == "h") {
            return str1 * 60 * 60 * 1000;
        } else if (str2 == "d") {
            return str1 * 24 * 60 * 60 * 1000;
        }
    }

    function getCookie(c_name) {
        if (document.cookie.length > 0) {
            c_start = document.cookie.indexOf(c_name + "=");
            if (c_start != -1) {
                c_start = c_start + c_name.length + 1;
                c_end = document.cookie.indexOf(";", c_start);
                if (c_end == -1) {
                    c_end = document.cookie.length;
                }

                return unescape(document.cookie.substring(c_start, c_end));
            }
        }
        return "";
    }

});