package org.succlz123.bluetube.bean.acfun;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by fashi on 2015/7/26.
 */
public class AcReOther {

	public static AcReOther parseJson(String json) {
		Gson gson = new Gson();

		return gson.fromJson(json, AcReOther.class);
	}

	/**
	 * msg : 查询成功
	 * data : {"page":{"pageNo":1,"pageSize":20,"orderBy":4,"totalCount":0,"list":[{"stows":5,"isRecommend":0,"comments":7,"releaseDate":1437875360000,"isArticle":0,"contentId":2052438,"description":"这个可是我做了两个晚上哦！希望观众老爷喜欢","title":"【新番介绍】2015年10月番介绍 第一弹","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26092157v5rf.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201503/25154635sejy.jpg","userId":1224336,"username":"亚丝娜3"},"channelId":106,"views":2241},{"stows":16,"isRecommend":0,"comments":6,"releaseDate":1437817785000,"isArticle":0,"contentId":2051339,"description":"「献给我那温柔的王，集。」BGM：Everybody Wants To Rule The World.<br/>在画面配合歌词的意义上下了一些功夫，观看的时候记得打开字幕弹幕哦。虽然还不成熟，希望大家会喜欢喵。这是我第二次用这个BGM了，非常喜欢LORDE的这首歌，我打算以这首歌做一个系列的MAD。联动<br/>系列1 【鲁鲁修】ac1848732","title":"【罪恶王冠】大家都想征服世界，而我只想包容一切。","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/25172941y4q8.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201501/05121122p0o0.jpg","userId":1136514,"username":"枫叶大大"},"channelId":107,"views":2944},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437893206000,"isArticle":0,"contentId":2053035,"description":"自制 恭喜大圣六亿达成！请继续向七亿八亿迈进！\r<br/>BGM是秦时明月之万里长城预告的音乐，那个预告也是拉我入秦时坑的契机~\r<br/>不过只找到这一个音频资源所以音质可能不算特别好_(:зゝ∠)_\r<br/>想当初进驻微博就是因为秦时，也算是追了五六年了，感情还是很深的qwq\r<br/>素材略少，求多包涵_(:зゝ∠)_\r<br/>希望无论大圣还是秦时都能创造出新的辉煌！！！","title":"【燃/同步率100%】大圣归来X秦时明月（六亿贺）","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261446370m25.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261438064zk6.jpg","userId":1169366,"username":"工藤梦樱"},"channelId":120,"views":29},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437892018000,"isArticle":0,"contentId":2052976,"description":"<br/>本来想作为放映四周年纪念的，结果赶上封杀，然后又肝刀肝舰，就一直坑到现在，导致我连压制都不会了，下一部剧场版到底什么时候上映啦。。。。BGM：scars<br/>of love 原档下载：http://pan.baidu.com/s/1c08nKhM 封面p站id31335370，画师Azmodan","title":"【小圆新篇MAD】scars of love","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261411044b41.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201402/08164059m8kw.jpg","userId":280198,"username":"小偶先生"},"channelId":107,"views":4},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437891884000,"isArticle":0,"contentId":2052970,"description":"【OAD】咲日和 01 【生肉】","title":"【OAD】咲日和 01 【生肉】","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26142418fpen.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201506/06011106npc6.jpg","userId":164298,"username":"渣文老湿"},"channelId":67,"views":13},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437891601000,"isArticle":0,"contentId":2052959,"description":"sm26738274 不清楚撞了沒。。","title":"【MMD艦これ】加賀岬","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26125154nlgz.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/03130404t7gk.jpg","userId":147973,"username":"囧书上小鸟"},"channelId":108,"views":11},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437888231000,"isArticle":0,"contentId":2052851,"description":"女王绿毛你们都是我的翅膀！","title":"【剧场版】超时空要塞 Frontier(2009/2011)","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26132332vkzy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/23152021snga.jpg","userId":82389,"username":"黄金的狮龙"},"channelId":109,"views":2},{"stows":18,"isRecommend":0,"comments":6,"releaseDate":1437884158000,"isArticle":0,"contentId":2052750,"description":"youtube.com/watch?v=jUJ9PrS4z0M，这是什么奇葩帧数","title":"【MMD】精灵ミク三人 【ねぇ】紳士视角 48fps","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261214262cwj.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":467},{"stows":7,"isRecommend":0,"comments":2,"releaseDate":1437883455000,"isArticle":0,"contentId":2052724,"description":"youtube=dBAF0lxObF4","title":"【MMD】旗袍爱丽丝 辉夜 - 一騎当千","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042040cr7n.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":403},{"stows":0,"isRecommend":0,"comments":1,"releaseDate":1437883222000,"isArticle":0,"contentId":2052722,"description":"自制 实在是蛋疼了，看着你们又是画画又是N刷我浑身难受啊！只好这样了，谁让劈胃尸是食物链的最末端呢\u2026\u2026图片作者：Eclosion 视频合成：DLS_MWZZ。顺便搞点小情怀~此处应有BGM~","title":"[MAD]大圣归来\u2014\u2014佛神坛斗士","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26115902dqpd.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201308/24233155455c.jpg","userId":26658,"username":"DLS"},"channelId":107,"views":190},{"stows":9,"isRecommend":0,"comments":6,"releaseDate":1437882946000,"isArticle":0,"contentId":2052714,"description":"youtube=zGqEA83HU3g","title":"【MMD】 夢と葉桜","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042049n61p.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":287},{"stows":20,"isRecommend":0,"comments":6,"releaseDate":1437882474000,"isArticle":0,"contentId":2052699,"description":"2P 黑丝连裤袜版 youtube =AV7iwAd42AE 作者：whonaru1。","title":"【MMD】诱人黑丝Miku酱  - Get Up &amp; Move!!","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26034432gbsa.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":522},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437881785000,"isArticle":0,"contentId":2052677,"description":"sm25049175，正在打扫中才有鬼了","title":"【MMD】Jabberwocky Jabberwocka 神社打扫中","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26113727zykc.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":30},{"stows":5,"isRecommend":0,"comments":2,"releaseDate":1437881304000,"isArticle":0,"contentId":2052658,"description":"sm25376229,这曲有毒","title":"【MMD艦これ】Jabberwocky Jabberwocka -- 時雨夕立 驱逐舰CQC","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26112842if3t.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":167},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437880846000,"isArticle":0,"contentId":2052636,"description":"youtube.com/watch?v=rRa9nBLckAs","title":"【MMD】Jabberwocky Jabberwocka - Len &amp; Rin","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26111850per1.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":11},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879757000,"isArticle":0,"contentId":2052593,"description":"动画TV版 超人 \r<br/>第三季 第六集 \r<br/>Where There's Smoke \r<br/>火热的地方","title":"超人 动画 S03 E06 Where There s Smoke 火热的地方 无字幕","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102515bbsu.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/20163445et4g.jpg","userId":1267479,"username":"CEOStony"},"channelId":109,"views":18},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879669000,"isArticle":0,"contentId":2052589,"description":"Youtube,原标题Eruption.原作者：itsAvientAMV.Music：The Lonely Island\u2014\u2014 Jizz In<br/>My Pants","title":"【综漫/AMV】射在我的裤子里","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26110008cf50.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":27},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437878053000,"isArticle":0,"contentId":2052510,"description":"荣获动画界最高荣誉\u2014\u2014法国安锡动画影展首奖。童真如何面对成长的苦涩与世界的现实？宛如巴西版的囧男孩，一家务农生活穷苦却过得快乐。然而为求谋生，父亲只得离乡出外谋生。小男孩不忍母亲伤心，踏上寻父的大冒险旅程。沿途光怪陆离的人事，城乡风景，媒体，贪婪嘴脸的美国老板与压榨扁瘦得劳动者，美丽又悲伤的世界景象。","title":"男孩和世界-The.Boy.and.the.World.","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261032017wxm.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/25212854b1u3.jpg","userId":1282926,"username":"A爺"},"channelId":106,"views":4},{"stows":3,"isRecommend":0,"comments":0,"releaseDate":1437877911000,"isArticle":0,"contentId":2052505,"description":"回山之作.链接: http://pan.baidu.com/s/1hq7zJIs 密码: byxi 喜欢的投个焦谢谢了各位","title":"【绅士福利向】 请蹂躏我吧.我是M","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102855ax1a.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201411/08143819b41v.jpg","userId":1007431,"username":"腾讯国际ceo马化腾"},"channelId":106,"views":51},{"stows":2,"isRecommend":0,"comments":3,"releaseDate":1437876437000,"isArticle":0,"contentId":2052467,"description":"Youtube,NeuroAMV作品。MUSIC:&amp;quot;War of Change&amp;quot; by Thousand Foot Krutch","title":"【AMV/火影】卡卡西VS带土","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/2609564956hy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":1153}]}}
	 * success : true
	 * status : 200
	 */
	private String msg;
	private DataEntity data;
	private boolean success;
	private int status;

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setData(DataEntity data) {
		this.data = data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public DataEntity getData() {
		return data;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getStatus() {
		return status;
	}

	public class DataEntity {
		/**
		 * page : {"pageNo":1,"pageSize":20,"orderBy":4,"totalCount":0,"list":[{"stows":5,"isRecommend":0,"comments":7,"releaseDate":1437875360000,"isArticle":0,"contentId":2052438,"description":"这个可是我做了两个晚上哦！希望观众老爷喜欢","title":"【新番介绍】2015年10月番介绍 第一弹","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26092157v5rf.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201503/25154635sejy.jpg","userId":1224336,"username":"亚丝娜3"},"channelId":106,"views":2241},{"stows":16,"isRecommend":0,"comments":6,"releaseDate":1437817785000,"isArticle":0,"contentId":2051339,"description":"「献给我那温柔的王，集。」BGM：Everybody Wants To Rule The World.<br/>在画面配合歌词的意义上下了一些功夫，观看的时候记得打开字幕弹幕哦。虽然还不成熟，希望大家会喜欢喵。这是我第二次用这个BGM了，非常喜欢LORDE的这首歌，我打算以这首歌做一个系列的MAD。联动<br/>系列1 【鲁鲁修】ac1848732","title":"【罪恶王冠】大家都想征服世界，而我只想包容一切。","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/25172941y4q8.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201501/05121122p0o0.jpg","userId":1136514,"username":"枫叶大大"},"channelId":107,"views":2944},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437893206000,"isArticle":0,"contentId":2053035,"description":"自制 恭喜大圣六亿达成！请继续向七亿八亿迈进！\r<br/>BGM是秦时明月之万里长城预告的音乐，那个预告也是拉我入秦时坑的契机~\r<br/>不过只找到这一个音频资源所以音质可能不算特别好_(:зゝ∠)_\r<br/>想当初进驻微博就是因为秦时，也算是追了五六年了，感情还是很深的qwq\r<br/>素材略少，求多包涵_(:зゝ∠)_\r<br/>希望无论大圣还是秦时都能创造出新的辉煌！！！","title":"【燃/同步率100%】大圣归来X秦时明月（六亿贺）","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261446370m25.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261438064zk6.jpg","userId":1169366,"username":"工藤梦樱"},"channelId":120,"views":29},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437892018000,"isArticle":0,"contentId":2052976,"description":"<br/>本来想作为放映四周年纪念的，结果赶上封杀，然后又肝刀肝舰，就一直坑到现在，导致我连压制都不会了，下一部剧场版到底什么时候上映啦。。。。BGM：scars<br/>of love 原档下载：http://pan.baidu.com/s/1c08nKhM 封面p站id31335370，画师Azmodan","title":"【小圆新篇MAD】scars of love","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261411044b41.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201402/08164059m8kw.jpg","userId":280198,"username":"小偶先生"},"channelId":107,"views":4},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437891884000,"isArticle":0,"contentId":2052970,"description":"【OAD】咲日和 01 【生肉】","title":"【OAD】咲日和 01 【生肉】","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26142418fpen.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201506/06011106npc6.jpg","userId":164298,"username":"渣文老湿"},"channelId":67,"views":13},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437891601000,"isArticle":0,"contentId":2052959,"description":"sm26738274 不清楚撞了沒。。","title":"【MMD艦これ】加賀岬","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26125154nlgz.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/03130404t7gk.jpg","userId":147973,"username":"囧书上小鸟"},"channelId":108,"views":11},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437888231000,"isArticle":0,"contentId":2052851,"description":"女王绿毛你们都是我的翅膀！","title":"【剧场版】超时空要塞 Frontier(2009/2011)","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26132332vkzy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/23152021snga.jpg","userId":82389,"username":"黄金的狮龙"},"channelId":109,"views":2},{"stows":18,"isRecommend":0,"comments":6,"releaseDate":1437884158000,"isArticle":0,"contentId":2052750,"description":"youtube.com/watch?v=jUJ9PrS4z0M，这是什么奇葩帧数","title":"【MMD】精灵ミク三人 【ねぇ】紳士视角 48fps","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261214262cwj.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":467},{"stows":7,"isRecommend":0,"comments":2,"releaseDate":1437883455000,"isArticle":0,"contentId":2052724,"description":"youtube=dBAF0lxObF4","title":"【MMD】旗袍爱丽丝 辉夜 - 一騎当千","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042040cr7n.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":403},{"stows":0,"isRecommend":0,"comments":1,"releaseDate":1437883222000,"isArticle":0,"contentId":2052722,"description":"自制 实在是蛋疼了，看着你们又是画画又是N刷我浑身难受啊！只好这样了，谁让劈胃尸是食物链的最末端呢\u2026\u2026图片作者：Eclosion 视频合成：DLS_MWZZ。顺便搞点小情怀~此处应有BGM~","title":"[MAD]大圣归来\u2014\u2014佛神坛斗士","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26115902dqpd.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201308/24233155455c.jpg","userId":26658,"username":"DLS"},"channelId":107,"views":190},{"stows":9,"isRecommend":0,"comments":6,"releaseDate":1437882946000,"isArticle":0,"contentId":2052714,"description":"youtube=zGqEA83HU3g","title":"【MMD】 夢と葉桜","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042049n61p.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":287},{"stows":20,"isRecommend":0,"comments":6,"releaseDate":1437882474000,"isArticle":0,"contentId":2052699,"description":"2P 黑丝连裤袜版 youtube =AV7iwAd42AE 作者：whonaru1。","title":"【MMD】诱人黑丝Miku酱  - Get Up &amp; Move!!","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26034432gbsa.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":522},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437881785000,"isArticle":0,"contentId":2052677,"description":"sm25049175，正在打扫中才有鬼了","title":"【MMD】Jabberwocky Jabberwocka 神社打扫中","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26113727zykc.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":30},{"stows":5,"isRecommend":0,"comments":2,"releaseDate":1437881304000,"isArticle":0,"contentId":2052658,"description":"sm25376229,这曲有毒","title":"【MMD艦これ】Jabberwocky Jabberwocka -- 時雨夕立 驱逐舰CQC","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26112842if3t.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":167},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437880846000,"isArticle":0,"contentId":2052636,"description":"youtube.com/watch?v=rRa9nBLckAs","title":"【MMD】Jabberwocky Jabberwocka - Len &amp; Rin","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26111850per1.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":11},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879757000,"isArticle":0,"contentId":2052593,"description":"动画TV版 超人 \r<br/>第三季 第六集 \r<br/>Where There's Smoke \r<br/>火热的地方","title":"超人 动画 S03 E06 Where There s Smoke 火热的地方 无字幕","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102515bbsu.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/20163445et4g.jpg","userId":1267479,"username":"CEOStony"},"channelId":109,"views":18},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879669000,"isArticle":0,"contentId":2052589,"description":"Youtube,原标题Eruption.原作者：itsAvientAMV.Music：The Lonely Island\u2014\u2014 Jizz In<br/>My Pants","title":"【综漫/AMV】射在我的裤子里","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26110008cf50.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":27},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437878053000,"isArticle":0,"contentId":2052510,"description":"荣获动画界最高荣誉\u2014\u2014法国安锡动画影展首奖。童真如何面对成长的苦涩与世界的现实？宛如巴西版的囧男孩，一家务农生活穷苦却过得快乐。然而为求谋生，父亲只得离乡出外谋生。小男孩不忍母亲伤心，踏上寻父的大冒险旅程。沿途光怪陆离的人事，城乡风景，媒体，贪婪嘴脸的美国老板与压榨扁瘦得劳动者，美丽又悲伤的世界景象。","title":"男孩和世界-The.Boy.and.the.World.","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261032017wxm.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/25212854b1u3.jpg","userId":1282926,"username":"A爺"},"channelId":106,"views":4},{"stows":3,"isRecommend":0,"comments":0,"releaseDate":1437877911000,"isArticle":0,"contentId":2052505,"description":"回山之作.链接: http://pan.baidu.com/s/1hq7zJIs 密码: byxi 喜欢的投个焦谢谢了各位","title":"【绅士福利向】 请蹂躏我吧.我是M","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102855ax1a.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201411/08143819b41v.jpg","userId":1007431,"username":"腾讯国际ceo马化腾"},"channelId":106,"views":51},{"stows":2,"isRecommend":0,"comments":3,"releaseDate":1437876437000,"isArticle":0,"contentId":2052467,"description":"Youtube,NeuroAMV作品。MUSIC:&amp;quot;War of Change&amp;quot; by Thousand Foot Krutch","title":"【AMV/火影】卡卡西VS带土","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/2609564956hy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":1153}]}
		 */
		private PageEntity page;

		public void setPage(PageEntity page) {
			this.page = page;
		}

		public PageEntity getPage() {
			return page;
		}

		public class PageEntity {
			/**
			 * pageNo : 1
			 * pageSize : 20
			 * orderBy : 4
			 * totalCount : 0
			 * list : [{"stows":5,"isRecommend":0,"comments":7,"releaseDate":1437875360000,"isArticle":0,"contentId":2052438,"description":"这个可是我做了两个晚上哦！希望观众老爷喜欢","title":"【新番介绍】2015年10月番介绍 第一弹","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26092157v5rf.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201503/25154635sejy.jpg","userId":1224336,"username":"亚丝娜3"},"channelId":106,"views":2241},{"stows":16,"isRecommend":0,"comments":6,"releaseDate":1437817785000,"isArticle":0,"contentId":2051339,"description":"「献给我那温柔的王，集。」BGM：Everybody Wants To Rule The World.<br/>在画面配合歌词的意义上下了一些功夫，观看的时候记得打开字幕弹幕哦。虽然还不成熟，希望大家会喜欢喵。这是我第二次用这个BGM了，非常喜欢LORDE的这首歌，我打算以这首歌做一个系列的MAD。联动<br/>系列1 【鲁鲁修】ac1848732","title":"【罪恶王冠】大家都想征服世界，而我只想包容一切。","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/25172941y4q8.jpg","toplevel":7,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201501/05121122p0o0.jpg","userId":1136514,"username":"枫叶大大"},"channelId":107,"views":2944},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437893206000,"isArticle":0,"contentId":2053035,"description":"自制 恭喜大圣六亿达成！请继续向七亿八亿迈进！\r<br/>BGM是秦时明月之万里长城预告的音乐，那个预告也是拉我入秦时坑的契机~\r<br/>不过只找到这一个音频资源所以音质可能不算特别好_(:зゝ∠)_\r<br/>想当初进驻微博就是因为秦时，也算是追了五六年了，感情还是很深的qwq\r<br/>素材略少，求多包涵_(:зゝ∠)_\r<br/>希望无论大圣还是秦时都能创造出新的辉煌！！！","title":"【燃/同步率100%】大圣归来X秦时明月（六亿贺）","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261446370m25.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261438064zk6.jpg","userId":1169366,"username":"工藤梦樱"},"channelId":120,"views":29},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437892018000,"isArticle":0,"contentId":2052976,"description":"<br/>本来想作为放映四周年纪念的，结果赶上封杀，然后又肝刀肝舰，就一直坑到现在，导致我连压制都不会了，下一部剧场版到底什么时候上映啦。。。。BGM：scars<br/>of love 原档下载：http://pan.baidu.com/s/1c08nKhM 封面p站id31335370，画师Azmodan","title":"【小圆新篇MAD】scars of love","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261411044b41.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201402/08164059m8kw.jpg","userId":280198,"username":"小偶先生"},"channelId":107,"views":4},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437891884000,"isArticle":0,"contentId":2052970,"description":"【OAD】咲日和 01 【生肉】","title":"【OAD】咲日和 01 【生肉】","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26142418fpen.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201506/06011106npc6.jpg","userId":164298,"username":"渣文老湿"},"channelId":67,"views":13},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437891601000,"isArticle":0,"contentId":2052959,"description":"sm26738274 不清楚撞了沒。。","title":"【MMD艦これ】加賀岬","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26125154nlgz.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/03130404t7gk.jpg","userId":147973,"username":"囧书上小鸟"},"channelId":108,"views":11},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437888231000,"isArticle":0,"contentId":2052851,"description":"女王绿毛你们都是我的翅膀！","title":"【剧场版】超时空要塞 Frontier(2009/2011)","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26132332vkzy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/23152021snga.jpg","userId":82389,"username":"黄金的狮龙"},"channelId":109,"views":2},{"stows":18,"isRecommend":0,"comments":6,"releaseDate":1437884158000,"isArticle":0,"contentId":2052750,"description":"youtube.com/watch?v=jUJ9PrS4z0M，这是什么奇葩帧数","title":"【MMD】精灵ミク三人 【ねぇ】紳士视角 48fps","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261214262cwj.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":467},{"stows":7,"isRecommend":0,"comments":2,"releaseDate":1437883455000,"isArticle":0,"contentId":2052724,"description":"youtube=dBAF0lxObF4","title":"【MMD】旗袍爱丽丝 辉夜 - 一騎当千","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042040cr7n.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":403},{"stows":0,"isRecommend":0,"comments":1,"releaseDate":1437883222000,"isArticle":0,"contentId":2052722,"description":"自制 实在是蛋疼了，看着你们又是画画又是N刷我浑身难受啊！只好这样了，谁让劈胃尸是食物链的最末端呢\u2026\u2026图片作者：Eclosion 视频合成：DLS_MWZZ。顺便搞点小情怀~此处应有BGM~","title":"[MAD]大圣归来\u2014\u2014佛神坛斗士","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26115902dqpd.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201308/24233155455c.jpg","userId":26658,"username":"DLS"},"channelId":107,"views":190},{"stows":9,"isRecommend":0,"comments":6,"releaseDate":1437882946000,"isArticle":0,"contentId":2052714,"description":"youtube=zGqEA83HU3g","title":"【MMD】 夢と葉桜","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26042049n61p.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":287},{"stows":20,"isRecommend":0,"comments":6,"releaseDate":1437882474000,"isArticle":0,"contentId":2052699,"description":"2P 黑丝连裤袜版 youtube =AV7iwAd42AE 作者：whonaru1。","title":"【MMD】诱人黑丝Miku酱  - Get Up &amp; Move!!","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26034432gbsa.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201505/10223423gu0i.jpg","userId":905818,"username":"进击的壹加壹"},"channelId":108,"views":522},{"stows":1,"isRecommend":0,"comments":0,"releaseDate":1437881785000,"isArticle":0,"contentId":2052677,"description":"sm25049175，正在打扫中才有鬼了","title":"【MMD】Jabberwocky Jabberwocka 神社打扫中","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26113727zykc.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":30},{"stows":5,"isRecommend":0,"comments":2,"releaseDate":1437881304000,"isArticle":0,"contentId":2052658,"description":"sm25376229,这曲有毒","title":"【MMD艦これ】Jabberwocky Jabberwocka -- 時雨夕立 驱逐舰CQC","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26112842if3t.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":167},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437880846000,"isArticle":0,"contentId":2052636,"description":"youtube.com/watch?v=rRa9nBLckAs","title":"【MMD】Jabberwocky Jabberwocka - Len &amp; Rin","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26111850per1.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201406/19192801bj20.jpg","userId":603259,"username":"批发初音葱籽"},"channelId":108,"views":11},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879757000,"isArticle":0,"contentId":2052593,"description":"动画TV版 超人 \r<br/>第三季 第六集 \r<br/>Where There's Smoke \r<br/>火热的地方","title":"超人 动画 S03 E06 Where There s Smoke 火热的地方 无字幕","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102515bbsu.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/20163445et4g.jpg","userId":1267479,"username":"CEOStony"},"channelId":109,"views":18},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437879669000,"isArticle":0,"contentId":2052589,"description":"Youtube,原标题Eruption.原作者：itsAvientAMV.Music：The Lonely Island\u2014\u2014 Jizz In<br/>My Pants","title":"【综漫/AMV】射在我的裤子里","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26110008cf50.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":27},{"stows":0,"isRecommend":0,"comments":0,"releaseDate":1437878053000,"isArticle":0,"contentId":2052510,"description":"荣获动画界最高荣誉\u2014\u2014法国安锡动画影展首奖。童真如何面对成长的苦涩与世界的现实？宛如巴西版的囧男孩，一家务农生活穷苦却过得快乐。然而为求谋生，父亲只得离乡出外谋生。小男孩不忍母亲伤心，踏上寻父的大冒险旅程。沿途光怪陆离的人事，城乡风景，媒体，贪婪嘴脸的美国老板与压榨扁瘦得劳动者，美丽又悲伤的世界景象。","title":"男孩和世界-The.Boy.and.the.World.","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/261032017wxm.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201504/25212854b1u3.jpg","userId":1282926,"username":"A爺"},"channelId":106,"views":4},{"stows":3,"isRecommend":0,"comments":0,"releaseDate":1437877911000,"isArticle":0,"contentId":2052505,"description":"回山之作.链接: http://pan.baidu.com/s/1hq7zJIs 密码: byxi 喜欢的投个焦谢谢了各位","title":"【绅士福利向】 请蹂躏我吧.我是M","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26102855ax1a.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201411/08143819b41v.jpg","userId":1007431,"username":"腾讯国际ceo马化腾"},"channelId":106,"views":51},{"stows":2,"isRecommend":0,"comments":3,"releaseDate":1437876437000,"isArticle":0,"contentId":2052467,"description":"Youtube,NeuroAMV作品。MUSIC:&amp;quot;War of Change&amp;quot; by Thousand Foot Krutch","title":"【AMV/火影】卡卡西VS带土","tags":[],"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/2609564956hy.jpg","toplevel":0,"viewOnly":0,"user":{"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201211/251349558ip6.jpg","userId":334410,"username":"Mandopop"},"channelId":107,"views":1153}]
			 */
			private int pageNo;
			private int pageSize;
			private int orderBy;
			private int totalCount;
			private List<ListEntity> list;

			public void setPageNo(int pageNo) {
				this.pageNo = pageNo;
			}

			public void setPageSize(int pageSize) {
				this.pageSize = pageSize;
			}

			public void setOrderBy(int orderBy) {
				this.orderBy = orderBy;
			}

			public void setTotalCount(int totalCount) {
				this.totalCount = totalCount;
			}

			public void setList(List<ListEntity> list) {
				this.list = list;
			}

			public int getPageNo() {
				return pageNo;
			}

			public int getPageSize() {
				return pageSize;
			}

			public int getOrderBy() {
				return orderBy;
			}

			public int getTotalCount() {
				return totalCount;
			}

			public List<ListEntity> getList() {
				return list;
			}

			public class ListEntity {
				/**
				 * stows : 5
				 * isRecommend : 0
				 * comments : 7
				 * releaseDate : 1437875360000
				 * isArticle : 0
				 * contentId : 2052438
				 * description : 这个可是我做了两个晚上哦！希望观众老爷喜欢
				 * title : 【新番介绍】2015年10月番介绍 第一弹
				 * tags : []
				 * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/26092157v5rf.jpg
				 * toplevel : 7
				 * viewOnly : 0
				 * user : {"userImg":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201503/25154635sejy.jpg","userId":1224336,"username":"亚丝娜3"}
				 * channelId : 106
				 * views : 2241
				 */
 				private int stows;
				private int isRecommend;
				private int comments;
				private long releaseDate;
				private int isArticle;
				private String contentId;
				private String description;
				private String title;
				private List<?> tags;
				private String cover;
				private int toplevel;
				private int viewOnly;
				private UserEntity user;
				private int channelId;
				private int views;

				public void setStows(int stows) {
					this.stows = stows;
				}

				public void setIsRecommend(int isRecommend) {
					this.isRecommend = isRecommend;
				}

				public void setComments(int comments) {
					this.comments = comments;
				}

				public void setReleaseDate(long releaseDate) {
					this.releaseDate = releaseDate;
				}

				public void setIsArticle(int isArticle) {
					this.isArticle = isArticle;
				}

				public void setContentId(String contentId) {
					this.contentId = contentId;
				}

				public void setDescription(String description) {
					this.description = description;
				}

				public void setTitle(String title) {
					this.title = title;
				}

				public void setTags(List<?> tags) {
					this.tags = tags;
				}

				public void setCover(String cover) {
					this.cover = cover;
				}

				public void setToplevel(int toplevel) {
					this.toplevel = toplevel;
				}

				public void setViewOnly(int viewOnly) {
					this.viewOnly = viewOnly;
				}

				public void setUser(UserEntity user) {
					this.user = user;
				}

				public void setChannelId(int channelId) {
					this.channelId = channelId;
				}

				public void setViews(int views) {
					this.views = views;
				}

				public int getStows() {
					return stows;
				}

				public int getIsRecommend() {
					return isRecommend;
				}

				public int getComments() {
					return comments;
				}

				public long getReleaseDate() {
					return releaseDate;
				}

				public int getIsArticle() {
					return isArticle;
				}

				public String getContentId() {
					return contentId;
				}

				public String getDescription() {
					return description;
				}

				public String getTitle() {
					return title;
				}

				public List<?> getTags() {
					return tags;
				}

				public String getCover() {
					return cover;
				}

				public int getToplevel() {
					return toplevel;
				}

				public int getViewOnly() {
					return viewOnly;
				}

				public UserEntity getUser() {
					return user;
				}

				public int getChannelId() {
					return channelId;
				}

				public int getViews() {
					return views;
				}

				public class UserEntity {
					/**
					 * userImg : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201503/25154635sejy.jpg
					 * userId : 1224336
					 * username : 亚丝娜3
					 */
					private String userImg;
					private int userId;
					private String username;

					public void setUserImg(String userImg) {
						this.userImg = userImg;
					}

					public void setUserId(int userId) {
						this.userId = userId;
					}

					public void setUsername(String username) {
						this.username = username;
					}

					public String getUserImg() {
						return userImg;
					}

					public int getUserId() {
						return userId;
					}

					public String getUsername() {
						return username;
					}
				}
			}
		}
	}
}
