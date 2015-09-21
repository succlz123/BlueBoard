package org.succlz123.bluetube.bean.newacfun;

import java.util.List;

/**
 * Created by succlz123 on 15/9/21.
 */
public class NewAcVideo {

    /**
     * code : 200
     * data : {"files":[{"code":1,"description":"标清","url":["http://g3.letv.cn/vod/v2/MTYyLzE3LzQ5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNTgtYXZjLTIyNjI4My1hYWMtMTI4MDI1LTEyNDA1OC01NjU0NDYyLWU3NDhiNjU4MDI0ZGM0NWFmZDNjNjkyNzQxOGY0YjhiLTE0NDI2NzY3ODU0NjcubXA0?b=363&mmsid=35132975&tm=1442828028&key=6c2c6a481e5fbacf98ddc25e494fd9de&platid=2&splatid=208&playid=0&tss=no&vtype=21&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":2,"description":"高清","url":["http://g3.letv.cn/vod/v2/MTYyLzEyLzMvYmNsb3VkLzEyMzk2Ni92ZXJfMDBfMjItMTAwMTMxOTA2Mi1hdmMtNDY3NjgzLWFhYy0xMjgwMjUtMTI0MDU4LTkzOTgwMTYtYmJmNDZjOGRiMDJiYzVkMzhkZmM0YjEwMjI3MGRkNDItMTQ0MjY3NjgwNDg1My5tcDQ=?b=604&mmsid=35132975&tm=1442828028&key=b00170e3d4353a421009598c324e84fb&platid=2&splatid=208&playid=0&tss=no&vtype=13&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":3,"description":"超清","url":["http://g3.letv.cn/vod/v2/MTUyLzM4LzI5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNTktYXZjLTk1MTMwMi1hYWMtMTI4MDI1LTEyNDA1OC0xNjg5Nzg3MC0wYmM2NzljNmIwYWE0NmZmYmU5YmFlNDE1NmE3ZjY1ZS0xNDQyNjc2ODUyODAwLm1wNA==?b=1086&mmsid=35132975&tm=1442828028&key=9eefee180b020278dcca50ccdf321822&platid=2&splatid=208&playid=0&tss=no&vtype=22&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":4,"description":"原画","url":["http://g3.letv.cn/vod/v2/MTc5LzQ5Lzg5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNjEtYXZjLTYyOTUxNy1hYWMtMTI4MDAwLTEyNDA5MS0xMTk1MzU1MS0wMzJjZmRmNGQ0OTc1ZDIyZDc4YWI0MzgxZGJhODAyYS0xNDQyNjc2NjUxMTA2Lm1wNA==?b=768&mmsid=35132975&tm=1442828028&key=6385dc36decec181be144e70a0ea6d66&platid=2&splatid=208&playid=0&tss=no&vtype=28&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]}],"source":"letv","title":"Part 1","totalseconds":125}
     * message : OK
     */

    private int code;
    private DataEntity data;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static class DataEntity {
        /**
         * files : [{"code":1,"description":"标清","url":["http://g3.letv.cn/vod/v2/MTYyLzE3LzQ5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNTgtYXZjLTIyNjI4My1hYWMtMTI4MDI1LTEyNDA1OC01NjU0NDYyLWU3NDhiNjU4MDI0ZGM0NWFmZDNjNjkyNzQxOGY0YjhiLTE0NDI2NzY3ODU0NjcubXA0?b=363&mmsid=35132975&tm=1442828028&key=6c2c6a481e5fbacf98ddc25e494fd9de&platid=2&splatid=208&playid=0&tss=no&vtype=21&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":2,"description":"高清","url":["http://g3.letv.cn/vod/v2/MTYyLzEyLzMvYmNsb3VkLzEyMzk2Ni92ZXJfMDBfMjItMTAwMTMxOTA2Mi1hdmMtNDY3NjgzLWFhYy0xMjgwMjUtMTI0MDU4LTkzOTgwMTYtYmJmNDZjOGRiMDJiYzVkMzhkZmM0YjEwMjI3MGRkNDItMTQ0MjY3NjgwNDg1My5tcDQ=?b=604&mmsid=35132975&tm=1442828028&key=b00170e3d4353a421009598c324e84fb&platid=2&splatid=208&playid=0&tss=no&vtype=13&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":3,"description":"超清","url":["http://g3.letv.cn/vod/v2/MTUyLzM4LzI5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNTktYXZjLTk1MTMwMi1hYWMtMTI4MDI1LTEyNDA1OC0xNjg5Nzg3MC0wYmM2NzljNmIwYWE0NmZmYmU5YmFlNDE1NmE3ZjY1ZS0xNDQyNjc2ODUyODAwLm1wNA==?b=1086&mmsid=35132975&tm=1442828028&key=9eefee180b020278dcca50ccdf321822&platid=2&splatid=208&playid=0&tss=no&vtype=22&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]},{"code":4,"description":"原画","url":["http://g3.letv.cn/vod/v2/MTc5LzQ5Lzg5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNjEtYXZjLTYyOTUxNy1hYWMtMTI4MDAwLTEyNDA5MS0xMTk1MzU1MS0wMzJjZmRmNGQ0OTc1ZDIyZDc4YWI0MzgxZGJhODAyYS0xNDQyNjc2NjUxMTA2Lm1wNA==?b=768&mmsid=35132975&tm=1442828028&key=6385dc36decec181be144e70a0ea6d66&platid=2&splatid=208&playid=0&tss=no&vtype=28&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]}]
         * source : letv
         * title : Part 1
         * totalseconds : 125
         */

        private String source;
        private String title;
        private int totalseconds;
        private List<FilesEntity> files;

        public void setSource(String source) {
            this.source = source;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTotalseconds(int totalseconds) {
            this.totalseconds = totalseconds;
        }

        public void setFiles(List<FilesEntity> files) {
            this.files = files;
        }

        public String getSource() {
            return source;
        }

        public String getTitle() {
            return title;
        }

        public int getTotalseconds() {
            return totalseconds;
        }

        public List<FilesEntity> getFiles() {
            return files;
        }

        public static class FilesEntity {
            /**
             * code : 1
             * description : 标清
             * url : ["http://g3.letv.cn/vod/v2/MTYyLzE3LzQ5L2JjbG91ZC8xMjM5NjYvdmVyXzAwXzIyLTEwMDEzMTkwNTgtYXZjLTIyNjI4My1hYWMtMTI4MDI1LTEyNDA1OC01NjU0NDYyLWU3NDhiNjU4MDI0ZGM0NWFmZDNjNjkyNzQxOGY0YjhiLTE0NDI2NzY3ODU0NjcubXA0?b=363&mmsid=35132975&tm=1442828028&key=6c2c6a481e5fbacf98ddc25e494fd9de&platid=2&splatid=208&playid=0&tss=no&vtype=21&cvid=8169178871&payff=0&pip=17f534203771fb3976f30dea751d1fe8&tag=mobile&sign=bcloud_123966&termid=2&pay=0&ostype=android&hwtype=un"]
             */

            private int code;
            private String description;
            private List<String> url;

            public void setCode(int code) {
                this.code = code;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setUrl(List<String> url) {
                this.url = url;
            }

            public int getCode() {
                return code;
            }

            public String getDescription() {
                return description;
            }

            public List<String> getUrl() {
                return url;
            }
        }
    }
}
