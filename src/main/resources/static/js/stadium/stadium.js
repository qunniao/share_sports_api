$(function () {
    console.log("店家分页stadium!!!!!!!!!!!!!!!!!!!");
    com.post()
    var lists = [
        {
            "text": {
                name: "店名",
                id: "a1",
                paramName: "gymName"
            }
        },
        {
            "select": {
                name: "手机号2",
                id: "a2",
                paramName: "name1",
                ds: textFinal.type,
                keyName: "key",
                valueName: "value",
                flag: true
            }
        },
        {
            "select": {
                name: "手机号3",
                id: "a3",
                paramName: "name2",
                ds: textFinal.type,
                keyName: "key",
                valueName: "value",
                flag: true
            }
        },
        {
            "select":   {
                name: "手机号4",
                id: "a4",
                paramName: "name3",
                ds: textFinal.type,
                keyName: "key",
                valueName: "value",
                flag: true
            }
        }
    ];
    // var opt={"gymBuildingPOS[0].bid":1,"gymBuildingPOS[1].bid":3};

    var opt=null;
    var serch = new mySearcher();
    serch.init(lists, "pagination_14", "condList", stadiumApi.pageGymShop, opt, function (res) {
        console.log(res)
    })


});