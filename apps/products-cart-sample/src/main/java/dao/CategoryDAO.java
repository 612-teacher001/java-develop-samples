package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Category;
import dao.common.BaseDAO;
import dao.common.DAOException;

/**
 * categoriesテーブルにアクセスするDAOクラス
 */
public class CategoryDAO extends BaseDAO {

	private static final  String SQL_FIND_ALL = "SELECT * FROM categories ORDER BY id";
	
	
	/**
	 * 引数なしコンストラクタ
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	public CategoryDAO() throws DAOException {
		super();
	}

	/**
	 * categoriesテーブルからすべてのカテゴリを取得する
	 * @return カテゴリIDの昇順に並べ替えられたカテゴリリスト
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public List<Category> findAll() throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL);
			 ResultSet rs = pstmt.executeQuery();) {
			List<Category> list = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				list.add(new Category(id, name));
			}
			return list;
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

}
