function login(){
    var text = document.getElementById("login").innerText;
    if(text == "登录"){
        //未登录跳转到登录界面
        window.location.href = "login.html";
    } else {
        //注销
        if(window.confirm('是否确定退出登录？')){
            //alert("确定");
            //注销当前用户
         }
    }
}