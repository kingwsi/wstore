$("#add_address_btn").click(function () {
    $("#city-picker2").citypicker('destroy');
    $(":hidden").val("");
    $("#add_address").removeClass('hide')
});
$(".mz-dialog-close").click(function () {
    $(this).parent().parent().parent().addClass('hide');
});
$(".mz-btn.cancel").click(function () {
    $(this).parent().parent().parent().addClass('hide');
});
/*消息框btn*/
$(".mz-dialog-bottom>.msg-btn").click(function () {
    $("#msg_dialog").addClass('hide');
    location.reload();
});

function show_msg(msg) {
    $("#msg_dialog").removeClass('hide');
    $("#msg_dialog").find('.mz-dialog-content').text(msg);
}

$("#msg_dialog")
$(function () {
    $('#get-code').click(function () {//获取省市区县id
        //var $address = $('.address-adder-select .select-item');
        var msg = "";
        var type = "";
        //var length = $("#form_address").find("input[name='id']").val().length;
        if ($("#form_address").find("input[name='id']").val().length > 0) {
            msg = "更新成功！";
            type = "PUT";
        } else {
            msg = "添加成功！";
            type = "POST";
        }
        $(".mz-input-warp").removeClass('tips').attr("data-tips", "");
        $.ajax({
            url: "/address",
            type: type,
            data: $("#form_address").serialize(),
            success: function (result) {
                if (result.code == 100) {
                    $("#add_address").addClass('hide');
                    show_msg(msg);
                } else {
                    console.log(result);
                    if (result.code == 501) {
                        $("input[name=receiver]").parent().addClass('tips').attr("data-tips", result.msg);
                    } else if (result.code == 502) {
                        $("input[name=phone]").parent().addClass('tips').attr("data-tips", result.msg);
                    } else if (result.code == 503) {
                        $("input[name=address]").parent().addClass('tips').attr("data-tips", result.msg);
                    } else {
                        $("#add_address").addClass('hide');
                        show_msg(result.extend.error);
                    }
                }
            }

        });
        console.log($("#form_address").serialize());
    });

    $('.dropup .dropdown-menu a').click(function () {
        var $btn = $('#code-count');
        $btn.data('count', $(this).data('count')).find('.text').text($(this).text());
        if ($('#get-code .code').text()) {
            $('#get-code').trigger('click');
        }
    });

    /**
     * 更新地址
     */
    $(".order-address-checkbox-edit").click(function () {
        $("#city-picker2").citypicker('destroy');
        $("#form_address")[0].reset();
        $("#form_address .city-picker-input").citypicker('reset');
        var address_id = $(this).attr("data-address");
        $.ajax({
            url: "/user/address",
            type: "GET",
            data: "id=" + address_id,
            success: function (result) {
                if (result.code == 100) {
                    $("#add_address").removeClass('hide')
                    var address = result.extend.address;
                    $("#form_address").find("input[name='id']").val(address.id);
                    $("#form_address").find("input[name='receiver']").val(address.receiver);
                    $("#form_address").find("input[name='phone']").val(address.phone);
                    $("#form_address").find("input[name='address']").val(address.address);
                    $("#form_address").find("input[name='area']").attr("value", address.area);
                    console.log($("#form_address").find("input[name='area']"));
                    $("#form_address .placeholder").text(address.area);
                }
                console.log(result)
            }
        });
    });

    $(".order-address-checkbox-delete").click(function () {
        var address_id = $(this).attr("data-address");
        alert(address_id);
        $.ajax({
            url: "/address/" + address_id,
            type: "DELETE",
            success: function (result) {
                if (result.code == 100) {
                    show_msg("删除成功!");
                } else {
                    show_msg(result.extend.error)
                }
            }
        });
    });

    /*设置为默认*/
    $(".order-address-checkbox-set-default").click(function () {
        var address_id = $(this).attr("data-address");
        $.ajax({
            url: "/default/address",
            type: "POST",
            data: "id=" + address_id,
            success: function (result) {
                if (result.code == 100) {
                    show_msg("设置成功！");
                    return;
                }
            },
            error: function () {
                show_msg("服务器错误");
            }
        });
    });

    /*选择地址*/
    $("#addressList li").click(function () {
        if ($(this).hasClass('add')){
            return;
        }
        $("#addressList li").removeClass('checked');
        $(this).addClass('checked');
    });
});

$(document).ready(function () {
    /*计算价格*/
    var $total = $(".order-product-table-total>.total");
    var total_price = 0;
    $.each($total, function (index, item) {
        total_price += parseFloat($(item).text());
    });
    total_price = Number(total_price).toFixed(2);
    $(".pay-total").text(total_price);
});

//订单提交事件
$("#submitForm").click(function () {
    var $form = $('<form method="post" action="http://localhost:8096/order/form" name="order-form"></form>');
    $($form).append('<input type="hidden" name="address" value="'+$(".order-address-list .checked").attr("data-addr")+'">');
    $.each($("input[name='cartList']"),function (index, item) {
        console.log(item);
        $($form).append(item);
    });
    $($form).append('<input type="hidden" name="leaveWord" value="'+$(".order-product-remark-input").text()+'">');
    $('body').append($form);
    $('form[name="order-form"]').submit();
});