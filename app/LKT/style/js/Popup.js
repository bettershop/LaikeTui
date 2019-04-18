// this function is used to generate common popup
//      date: 2007.8.4.
var theClient;
var popupBack;
var popupWindow;
var backIframe;

function ShowPopup(id) {
    theClient=document.documentElement;
     popupBack=document.getElementById('popupBack');
     popupWindow=document.getElementById(id);
     popupBack.style.width = parseInt(theClient.scrollWidth) + 'px';
     var backHeight = 0;
     if (parseInt(theClient.scrollHeight) > parseInt(theClient.clientHeight))
         backHeight = parseInt(theClient.scrollHeight);
     else
         backHeight = parseInt(theClient.clientHeight);
     if (backHeight < (window.screen.height + 66))

         backHeight = window.screen.height + 66;

     popupBack.style.height = backHeight + 'px';

    var left=(parseInt(theClient.clientWidth)+parseInt(theClient.scrollLeft)-parseInt(popupWindow.offsetWidth))/2; 
    if(left>0)
        popupWindow.style.left=left+'px';
    else
        popupWindow.style.left=0+'px';

        popupWindow.style.top=90+'px';

    var isIE = (document.all) ? true : false;
    var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
    backIframe=document.createElement('iframe');
    backIframe.style.cssText = "top:0;left:0;z-index:99;opacity: 0.50;-moz-opacity:0.5;visibility:visible;position:absolute;border:none;filter:chroma(color=#d8d0c8);filter:chroma(color=#ffffff);";
    backIframe.style.width=parseInt(popupBack.offsetWidth)+'px';
    backIframe.style.height=parseInt(popupBack.offsetHeight)+'px';
    popupBack.appendChild(backIframe);
    popupBack.style.top = 0;
    popupBack.style.visibility='visible';
    popupBack.style.display = "block";
    popupBack.style.position = !isIE6 ? "fixed" : "absolute";
    popupWindow.style.visibility='visible';
    popupWindow.style.display="block";
}
function HidePopup()
{
    if (popupBack != null)
    {
        if (popupBack.style.display!="")
        {
            popupBack.removeChild(backIframe);
            popupBack.style.visibility="";
            popupBack.style.display="";
            popupWindow.style.visibility="";
            popupWindow.style.display="";
            popupWindow.style.top = -10000;
            popupBack.style.top = -100000;
        } 
    }
    
    if (global_CalendarInstance != null) CalendarCancel(global_CalendarInstance);
}

function ShowPopupTwo(id) {

    theClient = document.documentElement;
    popupBack = document.getElementById('popupBack');
    popupWindow = document.getElementById(id);
    popupBack.style.width = parseInt(theClient.scrollWidth) + 'px';
    var backHeight = 0;
    backHeight = parseInt(theClient.clientHeight);
    if (backHeight <window.screen.height)
        backHeight = window.screen.height;

    popupBack.style.height = backHeight + 'px';


    var left = (parseInt(theClient.clientWidth) + parseInt(theClient.scrollLeft) - parseInt(popupWindow.offsetWidth)) / 2;
    if (left > 0)
        popupWindow.style.left = left + 'px';
    else
        popupWindow.style.left = 0 + 'px';

    popupWindow.style.top = 50 + 'px';

    backIframe = document.createElement('iframe');
    backIframe.style.cssText = "top:0;left:0;z-index:99;opacity: 0.50;-moz-opacity:0.5;filter: alpha(opacity=50);visibility:visible;position:absolute;border:none;filter:chroma(color=#d8d0c8);filter:chroma(color=#ffffff);";
    backIframe.style.width = parseInt(popupBack.offsetWidth) + 'px';
    backIframe.style.height = parseInt(popupBack.offsetHeight) + 'px';
    popupBack.appendChild(backIframe);
    popupBack.style.top = 0;
    popupBack.style.visibility = 'visible';
    popupBack.style.display = "block";
    popupWindow.style.visibility = 'visible';
    popupWindow.style.display = "block";
}
// this function is used by popup to get all the selected checkbox
// make sure that all the checkboxes' id must start with "ckb" and end with numbers which is generally the record's id in database

