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
<script type="text/JavaScript" charset="gb2312">
 s_noadid="";
 s_hang=1;
 s_lie=2;
 s_width=720;
 s_height=90;
 s_id=41422;
 s_bk_color="CCCCCC";
 s_bt_color="c90000";
 s_bg_color="ffffff";
</script>
<script src="http://e.70e.com/js/cpc_wz_tw_ztyw.js" type=text/javascript charset="gb2312"></script>
     </logic:equal>
     <logic:equal name="adIndex" value="3">
       <iframe src="http://www.hexinx.com/page/?s=595"  width="760" height="90" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no"></iframe>
     </logic:equal>
          
 </logic:present>

 <logic:notPresent name="adIndex" scope="request">
     <iframe src="http://www.hexinx.com/page/?s=595"  width="760" height="90" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no"></iframe>
 </logic:notPresent>

</logic:equal>



<logic:equal name="fmt" value="336x280_2_b">
<script type="text/javascript">/*336*280，创建于2012-3-17*/ var cpro_id = 'u811104';</script><script src="http://cpro.baidu.com/cpro/ui/c.js" type="text/javascript"></script>
</logic:equal>

