package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Product;
import dao.common.BaseDAO;
import dao.common.DAOException;

/**
 * productsテーブルにアクセスするDAOクラス
 */
public class ProductDAO extends BaseDAO {

	/**
	 * クラス定数：SQL文字列定数群
	 */
	private static final String SQL_FIND_ALL = "SELECT * FROM products ORDER BY id";
	private static final String SQL_FIND_BY_CATEGORY_ID = "SELECT * FROM products WHERE category_id = ? ORDER By id";
	private static final String SQL_FIND_ALL_ORDER_BY_PRICE_ASC = "SELECT * FROM products ORDER BY price ASC";
	private static final String SQL_FIND_ALL_ORDER_BY_PRICE_DESC = "SELECT * FROM products ORDER BY price DESC";
	private static final String SQL_FIND_NAME_LIKE_KEYWORD = "SELECT * FROM products WHERE name LIKE ? ORDER BY id";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_ASC = "SELECT * FROM products WHERE name LIKE ? ORDER BY price ASC";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_DESC = "SELECT * FROM products WHERE name LIKE ? ORDER BY price DESC";
	private static final String SQL_FIND_BY_PRICE_LESS_THAN_EQUAL = "SELECT * FROM products WHERE price <= ? ORDER BY price DESC";
	private static final String SQL_FIND_BY_PRICE_LESS_THAN_EQUAL_ORDER_BY_PRICE_ASC = "SELECT * FROM products WHERE price <= ? ORDER BY price ASC";
	private static final String SQL_FIND_BY_PRICE_LESS_THAN_EQUAL_ORDER_BY_PRICE_DESC = "SELECT * FROM products WHERE price <= ? ORDER BY price DESC";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_AND_PRICE_LESS_THAN_EQUAL_ASC = "SELECT * FROM products WHERE name LIKE ? AND price <= ? ORDER BY price ASC";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_AND_PRICE_LESS_THAN_EQUAL_DESC = "SELECT * FROM products WHERE name LIKE ? AND price <= ? ORDER BY price DESC";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM products WHERE id = ?";
	
