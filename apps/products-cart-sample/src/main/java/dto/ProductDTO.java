package dto;

import java.io.Serializable;

/**
 * 商品情報を保持するデータ転送オブジェクト（DTO）
 */
public class ProductDTO implements Serializable {

	/**
	 * フィールド
	 */
	private String id;           // 商品ID
	private String categoryId;   // 商品カテゴリID
	private String categoryName; // 商品カテゴリ名
	private String name;         // 商品名
	private String price;        // 価格
	private String quantity;     // 数量
	
	/**
	 * 引数なしコンストラクタ
	 */
	public ProductDTO() {}

	/**
	 * コンストラクタ
	 * @param categoryId 商品カテゴリID
	 * @param name       商品名
	 * @param price      価格
	 * @param quantity   数量
	 */
	public ProductDTO(String categoryId, String name, String price, String quantity) {
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * コンストラクタ
	 * @param categoryId   商品カテゴリID
	 * @param categoryName 商品カテゴリ名
	 * @param name         商品名
	 * @param price        価格
	 * @param quantity     数量
	 */
	public ProductDTO(String categoryId, String categoryName, String name, String price, String quantity) {
		this(categoryId, name, price, quantity);
		this.categoryName = categoryName;
	}

	/**
	 * コンストラクタ
	 * @param id           商品ID
	 * @param categoryId   商品カテゴリID
	 * @param categoryName 商品カテゴリ名
	 * @param name         商品名
	 * @param price        価格
	 * @param quantity     数量
	 */
	public ProductDTO(String id, String categoryId, String categoryName, String name, String price, String quantity) {
		this(categoryId, categoryName, name, price, quantity);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public int getCategoryIdAsInt() {
		// インスタンス化された時点ですでにnullではないという前提でnullチェックは省略
		return Integer.parseInt(this.categoryId);
	}

	public int getPriceAsInt() {
		// インスタンス化された時点ですでにnullではないという前提でnullチェックは省略
		return Integer.parseInt(this.price);
	}

	public int getQuantityAsInt() {
		// インスタンス化された時点ですでにnullではないという前提でnullチェックは省略
		return Integer.parseInt(this.quantity);
	}
	
	/**
	 * このオブジェクトの文字列表現を返す。
	 * <br />
	 * 主にデバッグやログ出力など、人間が読みやすい形式での利用を想定している。<br />
	 * オブジェクトの内部状態をすべて網羅しているが、比較やハッシュ計算用でない。
	 * 比較用の文字列が必要な場合は {@link #toCompare()} を使用する。
	 *
	 * @return オブジェクトの人間向け文字列表現
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDTO [");
		builder.append("id=" + id + ", ");
		builder.append("categoryId=" + categoryId + ", ");
		builder.append("categoryName=" + categoryName + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * このオブジェクトの比較用文字列表現を返しす。
	 * <br />
	 * 主キー以外の内部状態のすべてを反映しており、比較の精度が必要な場面で使用する。<br />
	 * デバッグやログ出力のような「人間向け」の表示には {@link #toString()} を使用する。
	 *
	 * @return 比較用文字列表現
	 */
	public String toCompare() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductDTO [");
		builder.append("categoryId=" + categoryId + ", ");
		builder.append("categoryName=" + categoryName + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity);
		builder.append("]");
		return builder.toString();
	}

}
