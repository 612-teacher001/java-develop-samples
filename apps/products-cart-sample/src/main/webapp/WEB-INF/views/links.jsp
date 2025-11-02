<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/WEB-INF/views/taglib.jsp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
	<head>
		<meta charset="UTF-8">
		<title>サンプルページリンク</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/links.css">
	</head>
	<body id="links">
		<h1>サンプルページリンク</h1>
		<p>仕様書に記載の初期画面はこの画面です。</p>
		<p>この画面の各実装のリンクの機能を確認してください。</p>
		<table border="1">
			<tr class="row-bold">
				<th>バージョン</th>
				<th>マイルストン</th>
				<th>コード名</th>
				<th>処理内容</th>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.00</td>
				<td>MLS001</td>
				<td>prepared</td>
				<td>
					プロジェクトの初期化
				</td>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.10</td>
				<td>MLS002</td>
				<td>gin</td>
				<td>
					システムの初期画面を表示する
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS003</td>
				<td>vodka</td>
				<td>
					商品一覧画面の表示
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS004</td>
				<td>vodka</td>
				<td>
					商品の全件取得
				</td>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.20</td>
				<td>MLS005</td>
				<td>vodka</td>
				<td>
					すべての商品の一覧を表示する
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS006</td>
				<td>rum</td>
				<td>
					カテゴリリンクの表示
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS007</td>
				<td>rum</td>
				<td>
					カテゴリの全件取得
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS008</td>
				<td>rum</td>
				<td>
					カテゴリリンクの自動表示化
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS009</td>
				<td>rum</td>
				<td>
					カテゴリ検索の実装
				</td>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.21</td>
				<td>MLS010</td>
				<td>rum</td>
				<td>
					カテゴリリンクからのカテゴリ検索の結果を表示する
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS011</td>
				<td>tequila</td>
				<td>
					入力部品（テキストボックス）の表示
				</td>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.22</td>
				<td>MLS012</td>
				<td>tequila</td>
				<td>
					キーワードによる商品名検索の結果を表示する
				</td>
			</tr>
			<tr class="no-border">
				<td></td>
				<td>MLS013</td>
				<td>whisky</td>
				<td>
					入力部品（数値ボックス）の表示
				</td>
			</tr>
			<tr class="row-bold">
				<td>Ver.0.23</td>
				<td>MLS014</td>
				<td>whisky</td>
				<td>
					価格上限による範囲検索の結果を表示する
				</td>
			</tr>
		</table>
	</body>
</html>