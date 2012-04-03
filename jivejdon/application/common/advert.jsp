<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="fmt" name="fmt" />

<logic:equal name="fmt" value="728x90">

  <logic:present name="adIndex" scope="request">
     <logic:equal name="adIndex" value="0">
     <script type="text/javascript">
 u_a_client="1688";
 u_a_width="960";
 u_a_height="90";
 u_a_zones="892";
 u_a_type="0"
 </script>
 <script src="http://i.3bgg.com:1818/i.js"></script>     
     </logic:equal>
     <logic:equal name="adIndex" value="1">
     <script type="text/javascript">/*728*90，创建于2012-3-17*/ var cpro_id = 'u811115';</script><script src="http://cpro.baidu.com/cpro/ui/c.js" type="text/javascript"></script>
     </logic:equal>
     <logic:equal name="adIndex" value="2">
              <script type="text/javascript"  language="javascript">
                 var show_url = '<scr' + 'ipt type="text/javascript" language="javascript" src="http://www.samboc.com/svr/show.js?mark=0';
                 show_url += '&sn=000058a40000c5130000c7986402d8005a';
                 show_url += '&url=' + window.location;
                 show_url += '"><' + '/script>';
                 document.write(show_url);
              </script>            
     </logic:equal>     
     <logic:equal name="adIndex" value="3">
<script type="text/javascript">
var reffer ="";
var url ="";
if (window.parent != window.self){
try{reffer = parent.document.referrer; }
catch(err) { reffer = document.referrer;}
try { url = parent.document.location;}
catch(err) { url = document.location;}
}else{reffer = document.referrer; url = document.location;}
document.writeln("<iframe marginwidth='0' marginheight='0' frameborder='0' bordercolor='#000000' scrolling='no' src='http://pv.heima8.com/index.php?p=138919661&b=100002881&reffer="+escape(reffer)+"&url="+escape(url)+"' width='760' height='90'></iframe>");
</script>
     </logic:equal>
          
 </logic:present>

 <logic:notPresent name="adIndex" scope="request">

              <script type="text/javascript"  language="javascript">
                 var show_url = '<scr' + 'ipt type="text/javascript" language="javascript" src="http://www.samboc.com/svr/show.js?mark=0';
                 show_url += '&sn=000058a40000c5130000c7986402d8005a';
                 show_url += '&url=' + window.location;
                 show_url += '"><' + '/script>';
                 document.write(show_url);
              </script>

 </logic:notPresent>

</logic:equal>



<logic:equal name="fmt" value="336x280_2_b">
<script type="text/javascript">/*336*280，创建于2012-3-17*/ var cpro_id = 'u811104';</script><script src="http://cpro.baidu.com/cpro/ui/c.js" type="text/javascript"></script>
</logic:equal>

