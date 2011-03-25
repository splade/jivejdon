
var popupW;
function openPopUpWindow(wtitlename, url){
    if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
    
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
