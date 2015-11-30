package org.succlz123.blueboard.model.bean.acfun;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by succlz123 on 2015/7/22.
 */
public class AcReHot {

	public static AcReHot parseJson(String json) {
		Gson gson = new Gson();

		return gson.fromJson(json, AcReHot.class);
	}

	/**
	 * msg : ok
	 * data : {"page":{"pageNo":1,"pageSize":10,"orderBy":1,"totalCount":1694,"list":[{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]}}
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

	public static class DataEntity {
		/**
		 * page : {"pageNo":1,"pageSize":10,"orderBy":1,"totalCount":1694,"list":[{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]}
		 */
		private PageEntity page;

		public void setPage(PageEntity page) {
			this.page = page;
		}

		public PageEntity getPage() {
			return page;
		}

		public static class PageEntity {
			/**
			 * pageNo : 1
			 * pageSize : 10
			 * orderBy : 1
			 * totalCount : 1694
			 * list : [{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg","slideId":20763,"releaseDate":1437549156000,"subtitle":"魔兽电影新镜头","weekday":null,"description":"","time":null,"title":"洛萨与迦罗娜镜头流出","type":0,"config":6,"url":null,"specialId":"2042003"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22125758743o.jpg","slideId":20777,"releaseDate":1437548960000,"subtitle":"生日快乐niconiconi~","weekday":null,"description":"","time":null,"title":"矢泽妮可的疯狂RAP","type":0,"config":6,"url":null,"specialId":"2016497"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/18180440kru9.jpg","slideId":20768,"releaseDate":1437548812000,"subtitle":"激燃向高同步","weekday":null,"description":"","time":null,"title":"大圣归来x龙战骑士","type":0,"config":6,"url":null,"specialId":"2035416"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/220042197c33.jpg","slideId":20766,"releaseDate":1437548791000,"subtitle":"大圣威武！！","weekday":null,"description":"","time":null,"title":"献与大圣的赞歌  The Dawn","type":0,"config":6,"url":null,"specialId":"2042863"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22143736p3kq.jpg","slideId":20792,"releaseDate":1437538448000,"subtitle":"周杰伦赞音准！","weekday":null,"description":"","time":null,"title":"马云献唱《好声音》","type":0,"config":6,"url":null,"specialId":"2043959"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22055851utuz.jpg","slideId":20789,"releaseDate":1437534965000,"subtitle":"【鸡蛋狂魔】","weekday":null,"description":"","time":null,"title":"如何在3秒内完美煎蛋","type":0,"config":6,"url":null,"specialId":"2042995"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22114659kv8v.jpg","slideId":20788,"releaseDate":1437534025000,"subtitle":"原创歌曲","weekday":null,"description":"","time":null,"title":"【围观】索尼大法好","type":0,"config":6,"url":null,"specialId":"2043538"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/22100811o4bs.jpg","slideId":20765,"releaseDate":1437531281000,"subtitle":"用最美的方法让家乡重生","weekday":null,"description":"","time":null,"title":"被艺术拯救的日本乡村","type":0,"config":6,"url":null,"specialId":"2042513"},{"cover":"http://ww2.sinaimg.cn/mw690/945b7e2ajw1euap329b52j205u03c0st.jpg","slideId":20771,"releaseDate":1437531240000,"subtitle":"中国LL粉上日本电视节目","weekday":null,"description":"","time":null,"title":"为了LL登陆大霓虹","type":0,"config":6,"url":null,"specialId":"2041724"},{"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21211214vsud.jpg","slideId":20770,"releaseDate":1437531233000,"subtitle":"吉卜力的故事","weekday":null,"description":"","time":null,"title":"宫崎骏陪我们走过的30年","type":0,"config":6,"url":null,"specialId":"2042516"}]
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

			public static class ListEntity {
				/**
				 * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201507/21172547b5ej.jpg
				 * slideId : 20763
				 * releaseDate : 1437549156000
				 * subtitle : 魔兽电影新镜头
				 * weekday : null
				 * description :
				 * time : null
				 * title : 洛萨与迦罗娜镜头流出
				 * type : 0
				 * config : 6
				 * url : null
				 * specialId : 2042003
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
}
