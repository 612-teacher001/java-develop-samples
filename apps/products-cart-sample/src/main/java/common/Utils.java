package common;

/**
 * 共通で利用する便利なメソッドを公開するクラス
 */
public class Utils {
	
	/**
	 * nullまたは空文字列かどうかを判定する
	 * @param target 判定対象文字列
	 * @return nullまたは空文字の場合はtrue、それ以外はfalse
	 */
	public static Boolean isNullOrEmpty(String target) {
		return (target == null || target.isEmpty());
	}
	
}
