 {literal}
<link href="style/css/common.css" rel="stylesheet">
<link href="style/css/common.v2.css" rel="stylesheet">
<script>var _upload_url = "index.php?module=system&action=uploadImg";</script>
<script src="style/js/plupload.full.min.js"></script>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="style/js/common.js"></script>
<script src="style/js/common.v2.js"></script>
<script>
	//添加分组
	$('.add-group').live('click', function() {
		var group = {
			name: '未命名',
			is_default: 0
		};
		saveGroup(group);
	});

	$('.picker-district').live('click', function() {
		$.districtPicker({
			success: function(res) {
				$('#province_id').val(res.province_id);
				$('#city_id').val(res.city_id);
				$('#district_id').val(res.district_id);
				$('.district-text').val(res.province_name + "-" + res.city_name + "-" + res.district_name);
			},
			error: function(e) {
				console.log(e);
			}
		});
	});

	//保存单个分组信息
	function saveGroup(group) {
		$.ajax({
			url: group_img,
			dataType: 'json',
			type: 'post',
			data: {
				m: 'save_group',
				data: JSON.stringify(group)
			},
			success: function(res) {
				if (res.code == 0) {
					file_app.group_list = res.data;
				} else {
					// $.myAlert({
					//     content:res.smg
					// });
					alert(res.msg, {
						time: 2000
					});

				}
			}
		});
	}

	//选择分组
	$('.selected-group').live('click', function(event) {
		var text_name = $(this).find(".upload_more").text()

		$(this).children(".upload_list").find(".upload_more").addClass("upload_mask")
		$(this).siblings(".selected-group").find(".upload_more").removeClass("upload_mask")

		if (text_name == "全部") {
			console.log(1)
			$(this).siblings(".selected-group").find(".upload_setting").css("display", "none")
		} else {
			$(this).children(".upload_list").find(".upload_setting").css("display", "block")
			$(this).siblings(".selected-group").find(".upload_setting").css("display", "none")
		}


		file_app.selected = $(this).data('index');
		$("input[name='checkbox']").prop('checked', false);
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		getGroupFile();
	});

	//打开设置
	$('.setting-group').live('click', function(event) {

		var index = $(this).data('index');
		file_app.edit_group = -1;
		file_app.selected = index;
		if (file_app.edit_setting == index) {
			file_app.edit_setting = -1;
		} else {
			file_app.edit_setting = index;
		}
		//getGroupFile();
		event.stopPropagation();
	});

	//编辑分组
	$('.edit-group').live('click', function(event) {
		event.stopPropagation(); //阻止事件冒泡；
		var index = $(this).data('index');
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		file_app.edit_group = index;
	});

	//删除分组
	$('.delete-group').live('click', function(event) {
		event.stopPropagation(); //阻止事件冒泡；
		var index = $(this).data('index');
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		file_app.selected = 0;
		var group = file_app.group_list[index];
		if (group.id) {
			group.is_delete = '1';
		} else {
			file_app.group_list.splice(index, 1);
		}
		saveGroup(group);
	});

	//确定修改
	$('.save-group').live('click', function(event) {
		event.stopPropagation(); //阻止事件冒泡；
		var group = file_app.group_list[file_app.edit_group];
		group.name = $('.name-group').val();
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		saveGroup(group);
	});

	//取消修改
	$('.cancel-group').live('click', function(event) {
		event.stopPropagation(); //阻止事件冒泡；
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
	});

	//阻止冒泡事件
	$('.name-group').live('click', function(event) {
		event.stopPropagation(); //阻止事件冒泡；
	});

	//保存
	$('.save-group-list').live('click', function() {

		if (file_app.file_list.length > 0) {
			var item = file_app.file_list[0];

			if (typeof _file_select.success === 'function') {
				_file_select.success({
					name: item.file_url,
					url: item.file_url,
				});
			}
		}

		file_app.file_list = [];
		file_app.list = [];
		file_app.selected = 0;
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		$('#file_select_modal').modal('hide');
	});

	//取消
	$('.cancel-group-list').live('click', function() {
		file_app.file_list = [];
		file_app.list = [];
		file_app.selected = 0;
		file_app.edit_group = -1;
		file_app.edit_setting = -1;
		$('#file_select_modal').modal('hide');
	});

	//全选图片
	$(document).live('change', "input[name='checkbox']", function() {
		var list = file_app.list;
		file_app.file_list = [];
		$(list).each(function(i) {
			if ($("input[name='checkbox']:checked").val() == 1) {
				list[i].selected = 1;
				var file = list[i];
				file_app.file_list.push(file);
			} else {
				list[i].selected = 0;
			}
		});
		addFile()
	});

	//选择图片
	$('#file_select_modal .file-item').live('click', function() {
		var index = $(this).data('index');
		file_app.file_list = [];
		if (file_app.list[index].selected == 1) {
			file_app.list[index].selected = 0;
		} else {
			file_app.list[index].selected = 1;
		}
		$("input[name='checkbox']").prop('checked', false);
		addFile()
	});

	//选中的图片
	function addFile() {
		var list = file_app.list;
		file_app.file_list = [];
		$(list).each(function(i) {
			if (list[i].selected == 1) {
				var file = list[i];
				file_app.file_list.push(file);
			}
		});
	}

	//删除选中的图片
	$('.delete-file-group').live('click', function() {
		var file_list = file_app.file_list;
		if (file_list.length == 0) {
			// $.myAlert({
			// 	content: '请先勾选需要删除的图片'
			// });
			alert('请先勾选需要删除的图片', {
				time: 2000
			});
			return false;
		}
		$.ajax({
			url: group_img,
			dataType: 'json',
			type: 'post',
			data: {
				m: 'delete',
				data: JSON.stringify(file_list)
			},
			success: function(res) {
				if (res.code == 0) {
					var list = file_app.list;
					var new_list = [];
					$(list).each(function(i) {
						if (list[i].selected != 1) {
							new_list.push(list[i]);
						}
					});
					file_app.list = new_list;
					file_app.file_list = [];
					$("input[name='checkbox']").prop('checked', false);
					// $.myAlert({
					// 	content: '删除成功'
					// });
					alert('删除成功', {
						time: 2000
					});
				} else {
					// $.myAlert({
					// 	content: res.msg
					// });
					alert(res.msg, {
						time: 2000
					});
				}
			}
		});
	});

	//移动图片到某个分组
	$('.batch-group').live('click', function() {
		var file_list = file_app.file_list;
		var index = $(this).data('index');
		if (file_list.length == 0) {

			layer.msg('请先勾选需要删除的图片', {
				time: 2000
			});
			return false;
		}
		$.ajax({
			url: group_img,
			dataType: 'json',
			type: 'post',
			data: {
				m: 'move',
				data: JSON.stringify(file_list),
				group_id: file_app.group_list[index].id
			},
			success: function(res) {
				if (res.code == 0) {
					file_app.file_list = [];
					$("input[name='checkbox']").prop('checked', false);
					file_app.selected = index;
					getGroupFile();
					// $.myAlert({
					// 	content: '移动成功'
					// });
					layer.msg('移动成功', {
						time: 2000
					});
				} else {
					// $.myAlert({
					// 	content: res.msg
					// });
					layer.msg(res.msg, {
						time: 2000
					});
				}
			}
		});
	});

	//获得分组下的图片
	function getGroupFile() {
		var more_btn = $('#file_select_modal .file-more');
		var loading_block = $('#file_select_modal .file-loading');
		var group_id = file_app.group_list[file_app.selected].id;
		// var group_id = 1;
		loading_block.show();
		more_btn.hide();
		$.ajax({
			url: _upload_file_list_url,
			data: {
				dataType: 'json',
				type: 'image',
				page: 1,
				group_id: group_id
			},
			success: function(res) {
				more_btn.attr('data-page', 2);
				loading_block.hide();
				more_btn.show();
				file_app.list = res.data.list;
			}
		});
	}

	//上传图片
	$('.upload-group .upload-file-group').live('click', function() {
		console.log(12321323)
		//分组ID
		var group_id = file_app.group_list[file_app.selected].id;
		if (group_id <= 0) {
			group_id = 0;
		}
		var btn = $(this);
		var group = btn.parents('.upload-group');
		var input = group.find('.file-input');
		var preview = group.find('.upload-preview');
		var preview_img = group.find('.upload-preview-img');
		$.upload_file({
			url: _upload_url + '&group_id=' + group_id,
			accept: group.attr('accept') || 'image/*',
			start: function() {
				// btn.btnLoading(btn.text());
			},
			success: function(res) {
				btn.btnReset();
				if (res.code === 1) {
					$.alert({
						content: res.msg
					});
					return;
				}
				getGroupFile();
			},
		});
	});
	String.prototype._trim = function(char, type) {
		if (char) {
			if (type == 'left') {
				return this.replace(new RegExp('^\\' + char + '+', 'g'), '');
			} else if (type == 'right') {
				return this.replace(new RegExp('\\' + char + '+$', 'g'), '');
			}
			return this.replace(new RegExp('^\\' + char + '+|\\' + char + '+$', 'g'), '');
		}
		return this.replace(/^\s+|\s+$/g, '');
	};

	var pick_link_modal;
	$(document).ready(function() {
		var pick_link_btn;

		pick_link_modal = new Vue({
			el: "#pick_link_modal",
			data: {
				in_array: function(val, arr) {
					return $.inArray(val, arr);
				},
				open_type: [],
				selected_link: null,
				link_list: null,
				is_required: false
			}
		});

		$(".link-list-select").live("change", function() {
			var i = $(this).val();
			var arr = pick_link_modal.link_list[i].params
			if (arr.length) {
				if (arr[0].required == 'required') {
					pick_link_modal.is_required = true
				} else {
					pick_link_modal.is_required = false
				}
			}

			if (i == "") {
				pick_link_modal.selected_link = null;
				return;
			}
			pick_link_modal.selected_link = pick_link_modal.link_list[i];
		});

		$(".pick-link-btn").live("click", function() {
			pick_link_btn = $(this);
			var open_type = $(this).attr("open-type");
			if (open_type && open_type != "") {
				open_type = open_type.split(",");
			} else {
				open_type = ["navigate", "switchTab", "wxapp"];
			}
			pick_link_modal.open_type = open_type;
			$.ajax({
				url: show_pages,
				method: 'get',
				dataType: 'json',
				success: function(res) {
					pick_link_modal.link_list = res;
					$(".pick-link-modal").modal("show");
				}
			})
		});

		$(".pick-link-confirm").live("click",function() {
				if ($('.paramVal').val() == '' && pick_link_modal.is_required) {
					console.log($('.paramVal').val())
					return;
				}

				var selected_link = pick_link_modal.selected_link;
				if (!selected_link) {
					$(".pick-link-modal").modal("hide");
					return;
				}
				var link_input = pick_link_btn.parents(".page-link-input").find(".link-input");
				var open_type_input = pick_link_btn.parents(".page-link-input").find(".link-open-type");
				var params = "";
				if (selected_link.params && selected_link.params.length > 0) {
					for (var i in selected_link.params) {
						params += selected_link.params[i].key + "=" + encodeURIComponent(selected_link.params[i].value) + "&";
					}
				}
				var link = selected_link.link;
				link += "?" + params;
				link = link._trim("&");
				link = link._trim("?");
				link_input.val(link).change();
				open_type_input.val(selected_link.open_type).change();
				$(".pick-link-modal").modal("hide");

			});

	});
</script>

{/literal}
