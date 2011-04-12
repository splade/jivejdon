
  var newMessageW;
  function popUpNewMessageWithID(ID){     
     if( readCookie(ID) == "disable")
        return;
     popUpNewMessage();
        
  }
  
  function popUpNewMessage(){
   if (newMessageW == null) {
       newMessageW = new Window({className: "mac_os_x", width:250, height:150, title: " 20秒后自动关闭 "}); 
       newMessageW.setContent("isNewMessage",false, false);                  
       newMessageW.showCenter();	
       
   
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
	setTimeout(clearPopUP, 20*1000);	
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
  
      