function login(){
    var text = document.getElementById("login").innerText;
    if(text == "登录"){
        //未登录跳转到登录界面
        console.log("跳转语句前");
        window.location.href = "login.html";
        console.log("跳转语句后");
    } else {
        //注销
        if(window.confirm('是否确定注销？')){
            //alert("确定");
            //注销当前用户
         }
    }
}