/*分类管理*/
$("#category_add_btn").click(function () {
    $.ajax({
        url: "/category",
        type: "POST",
        data: $("#category_form").serialize(),
        success: function (result) {
            if (result.code != 100) {
                $("#warning_msg_category").addClass("am-text-danger");
                $("#warning_msg_category").empty().append(result.extend.error);
                return;
            }
            $("#add-first-category-modal").modal('close');
            reload_category();
        }
    })
});

$("#category_secondary_add_btn").click(function () {
    $.ajax({
        url: "/categorySecondary",
        type: "post",
        data: $("#category_secondary_form").serialize(),
        success: function (result) {
            if (result.code != 100) {
                $("#warning_msg_category_secondary").addClass("am-text-danger");
                $("#warning_msg_category_secondary").empty().append(result.extend.error);
                return;
            }
            $("#add-category-secondary-modal").modal('close');
            reload_secondary_category();
        },
        error: function (result) {

        }
    })
})

/*编辑一级分类公共方法*/
function common_category(category, link, ele, modal, fun) {
    $.ajax({
        url: link,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(category),
        dataType: "json",
        success: function (result) {
            if (result.code != 100) {
                $(ele).addClass("am-text-danger");
                $(ele).empty().append(result.extend.error);
                return false;
            }
            $(modal).modal('close');
            window[fun].call(this);
        }
    })
}

/*编辑一级分类*/
$("#category_edit_btn").click(function () {
    var category = {
        id: $("#edit_category_form_id").val(),
        name: $("#edit_category_form_name").val(),
        sort: $("#edit_category_form_sort").val()
    };
    /*调用公共方法 param json数据, 提交地址， 警告文本， 模态窗口， 刷新页面*/
    common_category(category, "/category", "#warning_msg_category_edit", "#edit-first-category-modal", "reload_category");
});

/*编辑二级分类*/
$("#category_secondary_edit_btn").click(function () {
    var categorySecondary = {
        id: $("#edit_category_secondary_form_id").val(),
        categoryId: $("#edit_category_secondary_form_category").val(),
        name: $("#edit_category_secondary_form_name").val(),
        sort: $("#edit_category_secondary_form_sort").val()
    };
    console.log(categorySecondary);
    common_category(categorySecondary, "/categorySecondary", "#warning_msg_category_secondary_edit", "#edit-category-secondary-modal", "reload_secondary_category");
});

/*编辑窗口*/
function edit_category(ele) {
    $("#edit_category_form_id").val($(ele).parent().parent().siblings(":eq(0)").text());
    $("#edit_category_form_name").val($(ele).parent().parent().siblings(":eq(1)").text());
    $("#edit-first-category-modal").modal({
        width: 800,
        closeViaDimmer: false
    });
}

function edit_category_secondary(ele) {
    $("#edit_category_secondary_form_id").val($(ele).parent().parent().siblings(":eq(0)").text())
    $("#edit_category_secondary_form_name").val($(ele).parent().parent().siblings(":eq(1)").text());
    $("#edit_category_secondary_form_category").val($(ele).parent().parent().siblings(":eq(3)").text());
    $("#edit-category-secondary-modal").modal({
        width: 800,
        closeViaDimmer: false
    });
}

/*标记为隐藏  param 当前分类 id，状态 0隐藏/1显示， 一级分类 1/二级分类 2 */
function hide_category(ele, stat, flag) {
    var category = {
        id: $(ele).parent().parent().siblings(":eq(0)").text(),
        name: $(ele).parent().parent().siblings(":eq(1)").text()
    };
    category.status = stat;

    var categorySecondary = {
        id: $(ele).parent().parent().siblings(":eq(0)").text(),
        name: $(ele).parent().parent().siblings(":eq(1)").text()
    };
    categorySecondary.status = stat;

    /*flag = 1 一级分类 flag = 2 二级分类*/
    if (flag == 1) {
        common_category(category, "/category", "#", "#", "reload_category");
    }
    if (flag == 2) {
        common_category(categorySecondary, "/categorySecondary", "#", "#", "reload_secondary_category");
    }
}

/*属性编辑窗口*/
function edit_properties(ele){
    var cid = $(ele).parent().parent().siblings(":eq(0)").text();
    $("#category_id").val(cid);
    var $input = $("#edit_properties input")[1];
    $($input).val("");
    $.ajax({
        url: "/category/properties/"+cid,
        type: "GET",
        success: function (result) {

            $.each(result.extend.properties, function (index, value) {
                $($input).val($($input).val() + value + "，");
            })
        }
    });
    $('#edit_properties').modal({
        relatedTarget: this,
        onConfirm: function (e) {
            $.ajax({
                url: "/category/property",
                type:"POST",
                data:{
                    "properties":e.data,
                    "categoryId":$("#category_id").val()
                },
                complete: function () {
                    $('#edit_properties').modal('close');
                },
                success:function (result) {
                    alert_msg("属性集修改成功！");
                },
                error:function () {
                    alert_msg("服务器开小差了,请稍后重试！");
                }
            });
        },
        onCancel: function (e) {
        }
    });
}

function reload_category() {
    location.reload();
}

$("#category_secondary_panel a").click();

function reload_secondary_category() {
    location.reload();
}

$(document).ready(function () {
    $("#left_nav li a").removeClass('active');
    $("#left_nav li:eq(4)").children('a').addClass('active');
});