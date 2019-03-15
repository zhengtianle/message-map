/**
 * 获取消息提醒（有人对你的留言点赞）
 */
if (eval("(" + getCookie("userInfo") + ")")) { //如果登录了

}

var hasNews = false;
var hasNotices = false;
$.ajax({
    url: 'http://localhost:8080/getMsgNotification',
    type: "post",
    data: {
        "uid": eval("(" + getCookie("userInfo") + ")").uid
    },
    success: function (data) {
        console.log("getMsg");
        if (data.result === "success") {
            //point
            //point_avatar
            //point_side
            //id：msg
            if (data.notification[0].read == 0) {
                //有未读消息
                hasNews = true;
                document.getElementsByClassName("point")[0].style.visibility = 'visible';
                document.getElementsByClassName("point_avatar")[0].style.visibility = 'visible';
                if (document.getElementsByClassName("point_side")[0]) {
                    document.getElementsByClassName("point_side")[0].style.visibility = 'visible';
                }
                if (document.getElementById("msg")) {
                    document.getElementById("msg").style.visibility = 'visible';
                }
            } else {
                hasNews = false;
                if (!hasNews && !hasNotices) {
                    document.getElementsByClassName("point")[0].style.visibility = 'hidden';
                    document.getElementsByClassName("point_avatar")[0].style.visibility = 'hidden';
                    if (!hasNews && !hasNotices && document.getElementsByClassName("point_side")[0]) {
                        document.getElementsByClassName("point_side")[0].style.visibility = 'hidden';
                    }
                }
                if (document.getElementById("msg")) {
                    document.getElementById("msg").style.visibility = 'hidden';
                }
            }
        }
    },
    error: function (e) {
        layer.msg("获取未读普通消息请求失败");
    }


});


/**
 * 获取系统通知
 */
$.ajax({
    url: 'http://localhost:8080/getSysNotification',
    type: "post",
    data: {
        "uid": eval("(" + getCookie("userInfo") + ")").uid
    },
    success: function (data) {
        if (data.result === "success") {
            //point
            //point_avatar
            //point_side
            //id：sys
            if (data.notification[0].hasRead == false) {
                //有未读通知
                hasNotices = true;
                document.getElementsByClassName("point")[0].style.visibility = 'visible';
                document.getElementsByClassName("point_avatar")[0].style.visibility = 'visible';
                if (document.getElementsByClassName("point_side")[0]) {
                    document.getElementsByClassName("point_side")[0].style.visibility = 'visible';
                }
                if (document.getElementById("sys")) {
                    document.getElementById("sys").style.visibility = 'visible';
                }
            } else {
                hasNotices = false;
                if (!hasNews && !hasNotices) {
                    document.getElementsByClassName("point")[0].style.visibility = 'hidden';
                    document.getElementsByClassName("point_avatar")[0].style.visibility = 'hidden';
                    if (document.getElementsByClassName("point_side")[0]) {
                        document.getElementsByClassName("point_side")[0].style.visibility = 'hidden';
                    }
                }
                if (document.getElementById("sys")) {
                    document.getElementById("sys").style.visibility = 'hidden';
                }
            }
        }
    },
    error: function (e) {
        layer.msg("获取未读系统通知请求失败");
    }


});