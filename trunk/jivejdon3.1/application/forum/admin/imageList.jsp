<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'images.jsp' starting page</title>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
		<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>"
			type="text/css">
		<script type="text/JavaScript">
			function delConfirm(){
  				if (confirm( '删除该图片 ! \n\n 你肯定吗?  '))
  				{
    				document.forms[0].method.value="delete";
    				document.forms[0].submit();
    				return true;
  				}else{
    				return false;
  				}
			}
			function ReSizeImg(cName,w,h){
   				 var reImgs = document.getElementsByTagName("img");
    			for (j=0;j<reImgs.length;j++){
        			if (reImgs[j].className==cName && (reImgs[j].height>h || reImgs[j].width>w)) {
            			if (reImgs[j].height==reImgs[j].width) {
                			reImgs[j].height=h;reImgs[j].width=w;
            			} else if (reImgs[j].height>reImgs[j].width) {
                			reImgs[j].height=h;
            			} else if (reImgs[j].height<reImgs[j].width){
                			reImgs[j].width=w;
            			}
        			}
    			}
			}
		</script>
		<STYLE>
			.product_img_div {width:210px;height:190px;overflow:hidden}
		</STYLE>
	</head>

	<body>
		图片管理
		<br>

		<div style="width:1000px">
			<logic:iterate indexId="i" id="image" name="imageListForm"
				property="list">

				<div style="width:150px;display:inline;magin-left:50px;">
					<table>
						<tr>
							<td align="center">
								<bean:write name="image" property="name" />
							</td>
						</tr>
						<tr>
							<td align="center">
								<div class="product_img_div">
									<img
										src="<%=request.getContextPath()%>/imageShow.jsp?type=images/jpeg&id=<bean:write name="image" property="imageId" />"
										class="product_img" onload='ReSizeImg("product_img",200,180);' >
								</div>
							</td>
						</tr>
						<tr>
							<td align="center">
								<html:link page="/forum/admin/imageSaveAction.shtml?action=edit"
									paramId="imageId" paramName="image" paramProperty="imageId">
									编辑
								</html:link>
								|
								<html:link
									page="/forum/admin/imageSaveAction.shtml?action=delete"
									paramId="imageId" paramName="image" paramProperty="imageId"
									onclick="return delConfirm()">
									删除
								</html:link>
							</td>
						</tr>
					</table>
					&nbsp;&nbsp;&nbsp;
				</div>
			</logic:iterate>
		</div>
		<MultiPages:pager actionFormName="imageListForm"
			page="/imageListAction.do">
			<MultiPages:prev>前页</MultiPages:prev>
			<MultiPages:index></MultiPages:index>
			<MultiPages:next>后页</MultiPages:next>
		</MultiPages:pager>
	</body>
</html>
