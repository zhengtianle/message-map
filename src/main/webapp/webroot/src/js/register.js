/**
 * 支付宝
 */
$(".download_btn").click(function () {
    if ($(".QRcode").css("display") == "none") {
        $(".QRcode").show();
        $(".download_btn").text("关闭二维码");
    } else {
        $(".QRcode").hide();
        $(".download_btn").text("支付宝花呗红包赞助");
    }
});


$(function () {
    //为表单的必填文本框添加提示信息（选择form中的所有后代input元素）
    // $("form :input.required").each(function () {
    //     //通过jquery api：$("HTML字符串") 创建jquery对象
    //     var $required = $("<strong class='high'>*</strong>");
    //     //添加到this对象的父级对象下
    //     $(this).parent().append($required);
    // });
    // var errorMsg = $(".error-msg").text();
    //为表单元素添加失去焦点事件
    $("form :input").blur(function () {
        var $parent = $(this).parent();
        $parent.find(".msg").remove(); //删除以前的提醒元素（find()：查找匹配元素集中元素的所有匹配元素）
        //验证验证码
        // if ($(this).is("#pin")) {
        // 	var pinVal = $.trim(this.value);
        // 	var regName = /[~#^$@%&!*()<>:;'"{}【】  ]/;
        // 	if (pinVal == "" || pinVal.length < 2 || regName.test(pinVal)) {
        // 		var errorMsg = " 姓名非空，长度2-20位，不包含特殊字符！";
        // 		$parent.append("<span class='msg onError'>" + errorMsg + "</span>");
        // 	} else {
        // 		var okMsg = " 输入正确";
        // 		$parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
        // 	}
        // }
        //验证手机号
        if ($(this).is("#mobile")) {
            var mobileVal = $.trim(this.value);
            var regMobile = /^1[3|4|5|7|8][0-9]{9}$/;
            if (mobileVal == "" || !regMobile.test(mobileVal)) {
                var errorMsg = " 请输入有效的11位手机号码！";
                $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
            } else {
                var okMsg = " 输入正确";
                $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
            }
        }
        //验证密码
        if ($(this).is("#psd")) {
            var psdVal = $.trim(this.value);
            var regPsd = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
            if (psdVal == "" || !regPsd.test(psdVal)) {
                var errorMsg = " 密码为6-20位字母、数字的组合！";
                $parent.append("<span class='msg onError'>" + errorMsg + "</span>");
            } else {
                var okMsg = " 输入正确";
                $parent.append("<span class='msg onSuccess'>" + okMsg + "</span>");
            }
        }
    }).keyup(function () {
        //triggerHandler 防止事件执行完后，浏览器自动为标签获得焦点
        $(this).triggerHandler("blur");
    }).focus(function () {
        $(this).triggerHandler("blur");
    });


    /**
     * 注册申请
     */
    layui.use('layer', function () {
        var layer = layui.layer;

        //点击按钮时，通过trigger()来触发文本框的失去焦点事件
        $("#btnSubmit").click(function () {
            //trigger 事件执行完后，浏览器会为submit按钮获得焦点
            $("form .required:input").trigger("blur");
            var numError = $("form .onError").length;
            if (numError) {
                return false;
            } else {
                $.ajax({
                    url: 'http://localhost:8080/register',
                    type: "post",
                    data: {
                        "mobile": $("#mobile").val(),
                        "pin":$("#pin").val(),
                        "psd": $("#psd").val()
                    },
                    success: function (data) {
                        //注册成功
                        var json = eval("(" + data + ")");
                        if (json.result === "success") {
                            layer.open({
                                content: "<div>恭喜您，注册成功！</div>"
                            });
                            window.location.href = 'login.html';
                        } else if(json.result === "repeat"){
                            layer.open({
                                content: "<div>该手机号已经注册过！</div>"
                            });
                        } else {
                            layer.open({
                                content: "<div>注册失败，请再次重试！</div>"
                            });
                        }
                    },
                    error: function (e) {
                        layer.open({
                            content: "<div>提交表单失败，请稍后重试！</div>"
                        });
                    }
                }); //ajax

            }

        });//$("#btnSubmit").click

    });

})