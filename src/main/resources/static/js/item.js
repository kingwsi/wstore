//SKU，Stock Keeping Uint(库存量单位)
var sku_list = [
    {}
];

function show_attr_item() {
    $.ajax({
        url: "/item/" + $("#code").text(),
        async: false,
        dataType: "json",
        type: "GET",
        success: function (result) {
            var keys = result.skuAllProps;
            //console.log(sku_list);
            sku_list = result.skuPolymerizations;
            console.log(result.skuPolymerizations);
            var html = '';
            for (k in keys) {
                html += '<div class="goods_attr" > <span class="label">' + keys[k]['propertyName'] + '</span>';
                html += '<ul>'
                for (k2 in keys[k]['value']) {
                    var _attr = keys[k]['value'][k2];
                    //console.log(_attr);
                    html += '<li class="text goods_sku" val="' + _attr + '" >';
                    html += '<span>' + _attr + '</span>';
                    html += '<s></s>';
                    html += '</li>'
                }
                html += '</ul>';
                html += '</div>';
            }
            $('#panel_sel').html(html);
        }
    });
}

show_attr_item();

//获取所有包含指定节点的路线
function filterProduct(ids) {
    var result = [];
    $(sku_list).each(function (k, v) {
        _attr = ';' + v['skuKey'] + ';';
        _all_ids_in = true;
        for (k in ids) {
            if (_attr.indexOf(';' + ids[k] + ';') == -1) {
                _all_ids_in = false;
                break;
            }
        }
        if (_all_ids_in) {
            result.push(v);
        }

    });
    return result;
}

//获取 经过已选节点 所有线路上的全部节点
// 根据已经选择得属性值，得到余下还能选择的属性值
function filterAttrs(ids) {
    var products = filterProduct(ids);
    //console.log(products);
    var result = [];
    $(products).each(function (k, v) {
        result = result.concat(v['skuKey'].split(';'));

    });
    return result;
}

$(document).ready(function () {
    $('.goods_attr .goods_sku').click(function () {
        if ($(this).hasClass('b')) {
            return; //被锁定了
        }
        if ($(this).hasClass('sel')) {
            $(this).removeClass('sel');
        } else {
            $(this).siblings().removeClass('sel');
            $(this).addClass('sel');

        }
        var select_ids = _getSelAttrId();
        //已经选择了的规格
        var $_sel_goods_attr = $('.goods_sku.sel').parents('.goods_attr');

        // step 1
        var all_ids = filterAttrs(select_ids);
        //比较选择的规格个数是否和键值个数是否一样
        if ($('.goods_sku.sel').length == all_ids.length) {
            //根据键值查询数据对应信息,并赋值
            $.each(sku_list, function (idx, obj) {
                sel_skuKey = all_ids.join(';');
                //console.log(sel_skuKey);
                if (obj['skuKey'] == sel_skuKey) {
                    $('#J_price').text((obj['price'] * 0.01).toFixed(2));
                    $('#stock').text(obj['stock']);
                    $('#sku_id').val(obj['id']);
                    $('#J_quantity').attr('data-max',obj['frozenStock']);

                    $("#buy_btn a").removeClass("disabled");
                    var $price_box = $("#J_price").parent()
                    $($price_box).removeClass("hide");
                    $($price_box).prev('div').addClass("hide");
                }
            });
        } else {
            $('#sku_id').val('');
            $("#buy_btn a").addClass("disabled");
            var $price_box = $("#J_price").parent();
            $($price_box).addClass("hide");
            $($price_box).prev('div').removeClass("hide");
        }

        //获取未选择的
        var $other_notsel_attr = $('.goods_attr').not($_sel_goods_attr);

        //设置为选择属性中的不可选节点
        $other_notsel_attr.each(function () {
            set_block($(this), all_ids);
        });

        //step 2
        //设置已选节点的同级节点是否可选
        $_sel_goods_attr.each(function () {
            update_2($(this));
        });


    });
});

//已选择的节点数组
function _getSelAttrId() {
    var list = [];
    $('.goods_attr .goods_sku.sel').each(function () {
        list.push($(this).attr('val'));
    });
    return list;
}


function update_2($goods_attr) {
    // 若该属性值 $li 是未选中状态的话，设置同级的其他属性是否可选
    var select_ids = _getSelAttrId();
    var $li = $goods_attr.find('.goods_sku.sel');

    var select_ids2 = del_array_val(select_ids, $li.attr('val'));

    var all_ids = filterAttrs(select_ids2);

    set_block($goods_attr, all_ids);
}

function set_block($goods_attr, all_ids) {
    //根据 $goods_attr下的所有节点是否在可选节点中（all_ids） 来设置可选状态
    $goods_attr.find('.goods_sku').each(function (k2, li2) {
        if ($.inArray($(li2).attr('val'), all_ids) == -1) {
            $(li2).addClass('b');
        } else {
            $(li2).removeClass('b');
        }
    });

}

function del_array_val(arr, val) {
    //去除 数组 arr中的 val ，返回一个新数组
    var a = [];
    for (k in arr) {
        if (arr[k] != val) {
            a.push(arr[k]);
        }
    }
    return a;
}

$("#J_previewThumb li").click(function () {
    var img = $(this).find('img').attr('src');
    $("#J_imgBooth img").attr("src", img);
});

$("#detailTabFixed li").click(function () {
    if ($(this).hasClass("current")) {
        return;
    } else {
        $(this).siblings().removeClass("current");
        $(this).addClass("current")
    }
});
$("#detailTabFixed li:eq(0)").click(function () {
    $("#introduce").siblings().css('display', 'none');
    $("#introduce").fadeIn();
});
$("#detailTabFixed li:eq(1)").click(function () {
    $("#standard").siblings().css('display', 'none');
    $("#standard").fadeIn();
});

/*模态框方法*/
function modal_msg() {
    var height = $(window).height() / 2 - 50;
    var width = $(window).width() / 2 - 125;
    $(".modal-msg").css({top: height, left: width});
    $(".modal-msg").fadeIn('100');
    setTimeout(function () { $(".modal-msg").fadeOut('slow'); }, 2500);
}

/*数量改变*/
$(".mod-control>.vm-minus").click(function () {
    console.log(parseInt($("#J_quantity").val())+1);
    update_quantity(parseInt($("#J_quantity").val())-1)
});
$(".mod-control>.vm-plus").click(function () {
    console.log(parseInt($("#J_quantity").val())+1);
    update_quantity(parseInt($("#J_quantity").val())+1)
});

$("#J_quantity").change(function () {
    update_quantity($(this).val());
});

function update_quantity(quantity){
    var max_quantity = parseInt($("#J_quantity").attr('data-max'));
    if (quantity<1){
        $("#J_quantity").val(1);
        return;
    }
    if (quantity>max_quantity){
        $("#J_quantity").val(max_quantity);
        return;
    }
    $("#J_quantity").val(quantity)
}

/*添加商品到购物车*/
$("#J_btnAddCart").click(function () {
    var id = $("#sku_id").val();
    var count = $("#J_quantity").val();
    if (id.length > 0) {
        $.ajax({
            url: "http://localhost:8095/cart",
            type: "POST",
            xhrFields: {
                withCredentials: true
            },
            data: {
                "id":id,
                "count":count
            },
            async: true,
            success: function (result) {
                modal_msg();
            },
            error: function () {
                alert("请稍后再试")
            }
        })
    }
});