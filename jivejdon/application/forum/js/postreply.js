var replyW;
  function openReplyWindow(pmessageId){
       initReplyForm(pmessageId);
       openWindowForReply(pmessageId);
   }   
   
    function openQuoteWindow(pmessageId){
       if (initQuoteForm(pmessageId)){
           
           openWindowForReply(pmessageId);
       }
   }       

    function openWindowForReply(pmessageId){
        
        if (replyW == null) {
           replyW = new Window({className: "mac_os_x", width:630, height:430, title: " Reply "}); 
           replyW.setContent("replyDiv",false, false);
           replyW.showCenter();
    	
    	    
    	   var myObserver = {
            onClose: function(eventName, myreplyW) {    	  
              if (myreplyW == replyW){        	        	
                replyW = null;   
                Windows.removeObserver(this);
              }
            }
           }
           var myObserver2 = {
            onMaximize: function(eventName, myreplyW) {    	  
              if (myreplyW == replyW){        	        	
                 $('formBody').setAttribute("cols", "80");
       	         $('formBody').setAttribute("rows", "23");
              }
            }
           }
           
           
         Windows.addObserver(myObserver);
         Windows.addObserver(myObserver2);
         } 
       }   
   
    function initReplyForm(pmessageId){
   	 $('parentMessageId').value =pmessageId;
   	 if ($("subject_" + pmessageId) != null){   	     
   	     replySubConvert($("subject_" + pmessageId).innerHTML);
   	 } 
   	 $('formBody').setAttribute("cols", "63");
   	 $('formBody').setAttribute("rows", "14");   	 
  }
  
  function replySubConvert(replyV){         
  	     var found = replyV.search(/回复:/g);     	 
    	 if (found != -1){
    	     replyV = doReply(replyV);  
    	     $('replySubject').value = replyV;
    	     return;  	 	   
    	 }	 
    	 
    	 found = replyV.search(/\d楼 /g);
    	 if (found != -1){
    	 	   replyV = dolou(replyV);
    	 	   
    	 }else{   	   
    	     replyV = donewReply(replyV);
    	 }
         $('replySubject').value = replyV;  
  }
  function doReply(replyV){
    	var replyCount = replyV.replace(/[^:]/g,'').length ;    	 
    	if (replyCount != 0){
    	 	  replyCount++;
    	    replyV = replyV.replace(/回复:/g, '');    	    
    	    replyV =  replyCount  + "楼 " +  replyV;	
       }
       return replyV;    	
    }
    
    function dolou(replyV){
    	var start = replyV.search(/\d楼 /g);	
    	if (start == -1) return;
    	var end = replyV.search(/楼 /g);       	 	   
    	replyCount = replyV.substring(start,end); 
    	if(replyCount == "") return;
    	replyCount  = parseInt(replyCount)  + 1;
    	replyV = replyV.replace(/\d楼 /g, '');    	 	   
    	replyV = replyCount  + "楼 " +  replyV;    
    	return replyV;   	
    }
    
    function donewReply(replyV){
    	replyV = "1楼 " +  replyV;
    	return replyV;
    }  
  
  
  function getSelText()
  {
    var txt = '';
     if (window.getSelection)
        txt = window.getSelection();
    else if (document.getSelection)
        txt = document.getSelection();
    else if (document.selection)
        txt = document.selection.createRange().text;
    
    return txt;
   }
  
  
   function initQuoteForm(pmessageId){
     var quotetexts = getSelText() + "";
   	 if (quotetexts == ""){
    	 Dialog.alert("请首先用鼠标选择本帖中需要回复的文字", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});   	    
      	return false;
   	 }   	 
   	 quotetexts = quotetexts.substring(0, 120) + " ... ";
   	 $('parentMessageId').value =pmessageId;
   	 if ($("subject_" + pmessageId) != null)
   	     $('replySubject').value =  " 回复:" + $("subject_" + pmessageId).innerHTML; 
   	 $('formBody').setAttribute("cols", "63");
   	 $('formBody').setAttribute("rows", "14");
   	 
     $('formBody').value="[quote author="+$("author_" + pmessageId).innerHTML+" date="+$("creationDate_" + pmessageId).innerHTML+"]"+ quotetexts +"[/quote]";
   	 var regExNewLine = /<br>/g;
   	 $('formBody').value = $('formBody').value.replace(regExNewLine, "\n");
   	 var regEx = /<[^>]*>/g; 
   	 $('formBody').value = $('formBody').value.replace(regEx, "");
   	 return true; 
   	 
  }
   
   function killreplyW(){
      if (replyW != null){          
           replyW.close();                     
     }
   }
   
   function callLogin(){    
     infoDiagClose();  
     killreplyW();           
     loginW('messageReply');
   }
   
   function goAfterLogged(fromFormName){
     $(fromFormName).submit();
     if (formSubmitcheck)
        openInfoDiag("登录成功，继续提交...");   
   }
   
   var anchor ;
   function forwardNewPage(fmainurl, fmainPars, anchor){
     
      fmainurl = fmainurl + "/" + "noheaderfooter"
      var pars = fmainPars; 
      this.anchor = anchor;      
      new Ajax.Updater("messageListBody", fmainurl, 
  	    {method: 'get', parameters: pars, onComplete: showReplyResponse});  
  	   
   }
   
   function showReplyResponse(transport){    
      infoDiagClose();      
      $('formBody').value = ""; //clear input                         
      window.location.href= '#'+ anchor;          
   }
   
   function callbackSubmit(){
       killreplyW();  
       openInfoDiag("正在提交...如没有反应，请刷新本页再提交 ctrl-v取出上次发贴内容");                
   }
  