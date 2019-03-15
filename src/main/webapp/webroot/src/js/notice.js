layui.use(['element','layer'], function () {
    var element = layui.element;
    var layer = layui.layer;

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
                var rootDiv = document.getElementById("layNotice");
                for (var i = 0; i < data.notification.length; i++) {
                    var divcard = document.createElement("div");
                    var divheader = document.createElement("div");
                    var divbody = document.createElement("div");
                    divcard.className = "layui-card";
                    divheader.className = "layui-card-header";
                    divbody.className = "layui-card-body";
                    divcard.style = "margin-top: 5px;";
                    divheader.innerHTML = data.notification[i].title + '<div style="float:right; position:relative;">' + data.notification[i].time +"</div>";
                    divbody.innerHTML = data.notification[i].content;

                    rootDiv.appendChild(divcard);
                    divcard.appendChild(divheader);
                    divcard.appendChild(divbody);
                }
                // {
                //     "result": "success",
                //     "notification": [
                //         {
                //             "title": "元旦快乐",
                //             "content": "留言山威恭祝您元旦快乐",
                //             "time": "2019-01-01 00:00:00",
                //             "hasRead": false
                //         },
                //         {
                //             "title": "测试系统通知",
                //             "content": "测试系统通知内容",
                //             "time": "2018-12-12 12:00:00",
                //             "hasRead": false
                //         },
                //         {
                //             "title": "恭喜您成功注册留言山威",
                //             "content": "“留言山威”是一款主打可视化留言的系统，支持点击标注留言，世界留言，轻度导航定位等日常功能，个人中心的消息通知等。如有疑问请移步github: <a href=\"https://github.com/zhengtianle/message-map\">留言山威Github</a>",
                //             "time": "2018-12-10 12:00:00",
                //             "hasRead": false
                //         }
                //     ]
                // }
            } else {
                layer.msg("获取系统通知异常，请刷新重试");
            }
        },
        error: function (e) {
            layer.msg("获取系统通知表请求失败");
        }
    });

})


var readtime = getNowFormatDate();
/**
 * 2. 系统通知已读
 */
$.ajax({
    url: 'http://localhost:8080/readSysNotification',
    type: "post",
    data: {
        "readtime": readtime,
        "uid": eval("(" + getCookie("userInfo") + ")").uid
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
        console.log(currentdate);
    return currentdate;
}