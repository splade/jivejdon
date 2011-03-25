
  var newMessageW;
  function popUpNewMessageWithID(ID){
     if( readCookie(ID) == "disable")
        return;
     popUpNewMessage();  
  }
  
  function popUpNewMessage(){
   if (newMessageW == null) {
       newMessageW = new Window({className: "mac_os_x", width:250, height:150, title: " Have a Message "}); 
       newMessageW.setContent("isNewMessage",false, false);                  
       newMessageW.showCenter();	
       WindowCloseKey.init();
   
       var myObserver = {
        onClose: function(eventName, mywinMessage) {    	  
          if (mywinMessage == newMessageW){        	
            newMessageW = null;
            Windows.removeObserver(this);
          }
        }
      }
      Windows.addObserver(myObserver);  	 
	} else
	  newMessageW.showCenter();
   }     
   
   function clearPopUPWithID(ID){
      createCookie(ID,"",-1);   
      clearPopUP();
   }
  
   function clearPopUP(){
     if (newMessageW != null){   
           newMessageW.close();    
           newMessageW = null;                  
     }     
     $('isNewMessage').innerHTML = "";
  }
  
   function disablePopUPWithID(ID, seconds){       
       createCookie(ID,"disable",seconds);  
       clearPopUP();     
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