package service;

import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import bean.Category;
import dao.CategoryDAO;
import dao.common.DAOException;

/**
 * 商品カテゴリに関する業務を実行するサービスクラス
 */
public class CategoryService extends BaseService {

	/**
	 * クラス定数
	 */
	// スコープ登録キー定数群
	private static final String KEY_CATEGORIES     = "appCategories";
	
	/**
	 * 商品カテゴリリストをアプリケーションスコープに登録する
	 * @param servletContext 
	 * @throws ServletException 
	 */
	public void initializeCategoriesIfAbsent(ServletContext servletContext) throws ServletException {
		
		// アプリケーションスコープからカテゴリリストを取得
		@SuppressWarnings("unchecked")
		List<Category> categoryList = (List<Category>) servletContext.getAttribute(KEY_CATEGORIES);
		if (categoryList != null) {
			// すでにカテゴリリストが存在する場合は初期化不要
			return;
		}
		
		try (CategoryDAO dao = new CategoryDAO();) {
			categoryList = dao.findAll();
			// アプリケーションスコープに登録
			servletContext.setAttribute(KEY_CATEGORIES, categoryList);
		} catch (DAOException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
		
	}

	
	
}
