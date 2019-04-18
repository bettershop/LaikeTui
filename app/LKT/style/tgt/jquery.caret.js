(function(jQuery){
	jQuery.fn.extend({
		/**  
		 * 选中内容  
		 */  
		selectContents: function(){	
			$(this).each(function(i){	
				var node = this;	
				var selection, range, doc, win;	
				if ((doc = node.ownerDocument) &&	
					(win = doc.defaultView) &&	
					typeof win.getSelection != 'undefined' &&	
					typeof doc.createRange != 'undefined' &&	
					(selection = window.getSelection()) &&	
					typeof selection.removeAllRanges != 'undefined')	
				{	
					range = doc.createRange();	
					range.selectNode(node);	
					if(i == 0){	
						selection.removeAllRanges();	
					}	
					selection.addRange(range);	
				}	
				else if (document.body &&	
						 typeof document.body.createTextRange != 'undefined' &&	
						 (range = document.body.createTextRange()))	
				{	
					range.moveToElementText(node);	
					range.select();	
				}	
			});	
		},	
		/**  
		 * 初始化对象以支持光标处插入内容  
		 */  
		setCaret: function(){	
			if(!document.selection) return;	
			var initSetCaret = function(){	
				var textObj = $(this).get(0);	
				textObj.caretPos = document.selection.createRange().duplicate();	
			};	
			$(this)	
			.click(initSetCaret)	
			.select(initSetCaret)	
			.keyup(initSetCaret);	
		},	
		/**  
		 * 在当前对象光标处插入指定的内容  
		 */  
		insertAtCaret: function(textFeildValue){	
			var textObj = $(this).get(0);	
			if(document.all && textObj.createTextRange && textObj.caretPos){	
				var caretPos=textObj.caretPos;	
				caretPos.text = caretPos.text.charAt(caretPos.text.length-1) == '' ?	
									textFeildValue+'' : textFeildValue;	
			}	
			else if(textObj.setSelectionRange){	
				var rangeStart=textObj.selectionStart;	
				var rangeEnd=textObj.selectionEnd;	
				var tempStr1=textObj.value.substring(0,rangeStart);	
				var tempStr2=textObj.value.substring(rangeEnd);	
				textObj.value=tempStr1+textFeildValue+tempStr2;	
				textObj.focus();	
				var len=textFeildValue.length;	
				textObj.setSelectionRange(rangeStart+len,rangeStart+len);	
				textObj.blur();	
			}	
			else {	
				textObj.value+=textFeildValue;	
			}	
		}	
	}); 
})(jQuery);
