$("#left_nav li a").removeClass('active');
$("#left_nav li:eq(5)").children('a').addClass('active');

/*状态修改*/
function switchStatus(ele, status) {
    $(ele).parent().parent().siblings(":eq(0)").text();
    console.log($(ele).parent().parent().siblings(":eq(0)").text());
    $.ajax({
        url: "/brand/status/" + $(ele).parent().parent().siblings(":eq(0)").text(),
        type: "POST",
        data: "status=" + status,
        success: function (data) {
            location.reload();
        },
        error: function (status) {
            alert("请稍后重试！");
        }
    })
}

var pos = 1;
/*选择事件*/
$("#position_select").change(function () {
    var num = $("#position_select").val();
    var msg;
    if (num==1){
        msg="3480 * 240";
    } if (num==2){
        msg="1920 * 480";
    } if (num==3){
        msg="488 * 280";
    } if (num==4){
        msg="480 * 680";
    } if (num==5){
        msg="1240 * 120";
    }
    $("#img_size_msg").empty().append("请上传分辨率为"+msg+"的图片");
    pos = num;
    $("#position_input").val(num);
});
/*添加图片*/
$("#img_form input").change(function () {
    var data = new FormData($("#img_form")[0]);
    if ($("#img_form input[name='file']:eq(0)").val().length<1){
        return;
    }
    $.ajax({
        url: "/poster/picture/"+pos,
        type: "POST",
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function (result) {
            console.log(result);
            if (result.code == 100) {
                $("#img_form img").attr('src', result.extend.imgPath);
                $("input[name='picture']").val(result.extend.imgPath);
                $("#img_cache").val(result.extend.imgName);
            } else {
                $("#warning_msg").empty().append(result.extend.error);
            }
        },
        error: function (result, status) {
            alert(status);
        }
    });
});

/*如果关闭添加brand窗口，清除图片缓存*/
$("#add-poster-modal").on('closed.modal.amui', function(){
    var img = $("#img_cache").val();
    console.log(img);
    if (img == null || img == undefined || img == ""){
        return;
    }
    $.ajax({
        url: "/upload/picture",
        type: "DELETE",
        contentType: "application/json",
        data: "name=" + img,
        dataType: "json",
        success: function (result, status, xhr) {
            console.log(result);
            if (result.code == 100) {
                $("#img_form img").attr('src', '/assets/img/add_bg.png');
                $("input[name='picture']").val("");
                $("#img_cache").val("");
                $("#img_form")[0].reset();
            } else {
                alert_msg(result.extend.error)
            }
        },
        error: function (result, status, xhr) {
            alert_msg("服务器错误")
        }
    });
});

/*提交数据*/
$("#add_poster_btn").click(function () {
   $.ajax({
       url:"/poster",
       type:"POST",
       data:$("#poster_form").serialize(),
       success:function (result) {
           console.log(result);
           if (result.code==100){
               $("#img_cache").val("");
               location.reload();
           } else {
               $("#warning_msg").empty().append(result.extend.error);
           }
       },
       error:function () {
           alert_msg("服务器错误！请稍后重试");
       }
   })
});

/*状态改变警告*/
function update(val,status){
    var poster_id = $(val).parent().parent().siblings(':eq(0)').text();
    console.log(poster_id);
    var msg = "删除";
    if (status==1){
        msg = "下架"
    }
    if (status==2){
        msg = "上架"
    }
    open_warning_modal("确定要"+msg,function () {
        $.ajax({
            url:"/poster/status/"+poster_id+"/"+status,
            type:"POST",
            success:function () {
                alert_msg(msg+"成功");
                location.reload()
            },
            error:function () {
                alert_msg("服务器错误！")
            }
        })
    })
}
$("#del_btn").click(function () {

});