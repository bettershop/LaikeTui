;(function($){
    $.fn.drag = function(options){
        var el = this
        function drag(options){
            clip = el.find('.drag-clip');
            move = el.find('.drag-move');
            /**
             * options            设置属性
             * elBound            drag最外层属性
             * moveBound          移动框属性
             * clipBound          图片clip属性
             * parentGap          移动框距离父元素间隙
             * clipNewBound       图片距离父元素间隙
             * updateBound        移动框移动后的属性
             * clipUpdae          clip图片更新后的属性
             */
            var elBound,moveBound,clipBound,parentGap,clipNewBound,updateBound,clipUpdae;
        }
        // 获取移动框的属性值
        drag.prototype.getBound = function(){
            var movegetBound = move[0].getBoundingClientRect();
            var elgetBound = el[0].getBoundingClientRect();
            var clipgetBound = clip[0].getBoundingClientRect();
            moveBound = {
                'width': movegetBound.width,
                'height': movegetBound.height,
                'top': movegetBound.top,
                'bottom': movegetBound.bottom,
                'left': movegetBound.left,
                'right': movegetBound.right
            }
            elBound = {
                'width': elgetBound.width,
                'height': elgetBound.height,
                'top': elgetBound.top,
                'bottom': elgetBound.bottom,
                'left': elgetBound.left,
                'right': elgetBound.right
            }
            clipBound = {
                'width': clipgetBound.width,
                'height': clipgetBound.height,
                'top': clipgetBound.top,
                'bottom': clipgetBound.bottom,
                'left': clipgetBound.left,
                'right': clipgetBound.right
            }
            console.log('X坐标是'+elgetBound.width);
        }
        // 初始化
        drag.prototype.creat = function(){
            this.getBound()
            clip.css({'clip':'rect('
                +(moveBound.top - elBound.top)+'px,'
                +(moveBound.right - elBound.left)+'px,'
                +(moveBound.bottom - elBound.top)+'px,'
                +(moveBound.left - elBound.left)+'px)',
                'width': clipBound.width,'height': clipBound.height
            })
            // if(options.clipShow){
            //     clipShow = $(options.clipShow).append('<img src="'+ clip[0].src+'" />').find('img').css({
            //         'position': 'absolute',
            //         'clip':'rect('
            //         +(moveBound.top - elBound.top)+'px,'
            //         +(moveBound.right - elBound.left)+'px,'
            //         +(moveBound.bottom - elBound.top)+'px,'
            //         +(moveBound.left - elBound.left)+'px)',
            //         'width': clipBound.width,'height': clipBound.height,
            //         'left': -moveBound.left + 'px',
            //         'top': -moveBound.top + 'px',
            //     })
            // }
        }
        drag.prototype.setBound = function(){
            var _this = this;              
            _this.creat()
            move.on('mousedown',function(even){
                if(even.target.nodeName != 'DIV') return false;
                _this.getBound()
                var _x = even.pageX;
                var _y = even.pageY;

                var parentGap = {
                    'top': _y - moveBound.top,
                    'left': _x - moveBound.left,
                }
                $(document).on('mousemove',function(e){
                    var x = e.pageX;
                    var y = e.pageY;
                    var updateBound = {
                        'top': y-parentGap.top-elBound.top <= 0 ? 0 : (y-parentGap.top-elBound.top >= clipBound.height-moveBound.height ? clipBound.height-moveBound.height :  y-parentGap.top-elBound.top),
                        'left': x-parentGap.left-elBound.left <= 0 ? 0 : (x-parentGap.left-elBound.left >= clipBound.width-moveBound.width ? clipBound.width-moveBound.width :  x-parentGap.left-elBound.left),
                        'right': x-parentGap.left-elBound.left <= 0 ? moveBound.width : x-parentGap.left-elBound.left+moveBound.width,
                        'bottom': y-parentGap.top-elBound.top <= 0 ? moveBound.height : (y-parentGap.top-elBound.top+moveBound.height)
                    }
                    move.stop(updateBound).animate({
                        'top': updateBound.top+'px',
                        'left': updateBound.left+'px',
                    },0)

                    clip.css({'clip':'rect('
                        +updateBound.top+'px,'
                        +updateBound.right+'px,'
                        +updateBound.bottom+'px,'
                        +updateBound.left+'px)'
                    })
                    $("#yzb").val(updateBound.top);
                    $("#xzb").val(updateBound.left);
                    console.log('X-----坐标是'+updateBound.top+'   Y-----坐标是'+(updateBound.left));
                    return false
                })
            })
            move.find('span').on('mousedown',function(even){
                    if(even.target.nodeName != 'SPAN') return false;
                    var index = $(this).index();
                    _this.getBound()
                    $(document).on('mousemove',function(e){
                        var x  = e.pageX;
                        var y = e.pageY;
                        if(index == 0){
                            var parentGap = {
                                'top': (y - moveBound.top ) >= moveBound.height ? moveBound.height : (y - moveBound.top ),
                                'left': (x - moveBound.left ) >= moveBound.width ? moveBound.width : (x - moveBound.left )
                            }
                            var updateBound = {
                                'top': moveBound.top+parentGap.top - elBound.top <= 0 ? 0 : moveBound.top+parentGap.top - elBound.top,
                                'height': y-elBound.top >= 0 ? moveBound.height-parentGap.top-2 : (y-elBound.top <= moveBound.bottom - elBound.top ? moveBound.bottom - elBound.top - 2 : ''),
                                'left': moveBound.left+parentGap.left <= elBound.left ? 0 : moveBound.left + parentGap.left - elBound.left,
                                'width': x-elBound.left >=0 ? moveBound.width-parentGap.left-2 : (x-elBound.left <= (moveBound.right -  elBound.left) ? moveBound.right -  elBound.left -2 : '')
                            }
                            clipUpdae  = {
                                'top': y - elBound.top,
                                'right': moveBound.right -  elBound.left,
                                'bottom':  moveBound.bottom-elBound.top,
                                'left': x - elBound.left
                            }
                            move.stop().animate({
                                'top': updateBound.top+'px',
                                'height': updateBound.height+'px',
                                'left': updateBound.left+'px',
                                'width': updateBound.width+'px',
                            },0)
                        }else if(index == 1){
                            var parentGap = {
                                'top': (y - moveBound.top ) >= moveBound.height ? moveBound.height : (y - moveBound.top ),
                                'left': (x - moveBound.left ) >= moveBound.width ? moveBound.width : (x - moveBound.left )
                            }
                            var updateBound = {
                                'top': moveBound.top+parentGap.top - elBound.top <= 0 ? 0 : moveBound.top+parentGap.top - elBound.top,
                                'height': y-elBound.top >= 0 ? moveBound.height-parentGap.top-2 : (y-elBound.top <= moveBound.bottom - elBound.top ? moveBound.bottom - elBound.top - 2 : ''),
                                'width': (x-moveBound.left) >= elBound.width ? elBound.width : (x >= clipBound.width+clipBound.left ? (clipBound.left+clipBound.width-moveBound.left-2) : x-moveBound.left)
                            }
                            clipUpdae  = {
                                'top': y - elBound.top,
                                'right': x+1 - elBound.left,
                                'bottom':  moveBound.bottom-elBound.top,
                                'left':moveBound.left - elBound.left
                            }
                            move.stop().animate({ 
                                'top': updateBound.top+'px',
                                'height':updateBound.height+'px',
                                'width':updateBound.width+'px',
                            },0)
                        }else if(index == 2){
                            var updateBound = {
                                'height': y - moveBound.top-2 >= clipBound.top+clipBound.height-moveBound.top ? clipBound.top+clipBound.height-moveBound.top-2 : y - moveBound.top-2,
                                'width': (x-moveBound.left) >= elBound.width ? elBound.width : (x >= clipBound.width+clipBound.left ? (clipBound.left+clipBound.width-moveBound.left-2) : x-moveBound.left)
                            }
                            clipUpdae  = {
                                'top': moveBound.top - elBound.top,
                                'right': x+1 - elBound.left,
                                'bottom':  y-elBound.top,
                                'left':moveBound.left - elBound.left
                            }
                            move.stop().animate({
                                'height': updateBound.height+'px',
                                'width': updateBound.width+'px',
                            },0)
                        }else if(index == 3){
                            var parentGap = {
                                'top': (y - moveBound.top ) >= moveBound.height ? moveBound.height : (y - moveBound.top ),
                                'left': (x - moveBound.left ) >= moveBound.width ? moveBound.width : (x - moveBound.left )
                            }
                            var updateBound = {
                                'height': y - moveBound.top-2 >= clipBound.top+clipBound.height-moveBound.top ? clipBound.top+clipBound.height-moveBound.top-2 : y - moveBound.top-2,
                                'left': moveBound.left+parentGap.left <= elBound.left ? 0 : moveBound.left + parentGap.left - elBound.left,
                                'width': x-elBound.left >=0 ? moveBound.width-parentGap.left-2 : (x-elBound.left <= (moveBound.right -  elBound.left) ? moveBound.right -  elBound.left -2 : '')
                            }
                            clipUpdae  = {
                                'top': moveBound.top - elBound.top,
                                'right': moveBound.right - elBound.left,
                                'bottom':  y-elBound.top,
                                'left': x - elBound.left
                            }

                            move.stop().animate({
                                'height': updateBound.height+'px',
                                'left': updateBound.left+'px',
                                'width': updateBound.width+'px',
                            },0)
                        }
                        // 通用
                        clip.css({'clip':'rect('
                            +clipUpdae.top+'px,'
                            +clipUpdae.right+'px,'
                            +clipUpdae.bottom+'px,'
                            +clipUpdae.left+'px)'
                        })
                        // clipShow.css({'clip':'rect('
                        //     +clipUpdae.top+'px,'
                        //     +clipUpdae.right+'px,'
                        //     +clipUpdae.bottom+'px,'
                        //     +clipUpdae.left+'px)',
                        //     'left': -move.offset().left + 'px',
                        //     'top': -move.offset().top + 'px',
                        // })
                        e.stopPropagation();
                        //在这打印
                        
                    })

                    
            })
            $(document).on('mouseup',function(){
                $(document).off('mousemove')
            })
        }
        var drag = new drag();
        drag.setBound()
    }
})(jQuery);