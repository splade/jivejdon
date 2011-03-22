<%@ page contentType="text/javascript; charset=UTF-8" %>
<%          
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

<%@ include file="./common/js/prototype.js"%>
<%@ include file="./common/js/effects.js"%>
<%@ include file="./common/js/window.js"%>
<%@ include file="./common/js/window_effects.js"%>
<%@ include file="./common/js/tooltip.js"%>
<%@ include file="./common/js/pagination.js"%>
<%@ include file="./common/js/autocomplete.js"%>

function addfavorite( title) {
 var url = location.href;
 if (document.all) {
   window.external.addFavorite(url,title); 
 }else if (window.sidebar) {
   window.sidebar.addPanel(title, url, ""); 
 }else{
   alert('Press ctrl+D to bookmark ');
 }
}

var timeout = 1;
var startDiaglog = false;  
var scontent;

function openInfoDiag(content) {  
      scontent = content;       
      Dialog.info(scontent + "  计时：" +  timeout + " 秒   ",
               {width:260, height:150, showProgress: true});
      setTimeout(infoTimeOut, 1000);
      startDiaglog = true;   
}
   
function infoTimeOut() {  
      if (startDiaglog){
         if (timeout > 10)
            infoDiagClose();
         else
            timeout++;  
            Dialog.setInfoMessage(scontent + "  计时：" +  timeout + " 秒  ");
            setTimeout(infoTimeOut, 1000) ;
      }
}
   
function setDiagInfo(content){
      scontent = content;
}
   
function infoDiagClose(){
     if (startDiaglog){
        Dialog.closeInfo();
        startDiaglog = false;
        timeout = 1;
     }
}

var popupW;
function openPopUpWindow(wtitlename, url){
    if (popupW == null) {       
       popupW = new Window({className: "mac_os_x", width:600, height:380, title: wtitlename}); 
       popupW.setURL(url);
       popupW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myW) {    	  
          if (myW == popupW){        	        	
            popupW = null;   
            Windows.removeObserver(this);
          }
        }
       }
     Windows.addObserver(myObserver);
     } 
 }     

function copyToClipboard(obj){
     if (document.execCommand){
		var e=$(obj);
		e.select();
		document.execCommand("Copy");
	 }
}
