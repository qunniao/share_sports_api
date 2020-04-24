$(function () {
    console.log("login !!!!")


    $("#login").click(function () {
        var userId = $("#userId").val();
        var userPassword = $("#userPassword").val();

        if (userId == ""){
            alert("账号不能为空")
            return false;
        }
        if (userPassword == ""){
            alert("密码不能为空")
            return false;
        }

        var option = {
            "userId": userId,
            "password": userPassword
        };

        com.post("/log/login", option, function (res) {
            console.log(res)
                var code = res.code;
                if (code == 1111) {
                    alert("账号不存在")
                }
                if (code == 2222) {
                    alert("密码不正确")
                }

                if (code == 200) {
                    var date = new Date();
                    var expiresDays = 10;

                    date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000);

                    document.cookie = "userId=" + res.data.userId + ";expires=" + date.toGMTString();

                    window.location.href = "/index.html";
                }

            }, function () {
            }, false
        );
    });
});