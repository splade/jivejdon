
function leftRightgoPageREST(event)
{
   var page;
   event = event ? event : (window.event ? window.event : null);

   if (event.keyCode==39) 
      page = start + count               
   else if (event.keyCode==37) 
      page = start - count     
   else return;
      
   if (page < 0 && prevThreadUrl != null){
       document.location = prevThreadUrl;
       return;
   }else if (page >= allCount && nextThreadUrl != null){
       document.location = nextThreadUrl;
       return;
   }else if (page < 0 || page >= allCount) {
       return;
   }
   
   var path;
   if (page != 0)	    	
	  path = pageURL + "/" + page;
   else
	  path = pageURL ;
   document.location = path;
	
} 


 function digMessage(id)
    {            
    	var pars = 'messageId='+id;   
        new Ajax.Updater('digNumber_'+id, contextpath +'/updateDigCount.shtml', { method: 'get', parameters: pars });
        $('textArea_'+id).update("顶一下");
        
    }
    
function stickyThread(threadId, ui_state,action,forumId)
	{
		var pars = 'threadId='+threadId+'&ui_state='+ui_state+'&action='+action; 
		new Ajax.Request(  
            contextpath +'/admin/stickyThread.shtml',  
            {  
                method:'post',  
                parameters:pars,  
                asynchronous:true  
            }  
        );  
		alert("操作成功");
		window.location = contextpath +'/' + forumId;
	}
	
function checkUserIfOnline(username,messageId)
	{
		var pars = 'username='+username; 
		new Ajax.Updater('messageOwnerOnline_'+messageId, contextpath +'/onlineCheck.jsp',  
            {  
               method:'get',  
               parameters:pars
            }
        );
	}
	

function openShortmessageWindow(url){
     if (!isLogin){//login defined in .common/security.jsp        
        Dialog.alert("请先登陆", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
        return false;
    }
   openPopUpWindow("发送消息", url);
}

function hotkeys(){
   if (typeof(Ajax) != "undefined")
        new Ajax.Updater('hotkeys', contextpath +'/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
}

function approveList(){
   if (typeof(Ajax) != "undefined")
        new Ajax.Updater('approved', contextpath +'/query/threadApprovedNewList.shtml?count=15', { method: 'get' });
}                
 
function hotList(){
   if (typeof(Ajax) != "undefined"){
        var pars = "";
        new Ajax.Updater('hotList', contextpath +'/hot/180_400_10_190.html', { method: 'get', parameters: pars });
   }
}

function loadWPostjs(mId){  
  if (typeof(openReplyWindow) == 'undefined') {
   $LAB
   .script(contextpath + '/forum/js/postreply.js').wait()
   .wait(function(){
      openReplyWindow(mId);
   })   
  }else{
     openReplyWindow(mId);
  }
}


function loadQPostjs(mId){  
  if (typeof(openReplyWindow) == 'undefined') {
   $LAB
   .script(contextpath + '/forum/js/postreply.js').wait()
   .wait(function(){
      openQuoteWindow(mId);
   })   
  }else{
     openQuoteWindow(mId);
  }
}




