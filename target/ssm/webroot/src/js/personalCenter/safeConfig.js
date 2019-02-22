layui.use(['form'], function () {
    var form = layui.form;
    form.verify({
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        pass: [
            /^[\S]{6,30}$/, '密码必须6到30位，且不能出现空格'
        ]
    });

    form.on('submit(buttonPassword)', function (data) {
        var newPasswordPlus = document.getElementsByName("newPasswordPlus");
        var newPassword = document.getElementsByName("newPassword");
        if (newPasswordPlus[0].value != newPassword[0].value) {
            layer.msg("确认密码失败");
        } else {
            $.ajax({
                url: 'http://localhost:8080/modifyPassword',
                type: "post",
                data: {
                    "uid": eval("(" + getCookie("userInfo") + ")").uid,
                    "oldPassword": data.field.oldPassword,
                    "newPassword": data.field.newPassword
                },
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.result === "success") {
                        layer.open({
                            content: "<div>修改成功！</div>"
                        });

                    } else if(json.result === "oldPassword"){
                        layer.msg("原密码输入错误");
                    } else {
                        layer.msg("修改失败，请重试");
                    }
                    console.log('提交表单成功！');
                },
                error: function (e) {
                    console.log('提交表单失败：' + e);
                }
            }); //ajax
        }

        return false; //阻止表单跳转。
    });
});