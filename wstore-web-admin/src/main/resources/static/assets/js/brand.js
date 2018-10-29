$("#left_nav li a").removeClass('active');
$("#left_nav li:eq(2)").children('a').addClass('active');

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

/*新增品牌*/

/*添加图片*/
function add_img(value) {
    var data = new FormData($(value).parent().parent()[0]);
    var add_form = $(value).parent().parent()[0];
    $.ajax({
        url: "/brand/logo",
        type: "POST",
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        success: function (result) {
            console.log(result);
            if (result.code == 100) {
                $(value).prev('img').attr('src', result.extend.imgPath);
                $("input[name='brandPic']").val(result.extend.imgPath);
                $(value).next('img').addClass('upload_add_img_after').attr('imgName', result.extend.imgName);
            } else {
                $("#add_brand_warning").empty().append(result.extend.error);
            }
        },
        error: function (result, status) {
            alert(status);
        }
    });
}

/*删除logo*/
function del_img(value) {
    $.ajax({
        url: "/upload/picture",
        type: "DELETE",
        contentType: "application/json",
        data: "name=" + $(value).attr('imgname'),
        dataType: "json",
        success: function (result, status, xhr) {
            console.log(result);
            if (result.code == 100) {
                $(value).prev().prev('img').attr('src', '/assets/img/add_logo.png');
                $(value).removeClass('upload_add_img_after');
                $("#pic_hide_input").val("");
                $(value).parent().parent()[0].reset();
            } else {
                console.log(result.extend.error);
            }
        },
        error: function (result, status, xhr) {
            console.log("服务器错误")
        }
    });
}
/*如果关闭添加brand窗口，清除图片缓存*/
$("#add-brand-modal").on('closed.modal.amui', function(){
    if ($("#cache_img").attr('imgname') == null || $("#cache_img").attr('imgname') == undefined || $("#cache_img").attr('imgname') == ""){
        return;
    }
    del_img("#cache_img");
});
/*添加brand*/
$("#add_brand_btn").click(function () {
    $.ajax({
        url: "/brand",
        type: "POST",
        data: $("#add_brand_form").serialize(),
        success: function (result) {
            console.log(result);
            if (result.code == 100) {
                $("#cache_img").attr('imgname',"");
                $("#add-brand-modal").modal('close');
                $("#add-brand-modal form")[0].reset();
                $("#add-brand-modal form")[1].reset();
                alert_msg("添加品牌成功！");
                location.reload();
            } else {
                $("#add_brand_warning").empty().append(result.extend.error);
            }
        },
        error: function (status) {
            alert(status);
        }
    })
});
/*打开编辑brand窗口*/
function open_edit_modal(ele) {
    $("#edit-brand-modal").modal('open');
    $("#edit_input_id").val($(ele).parent().parent().siblings(":eq(0)").text());
    $("#edit_input_name").val($(ele).parent().parent().siblings(":eq(2)").text());
    $("#edit_input_en_name").val($(ele).parent().parent().siblings(":eq(3)").text());
    $("#edit_input_sort").val($(ele).parent().parent().siblings(":eq(4)").text());
    $("#upload_img_div img:eq(0)").attr("src",$(ele).parent().parent().siblings(":eq(1)").children('img:eq(0)').attr('src'));
}

/*编辑提交*/
$("#edit_brand_btn").click(function () {
    console.log($("#edit_brand_form").serialize());
    $.ajax({
        url: "/brand/update",
        type: "POST",
        data: $("#edit_brand_form").serialize(),
        success: function (result) {
            $("#edit-brand-modal").modal('close');
            alert_msg("添加品牌成功！刷新查看");
        },
        error:function (status) {
            alert_msg(status);
        }
    })
});

/*删除品牌*/
function del_brand(ele) {
    var id = $(ele).parent().parent().siblings(":eq(0)").text();
    var name = $(ele).parent().parent().siblings(":eq(2)").text();
    open_warning_modal("确定要删除品牌["+name+"]", function () {
        $.ajax({
            url: "/brand/" + id,
            type: "DELETE",
            success: function (result) {
                alert_msg("已删除!请刷新页面");
            },
            error:function (status) {
                alert_msg("操作失败！请重试")
            }
        })
    });
}