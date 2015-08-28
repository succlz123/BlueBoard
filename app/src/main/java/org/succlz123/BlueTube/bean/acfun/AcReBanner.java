package org.succlz123.BlueTube.bean.acfun;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by fashi on 2015/7/19.
 */
public class AcReBanner {

	public static AcReBanner parseJson(String json) {
		Gson gson = new Gson();
		
		return gson.fromJson(json, AcReBanner.class);
	}

	/**
	 * msg : ok
	 * data : {"list":[{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180821llun.jpg","slideId":20628,"releaseDate":1437288522000,"subtitle":"","weekday":null,"description":"","time":null,"title":"北美票房周榜TOP30 第28周 ","type":0,"config":6,"url":null,"specialId":"2035009"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/181344178nqd.jpg","slideId":20627,"releaseDate":1437288516000,"subtitle":"","weekday":null,"description":"","time":null,"title":"终极电音榜【第二十九周】","type":0,"config":6,"url":null,"specialId":"2033193"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/17101240711t.jpg","slideId":20585,"releaseDate":1437099606000,"subtitle":"知名条子到访，大事件全程正能量！","weekday":null,"description":"","time":null,"title":"【暴走大事件第四季】第二期","type":0,"config":6,"url":null,"specialId":"2031830"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/16162325aknl.jpg","slideId":20572,"releaseDate":1437044558000,"subtitle":"","weekday":null,"description":"","time":null,"title":"男神强强联手 我还期待你们手牵手 大槽剧男神执事团","type":0,"config":6,"url":null,"specialId":"2029553"}]}
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
		 * list : [{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180821llun.jpg","slideId":20628,"releaseDate":1437288522000,"subtitle":"","weekday":null,"description":"","time":null,"title":"北美票房周榜TOP30 第28周 ","type":0,"config":6,"url":null,"specialId":"2035009"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/181344178nqd.jpg","slideId":20627,"releaseDate":1437288516000,"subtitle":"","weekday":null,"description":"","time":null,"title":"终极电音榜【第二十九周】","type":0,"config":6,"url":null,"specialId":"2033193"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/17101240711t.jpg","slideId":20585,"releaseDate":1437099606000,"subtitle":"知名条子到访，大事件全程正能量！","weekday":null,"description":"","time":null,"title":"【暴走大事件第四季】第二期","type":0,"config":6,"url":null,"specialId":"2031830"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/16162325aknl.jpg","slideId":20572,"releaseDate":1437044558000,"subtitle":"","weekday":null,"description":"","time":null,"title":"男神强强联手 我还期待你们手牵手 大槽剧男神执事团","type":0,"config":6,"url":null,"specialId":"2029553"}]
		 */
		private List<ListEntity> list;

		public void setList(List<ListEntity> list) {
			this.list = list;
		}

		public List<ListEntity> getList() {
			return list;
		}

		public class ListEntity {
			/**
			 * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180821llun.jpg
			 * slideId : 20628
			 * releaseDate : 1437288522000
			 * subtitle :
			 * weekday : null
			 * description :
			 * time : null
			 * title : 北美票房周榜TOP30 第28周
			 * type : 0
			 * config : 6
			 * url : null
			 * specialId : 2035009
			 */
			private String cover;
			private int slideId;
			private long releaseDate;
			private String subtitle;
			private String weekday;
			private String description;
			private String time;
			private String title;
			private int type;
			private int config;
			private String url;
			private String specialId;

			public void setCover(String cover) {
				this.cover = cover;
			}

			public void setSlideId(int slideId) {
				this.slideId = slideId;
			}

			public void setReleaseDate(long releaseDate) {
				this.releaseDate = releaseDate;
			}

			public void setSubtitle(String subtitle) {
				this.subtitle = subtitle;
			}

			public void setWeekday(String weekday) {
				this.weekday = weekday;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			public void setTime(String time) {
				this.time = time;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public void setType(int type) {
				this.type = type;
			}

			public void setConfig(int config) {
				this.config = config;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public void setSpecialId(String specialId) {
				this.specialId = specialId;
			}

			public String getCover() {
				return cover;
			}

			public int getSlideId() {
				return slideId;
			}

			public long getReleaseDate() {
				return releaseDate;
			}

			public String getSubtitle() {
				return subtitle;
			}

			public String getWeekday() {
				return weekday;
			}

			public String getDescription() {
				return description;
			}

			public String getTime() {
				return time;
			}

			public String getTitle() {
				return title;
			}

			public int getType() {
				return type;
			}

			public int getConfig() {
				return config;
			}

			public String getUrl() {
				return url;
			}

			public String getSpecialId() {
				return specialId;
			}
		}
	}
}
