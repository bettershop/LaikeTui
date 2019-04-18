function ajax(url,callbackfun){
  var xmlReq;
	try {
		xmlReq = new ActiveXObject("Microsoft.XMLHTTP");
	}
	catch(e){
		xmlReq = new XMLHttpRequest();
	}
  if(!xmlReq){ return; }
	xmlReq.onreadystatechange = function(){
    if (xmlReq.readyState == 4) {
			if (xmlReq.status == 200)	{
        try{ callbackfun(xmlReq.responseText); } catch(e) {} 
			}
		}
	}  
	xmlReq.open("GET",encodeURI(url+"&" + Math.random()), true);
	xmlReq.send(null);
}