<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>サンプルショッピングサイト</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	</head>
	<body>
		<header>
			<h1>サンプルショッピング</h1>
		</header>
		<main id="top">
			<article>
				<section clas="result">
					<table class="result__table">
						<tr class="table__row">
							<th class="table__header">商品ID</th>
							<th class="table__header">商品名</th>
							<th class="table__header">価格</th>
							<th class="table__header">数量</th>
						</tr>
						<c:forEach items="${requestScope.productList}" var="product">
						<tr class="table__row">
							<td class="table__cell">${product.id}</td>
							<td class="table__cell">${product.name}</td>
							<td class="table__cell">${product.price}</td>
							<td class="table__cell">${product.quantity}</td>
						</tr>
						</c:forEach>
					</table>
				</section>
			</article>
		</main>
		<footer>
			<div>&copy;2025 Sample Shopping Co.</div>
		</footer>
	</body>
</html>