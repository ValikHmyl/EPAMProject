<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<jsp:include page="/jsp/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/jsp/parts/userNav.jsp" />
				<div class="well col-xs-8">
					<div id="editForm" style="display: none"></div>
					<table>
						<thead>
							<tr>
								<th>id</th>
								<th>date</th>
								<th>date2</th>
								<th>status</th>
								<th>price</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr id="${order.id }">
									<td>${order.id }</td>
									<td>${order.orderDate }</td>
									<td>${order.confirmDate }</td>
									<td>${order.status }</td>
									<td>$${order.totalPrice }</td>
									<td>
										<button class="btn btn-info details" data-id="${order.id }">details</button>
									</td>
									<td><button class="btn btn-warning edit"
											data-id="${order.id }">edit</button></td>
								</tr>
								<tr id="details${order.id}" style="display: none">
									<td colspan="7" rowspan="${order.cart.size }">
										<div class="cartDetails" style="display: none">
											<c:forEach var="item" items="${order.cart.keySet() }">
												<p>${item.name }${item.price} ${order.cart[item] }</p>
											</c:forEach>
										</div>
										<div class="editForm" style="display: none">
											<div>
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
												</div>		
												<div>								
											<button class="btn btn-danger cancel" data-id="${order.id}"> cancel</button>
											<p id="errorConfirm" style="display:none">errormsg<p>
											</div>
										</div>
									</td>
								</tr>

							</c:forEach>
						</tbody>
					</table>
				</div>

			</div>
		</div>
		<jsp:include page="/jsp/parts/footer.jsp" />
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