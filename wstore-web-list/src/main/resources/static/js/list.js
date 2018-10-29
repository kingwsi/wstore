$(document).ready(function () {
    //build_list("");
    $("#search_btn").click(function () {
        var keyword = $("#search_btn").siblings(":eq(0)").val();
        build_list(keyword);
    })
});

/*function build_list(keyword) {
    $("#empty").fadeOut();
    $("#goodsListWrap").hide();
    $.ajax({
        url: "/search",
        type: "GET",
        data: "keyword=" + keyword,
        success: function (result) {
            $("#goodsListWrap").empty();
            if (result.length < 1) {
                $("#empty").fadeIn();
                return;
            }
            $("#goodsListWrap").fadeIn();
            $.each(result, function (index, product) {
                var li = $('<li class=\"gl-item\"></li>').append('<a target="_blank" href="' + product.id + '" title="">\n' +
                    '                        <div class="gl-item-wrap">\n' +
                    '                            <div class="mod-pic">\n' +
                    '                                <img width="220" height="220" class="lazy j-modProduct" src="' + product.mainPic + '">\n' +
                    '                            </div>\n' +
                    '                            <div class="item-slide j-pro-wrap">\n' +
                    '                                \n' +
                    '                            </div>\n' +
                    '\n' +
                    '                            \n' +
                    '\n' +
                    '\n' +
                    '                            <h2>' + product.name + '</h2>\n' +
                    '                            <h3 class="gray">\n' +
                    product.subName +
                    '                            </h3>\n' +
                    '                            <dd class="mod-price">\n' +
                    '                                <span>￥</span>\n' +
                    '                                <span class="vm-price">' + (product.minPrice) * 0.01 + '</span>\n' +
                    '                                <span class="vm-start">起</span>\n' +
                    '                            </dd>\n' +
                    '                        </div>\n' +
                    '                    </a>');
                $("#goodsListWrap").append(li);
            });

        },
        error: function () {
            alert("500")
        }
    })
}*/

$("#show_all_category").click(function () {
    var attr = $("#category_box").attr("style");
    if (attr == undefined || attr == "") {
        $("#category_box").attr("style", "height:auto");
    } else {
        $("#category_box").attr("style", "");
    }
});

$("#show_all_brand").click(function () {
    var attr = $("#brand_box").attr("style");
    if (attr == undefined || attr == "") {
        $("#brand_box").attr("style", "height:auto");
    } else {
        $("#brand_box").attr("style", "");
    }
});

$(document).ready(function () {
    /*分类选择*/
    $("#category_box a").click(function () {
        var cid = $(this).attr('value');
        var url = update_url('category', cid);
        url = update_url('page', '0', url);
        location.href = url;
    });
    active_nav("#category_box a","category");
    active_nav("#brand_box a","brand");
    /*品牌选择*/
    $("#brand_box a").click(function () {
        var bid = $(this).attr('value');
        var url = update_url('brand', bid);
        url = update_url('page', '0', url);
        location.href = url;
    });
    /*分页*/
    $("#pages a").click(function () {
        if ($(this).hasClass('current')) {
            return;
        }
        var num = $(this).attr('data');
        var href = update_url('page', num - 1);
        location.href = href;
    });

});

/**
 * url重新组合解析去重
 * @param param
 * @param val
 * @param url
 * @returns {*}
 */
function update_url(param, val, url) {
    if (url == undefined) {
        url = window.location.search;
    }
    var params = url.split('?');
    if (params == "") {
        url = param + "=" + val;
        return '?' + url;
    }
    url = url.substring(1, url.length);
    params = url.split('&&');
    url = "";
    var flag = 0;
    for (p in params) {
        if (params[p].match(param) != null) {
            if (val == 0) {
                params[p] = param + "=0";
            } else {
                params[p] = param + "=" + val;
            }
            flag = 1;
        }
        url += params[p] + '&&';
    }
    url = url.substring(0, url.length - 2);
    if (flag == 0) {
        url = url + "&&" + param + "=" + val;
    }
    return "?" + url;
}

/**
 * 获取url指定参数值
 * @param param    参数名
 * @param url      url
 * @returns {string}
 */
function get_url_param(param, url) {
    if (url == undefined) {
        url = window.location.search;
    }
    var params = url.split('?');
    if (params == "") {
        return;
    }
    url = url.substring(1, url.length);
    params = url.split('&&');
    if (params == "") {
        return;
    }
    for (x in params) {
        if (params[x].match(param) != null) {
            var substring = params[x].substring(param.length + 1, params[x].length);
            return substring;
        }
    }
}

/**
 * 分类导航栏选择
 * @param ele
 * @param param
 */
function active_nav(ele,param) {
    $ele = $(ele);
    $.each($ele, function () {
        var urlParam = get_url_param(param);
        if (urlParam === undefined) {
            $($ele).eq(0).parent('li').attr('class', 'active');
            return;
        }
        if ($(this).attr('value') === urlParam) {
            $(this).parent('li').attr('class', 'active')
        } else {
            $(this).parent('li').attr('class', '')
        }
    });
}