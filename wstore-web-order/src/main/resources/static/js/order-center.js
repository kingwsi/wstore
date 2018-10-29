$(document).ready(function () {
    getOrders(-1);
    $(".order-main .type-tab-btn a").click(function () {
        $(".order-main .type-tab-btn a").removeClass('active');
        getOrders($(this).attr('data-type'));
        $(this).addClass('active');
    });
});
function getOrders(status) {
    $.ajax({
        url:"/order/center",
        type:"POST",
        data:"status="+status,
        cache: false,
        beforeSend: function () {
            $("#tableList").empty().append('<div class="ui-load-content J_no_data"><p class="J_no_data_des">加载中...</p></div>');
        },
        success:function (result) {
            if (result.code==100){
                if (result.extend.orders.length == 0){
                    $("#tableList").empty().append('<div class="ui-load-content J_no_data"><p class="J_no_data_des">您暂无此类订单，赶快去下单吧！</p></div>')
                }else {
                    build_order_list(result.extend.orders)
                }
            } else{
                window.location.href='http://localhost:8100/login?'+ encodeURIComponent(encodeURIComponent(window.location.href));
            }

        },
        error:function () {
            $("#tableList").empty().append('<div class="ui-load-content J_no_data"><p class="J_no_data_des" onclick="getOrders(-1)">加载失败，点击重试</p></div>')
        }
    });
}
function build_order_list(orders) {
    var order_list = $("#tableList").empty();
    $.each(orders, function (index, order) {
        //构建订单状态
        var status_view = '';
        var operate = '';
        switch (order.status) {
            case 0:
                status_view = "待付款";
                operate = '<li class="goPay"><a href="/pay?sn=8967585798" target="_blank">立即付款</a></li>\n' +
                    '                                        <li class="cancel">取消订单</li>\n';
                break;
            case 1:
                status_view = "待发货";
                operate = '<li class="more"><a href="/pay?sn=8967585798" target="_blank">查看详情</a></li>\n' +
                    '                                        <li class="cancel">取消订单</li>\n';
                break;
            case 3:
                status_view = "待收货";
                operate = '<li class="cancel"><a href="/pay?sn=8967585798">查看详情</a></li>\n';
                break;
            case 4:
                status_view = "已完成";
                operate = '<li class="cancel"><a href="/pay?sn=8967585798">查看详情</a></li>\n';
                break;
            case 5:
                status_view = "交易关闭";
                operate = '<li class="cancel"><a href="/pay?sn=8967585798">查看详情</a></li>\n';
                break;
        }
        status_view = '<div></div>' + status_view + '<br>';
        //构建订单详情列表
        var detailList = '';
        $.each(order.orderDetails, function (index, detail) {
            detailList += '<div class="j-notCart">\n' +
                '                                        <div class="shop j-shop">\n' +
                '                                            <div class="item b-t clearfix j-iamMain">\n' +
                '                                                <a class="productDetail nameWidth" href="#" target="_blank">\n' +
                '                                                    <img src="' + detail.skuPic + '" class="f-fl"></a>\n' +
                '                                                <div class="describe f-fl ">\n' +
                '                                                    <div class="vertic clearfix">\n' +
                '                                      <span class="clearfix">\n' +
                '                                          <a class="productDetail nameWidth" href="#" target="_blank">\n' + detail.productName +
                '                                          </a>\n' +
                '\n' +
                '                                          <p>\n' +
                '                                            ￥' + Number(detail.price / 100).toFixed(2) + '\n' +
                '                                                <i class="text-gray">￥' + Number(detail.marketPrice / 100).toFixed(2) + '</i>\n' +
                '                                            ×1\n' +
                '                                          </p>\n' +
                '                                      </span>\n' +
                '                                                    </div>\n' +
                '                                                </div>\n' +
                '                                                <input type="hidden" class="orderSn" value="21101231260840408091">\n' +
                '                                                <input type="hidden" class="isCart" value="0">\n' +
                '                                                <input class="orderSnSon" type="hidden" value="21101231260840409091">\n' +
                '                                                <input type="hidden" class="supplierId" value="1">\n' +
                '                                                <input type="hidden" class="supportXiaoneng" value="1">\n' +
                '                                                <input type="hidden" class="brandName" value="魅族">\n' +
                '                                            </div>\n' +
                '                                        </div>\n' +
                '\n' +
                '                                    </div>'
        });
        var order_table = '<div class="ui-load-content"><input id="" type="hidden" value="1">\n' +
            '                        <table class="orderItem">\n' +
            '                            <tbody>\n' +
            '                            <tr class="trHead">\n' +
            '                                <td colspan="4" class="title clearfix">\n' +
            '                                    <div class="f-fl">下单时间：<span class="time">' + order.createTime + '</span>订单号：<span\n' +
            '                                            class="orderNumber">' + order.orderSn + '</span>\n' +
            '                                    </div>\n' +
            '                                </td>\n' +
            '                            </tr>\n' +
            '                            <tr class="list-box b-l b-r b-b">\n' +
            '                                <td class="list b-r j-iamCart">\n' +
            detailList +
            '                                </td>\n' +
            '                                <td class="b-r w125 center price b-t">\n' +
            '                                    <div class="priceDiv">\n' +
            '                                        ￥ ' + Number(order.totalMoney/100).toFixed(2) + '\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </td>\n' +
            '                                <td class="b-r w125 center state b-t">\n' +
            '                                    <div class="stateDiv">\n' +
            status_view +
            '\n' +
            '\n' +
            '                                    </div>\n' +
            '                                </td>\n' +
            '                                <td class="w125 center opreat b-t">\n' +
            '                                    <ul>\n' +
            operate +
            '                                    </ul>\n' +
            '                                </td>\n' +
            '                            </tr>\n' +
            '                            <tr class="empty">\n' +
            '                                <td></td>\n' +
            '                            </tr>\n' +
            '                            </tbody>\n' +
            '                        </table>\n' +
            '                    </div>';

        $(order_list).append(order_table)
    });

}