<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<jsp:include page="/WEB-INF/parts/top_banner.jsp" />
		<div class="content">



			<h1>О нас</h1>


			<div class="row">
				<div class="col col-sm-4">
					<img align="left" alt="about" class="img-responsive" height="222"
						src="${pageContext.request.contextPath}/img/about_us_1.png"
						width="300" />
				</div>
				<div class="col col-sm-8">Lorem ipsum dolor sit amet,
					consectetur adipiscing elit. Donec tincidunt odio sed dapibus
					rhoncus. Cras vitae elementum leo. Morbi luctus urna in nisi
					volutpat rhoncus. Ut pharetra tortor vitae urna congue tincidunt.
					Nulla et ligula auctor, scelerisque leo vel, mattis lorem.
					Vestibulum porta pulvinar elit, nec semper neque luctus sit amet.
					Morbi sed lobortis velit, eu volutpat nibh. Nullam id mattis magna.
					Maecenas imperdiet odio ipsum, eu viverra lorem bibendum nec. Nulla
					sodales diam quam. Maecenas elementum non mi a porttitor.
					Pellentesque eget massa sed ipsum lacinia efficitur. Etiam est
					lectus, mattis id metus nec, ultrices consectetur nisl. Nullam sit
					amet aliquam libero.</div>
			</div>
			<div class="row">
				<div class=" col-sm-8">Lorem ipsum dolor sit amet, consectetur
					adipiscing elit. Maecenas sit amet nisl consectetur, porta lorem
					imperdiet, mattis risus. Nullam aliquet nisl id metus volutpat
					luctus. In et nisi et orci feugiat dapibus eu laoreet elit. Donec
					posuere gravida euismod. Nullam eu ante et erat ullamcorper
					vehicula vel molestie metus. Nulla et ultrices ligula, vitae
					ultricies augue. Vivamus hendrerit, massa in lobortis hendrerit,
					arcu nisi molestie nunc, sit amet mattis arcu leo ac elit. Nunc
					faucibus pretium elit, vel maximus orci pretium vel. Morbi
					imperdiet auctor sapien, quis lacinia massa imperdiet ut. Ut non
					enim nec nibh sollicitudin luctus vehicula vel velit.</div>
				<div class="col-sm-4">
					<img align="left" alt="about" class="img-responsive" height="211"
						src="${pageContext.request.contextPath}/img/about_us_2.png"
						width="300" />
				</div>
			</div>
		</div>
		
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>