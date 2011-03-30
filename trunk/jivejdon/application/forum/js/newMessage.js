
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
  
      