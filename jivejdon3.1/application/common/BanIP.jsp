<%
try{
   String ip = request.getParameter("ip");
   if (ip == null)
      ip = request.getRemoteAddr();

   java.util.Calendar rightNow = java.util.Calendar.getInstance();
   int hour = rightNow.get(java.util.Calendar.HOUR_OF_DAY);
   if ((hour < 10) || (hour > 16)){
        System.out.println("not blocked");
   }else{
	Runtime sys= Runtime.getRuntime();
        //for linux iptables
        //sys.exec("/usr/bin/sudo -u root /sbin/iptables -A INPUT -s "+ ip +" -j DROP");
        sys.exec("/usr/bin/sudo -u root /home/jdon/block "+ ip );
        System.err.println(request.getRemoteAddr()+" was blocked");
   }

}catch(Exception e){

}
%>
