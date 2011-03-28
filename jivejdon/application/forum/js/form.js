
function copyToClipboard(obj){
  try{
    if (!document.execCommand()) return;
    
    var e = $(obj);
	e.select();
	document.execCommand("Copy");
	document.execCommand("Paste");
  }catch(e){}
	
}

var uploadW;
  function openUploadWindow(url){
   if (typeof(TooltipManager) == 'undefined') 
       loadWLJS(nof);
       
    if (uploadW == null) {
       uploadW = new Window({className: "mac_os_x", width:450, height:300, title: " Upload ", closable: false}); 
       uploadW.setURL(url);
       uploadW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myuploadW) {    	  
          if (myuploadW == uploadW){        	
            appendUploadUrl();
            appendUploadAttach();
            uploadW = null;
            Windows.removeObserver(this);
          }
         }
        }
        Windows.addObserver(myObserver);
    } else
	  uploadW.showCenter();
   }     
   
   var saveS;
   var attcount;
   function killUploadWindow(surl, attcount){
      this.saveS = surl;
      this.attcount = attcount;      
      if (uploadW != null){  
           uploadW.close();  //this will  enable  appendUploadUrl() or   appendUploadAttach()                    
     }
   }
   
  function appendUploadUrl(){
      if (saveS == null) return;
      saveS =  $F("formBody") + "\n" + saveS;      	 
      Form.Element.setValue("formBody", saveS);
   }
   
   function appendUploadAttach(){
      if (attcount == null) return;
      var insimag = "";
      for (i = 0; i < attcount; i++)
      {
        var ind = i + 1;
      	var inst = '[img index=' + ind + ']';      	
        insimag = insimag + '<a href="javascript:void(0);" onClick=\'insertString($("formBody"),"'+inst+'")\' title="插入第'+ ind +'个图片" > ' +
             $('insertImage').innerHTML + '</a> ';
      }                   
      $("attachsize").innerHTML = "有"+ attcount +"个附件:" + insimag;
   }
   
   
  function myalert(errorM){
        if (errorM == null) return;
        infoDiagClose();
        Dialog.alert(errorM, 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
  }
  
    

  function tag(theTag) {
    var e = $("formBody");        
    if (theTag == 'b') {
      insertString(e,"[b][/b]");        
    } else if (theTag == 'i') {
        insertString(e,"[i][/i]");
    } else if (theTag == 'u') {
        insertString(e,"[u][/u]");        
    } else if (theTag == 'code') {
        insertString(e,"\n[code]\n// 在此输入java代码\n[/code]");
    } else if (theTag == 'image') {
        var url = prompt("请输入一个图片的URL","http://");
        if (url != null) {
            insertString(e,"[img]" + url + "[/img]");
        }
    } else if (theTag == 'url') {
        var url = prompt("请输入链接的URL","http://");
        var text = prompt("请输入链接文本");
        if (url != null) {
            if (text != null) {
                insertString(e, "[url=" + url + "]" + text + "[/url]");
            } else {
                insertString(e, "[url]" + url + "[/url]");
            }
        }
    }
   }

  var formSubmitcheck = new Boolean(false);   
  var submitting = false; 
  function checkPost(theForm) {      
     if (!submitting)    
        submitting = true;
     else
        return;
           
      copyToClipboard($('formBody'));
      closeCopy();
      
      if (document.getElementById('forumId_select') == null
        || document.getElementById('forumId_select').value == ""){
            myalert("页面forum错误，请拷贝备份你的发言后，重新刷新本页");
            return formSubmitcheck;
     }
      
      
      if ((theForm.body.value  != "") && (theForm.subject.value  != "")){          
           if ((theForm.body.value.length  < bodyMaxLength)){
               
                  formSubmitcheck = new Boolean(true);
           }else{                   
                 myalert('请发言内容长度必须小于 ' + bodyMaxLength);                  
           }         
      }else{
           myalert("请输入发言标题和发言内容！");
           window.location.reload();          
      }
      
      return formSubmitcheck;      
   }




   var bodyLength = 0;
   function copyBody(){
       if ($('formBody').value != ""){
          if ($('formBody').value.length != bodyLength){
             bodyLength = $('formBody').value.length;
              copyToClipboard('formBody');
          }
       }
        
   }   
   
   
   
   var timedCopy;
   function startCopy(timeout){
       if (timedCopy == null)
           timedCopy = setInterval(copyBody, timeout);
   }
   
   function closeCopy(){
      clearInterval(timedCopy);
      timedCopy = null;
   }
   

function insertAtCursor(myField, myValue) {
  //IE support
  if (document.selection) {
    myField.focus();
    sel = document.selection.createRange();
    sel.text = myValue;
  }

  //MOZILLA/NETSCAPE support
  else if (myField.selectionStart || myField.selectionStart == '0') {
    var startPos = myField.selectionStart;
    var endPos = myField.selectionEnd;
    myField.value = myField.value.substring(0, startPos)
                  + myValue
                  + myField.value.substring(endPos, myField.value.length);
  } else {
    myField.value += myValue;
  }
}

function insertString(e,stringToInsert) {
	insertAtCursor(e,stringToInsert);
}
//end

function releaseKeyboard(){
   document.onkeydown=forumSubmit;
}   


function forumSubmit(event)
{
   var page;
   event = event ? event : (window.event ? window.event : null);

   if (event.keyCode==13 && event.ctrlKey){      
      document.getElementById("formSubmitButton").click();                     
   }else return;
   
 }
 
 