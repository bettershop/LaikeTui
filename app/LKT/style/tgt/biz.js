define(['bootstrap'], function($){
	var biz = {};
	
	biz.user = {};
	/**
	 * vals array 已经选中的用户编号
	 * callback function(users) 选择成功后回调
	 * options object mode 已经选中的用户展示方式, visible 显示出已经选中的用户, 并标记为选中状态(默认值) invisible 隐藏已经选中的用户
	 */
	biz.user.browser = function(vals, callback, options) {
		var mode = 'visible';
		if(options && options.mode){
			mode = options.mode;
		}
		var uids = '0';
		if($.isArray(vals) && vals.length>0){
			uids = vals.join();
		}
		if ($('#user-browser-dialog')[0]) {
			$('#user-browser-dialog').remove();
		}
		var footer = 
			'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>' +
			'<button type="button" class="btn btn-primary">确认</button>';
		var url = './index.php?c=utility&a=user&do=browser&callback=aMember'+'&mode='+mode+'&uids=' + uids;
		var dialog = util.dialog('请选择用户', '数据加载中......', footer,{containerName : 'user-browser-dialog'});
		dialog.modal('show');
		dialog.on('shown.bs.modal', function(){
			window.aMember.pIndex = 1;
			window.aMember.query();
		});
		dialog.find('.modal-footer .btn-primary').click(function(){
			var users = [];
			var chks = $('.user-browser .btn-primary');
			if(chks.length>0){
				chks.each(function(){
					users.push($(this).attr('js-uid'));
				});
				
				if($.isFunction(callback)) {
					callback(users);
					dialog.modal('hide');
				}
			}
		});
		
		window.aMember = {
			pIndex : 1,
			query : function() {
				var data = {
					keyword: $('#keyword').val(),
					page: aMember.pIndex,
					callback:'aMember',
					mode: mode,
					uids: uids
				};
				$.post(url, data, function(dat){
					dialog.find('.modal-body').html(dat);
					if (options.direct) {
						dialog.find('.js-btn-select').click(function(){
							dialog.find('.modal-footer .btn-primary').trigger('click');
						});
					}
					dialog.find('.pagination a').click(function(){
						window.aMember.pIndex = $(this).attr('page');
						window.aMember.query();
					});
				});
			}
		};
	}
	return biz;
});
