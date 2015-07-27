package org.succlz123.AxBTube.support.helper.acfun;

/**
 * Created by chinausky on 2015/7/27.
 */
public class AcString {
	public static final String AC_PARTITION_URL = "partitionUrl";
	public static final String AC_CHANNEL_IDS = "channelIds";

	public static final String HOT = "热门焦点";
	public static final String ANIMATION = "动画";
	public static final String ENTERTAINMENT = "娱乐";
	public static final String MUSIC = "音乐";
	public static final String GAME = "游戏";
	public static final String SCIENCE = "科学";
	public static final String SPORT = "体育";
	public static final String TV = "影视";

	public static String getTitle(int position) {
		switch (position) {
			case 1:
				return HOT;
			case 6:
				return ANIMATION;
			case 9:
				return ENTERTAINMENT;
			case 12:
				return MUSIC;
			case 15:
				return GAME;
			case 18:
				return SCIENCE;
			case 21:
				return SPORT;
			case 24:
				return TV;
		}
		return null;
	}
}
