tinymce.PluginManager.add('multiimage', function(editor) {
	function handleImageDialog() {
		var dom = editor.dom;
		var imgElm = editor.selection.getNode();
		require(['util'], function(u){
			var v = '';
			if (imgElm && imgElm.tagName.toLowerCase() == 'img') {
				v = imgElm.src;
			}
			u.uploadMultiPictures(function(list){
				for(var i=0;i<list.length;i++){
					data = {
						src: list[i]['url'],
						alt: list[i]['filename'],
						title: list[i]['filename']
					};
					editor.focus();
					editor.selection.setContent(dom.createHTML('img', data));
				}
			});
		});
	}

	editor.addButton('multiimage', {
		icon: 'image',
		tooltip: 'Insert multi image',
		onclick: handleImageDialog,
		stateSelector: 'img:not([data-mce-object],[data-mce-placeholder])'
	});

	editor.addMenuItem('multiimage', {
		icon: 'image',
		text: 'Insert multi image',
		onclick: handleImageDialog,
		context: 'insert',
		prependToContext: true
	});
});