	private static final String SQL_INSERT_PRODUCT = "INSERT INTO products (category_id, name, price, quantity) VALUES (?, ?, ?, ?)";
	
	
	/**
	 * 引数なしコンストラクタ
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	public ProductDAO() throws DAOException {
		super();
	}
	
	/**
	 * productsテーブルに商品を登録する
	 * @param product 登録対象の商品インスタンス（商品IDは初期値「0」）
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public void save(Product product) throws DAOException {
		
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_INSERT_PRODUCT)) {
			// パラメタバインディング
			pstmt.setInt(1, product.getCategoryId());
			pstmt.setString(2, product.getName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getQuantity());
			
			// SQLの実行
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}


	/**
	 * productsテーブルからすべての商品を取得する
	 * @return 商品IDの昇順に並べ替えられた商品リスト
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public List<Product> findAll() throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL);
			 ResultSet rs = pstmt.executeQuery();) {
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				int categoryId =  rs.getInt("category_id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				int quantity = rs.getInt("quantity");
				list.add(new Product(id, categoryId, name, price, quantity));
			}
			return list;
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 指定されたカテゴリIDに合致する商品をproductsテーブルから取得する
	 * @param categoryId 検索するカテゴリID
	 * @return 商品IDで昇順に並べ替えられた商品リスト
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public List<Product> findByCategoryId(int categoryId) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_CATEGORY_ID);) {
			// パラメータバインディング
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 価格の並び替え順を指定してすべての商品をproductsテーブルから取得する。
	 * @param sortOrder 並び順（"asc" または "desc"）、nullまたは"asc"の場合は昇順
	 * @return 価格順に並べ替えられた商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findAllOrderByPrice(String sortOrder) throws DAOException {
		// 引数によって実行するSQLを分岐
		String sql = this.createSqlOrderByPrice(sortOrder);
		// SQLの実行と結果セットの取得
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);
			 ResultSet rs= pstmt.executeQuery();) {
			List<Product> list = this.convertToProductList(rs);
			return list;
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * キーワードを含んだ商品名の商品を取得する
	 * @param keyword キーワード
	 * @return キーワードを含んだ商品名の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeyword(String keyword) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_NAME_LIKE_KEYWORD);) {
			// パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * キーワードを含んだ商品名の商品を指定した価格の並び順で取得する
	 * @param sortOrder 並び替え順序
	 * @param keyword キーワード
	 * @return 指定した価格の並び順でキーワードを含んだ商品名の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeywordOrderByPrice(String sortOrder, String keyword) throws DAOException {
		// 引数によって実行するSQLを分岐
		String sql = this.createSqlByKeywordOrderByPrice(sortOrder);
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			//パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * 指定された価格の上限以下の商品を取得する
	 * @param maxPrice 価格の上限
	 * @return 指定した価格以下の価格の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByPriceLessThanEqual(int maxPrice) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_PRICE_LESS_THAN_EQUAL);) {
			// パラメータバインディング
			pstmt.setInt(1, maxPrice);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 指定された価格の上限以下の商品をキーワード検索する
	 * @param keyword  キーワード
	 * @param maxPrice 価格の上限
	 * @return キーワードを含む商品名の指定した価格以下の価格の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeywordAndPriceLessThanEqual(String keyword, int maxPrice) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_NAME_LIKE_KEYWORD_AND_PRICE_LESS_THAN_EQUAL_DESC);) {
			// パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, maxPrice);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 価格上限値以下の商品を指定した価格の順序で並べ替えて取得する
	 * @param sortOrder 並べ替え順序
	 * @param maxPrice  価格上限値
	 * @return 商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByPriceLessThanEqualOrderByPrice(String sortOrder, int maxPrice) throws DAOException {
		String sql = SQL_FIND_BY_PRICE_LESS_THAN_EQUAL_ORDER_BY_PRICE_ASC;
		if (sortOrder.equals("desc")) {
			sql = SQL_FIND_BY_PRICE_LESS_THAN_EQUAL_ORDER_BY_PRICE_DESC;
		}
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			// パラメータバインディング
			pstmt.setInt(1, maxPrice);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * 指定された並べ替え順序でキーワードと価格上限の複合検索する 
	 * @param sortOrder 並べ替え順序
	 * @param keyword   キーワード
	 * @param maxPrice  価格上限値
	 * @return 複合検索した結果の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeywordAndPriceLessThanEqualOrderByPrice(String sortOrder, String keyword, int maxPrice) throws DAOException {
		String sql = SQL_FIND_BY_NAME_LIKE_KEYWORD_AND_PRICE_LESS_THAN_EQUAL_ASC;
		if (sortOrder.equals("desc")) {
			sql = SQL_FIND_BY_NAME_LIKE_KEYWORD_AND_PRICE_LESS_THAN_EQUAL_DESC;
		}
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			// パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, maxPrice);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 商品IDに一致する商品を取得する
	 * @param id 取得対象商品の商品ID
	 * @return 商品IDに一致する商品がある場合は商品インスタンス、それ以外の場合はnull
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public Product findById(int id) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_ID)) {
			// パラメータバインディング
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery();) {
				Product product = null;
				if (rs.next()) {
					product = this.convertToProduct(rs);
				}
				return product;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 指定された価格の並び替え順ですべての商品をproductsテーブルから取得するSQLを取得する
	 * @param orderBy 昇順の場合またはnullの場合は「asc」、それ以外は「desc」
	 * @return 価格で並べ替えた商品リストを取得するSQL
	 */
	private String createSqlOrderByPrice(String orderBy) {
		if (orderBy == null || orderBy.equals("asc")) {
			return SQL_FIND_ALL_ORDER_BY_PRICE_ASC;
		}
		return SQL_FIND_ALL_ORDER_BY_PRICE_DESC;
	}
	
	/**
	 * 指定された価格の並び替え順ですべての商品をproductsテーブルからキーワード検索するSQLを取得する
	 * @param orderBy 昇順の場合またはnullの場合は「asc」、それ以外は「desc」
	 * @return 価格で並べ替えた商品リストを取得するSQL
	 */
	private String createSqlByKeywordOrderByPrice(String sortOrder) {
		if (sortOrder == null || sortOrder.equals("asc")) {
			return SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_ASC;
		}
		return SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_DESC;
	}

	/**
	 * 結果セットから商品リストに変換する
	 * @param rs 結果セット
	 * @return 商品リスト
	 * @throws SQLException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	private List<Product> convertToProductList(ResultSet rs) throws SQLException {
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			list.add(this.convertToProduct(rs));
		}
		return list;
	}
	
	/**
	 * 結果セットの現在行から商品インスタンスに変換する
	 * @param rs 結果セットの現在行
	 * @return 商品インスタンス
	 * @throws SQLException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	private Product convertToProduct(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		int categoryId =  rs.getInt("category_id");
		String name = rs.getString("name");
		int price = rs.getInt("price");
		int quantity = rs.getInt("quantity");
		return new Product(id, categoryId, name, price, quantity);
	}

}
