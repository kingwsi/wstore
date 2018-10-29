/*初始化价格*/
$(document).ready(function () {
    // $.each($(".cart-col-total"), function (index, ele) {
    //     var price = $(ele).prev().prev().find('.cart-product-price').text();
    //     $(ele).find('.cart-product-price').text(price);
    // })
});

/*增加数量*/
$(".mz-adder-add").click(function () {
    $(this).parent().prev().empty();
    var $value = $(this).prev('div').find('input')[0];
    var num = parseInt($($value).val());
    var forzen = $($value).attr('data-frozen');
    if (num < forzen) {
        $($value).val(num + 1);
        update_price($(this), num + 1);
    } else {
        $(this).parent().prev().empty().append("限购" + forzen + "件");
    }
});
/*减少数量*/
$(".mz-adder-subtract").click(function () {
    $(this).parent().prev().empty();
    var $value = $(this).next('div').find('input')[0];
    var num = parseInt($($value).val());
    if (num > 1) {
        $($value).val(num - 1);
        update_price($(this), num - 1);
    }
});

/*数量输入框校验，防止非法参数*/
$(".mz-adder-input").change(function () {
    if ($(this).val() < 1) {
        $(this).val(1);
    } else if ($(this).val() >= $(this).attr('data-frozen')) {
        $(this).val($(this).attr('data-frozen'));
    } else {
        update_price($(this).parent(), $(this).val());
    }
});

/*商品价格改变*/
function update_price(ele, count) {
    var $price = $(ele).parent().parent().parent().prev('td').find('p span').get(0);
    var $ele = $(ele).parent().parent().parent().next('td').find('span').get(0);
    var price = parseFloat($($price).text());
    /*发送请求，修改数量*/
    $.ajax({
        url:"/cart/" + $(ele).parent().attr("data-cart") + "/" + $(ele).parent().find('input').val(),
        type:"POST",
        success:function (result) {
            if (result.code == 100) {
                $(ele).parent().find('input').attr("data-frozen", result.extend.frozenStock);
            } else {
                $(".cart-product-number-max .show").empty().append("限购" + $(ele).parent().find('input').attr("data-frozen") + "件");
            }
        },
        error:function () {
            alert("服务器正忙，请稍后重试");
        }
    });
    $($ele).text(Number(price * count).toFixed(2));
    /*数量调整后刷新价格*/
    checked();
};

/**
 * 商品选中 计算价格
 */
function checked() {
    var total_price = 0;
    var $sel = $(".cart-col-select>.checked");
    $("#cartSubmit").attr("disabled", "disabled");
    if ($sel.length > 0) {
        $("#cartSubmit").removeAttr("disabled");
        /*价格计算*/
        $.each($sel, function (index, sel) {
            var $find = $(sel).parent().parent().find(".cart-col-total").find('span').get(0);
            total_price += parseFloat($($find).text());
        });
        total_price = Number(total_price).toFixed(2);
        $("#totalPrice").text(total_price);
    } else {
        $("#totalPrice").text('0.00');
    }
}

/*按钮选中/取消*/
$(".cart-col-select .mz-checkbox").click(function () {
    if ($(this).hasClass("checked")) {
        $(this).removeClass("checked");
    } else {
        $(this).addClass("checked");
    }
    select_child_check($(this));
});

/**
 * 子类全选按钮监听
 * 全部选中或全部取消选中
 */
$(".cart-select-all .mz-checkbox").click(function () {
    var $li = $(this).parent().parent().next("table").find(".mz-checkbox");
    if ($(this).hasClass("checked")) {
        $(this).removeClass("checked");
        $($li).removeClass("checked");
    } else {
        $(this).addClass("checked");
        $($li).addClass("checked");
    }
});

/**
 * 子类选择检查
 * 子类全部选中则选中父类
 */
function select_child_check(ele) {
    var checkbox = $(ele).parent().parent().parent().find('.mz-checkbox').length;
    var cheched = $(ele).parent().parent().parent().find('.checked').length;
    /*父类选择框*/
    var $parent_checkbox = $(ele).parent().parent().parent().parent().prev().find('.mz-checkbox');
    if (checkbox === cheched) {
        $($parent_checkbox).addClass('checked');
    } else {
        $($parent_checkbox).removeClass('checked');
    }
}

/*全部选中事件*/
$('.cart-header .mz-checkbox').click(function () {
    if ($(this).hasClass('checked')) {
        $(this).removeClass('checked');
        $(".mz-checkbox").removeClass('checked');
    } else {
        $(this).addClass('checked');
        $(".mz-checkbox").addClass('checked');
    }
    checked();
});

/*是否选中购物车所有商品*/
function checked_all() {
    var checkbox = $(".cart-merchant-header>.cart-select-all>.mz-checkbox").length;
    var checked = $(".cart-merchant-header>.cart-select-all>.checked").length;
    if (checked === checkbox) {
        $('.cart-header .mz-checkbox').addClass('checked');
    } else {
        $('.cart-header .mz-checkbox').removeClass('checked');
    }
}

$(".mz-checkbox").click(function () {
    checked();
});

/*模态框方法*/
function modal_msg(msg) {
    var height = $(window).height() / 2 - 50;
    var width = $(window).width() / 2 - 125;
    $(".modal-msg p").text(msg);
    $(".modal-msg").css({top: height, left: width});
    $(".modal-msg").fadeIn(200);
    setTimeout(function () {
        $(".modal-msg").fadeOut(300);
    }, 2000);
}

/*删除购物车商品*/
$(".cart-col-ctrl>.cart-product-remove").click(function () {
    var $input = $(this).find('input');
    /*获取分组*/
    var group = $(this).parent().parent().parent().parent().prev().find('.cart-select-title').attr('data-group');
    var sku = $($input).val();
    $.ajax({
        url: "cart/" + group + "/" + sku,
        type: "DELETE",
        success: function (result) {
            if (result.code == 100) {
                modal_msg("删除成功");
                setTimeout(function () {
                    location.reload();
                }, 2000);
            } else {
                modal_msg(result.extend.error);
            }
        },
        error: function () {
            modal_msg("服务器出错");
        }
    })
});

/*结算*/
$("#cartSubmit").click(function () {
    if (undefined != $(this).attr('disabled')){
        return;
    };
    var $form = $('<form action="http://localhost:8096/order" method="POST"></form>');
    $(".cart-product .checked").each(function (index, item) {
        $($form).append('<input type="hidden" name="carts" value="'+$(item).attr('data-cart')+'">')
    });
    $('body').append($form);
    $($form).submit();

});