package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	
	/**
	 * シリアルバージョンUID：「保存したときのクラスと、今のクラスが同じ構造かどうか」をチェックするためのID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Get送信を受け付ける：URLが指定されて呼び出される場合またはformタグのmethod属性が「get」または省略されている場合に呼び出される
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 遷移先URLを設定
		String nextPath = "/WEB-INF/views/index.jsp";
		// 画面遷移実行オブジェクトを取得
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPath);
		// 画面遷移：フォワードの実行
		dispatcher.forward(request, response);
	}

	/**
	 * Post送信を受け付ける：formタグのmethod属性が「post」に指定されている場合に呼び出される
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// デフォルトではこのメソッドが呼び出されても、doGetメソッドを呼び出すだけ
		doGet(request, response);
	}

}
