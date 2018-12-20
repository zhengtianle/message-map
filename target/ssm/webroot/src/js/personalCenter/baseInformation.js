initUsername();
initFirstForm();
initSecondForm();
initAvatar();


//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'form', 'laydate'], function () {
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;
    //执行一个laydate实例
    laydate.render({
        elem: '#birthday' //指定元素
    });

    /**
     * 基本信息修改按钮
     */
    form.on('submit(button1)', function (data) {
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        console.log("uid: " + eval("(" + getCookie("userInfo") + ")").uid);
        //注意这里性别提交的是男和女
        $.ajax({
            url: 'http://localhost:8080/updateBasicInfo',
            type: "post",
            data: {
                "uid": eval("(" + getCookie("userInfo") + ")").uid,
                "username": data.field.username,
                "sex": data.field.sex,
                "birthday": data.field.birthday,
                "address": data.field.address,
                "profile": data.field.profile
            },
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.result === "success") {
                    layer.open({
                        content: "<div>修改成功！</div>"
                    });
                    //把用户基本信息存储到cookie中，不设置过期时间（浏览器关闭自动过期）
                    document.cookie = "userInfo=" + json.user;

                    //更新基本资料
                    initFirstForm();
                    initUsername();

                } else {
                    layer.open({
                        content: "<div>修改失败！</div>"
                    });
                }
                console.log('提交表单成功！');
            },
            error: function (e) {
                console.log('提交表单失败：' + e);
            }
        }); //ajax
        return false; //阻止表单跳转。
    });

    /**
     * 我的山威修改按钮
     */
    form.on('submit(button2)', function (data) {
        console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
        console.log("uid: " + eval("(" + getCookie("userInfo") + ")").uid);
        //注意这里性别提交的是男和女
        $.ajax({
            url: 'http://localhost:8080/updateMySduwh',
            type: "post",
            data: {
                "uid": eval("(" + getCookie("userInfo") + ")").uid,
                "sid": data.field.sid,
                "institute": data.field.institute
            },
            success: function (data) {
                var json = eval("(" + data + ")");
                if (json.result === "success") {
                    layer.open({
                        content: "<div>修改成功！</div>"
                    });
                    //把用户基本信息存储到cookie中，不设置过期时间（浏览器关闭自动过期）
                    document.cookie = "userInfo=" + json.user;

                    //更新我的山威
                    initSecondForm();

                } else {
                    layer.open({
                        content: "<div>修改失败！</div>"
                    });
                }
                console.log('提交表单成功！');
            },
            error: function (e) {
                console.log('提交表单失败：' + e);
            }
        }); //ajax
        return false; //阻止表单跳转。
    });

});

/**
 * 重置
 */
function reset() {

}

/**
 * 初始化头像
 */
function initAvatar() {

}

/**
 * 初始化第一个表单数据
 */
function initFirstForm() {
    console.log("初始化第一个表单数据");
    var userInfo = eval("(" + getCookie("userInfo") + ")");
    var username = userInfo.username !== undefined ? userInfo.username : userInfo.tel;
    var birthday = userInfo.birthday !== undefined ? userInfo.birthday : "";
    var address = userInfo.address !== undefined ? userInfo.address : "";
    var profile = userInfo.profile !== undefined ? userInfo.profile : "";
    var tel = userInfo.tel;
    $("input[name='username']").val(username);
    $("input[name='tel']").val(tel);
    $("input[name='birthday']").val(birthday);
    $("input[name='address']").val(address);
    $("textarea[name='profile']").val(profile);
    if (userInfo.sex === 1) {
        //男
        $("#man").attr("checked",true);
        $("#woman").attr("checked",false);
    } else {
        $("#woman").attr("checked",true);
        $("#man").attr("checked",false);
    }


}

/**
 * 初始化第二个表单
 */
function initSecondForm() {
    var userInfo = eval("(" + getCookie("userInfo") + ")");
    var sid = userInfo.sid !== undefined ? userInfo.sid : "";
    var institute = userInfo.institute !== undefined ? userInfo.institute : "";
    $("input[name='sid']").val(sid);
    $("#institute").val(institute);
}

/**
 * 初始化顶部菜单用户名
 */
function initUsername() {
    var userInfo = eval("(" + getCookie("userInfo") + ")");
    var username = userInfo.username !== undefined ? userInfo.username : userInfo.tel;
    $("#username").text(username);
}

/**
 * 初始化顶部菜单头像
 */
function initAvatar() {

}