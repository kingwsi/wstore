
/*警告*/
function warning(ele,msg){
    var elem = $(ele).next("p");
    if(elem.length==0){
        elem = $(ele).next().next("p");
    }
    elem.addClass("w-show");
    elem.empty().append(msg);
}
/*step1*/
$("#next-step").click(function(){
    var account = $("#account-input").val();
    var verifyCode = $("#verify_code").val();
    var reg1 = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    var reg2 = /^[1][3,4,5,7,8][0-9]{9}$/;
    if(account.length==0){
        warning("#account-input","请输入正确的手机号或邮箱号！");
        return;
    }
    if(!reg1.test(account)&&!reg2.test(account)){
        warning("#account-input","请输入正确的手机号或邮箱号！");
        return;
    }
    if(verifyCode.length==0){
        warning("#verify_code","请输入验证码！");
        return;
    }

    $.ajax({
        url:"/findverify",
        type:"POST",
        data:{
            account:$("#account-input").val(),
            verifyCode:$("#verify_code").val()
        },
        success:function(result){
            if(result.code == 100){
                $("#account-label").next().val(account)
                if(reg1.test(account)){
                    $("#account-label").empty().append("邮箱找回：").append(account);
                }
                if(reg2.test(account)){
                    $("#account-label").empty().append("手机号找回：").append(account);
                }

                $("#first-step").addClass("w-hide");
                $("#second-step").removeClass("w-hide").fadeIn();
                return;
            }
            if(result.code == 202){
                warning("#verify_code",result.msg);
                reload_verifycode();
                return;
            }
        }
    });

});

/*step2*/
$("#register-btn").click(function () {
    $.ajax({
        url:"/user",
        type:"PUT",
        data:$("#update-form").serialize(),
        success:function(result){
            if(result.code == 100){
                alert("密码修改成功！");
                window.location.href = "/login";
            }else{
                warning("#repeatPassword",result.extend.error);
            }
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