function GetAllId(prefix, type, outFrm)
{
    var ret = "";
    if (prefix == null || prefix == "") prefix = "ckb";
    if (type == null || type == "") type = "checkbox";
    if (outFrm == null || outFrm == "") outFrm = "ListDiv";
    
    for (var i=0; i < document.forms[0].elements.length; i++) 
    {
        var e = document.forms[0].elements[i];
        if (e.type == type && e.id.substring(0,prefix.length) == prefix && e.checked == true) 
        {
            var b = e.id.substring(prefix.length,e.id.length)
            if (b != 0)
            {
                var divControl = $(outFrm + b);
                if (divControl != null && divControl.style.display != 'none')
                {
                    ret += b+"_";
                }
	        }
        }
    }
    if (ret == "") ret = "error";
    
    return ret;
}

// find all the checkboxes that are selected
function GetSelectedId(rmdr)
{
    var i;
    var strline="";
    var returnstr="";
    var strid
    var j=0;
    for (i=0; i < document.forms[0].elements.length; i++) 
    {
        if (document.forms[0].elements[i].type == 'checkbox' & document.forms[0].elements[i].checked == true) 
        {
    	    j=j+1;
    	    strid=document.forms[0].elements[i].id;
    	    var a=strid.length;
    	    var c=strid.substring(0,3);
    	    if(c=="ckb")
    	    {
	            var b=strid.substring(3,strid.length);
	            if(b!=0)
	            {
	                strline=b+"_";
	                returnstr=returnstr+strline;
	            }
	        }
        }
    }
    if(j == 0 || returnstr == "")
    {
        alert(rmdr);
        return '';
    }
    
    return returnstr;
}

// put selected page element value into hidden control element.
function SetHid(name, rmdr, prefix)
{
    var e = $(name);
    e.value = GetAllId(prefix);
    if (e.value == "error")
    {
      alert(qingxuanzexuesheng);
        return;
    }
    if (confirm(rmdr)) window.document.forms[0].submit();
}

//add By liuxili20080518
function SetHidStudent(name, rmdr, prefix)
{
    var e = $(name);
    e.value = GetAllId(prefix);
    if (e.value == "error")
    {
      alert("请选择需要验证的学生!");
        return;
    }
    if (confirm(rmdr)) window.document.forms[0].submit();
}
function SetHidNoCfm(name, v)
{
    $(name).value = v;
    window.document.forms[0].submit();
}
function DisposeDiv(e)
{
   e.style.display = "none";
   e.innerHTML = "";
   e.id = ""; 
}
function DeleteId(divId)
{
    if(confirm('确定要删除吗？'))
    {
        DisposeDiv($(divId));
    }
}
function ClearID(prefix)
{
    var r = '';
    for (var i=0; i < document.forms[0].elements.length; i++) 
    {
        var e = document.forms[0].elements[i];
        if (e.type == 'checkbox' && e.id.substring(0,3) == prefix && e.checked != true)
        {
            var b = e.id.substring(3,e.id.length);
            if (b != 0) r += b + ',';
        }
    }
    var t = r.split(',');
    for (var i=0;i<t.length-1;i++) DisposeDiv(document.getElementById('ListDiv'+ t[i]));
}

