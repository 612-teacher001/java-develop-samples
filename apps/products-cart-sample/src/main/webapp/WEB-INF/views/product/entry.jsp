<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<!-- head要素領域 -->
	<jsp:include page="../common/head.jsp" />
	
	<body>

		<!-- ページヘッダ領域 -->
		<jsp:include page="../common/header.jsp" />

		<!-- メインコンテンツ領域：商品登録画面 -->
		<main id="entry">
			<article>
				<h2>商品登録</h2>
				<section>
					<ul class="entry__error">
						<c:forEach items="${requestScope.errorList}" var="error">
						<li>${error}</li>
						</c:forEach>
					</ul>
					<form action="${pageContext.request.contextPath}/ProductServlet/insert" method="post">
						<table class="entry__table">
							<%-- 商品更新の場合は商品IDの表示が必要 --%>
							<c:if test="${requestScope.mode ne 'insert'}">
							<tr>
								<th class="table__label">商品ID</th>
								<td class="table__value"></td>
							</tr>
							</c:if>
							<tr>
								<th class="table__label">商品カテゴリ</th>
								<td class="table__value">
									<select name="categoryId">
										<c:forEach items="${applicationScope.appCategories}" var="category">
											<c:choose>
												<c:when test="${requestScope.product.categoryId == category.id}">
													<option value="${category.id}" selected>${category.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${category.id}">${category.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th class="table__label">商品名</th>
								<td class="table__value">
									<input type="text" name="name" placeholder="商品名" value="<c:out value='${requestScope.product.name}' />" />
								</td>
							</tr>
							<tr>
								<th class="table__label">価格</th>
								<td class="table__value">
									<input type="number" name="price" placeholder="価格" value="<c:out value='${requestScope.product.price}' />" />
								</td>
							</tr>
							<tr>
								<th class="table__label">数量</th>
								<td class="table__value">
									<input type="number" name="quantity" placeholder="数量" value="<c:out value='${requestScope.product.quantity}' />" />
								</td>
							</tr>
						</table>
						<div class="entry__nav">
							<a href="${pageContext.request.contextPath}/ProductServlet/list">一覧画面に戻る</a>
							<button class="entry__submit">確認画面へ</button>
							<input type="hidden" name="action" value="confirm" />
						</div>
					</form>
				</section>
			</article>
		</main>

		<!-- ページフッタ領域 -->
		<jsp:include page="../common/footer.jsp" />

	</body>
</html>