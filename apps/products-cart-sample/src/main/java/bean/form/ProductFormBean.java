package bean.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import bean.Product;
import dto.ProductDTO;
import servlet.validator.Validator;

public class ProductFormBean implements Serializable {

	/**
	 * フィールド
	 */
	private ProductDTO dto;
	
	/**
	 * コンストラクタ
	 * @param request HttpServletRequestオブジェクト
	 */
	public ProductFormBean(HttpServletRequest request) {
		this.dto = new ProductDTO();
		this.dto.setCategoryId(request.getParameter("categoryId"));
		this.dto.setCategoryName(request.getParameter("categoryName"));
		this.dto.setName(request.getParameter("name"));
		this.dto.setPrice(request.getParameter("price"));
		this.dto.setQuantity(request.getParameter("quantity"));
	}
	
	/**
	 * dtoフィールドのProductDTOインスタンスを取得する
	 * @return dtoフィールド
	 */
	public ProductDTO getDto() {
		return this.dto;
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
	public List<String> validate() {
		List<String> errorList = new ArrayList<>();
		Validator.isRequiredAndPositiveInt("商品カテゴリ", dto.getCategoryId(), errorList);
		Validator.isRequired("商品名", dto.getName(), errorList);
		Validator.isRequiredAndPositiveInt("価格", dto.getPrice(), errorList);
		Validator.isRequiredAndPositiveInt("数量", dto.getQuantity(), errorList);
		return errorList;
	}

	/**
	 * ProductDTOインスタンスをProductインスタンスに変換する
	 * @return Productインスタンス
	 */
	public Product convertDtoToBean() {
		// dtoフィールドの各値を取得
		int categoryId = this.dto.getCategoryIdAsInt();
		String name = this.dto.getName();
		int price = this.dto.getPriceAsInt();
		int quantity = this.dto.getQuantityAsInt();
		// リクエストパラメータから登録する商品をインスタンス化
		Product product = new Product(categoryId, name, price, quantity);
		return product;
	}
	
}
