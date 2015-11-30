package org.succlz123.blueboard.model.bean.acfun;

import java.util.List;

/**
 * Created by succlz123 on 2015/8/19.
 */
public class AcGetH5ByVid {

	/**
	 * result : {"C20":{"totalseconds":431.613,"files":[{"url":"http://k.youku.com/player/getFlvPath/sid/343998761309212fe7c78_00/st/mp4/fileid/0300200100557648F3218E05F4B68227C6988B-8371-8DB9-0078-223327398B19?K=3fd263d1f435604724124f75&ctype=12&ev=1&oip=2067301590&token=3722&ep=cSaXE0CFUcgB5iPWij8bMXnlJ3AHXP4J9h%2BHgdJjALshTe%2B5nTanxJ%2BxRopCE48dACQOEun22NbuYkhmYfhArmkQ1zraMPrg%2Fvfr5a9Rx5MCYRo%2Fer%2FWvVSdRDLz&ymovie=1"}]}}
	 * code : 200
	 * success : true
	 * source : {"name":"2015年 22、23周","type":"youku","title":"2015年 22、23周"}
	 * message : 成功
	 */
	private ResultEntity result;
	private int code;
	private boolean success;
	private SourceEntity source;
	private String message;

	public void setResult(ResultEntity result) {
		this.result = result;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setSource(SourceEntity source) {
		this.source = source;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultEntity getResult() {
		return result;
	}

	public int getCode() {
		return code;
	}

	public boolean isSuccess() {
		return success;
	}

	public SourceEntity getSource() {
		return source;
	}

	public String getMessage() {
		return message;
	}

	public class ResultEntity {
		/**
		 * C20 : {"totalseconds":431.613,"files":[{"url":"http://k.youku.com/player/getFlvPath/sid/343998761309212fe7c78_00/st/mp4/fileid/0300200100557648F3218E05F4B68227C6988B-8371-8DB9-0078-223327398B19?K=3fd263d1f435604724124f75&ctype=12&ev=1&oip=2067301590&token=3722&ep=cSaXE0CFUcgB5iPWij8bMXnlJ3AHXP4J9h%2BHgdJjALshTe%2B5nTanxJ%2BxRopCE48dACQOEun22NbuYkhmYfhArmkQ1zraMPrg%2Fvfr5a9Rx5MCYRo%2Fer%2FWvVSdRDLz&ymovie=1"}]}
		 */
		private C20Entity C20;

		public void setC20(C20Entity C20) {
			this.C20 = C20;
		}

		public C20Entity getC20() {
			return C20;
		}

		public class C20Entity {
			/**
			 * totalseconds : 431.613
			 * files : [{"url":"http://k.youku.com/player/getFlvPath/sid/343998761309212fe7c78_00/st/mp4/fileid/0300200100557648F3218E05F4B68227C6988B-8371-8DB9-0078-223327398B19?K=3fd263d1f435604724124f75&ctype=12&ev=1&oip=2067301590&token=3722&ep=cSaXE0CFUcgB5iPWij8bMXnlJ3AHXP4J9h%2BHgdJjALshTe%2B5nTanxJ%2BxRopCE48dACQOEun22NbuYkhmYfhArmkQ1zraMPrg%2Fvfr5a9Rx5MCYRo%2Fer%2FWvVSdRDLz&ymovie=1"}]
			 */
			private double totalseconds;
			private List<FilesEntity> files;

			public void setTotalseconds(double totalseconds) {
				this.totalseconds = totalseconds;
			}

			public void setFiles(List<FilesEntity> files) {
				this.files = files;
			}

			public double getTotalseconds() {
				return totalseconds;
			}

			public List<FilesEntity> getFiles() {
				return files;
			}

			public class FilesEntity {
				/**
				 * url : http://k.youku.com/player/getFlvPath/sid/343998761309212fe7c78_00/st/mp4/fileid/0300200100557648F3218E05F4B68227C6988B-8371-8DB9-0078-223327398B19?K=3fd263d1f435604724124f75&ctype=12&ev=1&oip=2067301590&token=3722&ep=cSaXE0CFUcgB5iPWij8bMXnlJ3AHXP4J9h%2BHgdJjALshTe%2B5nTanxJ%2BxRopCE48dACQOEun22NbuYkhmYfhArmkQ1zraMPrg%2Fvfr5a9Rx5MCYRo%2Fer%2FWvVSdRDLz&ymovie=1
				 */
				private String url;

				public void setUrl(String url) {
					this.url = url;
				}

				public String getUrl() {
					return url;
				}
			}
		}
	}

	public class SourceEntity {
		/**
		 * name : 2015年 22、23周
		 * type : youku
		 * title : 2015年 22、23周
		 */
		private String name;
		private String type;
		private String title;

		public void setName(String name) {
			this.name = name;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public String getTitle() {
			return title;
		}
	}
}
