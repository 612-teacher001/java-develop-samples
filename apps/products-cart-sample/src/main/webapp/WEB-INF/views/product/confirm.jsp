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

		<!-- メインコンテンツ領域：商品確認画面 -->
		<main id="confirm">
			<article>
				<h2>商品登録</h2>
				<section>
					<p>以下の内容で登録しますか？</p>
					<form action="${pageContext.request.contextPath}/ProductServlet/insert" method="post">
						<table class="confirm__table">
							<%-- 商品更新の場合は商品IDの表示が必要 --%>
							<c:if test="${requestScope.mode ne 'insert'}">
							<tr>
								<th class="table__label">商品ID</th>
								<td class="table__value">${requestScope.product.id}</td>
							</tr>
							</c:if>
							<tr>
								<th class="table__label">商品カテゴリ</th>
								<td class="table__value">
									<c:forEach items="${applicationScope.appCategories}" var="category">
										<c:if test="${requestScope.product.categoryId == category.id}">
											${category.name}
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th class="table__label">商品名</th>
								<td class="table__value">
									<c:out value="${requestScope.product.name}" />
								</td>
							</tr>
							<tr>
								<th class="table__label">価格</th>
								<td class="table__value">
									<c:out value="${requestScope.product.price}" />
								</td>
							</tr>
							<tr>
								<th class="table__label">数量</th>
								<td class="table__value">
									<c:out value="${requestScope.product.quantity}" />
								</td>
							</tr>
						</table>
						<div class="confirm__nav">
							<a href="${pageContext.request.contextPath}/ProductServlet/list">一覧画面に戻る</a>
							<button class="confirm__submit">登録する</button>
							<input type="hidden" name="action" value="execute" />
							<%-- この画面では入力部品がないので登録処理するにはhiddenタグとして送信 --%>
							<input type="hidden" name="categoryId" value="${requestScope.product.categoryId}" />
							<input type="hidden" name="name" value="${requestScope.product.name}" />
							<input type="hidden" name="price" value="${requestScope.product.price}" />
							<input type="hidden" name="quantity" value="${requestScope.product.quantity}" />
						</div>
					</form>
				</section>
			</article>
		</main>

		<!-- ページフッタ領域 -->
		<jsp:include page="../common/footer.jsp" />

	</body>
</html>