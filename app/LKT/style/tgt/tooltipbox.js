var _TipIDCounter = 0;

function Tip(ele, msg, position, autoClose, withObject) {  
    this.ElementID = ele;
    this.withObject = withObject;
    if (!position) {
        position = "top";
    }
    this.Position = position;
    
    this.Element = null;
    if(typeof ele == "object"){
        this.Element =ele.get(0);
    }
    else{
       if(ele.substr(0,1)!="#" && ele.substr(0,1)!="."  && ele.substr(0,1)!=":"){
            this.Element = $("#" + this.ElementID).get(0);
        }
        else{
            this.Element= $(this.ElementID).get(0);
        }
    }
    this.Message = msg;
    if (!autoClose) {
        autoClose = true;
    }
    this.AutoClose = autoClose;
    this.X = 0;
    this.Y = 0;
}
Tip.AutoCloseTips = [];
Tip.Tips = [];
Tip.prototype.init = function() {

}

Tip.prototype.show = function() {
 
    var el = null;
 
    if(typeof this.ElementID=="object"){
        el = this.ElementID;  
    }
    else{
        if(this.ElementID.substr(0,1)!="#" && this.ElementID.substr(0,1)!="."  && this.ElementID.substr(0,1)!=":"){
            el = $("#" + this.ElementID);
        }
        else{
            el = $(this.ElementID);
        }
    }
    
    
    if (this.withObject != null && this.withObject != undefined) {
          if(typeof this.withObject=="object"){
             el = this.withObject;
            }
            else{
                if(this.withObject.substr(0,1)!="#" && this.withObject.substr(0,1)!="."  && this.withObject.substr(0,1)!=":"){
                   el = $("#" + this.withObject);
                }
                else{
                    el = $(this.withObject);
                }
            }
    }
    if( el.length<=0){
        alert( this.ElementID + " is null" );
        return;
    }

    var arr = [];
    var divid = "tooltipbox_" + _TipIDCounter++;
    arr.push("<div id='" + divid + "' class='tooltipbox'><span>");
    arr.push(this.Message);
    arr.push("</span></div>");
    this.Html = arr.join('');

     $(this.Html).appendTo($(document.body));
    
    var dl = $("#" + divid);
    var p = el.offset();
 
    var fixLeft = parseInt(el.css("margin-left")) || 0;
    var fixTop = parseInt(el.css("margin-top")) || 0;
 
    var fixTopPadding = parseInt(el.css("padding-top")) || 0;
    var fixLeftPadding = parseInt(el.css("padding-left")) || 0;
    var fixRightPadding = parseInt(el.css("padding-right")) || 0;

    var x = 0;
    var y = 0;
 
    //   $("#" + this.ElementID).css("background","#f9fcde");
    switch (this.Position) {
        case "left":
            x = p.left +el.width();
            y = p.top + (el.height() - dl.height()) / 2 + 3;

            break;
        case "right":
            x = p.left + el.width() + fixLeftPadding+fixLeft;
            y = p.top + (el.height() - dl.height()) / 2;
 
            break;
        case "top":
            x = p.left;
            y = p.top - el.height() - fixTopPadding - fixTop - 5; 
            
            break;
        case "bottom":
            x = p.left;
            y = p.top + el.height() + fixTopPadding + fixTop + 5;
            y += 10;
            break;
    }
    dl.css({
     left: x+"px"     ,
     top: y+"px"
});
    this.Div =dl;
    if (this.AutoClose) {

        Tip.AutoCloseTips.push(this);
    }
    Tip.Tips.push(this);
}

Tip.prototype.close = function() {
    this.Div.remove();
}

Tip.show = function(ele, msg, pos, autoClose, withObject) {

    var tip = new Tip(ele, msg, pos, autoClose, withObject);

    tip.show();

    if (!tip.AutoClose) {
        if (!tip.Element._Tips) {
            tip.Element._Tips = [];
        }
        tip.Element._Tips.push(tip);
    }
    return tip;
}
Tip.focus = function(ele, msg, pos, autoClose, withObject) {
    
    if(typeof ele=="object"){
        ele.focus();
    }
    else{
        if(ele.substr(0,1)!="#" && ele.substr(0,1)!="." && ele.substr(0,1)!=":"){
           $("#" + ele).focus();    
        }
        else{
            $(ele).focus();    
        }
   }
   
    return Tip.show(ele, msg, pos, autoClose, withObject);
}

Tip.select = function(ele, msg, pos, autoClose, withObject) {
     if(typeof ele=="object"){
        ele.focus();
         ele.select();
    }
    else if(ele.substr(0,1)!="#" && ele.substr(0,1)!="." && ele.substr(0,1)!=":"){
        $("#" + ele).focus();    
        $("#" + ele).select();
    }
    else{
        $(ele).focus();    
         $(ele).select();    
    }
    return Tip.show(ele, msg, pos, autoClose, withObject);
}
Tip.getTipCount = function(ele) {
    ele = $(ele);
    if (!ele._Tips) {
        return 0;
    }
    return ele._Tips.length;
}
Tip.closeAll = function() {
    var arr = Tip.Tips;

    for (var i = arr.length; i > 0; i--) {
        arr[i - 1].close();
        arr.splice(i - 1, 1)
    }
};
Tip.close = function(ele) {//将与ele有关的Tip都关闭掉
    ele = document.getElementById(ele);


    if (ele._Tips) {
        for (var i = 0; i < ele._Tips.length; i++) {
            if (ele._Tips[i]) {

                ele._Tips[i].close();
            }
        }
        ele._Tips = [];
    }
}

$(document).mousedown(function() {
    var arr = Tip.AutoCloseTips;

    for (var i = arr.length; i > 0; i--) {
        //       $("#" + arr[i-1].ElementID).css("background","#fff");
        arr[i - 1].close();
        arr.splice(i - 1, 1)
    }
});
$(document).keydown(function() {
    var arr = Tip.AutoCloseTips;

    for (var i = arr.length; i > 0; i--) {
        //       $("#" + arr[i-1].ElementID).css("background","#fff");
        arr[i - 1].close();
        arr.splice(i - 1, 1)
    }
});