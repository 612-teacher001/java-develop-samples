package bean;

import java.io.Serializable;

/**
 * categoriesテーブルの1件分のレコードを格納するJavaBeans
 */
public class Category implements Serializable {
	
	/**
	 * フィールド
	 */
	private int id;      // カテゴリID
	private String name; // カテゴリ名
	
	/**
	 * 引数なしコンストラクタ
	 */
	public Category() {}

	/**
	 * コンストラクタ
	 * @param id   カテゴリID
	 * @param name カテゴリ名
	 */
	public Category(String name) {
		this.name = name;
	}
	
	/**
	 * コンストラクタ
	 * @param id   カテゴリID
	 * @param name カテゴリ名
	 */
	public Category(int id, String name) {
		this(name);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * このオブジェクトの文字列表現を返す。
	 * <p>
	 * 主にデバッグやログ出力など、人間が読みやすい形式での利用を想定している。
	 * オブジェクトの内部状態をすべて網羅しているが、比較やハッシュ計算用でない。
	 * 比較用の文字列が必要な場合は {@link #toCompare()} を使用する。
	 *
	 * @return オブジェクトの人間向け文字列表現
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [");
		builder.append("id=" + id + ", ");
		builder.append("name=" + name);
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * このオブジェクトの比較用文字列表現を返しす。
	 * <p>
	 * 主キー以外の内部状態のすべてを反映しており、比較の精度が必要な場面で使用する。
	 * デバッグやログ出力のような「人間向け」の表示には {@link #toString()} を使用する。
	 *
	 * @return 比較用文字列表現
	 */
	public String toCompare() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [");
		builder.append("name=" + name);
		builder.append("]");
		return builder.toString();
	}
	
}
