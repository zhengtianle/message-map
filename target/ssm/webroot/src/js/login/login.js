
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

/**
 * 登录验证
 */
layui.use('layer', function () {
    var layer = layui.layer;

    $(function () {
        $("#btnSubmit").click(function () {
    
            $.ajax({
                url: 'http://localhost:8080/login',
                type: "post",
                data: {
                    "mobile": $("#mobile").val(),
                    "psd": $("#psd").val()
                },
                success: function (data) {
                    var json = eval("(" + data + ")");
                    if (json.result === "success") {
                        //把用户基本信息存储到cookie中，不设置过期时间（浏览器关闭自动过期）
                        document.cookie = "userInfo="+json.user;
                        window.location.href='index.html';
                    } else {
                        layer.open({
                            content: "<div>手机号或者密码错误！</div>"
                        });
                    }
                    console.log('提交表单成功！');
                },
                error: function (e) {
                    console.log('提交登录表单错误：' + e);
                }
            }); //ajax
    
        }); //$("#btnSubmit").click
    })

    
});

/**
 * 回车->相当于点击登录
 */
document.onkeypress = function(e) {
    var event = e || window.event;
    if(event.keyCode == 13) {
        document.getElementById("btnSubmit").click();
    }
}