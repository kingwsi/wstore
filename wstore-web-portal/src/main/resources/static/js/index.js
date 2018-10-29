/* home */
function category_child_list(value){
	$(value).next("div").removeClass("mz-hide")
}
$(".category_nav>ul>li").hover(function(){
	$(this).find("div").removeClass("mz-hide")
},function(){
	$(this).find("div").addClass("mz-hide")
})
/* home-hot */
$('.bxslider').bxSlider({
			pager: false,
            controls: true,
			auto: true,
			minSlides: 2,
            maxSlides: 5,
			tickerHover: false,
            moveSlides: 5
        });