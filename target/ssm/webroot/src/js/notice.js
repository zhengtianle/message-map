/**
 * 1. 加载系统通知表的内容
 */
$.ajax({
    url: 'http://localhost:8080/getSysNotification',
    type: "post",
    data: {
        "uid": eval("(" + getCookie("userInfo") + ")").uid
    },
    success: function (data) {
        if (data.result === "success") {
            //显示系统通知
        }
    },
    error: function (e) {
        layer.msg("获取系统通知表请求失败");
    }
});


/**
 * 2. 系统通知已读
 */
$.ajax({
    url: 'http://localhost:8080/readSysNotification',
    type: "post",
    data: {
        "readtime": getNowFormatDate()
    },
    success: function () {
        document.getElementsByClassName("point")[0].style.visibility = 'hidden';
        document.getElementsByClassName("point_avatar")[0].style.visibility = 'hidden';
        document.getElementsByClassName("point_side")[0].style.visibility = 'hidden';
        document.getElementById("sys").style.visibility = 'hidden';
    },
    error: function (e) {
        console.log("系统通知已读请求失败")
    }
});

/**
 * 按照 yyyy-MM-dd HH-mm-ss 格式获取当前时间
 */
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
        " " + date.getHours() + seperator2 + date.getMinutes() +
        seperator2 + date.getSeconds();
    return currentdate;
}