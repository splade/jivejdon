function getFourmList(forumListJSONURL){
   var pars = "";
   new Ajax.Request(forumListJSONURL, 
  	    {method: 'post', parameters: pars, onComplete: shoForumResponse});  
}  
	    	  
function shoForumResponse(transport){
  if (!transport.responseText.isJSON())
	 	 return;
  var dataArray = (transport.responseText).evalJSON();	 	 
  dataArray.each(function(forum){          
      new Insertion.Bottom('forumId_select', " <option value='"+ forum.forumId +"' id='o_"+ forum.forumId +"' >"+ forum.name +"</option>");
  });
 }
 
 
function callbackSubmit(){
    openInfoDiag("正在提交...如没有反应，请刷新本页再提交 ctrl-v取出上次发贴内容");     

}

function callLogin(){    
      infoDiagClose();  
      loginW('messageNew');
}
   
function goAfterLogged(fromFormName){
     $(fromFormName).submit();
     openInfoDiag("登陆成功，继续提交...");   
}
   
function forwardNewPage(fmainurl, fmainPars, anchor){  
      infoDiagClose();       
      var url = fmainurl + fmainPars + "#" + anchor;   
     // window.alert("url=" + url);    
      window.location.href =  url;
      
}