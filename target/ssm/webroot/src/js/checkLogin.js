function login() {


    if (document.getElementById("loginOrExit").innerText == "登录") {
        //未登录跳转到登录界面
        window.location.href = "login.html";
    } else {
        //退出
        if (window.confirm('是否确定退出登录？')) {
            //alert("确定");
            hasLogin = false;
            //切换顶部菜单到未登录状态
            $("#loginOrExit").text("登录");
            $("#username").text("未登录");
            $("#avatar").attr("src", "../images/unlogin.png");
            $("#basicInfo").attr("href", "login.html");
            $("#myMessage").attr("href", "login.html");
            $("#safeConfig").attr("href", "login.html");
            $("#personalCenter").attr("href", "login.html");

            //删除存储在浏览器中的cookie
            deleteCookie("userInfo");
        }
    }
}

/**
 * 查找cookie
 * 根据键名name获取value
 * 没有该name则返回null
 */
function getCookie(name){
    if(document.cookie.length > 0){
        var start = document.cookie.indexOf(name+"=");
        if(start != -1){
            start = start + name.length + 1;
            var end = document.cookie.indexOf(";",start);
            if(end == -1){
                end = document.cookie.length;
            }
            return document.cookie.substring(start,end);
        }
    }

    //cookie为空return null
    return null;
}

/**
 * 检查cookie是否设置该name，如果设置了则将过期时间调到过去的时间;
 * 剩下就交给操作系统适当时间清理cookie
 */
function deleteCookie(name){
    if(getCookie(name)){
        document.cookie = name+"="+"; expires=Thu, 01-Jan-70 00:00:01 GMT";
    }
}