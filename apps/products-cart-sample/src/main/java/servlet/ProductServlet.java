package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import common.Utils;
import service.CategoryService;
import service.ProductService;

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
	private static final String JSP_VIEWS_DIR       = "/WEB-INF/views";
	private static final String JSP_DEFAULT_PAGE    = JSP_VIEWS_DIR + "/index.jsp";
	
	// 画面モード定数群
	private static final String MODE_INSERT = "insert";
	
	// パスパラメータ定数群
	private static final String PATH_LIST   = "/list";
	private static final String PATH_INSERT = "/" + MODE_INSERT; 
	
	/**
	 * 初期化処理
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		// サービス実行オブジェクトをインスタンス化
		CategoryService service = new CategoryService();
		// 商品カテゴリをアプリケーションスコープに登録
		service.initializeCategoriesIfAbsent(getServletContext());
		
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
		
		// サービス実行のインスタンス化
		ProductService service = new ProductService();
		
		switch (pathInfo) {
		case PATH_INSERT: // 商品登録
			nextPath = service.insertProduct(request);
			break;
		case PATH_LIST: // 商品一覧表示
			nextPath = service.showProductList(request);
			break;
		default:
			nextPath = JSP_DEFAULT_PAGE;
			break;
		}
		
		// 画面遷移
		service.dispatch(request, response, nextPath);

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
