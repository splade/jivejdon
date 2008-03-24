
<%
try{
    String ip = request.getParameter("ip");
  
	Runtime sys= Runtime.getRuntime();
    sys.exec("/usr/bin/sudo -u root /sbin/iptables -A INPUT -s "+ ip +" -j DROP");
    System.out.println(request.getRemoteAddr()+" was blocked");

}catch(Exception e){
   System.err.println(e.toString());
}
%>
