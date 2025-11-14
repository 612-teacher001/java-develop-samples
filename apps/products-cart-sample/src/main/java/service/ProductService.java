package service;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import bean.Product;
import bean.form.ProductFormBean;
import common.Utils;
import dao.ProductDAO;
import dao.common.DAOException;

/**
 * 商品に関する業務を実行するサービスクラス
 */
public class ProductService extends BaseService {
	
	// URL定数群
	private static final String JSP_VIEWS_DIR         = "/WEB-INF/views";
	private static final String JSP_DEFAULT_PAGE      = JSP_VIEWS_DIR + "/index.jsp";
	private static final String JSP_PRODUCT_LIST      = JSP_VIEWS_DIR + "/product/list.jsp";
	private static final String JSP_PRODUCT_ENTRY     = JSP_VIEWS_DIR + "/product/entry.jsp";
	private static final String JSP_PRODUCT_CONFIRM   = JSP_VIEWS_DIR + "/product/confirm.jsp";
	private static final String REDIRECT_PRODUCT_LIST = "/ProductServlet/list";
	
	// 画面モード定数群
	private static final String MODE_INSERT = "insert";
	
	private static final String KEY_ACTION         = "action";
	private static final String KEY_ACTION_ENTRY   = "entry";
	private static final String KEY_ACTION_CONFIRM = "confirm";
	private static final String KEY_ACTION_EXECUTE = "execute";

	/**
	 * 各種検索結果を含めた商品一覧を表示する
	 * @param request HttpServletRequestオブジェクト
	 * @return 商品一覧画面のパス
	 * @throws ServletException
	 */
	public String showProductList(HttpServletRequest request) throws ServletException {
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
	 * 商品登録サービスを実行する
	 * @param request HttpServletRequestオブジェクト
	 * @return 遷移先URL
	 * @throws ServletException
	 */
	public String insertProduct(HttpServletRequest request) throws ServletException {
		// リクエストパラメータのactionキーを取得
		String action = request.getParameter(KEY_ACTION);
		// 画面モードをリクエストスコープに登録
		request.setAttribute("mode", MODE_INSERT);
		// NPE対策：「定数.equals(変数)」の順で比較
		String nextPath = "";
		if (KEY_ACTION_ENTRY.equals(action)) {
			nextPath = JSP_PRODUCT_ENTRY;
		} else if (KEY_ACTION_CONFIRM.equals(action)) {
			nextPath = showConfirmPage(request);
		} else if (KEY_ACTION_EXECUTE.equals(action)) {
			nextPath = executeInsert(request);
		} else {
			nextPath = JSP_DEFAULT_PAGE;
		}
		return nextPath;
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
	
	/**
	 * 入力値チェックをして確認画面を表示する
	 * @param  request HttpServletRequestオブジェクト
	 * @return エラーがない場合は確認画面のパス、それ以外の場合は登録画面のパス
	 */
	private String showConfirmPage(HttpServletRequest request) {
		
		// ProductFormBeanをインスタンス化
		ProductFormBean formBean = new ProductFormBean(request);
		// 取得したリクエストパラメータをリクエストスコープに登録
		formBean.bindDtoToRequestAttributes(request);
		
		// 入力値チェック
		List<String> errorList = formBean.validate();
		
		// エラーの有無によって処理を分岐
		String nextPath = "";
		if (errorList.size() > 0) {
			// エラーメッセージをリクエストスコープに登録
			request.setAttribute("errorList", errorList);
			// 遷移先URLを設定
			nextPath = JSP_PRODUCT_ENTRY;
		} else {
			// 遷移先URLを設定
			nextPath = JSP_PRODUCT_CONFIRM;
		}
		
		return nextPath;
	}

	/**
	 * 商品を登録する
	 * @param request HttpServletRequestオブジェクト
	 * @return 商品一覧画面のパス
	 * @throws ServletException
	 */
	private String executeInsert(HttpServletRequest request) throws ServletException {
		// ProductFormBeanをインスタンス化
		ProductFormBean formBean = new ProductFormBean(request);
		Product product = formBean.convertDtoToBean();
		String nextPath = "";
		try (ProductDAO dao = new ProductDAO();) {
			// 商品登録の実行
			dao.store(product);
			// 遷移先URLの設定
			nextPath = REDIRECT_PRODUCT_LIST;
			return nextPath;
		} catch (DAOException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
	}

}
