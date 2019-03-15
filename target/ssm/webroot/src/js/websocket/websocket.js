var websocket;
var uid = eval("(" + getCookie("userInfo") + ")").uid;
if ('WebSocket' in window) {
    websocket = new WebSocket("ws://localhost:8080/webSocketServer?uid=" + uid);
} else {
    websocket = new SockJS("ws://localhost:8080/sockjs/webSocketServer?uid=" + uid);
}

websocket.onopen = function() {}
websocket.onmessage = function(e) {
    if(e.data == "新的系统通知") {
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
    }
} 