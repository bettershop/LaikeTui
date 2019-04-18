tinymce.PluginManager.add('image', function(editor) {
	function handleImageDialog() {
		var dom = editor.dom;
		var imgElm = editor.selection.getNode();
		require(['util'], function(u){
			var v = '';
			if (imgElm && imgElm.tagName.toLowerCase() == 'img') {
				v = imgElm.src;
			}
			u.image(v, function(val){
				data = {
					src: val.url,
					alt: val.filename,
					title: val.filename
				};

				if (!data["class"]) {
					delete data["class"];
				}

				if (!imgElm || imgElm.tagName.toLowerCase() != 'img') {
					data.id = '__mcenew';
					editor.focus();
					editor.selection.setContent(dom.createHTML('img', data));
					imgElm = dom.get('__mcenew');
					dom.setAttrib(imgElm, 'id', null);
				} else {
					dom.setAttribs(imgElm, data);
				}
			},'', {direct: true, multi : false} );
		});
	}

	editor.addButton('image', {
		icon: 'image',
		tooltip: 'Insert/edit image',
		onclick: handleImageDialog,
		stateSelector: 'img:not([data-mce-object],[data-mce-placeholder])'
	});

	editor.addMenuItem('image', {
		icon: 'image',
		text: 'Insert image',
		onclick: handleImageDialog,
		context: 'insert',
		prependToContext: true
	});
});
