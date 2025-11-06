package bean;

import java.io.Serializable;

/**
 * productsテーブルの1件分のレコードを格納するJavaBeans
 */
public class Product implements Serializable {
	
	/**
	 * フィールド
	 */
	private int id;         // 商品ID
	private int categoryId; // カテゴリID
	private String name;    // 商品名
	private int price;      // 価格
	private int quantity;   // 在庫数
	
	/**
	 * 引数なしコンストラクタ
	 */
	public Product() {}

	/**
	 * コンストラクタ
	 * @param categoryId カテゴリID
	 * @param name       商品名
	 * @param price      価格
	 * @param quantity   在庫数
	 */
	public Product(int categoryId, String name, int price, int quantity) {
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	/**
	 * コンストラクタ
	 * @param id         商品ID
	 * @param categoryId カテゴリID
	 * @param name       商品名
	 * @param price      価格
	 * @param quantity   在庫数
	 */
	public Product(int id, int categoryId, String name, int price, int quantity) {
		this(categoryId, name, price, quantity);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		builder.append("Product [");
		builder.append("id=" + id + ", ");
		builder.append("categoryId=" + categoryId + ", ");
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
		builder.append("Product [");
		builder.append("categoryId=" + categoryId + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity);
		builder.append("]");
		return builder.toString();
	}
	
}
