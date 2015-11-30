package org.succlz123.blueboard.model.bean.bilibili;

import java.util.List;

/**
 * Created by succlz123 on 2015/7/19.
 */
public class BiliBanner {

	/**
	 * ver : 2032
	 * code : 0
	 * count : 4
	 * screen : xxhdpi
	 * list : [{"weburl":"http://www.bilibili.com/html/Kichiku.html","imagekey":"cd0ea841b71b7f4cca3ec5fab4f8bf2b","imageurl":"http://i2.hdslb.com/640_196/u_user/9eca1a3a6da3f7d48c3ec382a270fc65.jpg","width":640,"remark":"bilibili夏夜鬼畜大赏","style":"banner","title":"bilibili夏夜鬼畜大赏","type":"weblink","remark2":"","height":196},{"spname":"六花的勇者","imagekey":"1bd8c3a9880094cc612896a3b58f4590","imageurl":"http://i0.hdslb.com/640_200/u_user/d7bb569349ab7ca87d0a53100ed6093a.jpg","width":640,"remark":"六花的勇者","style":"banner","title":"六花的勇者","type":"bangumi","spid":50453,"remark2":"","height":200},{"spname":"没有黄段子的无聊世界","imagekey":"3f72d568984c4d7f1c57cbac48fb8f13","imageurl":"http://i2.hdslb.com/640_200/u_user/36d6f65809ff1465a7717741b1c2bda9.jpg","width":640,"remark":"没有黄段子存在的无聊世界","style":"banner","title":"没有黄段子存在的无聊世界","type":"bangumi","spid":37733,"remark2":"","height":200},{"spname":"WORKING!!!迷糊餐厅（第三季）","imagekey":"7aefc03998e57535127c8bb4812a9448","imageurl":"http://i1.hdslb.com/640_200/u_user/db851c3f7388af69e3be685eb7706f46.jpg","width":640,"remark":"WORKING!!!迷糊餐厅（第三季）","style":"banner","title":"WORKING!!!迷糊餐厅（第三季）","type":"bangumi","spid":50438,"remark2":"","height":200}]
	 */
	private String ver;
	private int code;
	private int count;
	private String screen;
	private List<ListEntity> list;

	public void setVer(String ver) {
		this.ver = ver;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public void setList(List<ListEntity> list) {
		this.list = list;
	}

	public String getVer() {
		return ver;
	}

	public int getCode() {
		return code;
	}

	public int getCount() {
		return count;
	}

	public String getScreen() {
		return screen;
	}

	public List<ListEntity> getList() {
		return list;
	}

	public class ListEntity {
		/**
		 * weburl : http://www.bilibili.com/html/Kichiku.html
		 * imagekey : cd0ea841b71b7f4cca3ec5fab4f8bf2b
		 * imageurl : http://i2.hdslb.com/640_196/u_user/9eca1a3a6da3f7d48c3ec382a270fc65.jpg
		 * width : 640
		 * remark : bilibili夏夜鬼畜大赏
		 * style : banner
		 * title : bilibili夏夜鬼畜大赏
		 * type : weblink
		 * remark2 :
		 * height : 196
		 */
		private String weburl;
		private String imagekey;
		private String imageurl;
		private int width;
		private String remark;
		private String style;
		private String title;
		private String type;
		private String remark2;
		private int height;

		public void setWeburl(String weburl) {
			this.weburl = weburl;
		}

		public void setImagekey(String imagekey) {
			this.imagekey = imagekey;
		}

		public void setImageurl(String imageurl) {
			this.imageurl = imageurl;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public void setStyle(String style) {
			this.style = style;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setRemark2(String remark2) {
			this.remark2 = remark2;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public String getWeburl() {
			return weburl;
		}

		public String getImagekey() {
			return imagekey;
		}

		public String getImageurl() {
			return imageurl;
		}

		public int getWidth() {
			return width;
		}

		public String getRemark() {
			return remark;
		}

		public String getStyle() {
			return style;
		}

		public String getTitle() {
			return title;
		}

		public String getType() {
			return type;
		}

		public String getRemark2() {
			return remark2;
		}

		public int getHeight() {
			return height;
		}
	}
}
