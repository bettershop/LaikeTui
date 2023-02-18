$(function() {
	lanPosScroll();
	modelConfirm();
	shortcutChoice();
})

function lanPosScroll() {
	var index = 0;
	var shortcutBox = $('.modal .modal-dialog .shortcut-add-box');
	var shortcutBoxList = $('.shortcut-add-box-right .shortcut-element-list');
	$('.lanPos').css('top', $('.shortcut-add-box-left .active').position().top);
	$('.shortcut-add-box-left ul li').hover(function() {
		$('.lanPos').css('top', $(this).position().top);
	}, function() {
		$('.lanPos').css('top', $('.shortcut-add-box-left .active').position().top);
	})
	$('.shortcut-add-box-left ul li').click(function() {
		for(var i = 0; i < $('.shortcut-add-box-left ul li').size(); i++) {
			if(this == $('.shortcut-add-box-left ul li').get(i)) {
				$('.shortcut-add-box-left ul li').eq(i).addClass('active');
			} else {
				$('.shortcut-add-box-left ul li').eq(i).removeClass('active');
			}
		};
		index = $('.shortcut-add-box-left ul li').index(this);
		var s_box_cur = shortcutBoxList.eq(index)
		var posi = s_box_cur.position();
		shortcutBox.animate({
			scrollTop: posi.top
		}, 200);
	})
};

function shortcutChoice() {
	var shortcutBtn = $('.shortcut-element-box>li');
	shortcutBtn.click(function() {
		// alert('111');

		$('.shortcut-element-box>li').each(function(i){
		   		$(this).removeClass('shortcut-selected');
		 });
		if($(this).hasClass('shortcut-selected')) {
			$(this).removeClass('shortcut-selected');
		} else {
			$(this).addClass('shortcut-selected');
		}
	})
};

function modelConfirm() {
	var selectShortcut_box = $('#select-shortcut-box');
	var shortcutBoxLi = $('.shortcut-box li:not(:last)');
	$('#shortcutEnter').click(function() {
		$('#myModal').modal('hide');
		shortcutBoxLi.remove();

		function init_hasShortcut_html() {
			var select_iClass_data = [];
			var select_spanText_data = [];
			var shortcutSelected_data = [];
			var link_data = [];
			var has_p_data = [];
			var has_l_data = [];
			var has_iclass_data = [];
			var hasShortcut_html = '';
			var acticveSelect = null;
			acticveSelect = $('.shortcut-add-box-right').find('.shortcut-selected');
			$.each(acticveSelect, function(i) {
				select_iClass_data.push(acticveSelect.eq(i).find('i').attr('class'));
				select_spanText_data.push(acticveSelect.eq(i).find('span').text());
				link_data.push(acticveSelect.eq(i).find('input').val());
			});
			$.each($('.shortcut-box li:not(:last)'), function(i) {
				has_p_data.push($('.shortcut-box li:not(:last)').find('p').text());
			});
			$.each($('.shortcut-box li:not(:last)'), function(i) {
				has_l_data = $('.shortcut-box li:not(:last)').find('input').value();
			});
			$.each($('.shortcut-box li:not(:last)'), function(i) {
				has_iclass_data.push($('.shortcut-box li:not(:last)').find('i').attr('class'));
			});

			function test(a, b) {
				var c = [];
				var tmp = a.concat(b);
				var o = {};
				for(var i = 0; i < tmp.length; i++)(tmp[i] in o) ? o[tmp[i]]++ : o[tmp[i]] = 1;
				for(var x in o)
					if(o[x] == 1) c.push(x);
				return c;
			}
			for(var i = 0; i < test(has_iclass_data, select_iClass_data).length; i++) {
				var select_i_class = test(has_iclass_data, select_iClass_data)[i];
				var select_i_span = test(has_p_data, select_spanText_data)[i];
				var select_i_link = test(has_l_data, link_data)[i];
				var select_person = {
					"iClass": select_i_class,
					'spanText': select_i_span,
					'link':select_i_link
				};
				shortcutSelected_data.push(select_person);
			};
			console.log(shortcutSelected_data);
			$("#banner_url").val(shortcutSelected_data[0].link);
			for(var j = 0; j < shortcutSelected_data.length; j++) {
				var hasObj = shortcutSelected_data[j];
				hasShortcut_html += '<li class="col-xs-4 col-sm-4 col-md-3 col-lg-3">';
				hasShortcut_html += '<div>';
				hasShortcut_html += '<i class="' + hasObj.iClass + '">' + '</i>';
				hasShortcut_html += '<p>' + hasObj.spanText + '</p>';
				hasShortcut_html += '</div>';
				hasShortcut_html += '</li>';
			};
			return hasShortcut_html;
		}
		$('.shortcut-box li:not(:last)').remove();
		$('.shortcut-box li:last').before(init_hasShortcut_html());
		$.each($('.shortcut-box li:not(:last)'), function(i) {
			var bgIndex = (i + 0) % 5;
			switch(bgIndex) {
				case 1:
					$('.shortcut-box li:not(:last)').eq(i).find('div').css('background', '#82D5FF');
					break;
				case 2:
					$('.shortcut-box li:not(:last)').eq(i).find('div').css('background', '#C0E774');
					break;
				case 3:
					$('.shortcut-box li:not(:last)').eq(i).find('div').css('background', '#E96C96');
					break;
				case 4:
					$('.shortcut-box li:not(:last)').eq(i).find('div').css('background', '#f7d418');
					break;
				case 0:
					$('.shortcut-box li:not(:last)').eq(i).find('div').css('background', '#59d6ac');
					break;
			}
		})
	});
}