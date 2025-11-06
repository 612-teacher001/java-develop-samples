<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				<section class="criteria">
					<form action="${pageContext.request.contextPath}/ProductServlet/list" method="post">
						<input type="text" name="keyword" placeholder="商品名" />
						<button>検索</button>
					</form>
					<ul class="criteria__list--links">
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list">全商品</a></li>
						<c:forEach items="${applicationScope.appCategories}" var="category">
						<li><a href="${pageContext.request.contextPath}/ProductServlet/list?categoryId=${category.id}">${category.name}</a></li>
						</c:forEach>
					</ul>
				</section>
				<section class="result">
					<table class="result__table">
						<tr>
							<th>商品ID</th>
							<th>商品名</th>
							<th>価格</th>
							<th>数量</th>
						</tr>
						<c:forEach items="${requestScope.productList}" var="product">
						<tr>
							<td data-type="id" class="td-id">${product.id}</td>
							<td>${product.name}</td>
							<td data-type="number" class="td-price"><fmt:formatNumber value="${product.price}" pattern="###,### 円" /></td>
							<td data-type="number" class="td-quantity">${product.quantity}</td>
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