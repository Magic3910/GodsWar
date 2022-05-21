package godswar.godswar.utils.base.language.korean;

import godswar.godswar.utils.base.language.korean.detectors.EnglishCapitalDetector;
import godswar.godswar.utils.base.language.korean.detectors.EnglishDetector;
import godswar.godswar.utils.base.language.korean.detectors.EnglishNumberKorStyleDetector;
import godswar.godswar.utils.base.language.korean.detectors.HangulDetector;
import godswar.godswar.utils.base.language.korean.detectors.NumberDetector;

/**
 * 조사 처리 라이브러리
 *
 * @author Developed by Bae Yong Ju, and modified by Daybreak 새벽
 */
public class KoreanUtil {

	private static final JongSungDetector[] jongSungDetectors = {
			HangulDetector.instance,
			EnglishCapitalDetector.instance,
			EnglishDetector.instance,
			EnglishNumberKorStyleDetector.instance,
			NumberDetector.instance
	};

	public static String addJosa(String text, Josa josa) {
		return text + getJosa(text, josa);
	}

	public static String getJosa(String text, Josa josa) {
		if (text != null && text.length() > 0) {
			final String readText = getReadText(text);
			for (JongSungDetector jongSungDetector : jongSungDetectors)
				if (jongSungDetector.canHandle(readText))
					return (jongSungDetector.getJongSungType(readText) > 0 ? josa.withJongsung : josa.withoutJongsung);
		}
		return josa.unknown;
	}

	public static JosaType getNeededJosa(String text) {
		if (text != null && text.length() > 0) {
			final String readText = getReadText(text);
			for (JongSungDetector jongSungDetector : jongSungDetectors)
				if (jongSungDetector.canHandle(readText))
					return (jongSungDetector.getJongSungType(readText) > 0 ? JosaType.WITH_JONGSUNG : JosaType.WITHOUT_JONGSUNG);
		}
		return JosaType.UNKNOWN;
	}

	public static String getJosa(Josa josa, JosaType josaType) {
		switch (josaType) {
			case WITH_JONGSUNG: return josa.withJongsung;
			case WITHOUT_JONGSUNG: return josa.withoutJongsung;
			default: return josa.unknown;
		}
	}

	public enum JosaType { WITH_JONGSUNG, WITHOUT_JONGSUNG, UNKNOWN }

	private static String getReadText(String str) {
		for (Josa readingRule : Josa.values()) {
			str = str.replace(readingRule.withJongsung, readingRule.withoutJongsung);
		}

		int i;
		for (i = str.length() - 1; i >= 0; --i) {
			char ch = str.charAt(i);

			if ("\"')]}>".indexOf(ch) < 0) {
				break;
			}
		}

		return str.substring(0, i + 1);
	}

	public enum Josa {

		은는("은", "는", "은(는)"),
		이가("이", "가", "(이)가"),
		을를("을", "를", "을(를)"),
		과와("과", "와", "과(와)"),
		으로로("으로", "로", "(으)로"),
		이었였("이었", "였", "이었(였)");

		private final String withJongsung;
		private final String withoutJongsung;
		private final String unknown;

		Josa(String withJongsung, String withoutJongsung, String unknown) {
			this.withJongsung = withJongsung;
			this.withoutJongsung = withoutJongsung;
			this.unknown = unknown;
		}

	}

}
