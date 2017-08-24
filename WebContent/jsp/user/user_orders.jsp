<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />

<title>user</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/user_nav.jsp" />
				<div class="well col-sm-7">
				<ul class="nav nav-tabs text-center">
					<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=all&pageNumber=1">all</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=active&pageNumber=1">active</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=taken&pageNumber=1">taken</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=overdue&pageNumber=1">overdue</a></li>

  				</ul>
				<c:if test="${requestScope['errorMsg'] }"> <div>error ${orderId }</div></c:if>
					 <div class="pagination">
                 		<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command= "user_open_orders"/>  find:${total }
         		   	</div>
							<div class="row">
								<div class="col-xs-1">id</div>
								<div class="col-xs-3 col-sm-2">status</div>
								<div class="col-xs-3">date</div>
								
							</div>
							<c:forEach var="order" items="${orders}">
								<div class="row" id="${order.id }">
									<div class="col-xs-1">${order.id }</div>
									<div class="col-xs-3 col-sm-2 status">${order.status }</div>
									<div class="col-xs-3">${order.orderDate }</div>
									<div class="col-xs-3 col-sm-2">
										<button class="btn btn-info details" data-id="${order.id }">details</button>
									</div>
									<div class="col-xs-2"><button class="btn btn-warning edit"
											data-id="${order.id }" <c:if test="${order.status !='ACTIVE'}">disabled</c:if>>edit</button></div>
								</div>
								<div class="more" id="details${order.id}" style="display: none;">
									<div>
										<div class="cartDetails" style="display: none">
											<c:forEach var="item" items="${order.cart.keySet() }">
												<p>${item.name }${item.price} ${order.cart[item] }</p>
											</c:forEach>
											<p>total: $${order.totalPrice }</p>
											<p>date: ${order.confirmDate }</p>
										</div>
										<div class="editForm" style="display: none">
											<form method="POST" action="${pageContext.request.contextPath}/controller">
												<input type="hidden" name="command" value="edit_order">
												<input type="hidden" name="orderId" value="${order.id }">
												<input type="date" name="date" class="date" required /> 
												<select	class="time" name="time">
													<option>09:30</option>
													<option>10:00</option>
													<option>10:30</option>
													<option>11:00</option>
													<option>11:30</option>
													<option>12:00</option>
													<option>12:30</option>
													<option>13:00</option>
													<option>13:30</option>
													<option>14:00</option>
													<option>14:30</option>
													<option>15:00</option>
													<option>15:30</option>
													<option>16:00</option>
													<option>16:30</option>
													<option>17:00</option>
													<option>17:30</option>
													<option>18:00</option>
													<option>18:30</option>
													<option>19:00</option>
													<option>19:30</option>
													<option>20:00</option>
													<option>20:30</option>
												</select>
												
												<button class="btn btn-default"> edit</button>
												</form>		
												<div>								
											<button class="btn btn-danger cancel" data-id="${order.id}"> cancel</button>
											<p id="errorConfirm" style="display:none">errormsg<p>
											</div>
										</div>
									</div>
								</div>

							</c:forEach>
					
				</div>

			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>
		 <div class="modal fade" id="modalConfirm" role="dialog">
     	<div class="modal-dialog modal-sm">
      		<div class="modal-content">
      		 <div class="modal-header">
          		<button type="button" class="close" data-dismiss="modal">&times;</button>
        	  	<h4 class="modal-title">Confirm</h4>
        	  	</div>
        		<div class="modal-body">
          			<p>sure?</p>
          			<button id="confirmCancel" class="btn btn-danger"> yes</button>
          			<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    	    </div>
   		</div>
     </div>
		 <div class="modal fade" id="modalError" role="dialog">
     	<div class="modal-dialog modal-sm">
          		<div class="modal-content">
        		<div class="modal-body">
          			<p id="error"></p>
        		</div>
    	    </div>
   		</div>
     </div>
		</div>