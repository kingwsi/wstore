/*构建分类级联选择*/
$(function () {
    $.getJSON("/product/category", "", function (categories) {
        //遍历json生成一级菜单
        $.each(categories, function (index, obj) {
            $("#category").append("<option value='" + obj.id + "'>" + obj.name + "</option>");
        });
        //给一级菜单添加事件，点击生成二级菜单
        $("#category").change(function () {
            var now_province = $(this).val();
            $("#secondary").html('<option value="">请选择二级分类</option>');
            $.each(categories, function (index, obj) {
                $.each(obj.categorySecondary, function (ind, secondary) {
                    if (obj.id == now_province) {
                        $("#secondary").append('<option value="' + secondary.id + '">' + secondary.name + '</option>');
                    }
                });
            });
        });
    });
});

/*构建属性集*/
$("#category").change(function () {
    $("#properties_checkbox").empty();
    $.getJSON("/product/category/properties/" + $(this).val(), "", function (properties) {
        if (properties == null) {
            $("#properties_checkbox").empty().append('<label class="am-checkbox-inline">' +
                '<div class="am-text-danger">该分类没有属性可以选择<div>' +
                '</label>');
            return;
        }
        $.each(properties.commonProperties, function (index, obj) {
            $("#properties_checkbox").append('<label class="am-checkbox-inline">\n' +
                '<input type="checkbox" value="' + obj.name + '" name="properties"> ' + obj.name + '\n' +
                '</label>')
        });
    });
});

/*添加图片*/
function add_img(value) {
    var data = new FormData($(value).parent().parent()[0]);
    console.log($(value).parent().parent()[0]);
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
                $("#product_form").append('<input type="hidden" name="productImage" value="'+result.extend.imgPath+'">');
                $(value).prev('img').attr('src', result.extend.imgPath);
                $(value).prev('img').removeClass('old_img_link');
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

/*品牌选择框*/
$("#select_brand_btn").click(function () {
    $("#select_brand_modal").modal({
        width: 790,
        closeViaDimmer: false
    })
});

/*选择品牌事件*/
function selected_brand(ele) {
    var brand_img = $(ele).clone().get(0);
    console.log(brand_img);
    $("#select_brand_btn").empty().append(brand_img);
    $("#select_brand_modal").modal('close');
}

/*提交商品数据*/
$("#add_product_btn").click(function () {
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
    /*获取分类*/
    var secondary = $("#secondary").val();
    if (secondary==""){
        alert_msg("请选择分类！");
        scorll(80);
        return;
    }

    /*获取属性集合*/
    var properties="";
    $.each($("input[name='properties']:checked"),function (index,p) {
        if (index==0){
            properties = $(p).val();
        }else {
            properties = properties+","+$(p).val();
        }
    });

    if (properties.length<1){
        alert_msg("请选择商品属性集！");
        scorll(100);
        return;
    }
    console.log(properties);
    /*获取图片集合*/
    var imgList = "";
    $.each($('.upload_img_link'),function (index,img) {
        if (index==0){
            imgList = $(img).attr('src');
        }else {
            imgList = imgList+","+$(img).attr('src');
        }
    });
    if (imgList.length<1){
        alert_msg("至少上传一张商品图片！");
        scorll(100);
        return;
    }

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
        url: "/product",
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
            "name":name,
            "subName":$("#product_sub_name_input").val(),
            "brandId":brand,
            "images":imgList,
            "extend":editor.txt.html(),
            "param":param_table,
            "properties":properties,
            "category":secondary,
            "extendLength":editor.txt.text().length
        },
        success: function (result) {
            if (result.code!=100){
                alert_msg(result.extend.error)
            }else {
                alert_msg("添加商品成功！");
                location.reload();
            }
        },
        error:function (data) {
            alert_msg("服务器错误500");
        }
    });
});

/*滚动*/
function scorll(posi){
    var $w = $(window);
    $w.smoothScroll({position: posi});
}
/*规格参数*/

/*添加属性组*/
$("#add_param_group_btn").click(function () {
    $("#properties_table").append("<tr class='table_group'>\n" +
        "                                            <td colspan='2'><input type='text' placeholder=\"基本属性\"/>\n" +
        "                                                <button class='am-close' onclick='remove_param(this)'>&times;</button>\n" +
        "                                            </td>\n" +
        "                                        </tr>\n" +
        "                                        <tr>\n" +
        "                                            <td><input type='text' placeholder=\"属性名\"/></td>\n" +
        "                                            <td><input type='text' placeholder=\"属性值\"/>\n" +
        "                                                <button class='am-close' onclick='remove_param(this)'>&times;</button>\n" +
        "                                            </td>\n" +
        "                                        </tr>")
});

/*添加属性*/
$("#add_param_btn").click(function () {
    $("#properties_table").append("<tr>\n" +
        "                                            <td><input type='text' placeholder=\"属性名\"/></td>\n" +
        "                                            <td><input type='text' placeholder=\"属性值\"/>\n" +
        "                                                <button class='am-close' onclick='remove_param(this)'>&times;</button>\n" +
        "                                            </td>\n" +
        "                                        </tr>")
});

function remove_param(value) {
    $(value).parent().parent().remove();
}

/*加载文本编辑器*/
var E = window.wangEditor;
var editor = new E('#editorbar','#editor');
editor.customConfig.uploadImgServer = '/product/extend/image';
editor.customConfig.menus = ['head', 'bold', 'fontSize', 'table', 'justify', 'italic', 'image', 'foreColor',
    'link', 'underline'];
editor.customConfig.uploadFileName = 'file';
editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024;
editor.customConfig.uploadImgMaxLength = 5;
editor.customConfig.showLinkImg = false;
editor.customConfig.debug = true;
editor.customConfig.multi_selection = false;
editor.create();
editor.customConfig.uploadImgHooks = {
    before: function (xhr, editor, files) {
    },
    success: function (xhr, editor, result) {
        alert(1)
    },
    fail: function (xhr, editor, result) {
        // 图片上传并返回结果，但图片插入错误时触发
        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
    },
    error: function (xhr, editor) {
        alert(1)
    },
    timeout: function (xhr, editor) {
        alert(1)
    }
};