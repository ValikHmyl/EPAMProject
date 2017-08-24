<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="footer" var="footer"/>
<fmt:message key="footer.info" var="info"/>

<div class="footer">
	<div class="row text-center">
      		<div class="col-xs-5">
      			<a href="${pageContext.request.contextPath}/index.jsp"><img alt="logo" src="${pageContext.request.contextPath}/img/logo.png" width="85px"></a>
      			<p> ©2017 «McCafe» - ${footer }</p>
      		
      		</div>
      		<div class="col-xs-7">
      			<div class="col-sm-6"><span class="glyphicon glyphicon-map-marker"></span> ${info}</div>
      			<div class="col-sm-6"><span class="glyphicon glyphicon-earphone"></span> +375 29 567-00-00<br><span class="glyphicon glyphicon-earphone"></span> +375 25 789-00-00<br><span class="glyphicon glyphicon-earphone"></span> +375 33 678-00-00 </div>
			</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/order.js"></script>
<script src="${pageContext.request.contextPath}/js/edit.js"></script>
<script src="${pageContext.request.contextPath}/js/profile.js"></script>
</body>
</html>
