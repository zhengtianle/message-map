//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element'], function () {
    var element = layui.element;
    var laydate = layui.laydate;

    var userInfo = eval("(" + getCookie("userInfo") + ")");
    console.log(userInfo);
    if (userInfo) {
        console.log("设置顶部菜单状态");
        //登录状态切换顶部菜单为登录状态菜单
        $("#loginOrExit").text("退出");
        $("#basicInfo").attr("href", "personalCenter.html");
        $("#myMessage").attr("href", "myMessage.html");
        $("#safeConfig").attr("href", "safeConfig.html");
        $("#personalCenter").attr("href", "personalCenter.html");
        $("#username").text(userInfo.tel);
        if (userInfo.username !== undefined) {
            $("#username").text(userInfo.username);
        }
        if (userInfo.avatar !== undefined) {
            $("#avatar").attr("src", "../images/upload/" + userInfo.avatar);
        }
    } else {
        //未登录状态不需要做什么
    }
});