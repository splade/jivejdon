<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<bean:define id="title" name="accountForm" property="username" />
<%@ include file="header.jsp" %>

<table width="750" border="0" cellspacing="0" cellpadding="0" align="center">

  <tr> 
    <td height="11" colspan="7" bgcolor="#FFFFFF" class="bianxian"> 
      <table width="100%" border="0" cellspacing="0" cellpadding="0" height="1" bordercolor="#000000">
        <tr> 
          <td> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0" height="2" bgcolor="#000000">
              <tr> 
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr> 
    
    <td bgcolor="#FFFFFF"  valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
        <tr> 
          <td width="122" valign="top"  bgcolor="#FFFFD7">
          	
<!--     XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX height="1000" -->

          	<table width="100%" border="0" cellspacing="0" cellpadding="3" height="1000" align="center">
            <tr>
              <td align="left" valign="top"><!-- TemplateBeginEditable name="Menu" --> <!-- TemplateEndEditable --> </td>
            </tr>
            <tr>
              <td align="right" valign="top">
              
              <!-- personal information -->
				姓    名 ：
				注册时间 ：
				职　　业 ：
				地　　址 ：
				主　　页 ：
				发贴总数 ：
 
             
            
				  

              </td>
            </tr>
            <tr><td valign="bottom">

            	

<div id=vgad120x240 style="position:absolute;height:250pt;top:expression(document.body.clientHeight-this.style.pixelHeight+document.body.scrollTop);font:9pt;">
</div>
     	
            	</td></tr>
          </table> 
           
          </td>
		  <td width="1"  height="100%" >
<table height="100%" cellpadding="0" cellspacing="0" background="../images/blackpoint.gif" >
              <tr><td> <img name="" src="" width="1" height="1" alt="" ></td></tr></table>
		  </td > 
          <td valign="top" > 
            <table width="100%" border="0" cellspacing="8" cellpadding="3">
              <tr>
                <td>
                <!--  context -->
                
                
                
                
                  </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@include file="footer.jsp"%>
