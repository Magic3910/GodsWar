package godswar.godswar.utils.base.language.korean.detectors;

import godswar.godswar.utils.base.language.CharUtil;
import godswar.godswar.utils.base.language.korean.JongSungDetector;

public class HangulDetector implements JongSungDetector {

	public static JongSungDetector instance = new HangulDetector();

	private HangulDetector() {
	}

	@Override
	public boolean canHandle(String str) {
		return CharUtil.isHangulSyllables(CharUtil.lastChar(str));
	}

	@Override
	public int getJongSungType(String str) {
		return CharUtil.getHangulJongSungType(CharUtil.lastChar(str));
	}

}
