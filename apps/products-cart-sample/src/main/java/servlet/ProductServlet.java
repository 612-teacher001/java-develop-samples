package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Category;
import bean.Product;
import common.Utils;
import dao.CategoryDAO;
import dao.ProductDAO;
import dao.common.DAOException;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet({"/ProductServlet", "/ProductServlet/*"})
public class ProductServlet extends HttpServlet {
	
	/**
	 * シリアルバージョンUID：「保存したときのクラスと、今のクラスが同じ構造かどうか」をチェックするためのID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * クラス定数
	 */
	// URL定数群
	private static final String JSP_VIEWS_DIR = "/WEB-INF/views";
	private static final String JSP_DEFAULT_PAGE = JSP_VIEWS_DIR + "/index.jsp";
	private static final String JSP_PRODUCT_LIST = JSP_VIEWS_DIR + "/product/list.jsp";
	
	private static final String PATH_LIST = "/list";
	
	private static final String KEY_CATEGORIES = "appCategories"; 

	/**
	 * 初期化処理
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		// アプリケーションスコープからカテゴリリストを取得
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) getServletContext().getAttribute(KEY_CATEGORIES);
		if (categoryList != null) {
			// すでにカテゴリリストが存在する場合は初期化不要
			return;
		}
		
		try (CategoryDAO dao = new CategoryDAO();) {
			categoryList = dao.findAll();
			// アプリケーションスコープに登録
			getServletContext().setAttribute(KEY_CATEGORIES, categoryList);
		} catch (DAOException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
	}
	
	/**
	 * Get送信を受け付ける：URLが指定されて呼び出される場合またはformタグのmethod属性が「get」または省略されている場合に呼び出される
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// リクエストの文字コードを設定
		request.setCharacterEncoding("utf-8");
		// パスインフォを取得
		String pathInfo = Utils.isNullOrEmpty(request.getPathInfo()) ? "" : request.getPathInfo();
		// 遷移先URLを設定
		String nextPath = JSP_DEFAULT_PAGE;
		
		switch (pathInfo) {
		case PATH_LIST: // 商品一覧表示
			try (ProductDAO dao = new ProductDAO();) {
				// リクエストパラメータを取得
				String categoryIdString = request.getParameter("categoryId");
				// リクエストパラメータによる処理の分岐
				List<Product> productList = null;
				if (categoryIdString != null) {
					// リクエストパラメータのデータ型変換
					int categoryId = Integer.parseInt(categoryIdString);
					productList = dao.findByCategoryId(categoryId);
				} else {
					// 商品一覧用のすべての商品リストを取得
					productList = dao.findAll();
				}
				// 商品リストをリクエストスコープに登録：次画面へのデータの引き継ぎ
				request.setAttribute("productList", productList);
				// 遷移先URLの設定
				nextPath = JSP_PRODUCT_LIST;
			} catch (DAOException | NumberFormatException e) {
				// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
				e.printStackTrace();
				// あらためてServletExceptionをスロー
				throw new ServletException(e.getMessage(), e);
			}
			break;
		default:
			break;
		}
		
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
