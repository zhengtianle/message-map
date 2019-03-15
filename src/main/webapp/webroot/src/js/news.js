layui.use(['element', 'layer'], function () {
    var element = layui.element;
    var layer = layui.layer;

    /**
     * 1. 加载消息表的内容
     */
    $.ajax({
        url: 'http://localhost:8080/getMsgNotification',
        type: "post",
        data: {
            "uid": eval("(" + getCookie("userInfo") + ")").uid
        },
        success: function (data) {
            if (data.result === "success") {
                //显示系统通知
                var rootDiv = document.getElementById("layNews");
                for (var i = 0; i < data.notification.length; i++) {
                    var divcard = document.createElement("div");
                    //var divheader = document.createElement("div");
                    var divbody = document.createElement("div");
                    divcard.className = "layui-card";
                    //divheader.className = "layui-card-header";
                    divbody.className = "layui-card-body";
                    divcard.style = "margin-top: 5px;";
                    //divheader.innerHTML = '<div style="float:left;">' + data.notification[i].title; +'</div>' + '<div style="text-align:right;">' + data.notification[i].time +"</div>";
                    divbody.innerHTML = "用户“" + data.notification[i].susername + "”给您在“" + data.notification[i].message.location + "”处的留言“" + data.notification[i].message.content + "”点了一个赞" + '<div style="text-align:right;">' + data.notification[i].time +"</div>";

                    rootDiv.appendChild(divcard);
                    //divcard.appendChild(divheader);
                    divcard.appendChild(divbody);
                }

                // {
                //     "result": "success",
                //     "notification": [
                //         {
                //             "suid": 0,
                //             "ruid": 0,
                //             "susername": "冯宝宝",
                //             "title": "点赞",
                //             "content": "7",
                //             "time": "2019-03-03 21:24:59",
                //             "read": 0,
                //             "message": {
                //                 "mid": 7,
                //                 "uid": 1,
                //                 "location": "sduwh",
                //                 "content": "测试世界留5",
                //                 "time": "2018-12-12 12:05:01",
                //                 "stars": 1
                //             }
                //         },
                //         {
                //             "suid": 0,
                //             "ruid": 0,
                //             "susername": "冯宝宝",
                //             "title": "点赞",
                //             "content": "12",
                //             "time": "2018-12-14 13:00:10",
                //             "read": 0,
                //             "message": {
                //                 "mid": 12,
                //                 "uid": 1,
                //                 "location": "122.06086437.53258",
                //                 "content": "这是一条测试单点留言记录",
                //                 "time": "2018-12-22 21:10:59",
                //                 "stars": 1
                //             }
                //         },
                //         {
                //             "suid": 0,
                //             "ruid": 0,
                //             "susername": "冯宝宝",
                //             "title": "点赞",
                //             "content": "1",
                //             "time": "2018-12-14 13:00:00",
                //             "read": 1,
                //             "message": {
                //                 "mid": 1,
                //                 "uid": 1,
                //                 "location": "sduwh",
                //                 "content": "测试世界留言",
                //                 "time": "2018-12-12 12:00:01",
                //                 "stars": 2
                //             }
                //         }
                //     ]
                // }
            } else {
                layer.msg("获取消息异常，请刷新重试");
            }
        },
        error: function (e) {
            layer.msg("获取消息表请求失败");
        }
    });

})


/**
 * 2. 将消息表未读消息置为已读
 */
$.ajax({
    url: 'http://localhost:8080/readMsgNotification',
    type: "post",
    data: {
        "readtime": getNowFormatDate(),
        "uid": eval("(" + getCookie("userInfo") + ")").uid
    },
    success: function () {
        /**
         * XML Parsing Error: no root element found
         * Location: http://localhost:8080/readMsgNotification
         * Line Number 1, Column 1:
         */
        console.log("置为已读");
        document.getElementsByClassName("point")[0].style.visibility = 'hidden';
        document.getElementsByClassName("point_avatar")[0].style.visibility = 'hidden';
        document.getElementsByClassName("point_side")[0].style.visibility = 'hidden';
        document.getElementById("msg").style.visibility = 'hidden';
    },
    error: function () {
        console.log("将消息表未读消息置为已读请求失败")
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