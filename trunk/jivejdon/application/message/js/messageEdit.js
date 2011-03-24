
function callbackSubmit(){
    openInfoDiag("已经提交...");     

}

function forwardNewPage(fmainurl, fmainPars, anchor){
      infoDiagClose();         
      var url = fmainurl + "/nocache" +fmainPars + "#" + anchor;
      window.location.href =  url;
      
}
