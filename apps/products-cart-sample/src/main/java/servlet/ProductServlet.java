package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import servlet.validator.Validator;

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
	private static final String JSP_PRODUCT_LIST    = JSP_VIEWS_DIR + "/product/list.jsp";
	private static final String JSP_PRODUCT_ENTRY   = JSP_VIEWS_DIR + "/product/entry.jsp";
	private static final String JSP_PRODUCT_CONFIRM = JSP_VIEWS_DIR + "/product/confirm.jsp";
	
	// 画面モード定数群
	private static final String MODE_INSERT = "insert";
	
	// パスパラメータ定数群
	private static final String PATH_LIST   = "/list";
	private static final String PATH_INSERT = "/" + MODE_INSERT; 
	
	// スコープ登録キー定数群
	private static final String KEY_CATEGORIES     = "appCategories";
	private static final String KEY_ACTION         = "action";
	private static final String KEY_ACTION_ENTRY   = "entry";
	private static final String KEY_ACTION_CONFIRM = "confirm";
	private static final String KEY_ACTION_EXECUTE = "execute";

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
		case PATH_INSERT: // 商品登録
			nextPath = this.insertProduct(request);
			break;
		case PATH_LIST: // 商品一覧表示
			nextPath = this.searchProductList(request);
			break;
		default:
			nextPath = JSP_DEFAULT_PAGE;
			break;
		}
		
		// 画面遷移実行オブジェクトを取得
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPath);
		// 画面遷移：フォワードの実行
		dispatcher.forward(request, response);
	}

	/**
	 * 商品を登録する
	 * @param request HttpServletRequestオブジェクト
	 * @return 遷移先URL
	 * @throws ServletException 処理中に発生したServlet例外
	 */
	private String insertProduct(HttpServletRequest request) throws ServletException {
		// 遷移先URLを初期化
		String nextPath = "";
		// リクエストパラメータのactionキーを取得
		String action = request.getParameter(KEY_ACTION);
		// 画面モードをリクエストスコープに登録
		request.setAttribute("mode", MODE_INSERT);
		// NPE対策：「定数.equals(変数)」の順で比較
		if (KEY_ACTION_ENTRY.equals(action)) {
			// 遷移先URLを設定
			nextPath = JSP_PRODUCT_ENTRY;
		} else if (KEY_ACTION_CONFIRM.equals(action)) {
			// リクエストパラメータを取得
			String categoryIdString = request.getParameter("categoryId");
			String name = request.getParameter("name");
			String priceString = request.getParameter("price");
			String quantityString = request.getParameter("quantity");
			
			// リクエストスコープに登録：次画面への引き継ぎ
			request.setAttribute("categoryId", categoryIdString);
			request.setAttribute("name", name);
			request.setAttribute("price", priceString);
			request.setAttribute("quantity", quantityString);
			
			// 入力値チェック
			List<String> errorList = this.validateRequestInput(categoryIdString, name, priceString, quantityString);
			
			// エラーの有無によって処理を分岐
			if (errorList.size() > 0) {
				// エラーメッセージをリクエストスコープに登録
				request.setAttribute("errorList", errorList);
				// 遷移先URLを設定
				nextPath = JSP_PRODUCT_ENTRY;
			} else {
				// 遷移先URLを設定
				nextPath = JSP_PRODUCT_CONFIRM;
			}
		} else if (KEY_ACTION_EXECUTE.equals(action)) {
			Product product = this.parseProductFromRequest(request);
			try (ProductDAO dao = new ProductDAO();) {
				// 商品登録の実行
				dao.store(product);
				// 商品一覧を取得
				List<Product> productList = dao.findAll();
				request.setAttribute("productList", productList);
				// 遷移先URLの設定
				nextPath = JSP_PRODUCT_LIST;
			} catch (DAOException e) {
				// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
				e.printStackTrace();
				// あらためてServletExceptionをスロー
				throw new ServletException(e.getMessage(), e);
			}
		} else {
			nextPath = JSP_DEFAULT_PAGE;
		}
		return nextPath;
	}

	/**
	 * 商品一覧を検索する
	 * @param request HttpServletRequestオブジェクト
	 * @return 遷移先URL
	 * @throws ServletException 処理中に発生したServlet例外
	 */
	private String searchProductList(HttpServletRequest request) throws ServletException {
		String nextPath = "";
		try (ProductDAO dao = new ProductDAO();) {
			// リクエストパラメータを取得
			String categoryIdString = request.getParameter("categoryId");
			String keyword = request.getParameter("keyword");
			String maxPriceString = request.getParameter("maxPrice");
			// リクエストパラメータによる処理の分岐
			List<Product> productList = this.searchProducts(request, dao, categoryIdString, keyword, maxPriceString);
			// 商品リストをリクエストスコープに登録：次画面へのデータの引き継ぎ
			request.setAttribute("productList", productList);
			// 遷移先URLの設定
			nextPath = JSP_PRODUCT_LIST;
			return nextPath;
		} catch (DAOException | NumberFormatException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * Post送信を受け付ける：formタグのmethod属性が「post」に指定されている場合に呼び出される
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// デフォルトではこのメソッドが呼び出されても、doGetメソッドを呼び出すだけ
		doGet(request, response);
	}

	/**
	 * リクエストパラメータから商品インスタンスを生成する
	 * @param request HttpServletRequestオブジェクト
	 * @return 商品インスタンス
	 */
	private Product parseProductFromRequest(HttpServletRequest request) {
		// リクエストパラメータを取得
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		// リクエストパラメータから登録する商品をインスタンス化
		Product product = new Product(categoryId, name, price, quantity);
		return product;
	}

	/**
	 * リクエストパラメータをチェックする
	 * @param  categoryIdString 商品カテゴリID
	 * @param  name             商品名
	 * @param  priceString      価格
	 * @param  quantityString   数量
	 * @return errorList        エラーメッセージリスト
	 * 
	 */
	private List<String> validateRequestInput(String categoryIdString, String name, String priceString, String quantityString) {
		List<String> errorList = new ArrayList<>();
		Validator.isRequiredAndPositiveInt("商品カテゴリ", categoryIdString, errorList);
		Validator.isRequired("商品名", name, errorList);
		Validator.isRequiredAndPositiveInt("価格", priceString, errorList);
		Validator.isRequiredAndPositiveInt("数量", quantityString, errorList);
		return errorList;
	}

	/**
	 * 商品を検索する
	 * @param request          HttpServletRequestオブジェクト
	 * @param dao              ProductDAOオブジェクト
	 * @param categoryIdString 商品カテゴリID
	 * @param keyword          検索キーワード
	 * @param maxPriceString   価格上限値
	 * @return                 商品リスト
	 * @throws DAOException    データベース処理中に発生するDAO例外
	 */
	private List<Product> searchProducts(HttpServletRequest request, ProductDAO dao, String categoryIdString, String keyword, String maxPriceString) throws DAOException {
		List<Product> productList = new ArrayList<>();
		if (!Utils.isNullOrEmpty(categoryIdString)) {
			// リクエストパラメータのデータ型変換
			int categoryId = Integer.parseInt(categoryIdString);
			productList = dao.findByCategoryId(categoryId);
		} else if (!Utils.isNullOrEmpty(keyword) && Utils.isNullOrEmpty(maxPriceString)) {
			productList = dao.findByNameLikeKeyword(keyword);
			request.setAttribute("keyword", keyword);
		} else if (Utils.isNullOrEmpty(keyword) && !Utils.isNullOrEmpty(maxPriceString)) {
			// リクエストパラメータのデータ型変換
			int maxPrice = Integer.parseInt(maxPriceString);
			productList = dao.findByPriceLessThanEqual(maxPrice);
			request.setAttribute("maxPrice", maxPrice);
		} else if (!Utils.isNullOrEmpty(keyword) && !Utils.isNullOrEmpty(maxPriceString)) {
			// リクエストパラメータのデータ型変換
			int maxPrice = Integer.parseInt(maxPriceString);
			productList = dao.findByNameLikeKeywordAndPriceLessThanEqual(keyword, maxPrice);
			request.setAttribute("keyword", keyword);
			request.setAttribute("maxPrice", maxPrice);
		} else {
			// 商品一覧用のすべての商品リストを取得
			productList = dao.findAll();
		}
		return productList;
	}

}
