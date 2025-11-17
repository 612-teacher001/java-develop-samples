package bean.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import bean.Product;
import common.Utils;
import dto.ProductDTO;
import service.SessionHelper;
import servlet.validator.Validator;

public class ProductFormBean implements Serializable {

	/**
	 * フィールド
	 */
	private ProductDTO dto;         // ProductDTOインスタンス
	private List<String> errorList; // エラーメッセージリスト
	private boolean hasError;       // エラーの有無（エラーがある場合はtrue、それ以外はfalse）
	
	/**
	 * 引数なしコンストラクタ
	 */
	public ProductFormBean() {}

	/**
	 * コンストラクタ
	 * @param request HttpServletRequestオブジェクト
	 */
	public ProductFormBean(HttpServletRequest request) {
		// dtoフィールドに値を設定
		this.dto = new ProductDTO();
		this.dto.setId(request.getParameter("id"));
		this.dto.setCategoryId(request.getParameter("categoryId"));
		this.dto.setCategoryName(request.getParameter("categoryName"));
		this.dto.setName(request.getParameter("name"));
		this.dto.setPrice(request.getParameter("price"));
		this.dto.setQuantity(request.getParameter("quantity"));
		// errorListフィールドの初期化
		this.errorList = new ArrayList<>();
		// hasErrorフィールドの初期化
		this.hasError = false;
	}
	
	/**
	 * dtoフィールドのProductDTOインスタンスを取得する
	 * @return dtoフィールド
	 */
	public ProductDTO getDto() {
		return this.dto;
	}
	
	/**
	 * エラーメッセージリストを取得する
	 * @return errorListフィールド
	 */
	public List<String> getErrorList() {
		return this.errorList;
	}

	/**
	 * エラーがあるかどうかを調べる
	 * @return エラーがある場合はtrue、それ以外はfalse
	 */
	public boolean hasError() {
		return this.hasError;
	}

	/**
	 * ProductDTOインスタンスのフィールド値をリクエストスコープに登録する
	 * @param request HttpServletRequestオブジェクト
	 */
	public void bindDtoToRequestAttributes(HttpServletRequest request) {
		request.setAttribute("product", this.dto);
	}

	/**
	 * 入力値チェックを行う
	 * @return エラーメッセージリスト
	 */
	public void validate() {
		Validator.isRequiredAndPositiveInt("商品カテゴリ", dto.getCategoryId(), this.errorList);
		Validator.isRequired("商品名", dto.getName(), this.errorList);
		Validator.isRequiredAndPositiveInt("価格", dto.getPrice(), this.errorList);
		Validator.isRequiredAndPositiveInt("数量", dto.getQuantity(), this.errorList);
		if (this.errorList.size() > 0) {
			this.hasError = true;
		}
	}

	/**
	 * ProductDTOインスタンスをProductインスタンスに変換する
	 * @return Productインスタンス
	 */
	public Product convertDtoToBean() {
		// dtoフィールドの各値を取得
		int id = this.dto.getIdAsInt();
		int categoryId = this.dto.getCategoryIdAsInt();
		String name = this.dto.getName();
		int price = this.dto.getPriceAsInt();
		int quantity = this.dto.getQuantityAsInt();
		// リクエストパラメータから登録する商品をインスタンス化
		Product product = new Product(id, categoryId, name, price, quantity);
		return product;
	}

	/**
	 * ProductDTOインスタンスをセッションスコープに登録
	 * @param session HttpSesionオブジェクト
	 */
	public void bindDtoToSessionAttributes(HttpSession session) {
		// セッションスコープにproductの設定をチェック
		ProductDTO product = (ProductDTO) session.getAttribute("product");
		if (Utils.isNull(product)) {
			// 設定されていない場合はdtoフィールドから取得
			product = this.dto;
			session.setAttribute("product", product);
		} else {
			throw new IllegalStateException("システムエラーが発生しました。");
		}
	}

	/**
	 * セッションスコープのproductキーの値をdtoフィールドに設定する
	 * @param request HttpServletRequestオブジェクト
	 */
	public void setDtoFromSession(HttpServletRequest request) {
		// セッションスコープを取得
		HttpSession session = request.getSession(false);
		if (Utils.isNull(session)) {
			throw new IllegalStateException("システムエラーが発生しました。");
		}
		// セッションスコープからProductキーを取得
		this.dto = (ProductDTO) session.getAttribute("product");
		if (Utils.isNull(this.dto)) {
			throw new IllegalStateException("システムエラーが発生しました。");
		}
	}

	/**
	 * セッションスコープからproductキーを削除する
	 * @param request HttpServletRequestオブジェクト
	 */
	public void removeProductFromSession(HttpServletRequest request) throws IllegalStateException {
		// セッションスコープを取得
		SessionHelper.removeProductOrThrow(request);
	}

	/**
	 * リクエストスコープのproductキーに格納されたProductインスタンスを
	 * ProductDTOインスタンスに変換してセッションスコープに保存する
	 * @param request HttpServletRequestオブジェクト
	 */
	public void setDtoFromRequestAttribute(HttpServletRequest request) {
		Product bean = (Product) request.getAttribute("product");
		ProductDTO dto = this.convertBeanToDto(bean);
		SessionHelper.setProduct(request, dto);
	}

	/**
	 * ProductインスタンスをProductDTOインスタンスに変換する
	 * @param  bean Productインスタンス
	 * @return ProductDTOインスタンス
	 */
	private ProductDTO convertBeanToDto(Product bean) {
		String id = Integer.toString(bean.getId());
		String categoryId = Integer.toString(bean.getCategoryId());
		String name = bean.getName();
		String price = Integer.toString(bean.getPrice());
		String quantity = Integer.toString(bean.getQuantity());
		ProductDTO dto = new ProductDTO(categoryId, name, price, quantity);
		dto.setId(id);
		return dto;
	}
	
}
