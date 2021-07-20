
/*警告*/
function warning(ele,msg){
    var elem = $(ele).next("p");
    if(elem.length==0){
        elem = $(ele).next().next("p");
    }
    elem.addClass("w-show");
    elem.empty().append(msg);
}

function check(){
    if($("#account").val().length<1){
        warning("#account","请输入账号");
        return false;
    }
    if($("#password").val().length<1){
        warning("#password","请输入密码");
        return false;
    }
    if($("#verify_code").val().length<1){
        warning("#verify_code","请输入验证码");
        return false;
    }
    return true;
}

$("#login-btn").click(function () {
    $("#login-form p").removeClass("w-show");
    if(!check()){
        return;
    }
    $.ajax({
        url:"/login",
        data: $("#login-form").serialize()+"&&"+window.location.search.substring(1),
        type: "POST",
        success: function(result){
            if (result.code == 100){
                alert("登陆成功");
                window.location.replace(decodeURIComponent(result.extend.href));
            }
            if(result.code == 202){
                warning("#verify_code",result.msg);
            }
            if(result.code == 400){
                warning("#account",result.msg);
                reload_verifycode();
            }
            if(result.code == 405){
                reload_verifycode();
                warning("#password",result.msg);
            }
            if (result.code == 200){
                alert(result.msg)
            }
        },
        error:function () {
            alert("请刷新页面重试");
        }
    })
})

/*验证码刷新*/
$("#verify_code").next().click(function(){
    reload_verifycode();
})
/*验证码刷新方法*/
function reload_verifycode(){
    $("#verify_code").val("");
    $("#verify_code").next().attr("src",
        "verifyCode?code=" + new Date().getTime());
}