/************************************************************************
**  these functions below are used to control date and time choosing
**  Author: vinson chen
**  Date: 2007-9-16
************************************************************************/
var global_CalendarInstance;
function PopupCalendar(InstanceName)
{
	///Global Tag
	this.instanceName=InstanceName;
	///Properties
	this.separator="-"
	this.oBtnTodayTitle="今天";
	this.oBtnCancelTitle="取消";
	this.weekDaySting=new Array("S","M","T","W","T","F","S");
	this.monthSting=new Array("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月");
	this.Width=200;
	this.currDate=new Date();
	this.today=new Date();
	this.startYear=1970;
	this.endYear=2020;
	///Css
	this.normalfontColor="#666666";
	this.selectedfontColor="red";
	this.divBorderCss="1px solid #BCD0DE";
	this.titleTableBgColor="#98B8CD";
	this.tableBorderColor="#CCCCCC"
	///Method
	this.Init=CalendarInit;
	this.Fill=CalendarFill;
	this.Refresh=CalendarRefresh;
	this.Restore=CalendarRestore;
	///HTMLObject
	this.oTaget=null;
	this.oPreviousCell=null;
	this.sDIVID=InstanceName+"_Div";
	this.sTABLEID=InstanceName+"_Table";
	this.sMONTHID=InstanceName+"_Month";
	this.sYEARID=InstanceName+"_Year";
	this.sTODAYBTNID=InstanceName+"_TODAYBTN";
	
    global_CalendarInstance = this;
}
function CalendarInit()				///Create panel
{
	var sMonth,sYear
	sMonth=this.currDate.getMonth();
	sYear=this.currDate.getYear();
	htmlAll="<div id='"+this.sDIVID+"' style='z-Index:99999999999999;display:none;position:absolute;width:"+this.Width+"px;border:"+this.divBorderCss+";padding:2px;background-color:#FFFFFF'>";
	htmlAll+="<div align='center'>";
	/// Month
	htmloMonth="<select id='"+this.sMONTHID+"' onchange=CalendarMonthChange("+this.instanceName+") style='width:50%'>";
	for(i=0;i<12;i++)
	{			
		htmloMonth+="<option value='"+i+"'>"+this.monthSting[i]+"</option>";
	}
	htmloMonth+="</select>";
	/// Year
	htmloYear="<select id='"+this.sYEARID+"' onchange=CalendarYearChange("+this.instanceName+") style='width:50%'>";
	for(i=this.startYear;i<=this.endYear;i++)
	{
		htmloYear+="<option value='"+i+"'>"+i+"</option>";
	}
	htmloYear+="</select></div>";
	/// Day
	htmloDayTable="<table id='"+this.sTABLEID+"' width='100%' border=0 cellpadding=0 cellspacing=1 bgcolor='"+this.tableBorderColor+"'>";
	htmloDayTable+="<tbody bgcolor='#ffffff'style='font-size:13px'>";
	for(i=0;i<=6;i++)
	{
		if(i==0)
			htmloDayTable+="<tr bgcolor='" + this.titleTableBgColor + "'>";
		else
			htmloDayTable+="<tr>";
		for(j=0;j<7;j++)
		{

			if(i==0)
			{
				htmloDayTable+="<td height='20' align='center' valign='middle' style='cursor:hand'>";
				htmloDayTable+=this.weekDaySting[j]+"</td>"
			}
			else
			{
				htmloDayTable+="<td height='20' align='center' valign='middle' style='cursor:hand'";
				htmloDayTable+=" onmouseover=CalendarCellsMsOver("+this.instanceName+")";
				htmloDayTable+=" onmouseout=CalendarCellsMsOut("+this.instanceName+")";
				htmloDayTable+=" onclick=CalendarCellsClick(this,"+this.instanceName+")>";
				htmloDayTable+="&nbsp;</td>"
			}
		}
		htmloDayTable+="</tr>";	
	}
	htmloDayTable+="</tbody></table>";
	/// Today Button
	htmloButton="<div align='center' style='padding:3px'>"
	htmloButton+="<button id='"+this.sTODAYBTNID+"' style='width:40%;border:1px solid #BCD0DE;background-color:#eeeeee;cursor:hand'"
	htmloButton+=" onclick=CalendarTodayClick("+this.instanceName+")>"+this.oBtnTodayTitle+"</button>&nbsp;"
	htmloButton+="<button style='width:40%;border:1px solid #BCD0DE;background-color:#eeeeee;cursor:hand'"
	htmloButton+=" onclick=CalendarCancel("+this.instanceName+")>"+this.oBtnCancelTitle+"</button> "
	htmloButton+="</div>"
	/// All
	htmlAll=htmlAll+htmloMonth+htmloYear+htmloDayTable+htmloButton+"</div>";
	document.write(htmlAll);
	this.Fill();	
}
function CalendarFill()			///
{
	var sMonth,sYear,sWeekDay,sToday,oTable,currRow,MaxDay,iDaySn,sIndex,rowIndex,cellIndex,oSelectMonth,oSelectYear
	sMonth=this.currDate.getMonth();
	sYear=this.currDate.getYear();
	sWeekDay=(new Date(sYear,sMonth,1)).getDay();
	sToday=this.currDate.getDate();
	iDaySn=1
	oTable=document.all[this.sTABLEID];
	currRow=oTable.rows[1];
	MaxDay=CalendarGetMaxDay(sYear,sMonth);
	
	oSelectMonth=document.all[this.sMONTHID]
	oSelectMonth.selectedIndex=sMonth;
	oSelectYear=document.all[this.sYEARID]
	for(i=0;i<oSelectYear.length;i++)
	{
		if(parseInt(oSelectYear.options[i].value)==sYear)oSelectYear.selectedIndex=i;
	}
	////
	for(rowIndex=1;rowIndex<=6;rowIndex++)
	{
		if(iDaySn>MaxDay)break;
		currRow = oTable.rows[rowIndex];
		cellIndex = 0;
		if(rowIndex==1)cellIndex = sWeekDay;
		for(;cellIndex<currRow.cells.length;cellIndex++)
		{
			if(iDaySn==sToday)
			{
				currRow.cells[cellIndex].innerHTML="<font color='"+this.selectedfontColor+"'><i><b>"+iDaySn+"</b></i></font>";
				this.oPreviousCell=currRow.cells[cellIndex];
			}
			else
			{
				currRow.cells[cellIndex].innerHTML=iDaySn;	
				currRow.cells[cellIndex].style.color=this.normalfontColor;
			}
			CalendarCellSetCss(0,currRow.cells[cellIndex]);
			iDaySn++;
			if(iDaySn>MaxDay)break;	
		}
	}
}
function CalendarRestore()					/// Clear Data
{	
	var i,j,oTable
	oTable=document.all[this.sTABLEID]
	for(i=1;i<oTable.rows.length;i++)
	{
		for(j=0;j<oTable.rows[i].cells.length;j++)
		{
			CalendarCellSetCss(0,oTable.rows[i].cells[j]);
			oTable.rows[i].cells[j].innerHTML="&nbsp;";
		}
	}	
}
function CalendarRefresh(newDate)					///
{
	this.currDate=newDate;
	this.Restore();	
	this.Fill();	
}
function CalendarCellsMsOver(oInstance)				/// Cell MouseOver
{
	var myCell = event.srcElement;
	CalendarCellSetCss(0,oInstance.oPreviousCell);
	if(myCell)
	{
		CalendarCellSetCss(1,myCell);
		oInstance.oPreviousCell=myCell;
	}
}
function CalendarCellsMsOut(oInstance)				////// Cell MouseOut
{
	var myCell = event.srcElement;
	CalendarCellSetCss(0,myCell);	
}
function CalendarYearChange(oInstance)				/// Year Change
{
	var sDay,sMonth,sYear,newDate
	sDay=oInstance.currDate.getDate();
	sMonth=oInstance.currDate.getMonth();
	sYear=document.all[oInstance.sYEARID].value
	newDate=new Date(sYear,sMonth,sDay);
	oInstance.Refresh(newDate);
}
function CalendarMonthChange(oInstance)				/// Month Change
{
	var sDay,sMonth,sYear,newDate
	sDay=oInstance.currDate.getDate();
	sMonth=document.all[oInstance.sMONTHID].value
	sYear=oInstance.currDate.getYear();
	newDate=new Date(sYear,sMonth,sDay);
	oInstance.Refresh(newDate);	
}
function CalendarCellsClick(oCell,oInstance)
{
	var sDay,sMonth,sYear,newDate
	sYear=oInstance.currDate.getFullYear();
	sMonth=oInstance.currDate.getMonth();
	sDay=oInstance.currDate.getDate();
	if(oCell.innerText!=" ")
	{
		sDay=parseInt(oCell.innerText);
		if(sDay!=oInstance.currDate.getDate())
		{
			newDate=new Date(sYear,sMonth,sDay);
			oInstance.Refresh(newDate);
		}
	}
	sDateString=sYear+oInstance.separator+CalendarDblNum(sMonth+1)+oInstance.separator+CalendarDblNum(sDay);		///return sDateString
	if(oInstance.oTaget.tagName.toLowerCase()=="input")oInstance.oTaget.value = sDateString;
	else
	if (oInstance.oTaget.tagName.toLowerCase()=="span") // used in homework list, for changing homework expire time
	{
	    oInstance.oTaget.innerText = sDateString;
	    SaveChangedExpireTime(oInstance.oTaget.id);    // call local js file from homework.aspx page
	}
	    
	CalendarCancel(oInstance);
	return sDateString;
}
function CalendarTodayClick(oInstance)				/// "Today" button Change
{	
	oInstance.Refresh(new Date());		
}
function getDateString(oInputSrc,oInstance)
{
	if(oInputSrc&&oInstance) 
	{
		var CalendarDiv=document.all[oInstance.sDIVID];
		oInstance.oTaget=oInputSrc;
		CalendarDiv.style.pixelLeft=CalendargetPos(oInputSrc,"Left");
		CalendarDiv.style.pixelTop=CalendargetPos(oInputSrc,"Top") + oInputSrc.offsetHeight;
		CalendarDiv.style.display=(CalendarDiv.style.display=="none")?"":"none";	
	}	
}
function CalendarCellSetCss(sMode,oCell)			/// Set Cell Css
{
	// sMode
	// 0: OnMouserOut 1: OnMouseOver 
	if(sMode)
	{
		oCell.style.border="1px solid #5589AA";
		oCell.style.backgroundColor="#BCD0DE";
	}
	else
	{
		oCell.style.border="1px solid #FFFFFF";
		oCell.style.backgroundColor="#FFFFFF";
	}	
}
function CalendarGetMaxDay(nowYear,nowMonth)			/// Get MaxDay of current month
{
	var nextMonth,nextYear,currDate,nextDate,theMaxDay
	nextMonth=nowMonth+1;
	if(nextMonth>11)
	{
		nextYear=nowYear+1;
		nextMonth=0;
	}
	else	
	{
		nextYear=nowYear;	
	}
	currDate=new Date(nowYear,nowMonth,1);
	nextDate=new Date(nextYear,nextMonth,1);
	theMaxDay=(nextDate-currDate)/(24*60*60*1000);
	return theMaxDay;
}
function CalendargetPos(el,ePro)				/// Get Absolute Position
{
	var ePos=0;
	while(el!=null)
	{		
		ePos+=el["offset"+ePro];
		el=el.offsetParent;
	}
	return ePos;
}
function CalendarDblNum(num)
{
	if(num < 10) 
		return "0"+num;
	else
		return num;
}
function CalendarCancel(oInstance)
{
	var CalendarDiv=document.all[oInstance.sDIVID];
	CalendarDiv.style.display="none";		
}



