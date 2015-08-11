package org.succlz123.AxBTube.bean.acfun;

import java.util.HashMap;
import java.util.List;

/**
 * Created by succlz123 on 15/8/11.
 */
public class AcContentReply {

    /**
     * status : 200
     * data : {"cache":"nohit","page":{"totalCount":40,"pageNo":1,"map":{"c40554391":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/1023515987wh.jpg","type":1,"contentId":2086956,"id":40554391,"content":"[emot=ais,18/]深夜福利！","refCount":0,"time":1439222927000,"username":"ご壊吖头","floor":34,"isAt":0,"nameRed":0,"userId":1203123,"avatarFrame":1,"deep":0},"c40541476":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/17123427pc5t.jpg","type":1,"contentId":2086956,"id":40541476,"content":"4P足以。","refCount":0,"time":1439203658000,"username":"Thrud","floor":23,"isAt":0,"nameRed":1,"userId":715067,"avatarFrame":1,"deep":0}},"pageSize":20,"list":[40560620,40559764,40559566,40555347,40555155,40554716,40554391,40553316,40552370,40552270,40551284,40549931,40548988,40545923,40545559,40544016,40542092,40541476,40534174,40530678]}}
     * msg : 操作成功
     * success : true
     */
    private int status;
    private DataEntity data;
    private String msg;
    private boolean success;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class DataEntity {
        /**
         * cache : nohit
         * page : {"totalCount":40,"pageNo":1,"map":{"c40554391":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/1023515987wh.jpg","type":1,"contentId":2086956,"id":40554391,"content":"[emot=ais,18/]深夜福利！","refCount":0,"time":1439222927000,"username":"ご壊吖头","floor":34,"isAt":0,"nameRed":0,"userId":1203123,"avatarFrame":1,"deep":0},"c40541476":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/17123427pc5t.jpg","type":1,"contentId":2086956,"id":40541476,"content":"4P足以。","refCount":0,"time":1439203658000,"username":"Thrud","floor":23,"isAt":0,"nameRed":1,"userId":715067,"avatarFrame":1,"deep":0}},"pageSize":20,"list":[40560620,40559764,40559566,40555347,40555155,40554716,40554391,40553316,40552370,40552270,40551284,40549931,40548988,40545923,40545559,40544016,40542092,40541476,40534174,40530678]}
         */
        private String cache;
        private PageEntity page;

        public void setCache(String cache) {
            this.cache = cache;
        }

        public void setPage(PageEntity page) {
            this.page = page;
        }

        public String getCache() {
            return cache;
        }

        public PageEntity getPage() {
            return page;
        }

        public static class PageEntity {
            /**
             * totalCount : 40
             * pageNo : 1
             * map : {"c40554391":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/1023515987wh.jpg","type":1,"contentId":2086956,"id":40554391,"content":"[emot=ais,18/]深夜福利！","refCount":0,"time":1439222927000,"username":"ご壊吖头","floor":34,"isAt":0,"nameRed":0,"userId":1203123,"avatarFrame":1,"deep":0},"c40541476":{"quoteId":0,"avatar":"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/17123427pc5t.jpg","type":1,"contentId":2086956,"id":40541476,"content":"4P足以。","refCount":0,"time":1439203658000,"username":"Thrud","floor":23,"isAt":0,"nameRed":1,"userId":715067,"avatarFrame":1,"deep":0}}
             * pageSize : 20
             * list : [40560620,40559764,40559566,40555347,40555155,40554716,40554391,40553316,40552370,40552270,40551284,40549931,40548988,40545923,40545559,40544016,40542092,40541476,40534174,40530678]
             */
            private int totalCount;
            private int pageNo;
            private HashMap<String, Entity> map;
            private int pageSize;
            private List<Integer> list;

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public void setMap(HashMap<String, Entity> map) {
                this.map = map;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setList(List<Integer> list) {
                this.list = list;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public int getPageNo() {
                return pageNo;
            }

            public HashMap<String, Entity> getMap() {
                return map;
            }

            public int getPageSize() {
                return pageSize;
            }

            public List<Integer> getList() {
                return list;
            }

            private Entity entity;

            public void setEntity(Entity entity) {
                this.entity = entity;
            }

            public Entity getEntity() {
                return entity;
            }
        }

        public static class Entity {
            /**
             * quoteId : 0
             * avatar : http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/1023515987wh.jpg
             * type : 1
             * contentId : 2086956
             * id : 40554391
             * content : [emot=ais,18/]深夜福利！
             * refCount : 0
             * time : 1439222927000
             * username : ご壊吖头
             * floor : 34
             * isAt : 0
             * nameRed : 0
             * userId : 1203123
             * avatarFrame : 1
             * deep : 0
             */
            private int quoteId;
            private String avatar;
            private int type;
            private int contentId;
            private int id;
            private String content;
            private int refCount;
            private long time;
            private String username;
            private int floor;
            private int isAt;
            private int nameRed;
            private int userId;
            private int avatarFrame;
            private int deep;

            public void setQuoteId(int quoteId) {
                this.quoteId = quoteId;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setContentId(int contentId) {
                this.contentId = contentId;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setRefCount(int refCount) {
                this.refCount = refCount;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setFloor(int floor) {
                this.floor = floor;
            }

            public void setIsAt(int isAt) {
                this.isAt = isAt;
            }

            public void setNameRed(int nameRed) {
                this.nameRed = nameRed;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public void setAvatarFrame(int avatarFrame) {
                this.avatarFrame = avatarFrame;
            }

            public void setDeep(int deep) {
                this.deep = deep;
            }

            public int getQuoteId() {
                return quoteId;
            }

            public String getAvatar() {
                return avatar;
            }

            public int getType() {
                return type;
            }

            public int getContentId() {
                return contentId;
            }

            public int getId() {
                return id;
            }

            public String getContent() {
                return content;
            }

            public int getRefCount() {
                return refCount;
            }

            public long getTime() {
                return time;
            }

            public String getUsername() {
                return username;
            }

            public int getFloor() {
                return floor;
            }

            public int getIsAt() {
                return isAt;
            }

            public int getNameRed() {
                return nameRed;
            }

            public int getUserId() {
                return userId;
            }

            public int getAvatarFrame() {
                return avatarFrame;
            }

            public int getDeep() {
                return deep;
            }
        }
    }
}