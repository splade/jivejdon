 
    var fromFormName ;
    var logged = false;
    
    function setLogged(){
  	    logged = true;
  	    if (typeof(isLogin) != "undefined")
  	       isLogin = true;
  	 }

var initTooltipWL = function (){
  TooltipManager.init("tooltip", {url: "", options: {method: 'get'}}, {showEffect: Element.show, hideEffect: Element.hide,className: "mac_os_x", width: 250, height: 100});   
}

function getContextPath(){
  if (document.getElementById('contextPath') == null){
     alert("no contextPath");
     return null;
  }
   return document.getElementById('contextPath').value;  
}


function loadWLJS(myfunc){
  if (typeof(TooltipManager) == 'undefined') {     
     $LAB
     .script(getContextPath() + '/common/js/window_def.js').wait()
     .wait(function(){
          initTooltipWL();          
     })     
     .wait(function(){
          myfunc();          
     })    
  }else
     myfunc();
}

var nof = function(){
}

function loadWLJSWithP(param,myfunc){
  if (typeof(TooltipManager) == 'undefined') {     
     $LAB
     .script(getContextPath() + '/common/js/window_def.js').wait()
     .wait(function(){
          initTooltipWL();          
     }).wait()
     .wait(function(){
          myfunc(param);          
     })    
  }else
     myfunc(param);
}



var onlinesInf = function (){
   if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
    try{
           var onlineWindow = new Window({className: "mac_os_x", width:350, height:150, title: " 当前登录用户 "});
           onlineWindow.setURL(getContextPath() +"/onlineInfo.jsp");
           onlineWindow.showCenter();
    }catch(e){}
}  	

  	 
function loginW(fromForm) {  	    
         if (typeof(isLogin) != "undefined")
           if (isLogin) {//isLogin defined in /common/security.jsp  
              setLogged()            
              window.location.reload();
              return true;
           }
         fromFormName = fromForm;
  	     if (typeof(Dialog) != "undefined"){
  		     logindiag();
         }else
             alert("no load window_def.js");
  		
  	}


function logindiag(){
     Dialog.confirm($('loginAJAX').innerHTML, {className:"mac_os_x", width:350, height:180,
                                      okLabel: " 登录 ", cancelLabel: " 关闭窗口 ",
                                      onOk:function(win){    
                                          $('login_error_msg').innerHTML='';                                      
                                          login();                                      
                                          if (!logged){                     
                                            delloginCookies();                       
                                            $('login_error_msg').innerHTML=' 用户或密码错误 ';
                                            $('login_error_msg').show();                                        
                                            Windows.focusedWindow.updateHeight();
                                            new Effect.Shake(Windows.focusedWindow.getId());
                                            return false;
                                          }else
                                            return true;                                            
                                      
                                	}});
} 
  	
  	function delloginCookies(){  	    
    	eraseCookie("rememberMe");
  	  	eraseCookie("username");
   		eraseCookie("password");   		
  	}
  	
  	function login(){
	   var pars  = "j_username=" + $('j_username').value + "&j_password=" + $('j_password').value;
	   if ($('rememberMe').checked){
	      pars = pars + "&rememberMe=" + $('rememberMe').value;
	   }
       new Ajax.Request(loggedURL, 
  	    {method: 'get', parameters: pars, asynchronous: false, onComplete: showResponse});  	   
  	}
  	
  	function showResponse(transport) { 
  	  	
         var response = transport.responseText;
         if (response.indexOf("if(setLogged)setLogged()")   >=0)
			setLogged();
         if (logged){
            if (fromFormName != null){             
              goAfterLogged(fromFormName);              
            }else{
              window.location.reload();
            }         
         }
  	 }
  

function createCookie(name,value,seconds) {
	var date = new Date();
	if (seconds) 
		date.setTime(date.getTime()+(seconds*1000));
	else
		date.setTime(date.getTime()-10000);

	var expires = "; expires="+date.toGMTString();
	document.cookie = name+"="+value+expires+"; path=/";
}  

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function eraseCookie(name) {
	createCookie(name,"",-1);
}
  	 