package service;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * すべてのサービスクラスが継承する基底クラス
 */
public class BaseService {
	
	/**
	 * クラス内定数
	 */
	// 内部リソース（JSP）を配置するディレクトリ：フォワードとリダイレクトの判定条件
	private static final String INTERNAL_VIEW_PATH = "/WEB-INF/";

	/**
	 * 指定されたURLに遷移する
	 * @param request  HttpServletRequestオブジェクト
	 * @param response HttpServletResponseオブジェクト
	 * @param nextPath 遷移先URL
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void dispatch(HttpServletRequest request, HttpServletResponse response, String nextPath) throws ServletException, IOException {
		if (isInternalView(nextPath)) {
			// JSPファイルパスの場合
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPath);
			dispatcher.forward(request, response);
		} else {
			// Servletパスの場合
			response.sendRedirect(request.getContextPath() + nextPath);
		}
	}

	/**
	 * 指定されたパスが内部リソース（JSP）を配置するディレクトリを含んでいるかどうかを判定する
	 * @param  nextPath 判定対象パス
	 * @return 内部リソース（JSP）を配置するディレクトリを含んでいる場合はtrue、それ以外はfalse
	 */
	private boolean isInternalView(String nextPath) {
		return (nextPath.contains(INTERNAL_VIEW_PATH));
	}
}
