/*添加图片*/
function add_img(value) {
    var data = new FormData($(value).parent().parent()[0]);
    //console.log($(value).parent().parent()[0]);
    $.ajax({
        url: "/product/picture",
        type: "POST",
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        beforeSend: function () {
            $('#loading_modal').modal({
                closeViaDimmer: false
            })
        },
        complete: function () {
            $('#loading_modal').modal('close');
        },
        success: function (result, status, xhr) {
            if (result.code == 100) {
                $("#sku_pic").val(result.extend.imgPath);
                $(value).prev('img').attr('src', result.extend.imgPath);
                $(value).prev('img').addClass('upload_img_link');
                $(value).next('img').addClass('product_add_img_after').attr('imgName', result.extend.imgName);
            } else {
                alert_msg(result.extend.error);
                $(value).prev().prev('img').attr('src', '/assets/img/add_bg.png');
                $(value).removeClass('product_add_img_after');
                $(value).parent().parent()[0].reset();
            }
        },
        error: function (status, xhr) {
            alert_msg("服务器出错了，请稍后重试");
        }
    });
}

/*删除图片*/
function del_img(value) {
    var imgpath = $(value).attr('imgname');
    console.log(imgpath);
    if (imgpath == undefined){
        return;
    }
    $.ajax({
        url: "/upload/picture/",
        type: "DELETE",
        contentType: "application/json",
        data: "name=" + $(value).attr('imgname'),
        dataType: "json",
        success: function (result, status, xhr) {
            if (result.code == 100) {
                $(value).prev().prev('img').removeClass('upload_img_link');
                //隐藏域图片地址
                $("#sku_pic").val("");
                $(value).prev().prev('img').attr('src', '/assets/img/add_bg.png');
                $(value).removeClass('product_add_img_after');
                $(value).parent().parent()[0].reset();
            } else {
            }
        },
        error: function () {
            alert_msg("服务器出错了，请稍后重试");
        }
    });
}

$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/*编辑*/
function to_edit(ele){

    $("#table_window").addClass('am-hide');
    $("#edit_window").removeClass('am-hide');

    var input = $("#sku_form input");
    var length = input.length;
    for(var i=2;i<length;i++){
        var table_value = $(ele).parent().siblings(":eq("+i+")").text();
        $("#sku_form input:eq("+i+")").val(table_value)
    }
    /*构建属性*/
    var prop = $("form[name='properties_form']");
    var table = $(ele).parent().siblings(":eq(2)").find('span');
    var prop_id = $(ele).parent().siblings(":eq(2)").find('small');
    $.each(prop,function (index,par) {
        //取input节点 -属性值 -属性id
        var $prop_val = $(par)[0][2];
        var $prop_id = $(par)[0][3];
        //取table值
        var table_val = $($(table)[index]).text().trim();
        var prop_id_val = $($(prop_id)[index]).text().trim()
        //构建属性值
        $($prop_val).val(table_val);
        //构建sku属性id
        $($prop_id).val(prop_id_val)
    });
    /*构建图片*/
    var $img = $(ele).parent().siblings(":eq(0)").find('img')[0];
    var img = $($img).attr('src');
    $("#edit_img").attr("src",img);
    $("#sku_pic").val(img)
}

/*添加按钮*/
$("#add_sku_btn").click(function () {
    $("#sku_form")[0].reset();
    $("#table_window").addClass('am-hide');
    $("#edit_window").removeClass('am-hide');
});

/*关闭*/
$("#close_btn").click(function () {
    $("#edit_window").addClass('am-hide');
    $("#table_window").removeClass('am-hide');
});

/*提交sku数据*/
$("#add_sku_submit").click(function () {
    if ($("#sku_pic").val().length<1){
        alert_msg("请上传图片！");
        return;
    }
    var prop = $("form[name='properties_form']");
    var prop_list = new Array();
    $.each(prop,function (index,par) {
        prop_list[index] = $(par).serializeObject();
    });
    console.log(prop_list);
    var sku = $("#sku_form").serializeObject();
    $.ajax({
        url:"/product/sku",
        type:"POST",
        dataType:"json",
        data:{
            "properties":JSON.stringify(prop_list),
            "skuJson":JSON.stringify(sku)
        },
        success:function (result) {
            if (result.code==100){
                alert_msg("操作成功！");
                setTimeout(location.reload(),1000);
            } else {
                alert_msg(result.extend.error);
            }
        },
        error:function () {
            alert_msg("服务器出错了！请稍后重试")
        }
    })
});

/*删除*/
function del(val){
    var propCode = $("input[name='productCode']").val();
    var skuCode = $(val).parent().siblings(":eq(1)").text();
    console.log(propCode+","+skuCode);
    $.ajax({
        url:"/product/sku/"+skuCode+"/"+propCode,
        type:"DELETE",
        success:function () {
            setTimeout(alert_msg("删除成功！"));
        },
        error:function () {
            alert_msg("服务器错误500")
        }
    })
}