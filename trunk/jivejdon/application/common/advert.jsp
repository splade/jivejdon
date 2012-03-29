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
<script type="text/javascript"> 
alimama_pid="mm_10455490_523526_9871024"; 
alimama_titlecolor="0000FF"; 
alimama_descolor ="000000"; 
alimama_bgcolor="FFFFFF"; 
alimama_bordercolor="E6E6E6"; 
alimama_linkcolor="008000"; 
alimama_bottomcolor="FFFFFF"; 
alimama_anglesize="0"; 
alimama_bgpic="0"; 
alimama_icon="0"; 
alimama_sizecode="11"; 
alimama_width=760; 
alimama_height=90; 
alimama_type=2; 
</script> 
<script src="http://a.alimama.cn/inf.js" type="text/javascript"></script>
      </logic:equal>     
 </logic:present>

 <logic:notPresent name="adIndex" scope="request">
 <script type="text/javascript">
 u_a_client="1688";
 u_a_width="960";
 u_a_height="90";
 u_a_zones="892";
 u_a_type="0"
 </script>
 <script src="http://i.3bgg.com:1818/i.js"></script>

 </logic:notPresent>

</logic:equal>



<logic:equal name="fmt" value="336x280_2_b">
<script type="text/javascript">/*336*280，创建于2012-3-17*/ var cpro_id = 'u811104';</script><script src="http://cpro.baidu.com/cpro/ui/c.js" type="text/javascript"></script>
</logic:equal>

