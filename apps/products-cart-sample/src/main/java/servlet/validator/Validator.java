package servlet.validator;

import java.util.List;
import java.util.Objects;

/**
 * 入力値チェックユーティリティクラス
 */
public class Validator {

	/**
	 * 指定された文字列が「必須入力」として有効かどうかを判定する
	 * <p>
	 * 判定のルール:
	 * <ul>
	 *   <li>null の場合は false</li>
	 *   <li 空文字（""）または空白文字のみの文字列（半角スペース、全角スペース、タブ、改行など）は false</li>
	 *   <li 上記以外の文字列は true</li>
	 * </ul>
	 *
	 * @param  target 判定対象の文字列
	 * @return 必須入力として有効な場合は true、そうでない場合は false
	 *
	 * @implNote このメソッドは Java 11 以降の {@link String#isBlank()} を使用して判定しています。
	 */	
	public static boolean isRequired(String target) {
		return target != null && !target.isBlank();
	}

	/**
	 * 指定された文字列が「正の整数」として有効かどうかを判定する
	 * <p>
	 * 判定のルール:
	 * <ul>
	 *   <li>null または空文字列は false</li>
	 *   <li>負の整数（例: "-123"）や実数（例: "3.14"）は false</li>
	 *   <li>数字以外の文字列（例: "abc"、"記憶装置"）は false</li>
	 *   <li>0以上の整数（例: "0" および正の整数）は true</li>
	 * </ul>
	 *
	 * @param target 判定対象の文字列
	 * @return 正の整数として有効な場合は true、そうでない場合は false
	 *
	 * @implNote 内部では正規表現を使用して数値文字列かどうかを判定しています。
	 */	
	public static boolean isPositiveInt(String target) {
		return target != null && target.matches("\\d+");
	}
	
	/**
	 * 必須入力チェックを実施しエラーがあればエラーメッセージを生成する
	 * @param name  チェック項目名
	 * @param value チェック項目入力値
	 * @param messageList エラーがある場合はエラーメッセージが追加されるエラーリスト
	 */
	public static void isRequired(String name, String value, List<String> messageList) {
		// 前処理的チェック
		Objects.requireNonNull(messageList, "messageList must not be null");
		if (!isRequired(value)) {
			messageList.add(name + "は必須です。");
		}
	}
	
	/**
	 * 数値入力チェックを実施しエラーがあればエラーメッセージを生成する
	 * @param name  チェック項目名
	 * @param value チェック項目入力値
	 * @param messageList エラーがある場合はエラーメッセージが追加されるエラーリスト
	 */
	public static void isPositiveInt(String name, String value, List<String> messageList) {
		// 前処理的チェック
		Objects.requireNonNull(messageList, "messageList must not be null");
		if (!isPositiveInt(value)) {
			messageList.add(name + "は正の整数で入力してください。");
		}
	}
	
	/**
	 * 必須入力チェックと数値入力チェックの複合チェックを実施しエラーがあればエラーメッセージを生成する
	 * @param name  チェック項目名
	 * @param value チェック項目入力値
	 * @param messageList エラーがある場合はエラーメッセージが追加されるエラーリスト
	 */
	public static void isRequiredAndPositiveInt(String name, String value, List<String> messageList) {
		// 前処理的チェック
		Objects.requireNonNull(messageList, "messageList must not be null");
		if (!isRequired(value)) {
			messageList.add(name + "は必須です。");
		} else if (!isPositiveInt(value)) {
			messageList.add(name + "は正の整数で入力してください。");
		}
	}
	
}
