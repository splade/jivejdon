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
    	  //发完贴计时10秒刷新。
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