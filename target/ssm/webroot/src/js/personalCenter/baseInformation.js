initUsernameAndAvatar();
initFirstForm();
initSecondForm();
initAvatar();


//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use(['element', 'form', 'laydate', 'upload'], function () {
    var element = layui.element;
    var form = layui.form;
    var laydate = layui.laydate;
    var upload = layui.upload;
    //执行一个laydate实例
    laydate.render({
        elem: '#birthday' //指定元素
    });

    //修改上传头像
    upload.render({
        elem: '#modifyAvatar',
        data: {
            "uid":eval("(" + getCookie("userInfo") + ")").uid
        },
        accept: 'images',
        acceptMime: 'image/jpg, image/png, image/jpeg',
        url: '/updateAvatar',
        size: 1024,//1M
        before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                console.log("该图片的base64编码：" + result);
                $('#viewAvatar').attr('src', result); //图片链接（base64）
            });
        },
        done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传头像失败');
            }
            //上传成功
            //把用户基本信息存储到cookie中，不设置过期时间（浏览器关闭自动过期）
            document.cookie = "userInfo=" + res.user;
            //更新菜单的头像
            initUsernameAndAvatar();
            return layer.msg('修改成功');

        },
        error: function () {
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function () {
                uploadInst.upload();
            });
        }
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
                    layer.msg("修改失败");
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
                    layer.msg("修改失败");
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
 * 初始化头像表单
 */
function initAvatar() {
    var userInfo = eval("(" + getCookie("userInfo") + ")");
    if(userInfo.avatar !== undefined){
        $("#viewAvatar").attr("src", userInfo.avatar);
    }
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
        $("#man").attr("checked", true);
        $("#woman").attr("checked", false);
    } else {
        $("#woman").attr("checked", true);
        $("#man").attr("checked", false);
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
 * 初始化顶部菜单用户名和头像
 */
function initUsernameAndAvatar() {
    var userInfo = eval("(" + getCookie("userInfo") + ")");
    var username = userInfo.username !== undefined ? userInfo.username : userInfo.tel;
    $("#username").text(username);
    if(userInfo.avatar !== undefined){
        $("#avatar").attr("src", "../images/upload/" + userInfo.avatar);
    }
}