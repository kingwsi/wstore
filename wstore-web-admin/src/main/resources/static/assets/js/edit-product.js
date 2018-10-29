
$(document).ready(function () {
    $('#loading_modal').modal({
        closeViaDimmer: false
    });
});
window.onload = function(){
    /*初始化参数表格*/
    $.each($("#properties_table td"), function (index, val) {
        var temp = $(val).text();
        $(val).empty();
        $(val).append('<input type="text" placeholder="参数值" value="'+temp+'"/>');
        $("#properties_table").addClass('am-table-compact');
    });
    setTimeout(function () {
        $('#loading_modal').modal('close');
    },500);
};
var del_img_list = "";
var del_img_id = "";
/*删除图片*/
function edit_del_img(value) {
    //删除图片列表
    //旧图片前台删除
    var img_path = $(value).prev().prev('img').attr('src');
    var img_id = $(value).prev().prev('img').attr('alt');
    if (img_path == undefined){
        return;
    }
    del_img_list = del_img_list+","+img_path;
    del_img_id = del_img_id+img_id+",";
    $(value).prev().prev('img').removeClass('upload_img_link old_img_link');
    $(value).prev().prev('img').attr('src', '/assets/img/add_bg.png');
    $(value).removeClass('product_add_img_after');
    $(value).parent().parent()[0].reset();
    var img_storage = $(value).attr('imgname');
    if (img_storage==undefined){
        return;
    }
    //新上传的图片--从服务器直接删除
    $.ajax({
        url: "/upload/picture/",
        type: "DELETE",
        contentType: "application/json",
        data: "name=" + img_storage,
        dataType: "json",
        success: function (result, status, xhr) {
            console.log(result);
            if (result.code == 100) {
                $(value).prev().prev('img').removeClass('upload_img_link old_img_link');
                $(value).prev().prev('img').attr('src', '/assets/img/add_bg.png');
                $(value).removeClass('product_add_img_after');
                $(value).parent().parent()[0].reset();
            }
        },
        error: function (result, status, xhr) {
            console.log("服务器错误")
        }
    });
}

$("#edit_product_btn").click(function () {
    var vali_flag = false;

    /*名称检查*/
    var name = $("#product_name_input").val();
    if (name==""){
        alert_msg("请输入商品名称！");
        scorll(0);
        return;
    }
    /*获取品牌id*/
    var brand = $("#select_brand_btn input:eq(0)").val();
    if (brand==undefined){
        alert_msg("请选择品牌！");
        scorll(90);
        return;
    }

    /*获取图片集合*/
    if ($('.old_img_link').length < 1 && $('.upload_img_link').length < 1){
        scorll(100);
        alert_msg("至少上传一张商品图片！");
        return;
    }

    var imgList = "";
    $.each($('.upload_img_link'),function (index,img) {
        if (index==0){
            imgList = $(img).attr('src');
        }else {
            imgList = imgList+","+$(img).attr('src');
        }
    });

    /*获取扩展参数*/
    /*校验 、 改变表格状态*/
    $("#properties_table").removeClass('am-table-compact');
    $.each($("#properties_table input"), function (index, val) {
        if ($(val).val()==""){
            $(val).parent().addClass("am-danger");
            alert_msg("请输入参数");
            vali_flag = true;
            return;
        }
        $(val).parent().removeClass("am-danger");
        $(val).parent().empty().append($(val).val())
    });
    var param_table = $.trim($("#properties_table").html());
    param_table = param_table.replace("  ","");
    if (vali_flag){
        return;
    }
    if(editor.txt.html().length<10){
        alert_msg("请输入10个以上的商品详情！");
        return;
    }

    $.ajax({
        url: "/product/update",
        type: "POST",
        dataType:"json",
        connectType:"application/json",
        beforeSend: function () {
            $('#loading_modal').modal({
                closeViaDimmer: false
            })
        },
        complete: function () {
            $('#loading_modal').modal('close');
        },
        data:{
            "id":$("#product_id").val(),
            "name":name,
            "subName":$("#product_sub_name_input").val(),
            "brandId":brand,
            "code":$("#product_code").val(),
            "images":imgList,
            "extend":editor.txt.html(),
            "param":param_table,
            "waitDelImg":del_img_list,
            "properties":del_img_id,
            "extendLength":editor.txt.text().length
        },
        success: function (result) {
            if (result.code!=100){
                alert_msg(result.extend.error)
            }else {
                alert_msg(result.msg)
            }
        },
        error:function (data) {
            alert_msg("服务器错误500");
        }
    });
});

$("#del_product_btn").click(function () {
    var code = $("#del_product_btn").attr('value');
    del_warning_verify(function () {
        $.ajax({
            url:"/product/"+code,
            type:"DELETE",
            success:function (result) {
                alert_msg("删除成功！");
            },
            error:function () {
                alert_msg("系统错误500");
            }
        })
    });
});