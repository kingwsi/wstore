$("#left_nav li a").removeClass('active');
$("#left_nav li:eq(3)").children('a').addClass('active');

/*上架/下架*/
function onsale(value,status){
    var id = $(value).parent().parent().siblings(":eq(0)")[0];
    var code = $(value).parent().parent().siblings(":eq(4)").text();
    var msg = "上架";
    if (status==0){
        msg = "下架";
    }
    open_warning_modal("确定"+msg+"该商品？",function () {
        $.ajax({
            url:"/product/status/"+status,
            type:"POST",
            data:{
                "id":$(id).val(),
                "code":code
            },
            success:function (result) {
                if (result.code!=100){
                    alert_msg("无法上架，请检查该商品下是否添加sku");
                }else {
                    location.reload();
                }
            },
            error:function () {
                alert("服务器错误，请过会再试试吧!");
            }
        })
    });
}
function batch_sale_warning(status) {
    var msg = "下架";
    if(status==1){
        msg = "上架";
    }
    open_warning_modal(msg+"全部商品？",function() {
        $.ajax({
            url:"/products/status/"+status,
            type:"POST",
            beforeSend: function () {
                $('#loading_modal').modal({
                    closeViaDimmer: false
                })
            },
            complete: function () {
                $('#loading_modal').modal('close');
            },
            success:function (result) {
                if(result.code==100){
                    alert_msg(msg+"成功！共"+msg+result.extend.count+"个商品");
                    setTimeout(function(){
                        location.replace(location.href);
                    },2000);
                }else {
                    alert_msg("非法参数！");
                }
            },
            error:function () {
                alert_msg("服务器错误！")
            }
        });
    })
}
function add_sku_window(val) {
    window.open("/product/sku/"+val);
}

function open_edit_window(val) {
    window.open("/product/edit/"+val);
}