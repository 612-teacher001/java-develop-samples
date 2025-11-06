package dao.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.configure.DbConfigure;

/**
 * すべてのDAOクラスが継承する基底DAOクラス
 */
public class BaseDAO implements AutoCloseable{
	
	/**
	 * フィールド：データベース接続オブジェクト
	 */
	protected Connection conn;
	
	/**
	 * 引数なしコンストラクタ
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	public BaseDAO() throws DAOException {
		this.getConnection();
	}

	/**
	 * データベースに接続する：データベース接続オブジェクトを設定する
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	private void getConnection() throws DAOException {
		try {
			Class.forName("org.postgresql.Driver");
			DbConfigure configure = new DbConfigure();
			this.conn = DriverManager.getConnection(configure.getUrl(), configure.getUser(), configure.getPassword());
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("データベース接続に失敗しました。", e);
		} catch (ClassNotFoundException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("ドライバの読み込みに失敗しました。", e);
		}
	}
	
	/**
	 * データベースに接続する：データベース接続オブジェクトを設定する
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	@Override
	public void close() throws DAOException {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("データベース接続の切断に失敗しました。", e);
		}
	}
}
