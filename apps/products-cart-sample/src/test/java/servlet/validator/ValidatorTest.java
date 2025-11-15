package servlet.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidatorTest {
	
	@Nested
	@DisplayName("Validator#isNumeric(String)メソッドのテストクラス")
	class IsNumericTest {
		@Test
		@DisplayName("Test06: 空文字列「」はfalseである")
		void emptyString_returnFalse() {
			// setup
			String target = "";
			// execute & verify
			assertFalse(Validator.isPositiveInt(target));
		}
		@Test
		@DisplayName("Test05: nullはfalseである")
		void nullString_returFalse() {
			// setup
			String target = null;
			// execute & verify
			assertFalse(Validator.isPositiveInt(target));
		}
		@Test
		@DisplayName("Test04: 数字以外の文字列はfalseである")
		void nonNumberString_returnFalse() {
			// setup
			String target = "記憶装置";
			// execcute & verify
			assertFalse(Validator.isPositiveInt(target));
		}
		@Test
		@DisplayName("Test03: 実数はfalseである")
		void realNumberString_returnFalse() {
			// setup
			String target = "3.14";
			// execute & verify
			assertFalse(Validator.isPositiveInt(target));
		}
		@Test
		@DisplayName("Test02: 負の整数はfalseである")
		void negativeInntegerString_returnFalse() {
			// setup
			String target = "-274";
			// execute & verify
			assertFalse(Validator.isPositiveInt(target));
		}
		@Test
		@DisplayName("Test01: 正の整数はtrueである")
		void positiveIntegerString_returnTrue() {
			// setup
			String target = "1024";
			// execute & verify
			assertTrue(Validator.isPositiveInt(target));
		}
	}
	
	@Nested
	@DisplayName("Validator#isRequired(String)メソッドのテストクラス")
	class IsRrequiredTest {
		@Test
		@DisplayName("Test08: 空文字列「」はfalseである")
		void emptyString_returnFalse() {
			// setup
			String target = "";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test07: nullはfalseである")
		void nullString_returnFalse() {
			// setup
			String target = null;
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test06: 空白文字はfalseである")
		void halfWidthSpace_returnFalse() {
			// setup
			String target = " ";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test05: 全角空白文字はfalseである")
		void fullWidthSpace_returnFalse() {
			// setup
			String target = "　";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test04: タブ文字はfalseである")
		void tab_returnFalse() {
			// setup
			String target = "\t";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test03: 改行文字列はfalseである")
		void newline_returnFalse() {
			// setup
			String target = "\n";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test02: 改行文字とタブ文字はfalseである")
		void newlineAndTab_returnFalse() {
			// setup
			String target = "\n\t";
			// execute & verify
			assertFalse(Validator.isRequired(target));
		}
		@Test
		@DisplayName("Test01: 文字列はtrueである")
		void validString_returnTrue() {
			// setup
			String target = "junit";
			// execute & verify
			assertTrue(Validator.isRequired(target));
		}
	}
	
}