//add by:zhuwenjun datetime:2007-12-27 FTP上传时使用

  function ShowFtpMsgDiv()
    {
        MsgMainDiv.style.display == "none";
        MsgDiv.style.display = "";
        MsgDiv.style.position = "absolute";
        MsgDiv.style.zIndex = "1";
        MsgDiv.style.top =  "0px";
        MsgDiv.style.left = "0px";
        MsgDiv.style.width = document.body.offsetWidth + "px";
        MsgDiv.style.height = document.body.offsetHeight + "px";

        //maskDiv
        maskMsgDiv.style.position = "absolute";
        maskMsgDiv.style.zIndex = "1";
        maskMsgDiv.style.width = document.body.offsetWidth + "px";
        maskMsgDiv.style.height = document.body.offsetHeight + "px";
        maskMsgDiv.style.top = "0px";
        maskMsgDiv.style.left = "0px";
        maskMsgDiv.style.background = "#000000";
        maskMsgDiv.style.filter = "alpha(opacity=40)";
        maskMsgDiv.style.opacity = "0.40";
        maskMsgDiv.style.display = "";
        //maskIFrame
        document.getElementById("maskMsgIFrame").style.position = "absolute";
        document.getElementById("maskMsgIFrame").style.visibility = "inherit";
        document.getElementById("maskMsgIFrame").style.top = "0px";
        document.getElementById("maskMsgIFrame").style.left = "0px";
        document.getElementById("maskMsgIFrame").style.width = document.body.offsetWidth + "px";
        document.getElementById("maskMsgIFrame").style.height = document.body.offsetHeight + "px";
        document.getElementById("maskMsgIFrame").style.zIndex = "-1";
        document.getElementById("maskMsgIFrame").style.background = "#000000";
        document.getElementById("maskMsgIFrame").style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)";           
        return false;       	    
    }
    
    function HiddenFtpMsgDiv()
    {
        maskMsgDiv.style.display == "none";
        maskMsgDiv.style.position = "";
        maskMsgDiv.style.background = "#ffffff";
        maskMsgDiv.style.filter = "alpha(opacity=100)";        
        maskMsgDiv.style.width = "0px";
        maskMsgDiv.style.height = "0px";
        MsgDiv.style.display = "none";
        MsgMainDiv.style.display = "";
        return false;
    }