{literal}
<script src="style/js/Sortable.min.js"></script>
<script src="style/js/colorpicker.js"></script>
<script>
	
	$(document).ready(function() {
		$(".sidebar-1").niceScroll();
	})

	/*
	 * 获取浏览器竖向滚动条宽度
	 * 首先创建一个用户不可见、无滚动条的DIV，获取DIV宽度后，
	 * 再将DIV的Y轴滚动条设置为永远可见，再获取此时的DIV宽度
	 * 删除DIV后返回前后宽度的差值
	 *
	 * @return    Integer     竖向滚动条宽度
	 **/
	function getScrollWidth() {
		var noScroll, scroll, oDiv = document.createElement("DIV");
		oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
		noScroll = document.body.appendChild(oDiv).clientWidth;
		oDiv.style.overflowY = "scroll";
		scroll = oDiv.clientWidth;
		document.body.removeChild(oDiv);
		return noScroll - scroll;
	}

	if($('.sidebar-content')) {
		$('.sidebar-content').css('width', ($('.sidebar-content').width() + getScrollWidth()) + 'px');
	}

	$(document).on("click", "body > .sidebar .sidebar-2 .nav-item", function() {
		if($(this).hasClass('active')) {
			$(this).removeClass('active');
		} else {
			$(this).addClass('active');
		}
	});

	$(function() {
		$('[data-toggle="tooltip"]').tooltip({
			animation: false,
		})
	});

	$(document).on("click", ".input-hide .tip-block", function() {
		$(this).hide();
	});

	$(document).on("click", ".input-group .dropdown-item", function() {
		var val = $.trim($(this).text());
		$(this).parents(".input-group").find(".form-control").val(val);
	});

	//图片库vue对象
	var file_app;
</script>
{/literal}