package service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionHelper {

	/**
	 * セッションスコープが存在する場合に登録されたproductキーを削除する
	 * @param request HttpServletRequestオブジェクト
	 */
	public static void removeProductOrThrow(HttpServletRequest request) {
		removeOrThrow("product", request);
	}
	
	/**
	 * セッションが存在する場合に指定されたキーの属性を削除する
	 * セッションがない場合は IllegalStateException をスロー
	 * @param key 削除するセッションのキー名
	 * @param request HttpServletRequestオブジェクト
	 * @throws IllegalStateException
	 */
	private static void removeOrThrow(String key, HttpServletRequest request) throws IllegalStateException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new IllegalStateException("システムエラーが発生しました。");
		}
		session.removeAttribute(key);
	}

	/**
	 * セッションスコープに登録されたproductキーを削除する
	 * @param request HttpServletRequestオブジェクト
	 */
	public static void removeProductIfExists(HttpServletRequest request) {
		removeIfExists("product", request);
	}
	
	/**
	 * セッションが存在すれば指定されたキーの属性を削除する
	 * セッションがない場合は何もしない
	 * @param key 削除するセッションのキー名
	 * @param request HttpServletRequestオブジェクト
	 */
	private static void removeIfExists(String key, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(key);
		}
	}
	
}
