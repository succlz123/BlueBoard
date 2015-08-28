package org.succlz123.bluetube.bean.acfun;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by succlz123 on 15/8/3.
 */
public class AcContentInfo {
    /**
     * status : 200
     * data : {"fullContent":{"tags":["亡国的阿基德"],"toplevel":0,"channelId":67,"videos":[{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}],"isArticle":0,"contentId":2069095,"viewOnly":0,"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg","title":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","releaseDate":1438526006000,"description":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","views":4412,"stows":57,"user":{"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"},"isRecommend":0,"comments":24}}
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
         * fullContent : {"tags":["亡国的阿基德"],"toplevel":0,"channelId":67,"videos":[{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}],"isArticle":0,"contentId":2069095,"viewOnly":0,"cover":"http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg","title":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","releaseDate":1438526006000,"description":"【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】","views":4412,"stows":57,"user":{"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"},"isRecommend":0,"comments":24}
         */
        private FullContentEntity fullContent;

        public void setFullContent(FullContentEntity fullContent) {
            this.fullContent = fullContent;
        }

        public FullContentEntity getFullContent() {
            return fullContent;
        }

        public static class FullContentEntity {
            /**
             * tags : ["亡国的阿基德"]
             * toplevel : 0
             * channelId : 67
             * videos : [{"startTime":0,"sourceType":"letv","time":3602,"name":"F宅","danmakuId":2463027,"videoId":2463027,"type":"letv","commentId":2463027,"sourceId":"311043eae0"},{"startTime":0,"sourceType":"letv","time":3602,"name":"白恋","danmakuId":2463291,"videoId":2463291,"type":"letv","commentId":2463291,"sourceId":"36fc8b71e5"}]
             * isArticle : 0
             * contentId : 2069095
             * viewOnly : 0
             * cover : http://static.acfun.mm111.net/dotnet/artemis/u/cms/www/201508/02232914yqta.jpg
             * title : 【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】
             * releaseDate : 1438526006000
             * description : 【剧场版/720P】CODE GEASS 亡国的阿基德 第4章【F宅/白恋字幕组】
             * views : 4412
             * stows : 57
             * user : {"username":"零元帅","userId":1370061,"userImg":"http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg"}
             * isRecommend : 0
             * comments : 24
             */
            private List<String> tags;
            private int toplevel;
            private int channelId;
            private List<VideosEntity> videos;
            private int isArticle;
            private String contentId;
            private int viewOnly;
            private String cover;
            private String title;
            private long releaseDate;
            private String description;
            private int views;
            private int stows;
            private UserEntity user;
            private int isRecommend;
            private int comments;

            public void setTags(List<String> tags) {
                this.tags = tags;
            }

            public void setToplevel(int toplevel) {
                this.toplevel = toplevel;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public void setVideos(List<VideosEntity> videos) {
                this.videos = videos;
            }

            public void setIsArticle(int isArticle) {
                this.isArticle = isArticle;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }

            public void setViewOnly(int viewOnly) {
                this.viewOnly = viewOnly;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setReleaseDate(long releaseDate) {
                this.releaseDate = releaseDate;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setViews(int views) {
                this.views = views;
            }

            public void setStows(int stows) {
                this.stows = stows;
            }

            public void setUser(UserEntity user) {
                this.user = user;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public List<String> getTags() {
                return tags;
            }

            public int getToplevel() {
                return toplevel;
            }

            public int getChannelId() {
                return channelId;
            }

            public List<VideosEntity> getVideos() {
                return videos;
            }

            public int getIsArticle() {
                return isArticle;
            }

            public String getContentId() {
                return contentId;
            }

            public int getViewOnly() {
                return viewOnly;
            }

            public String getCover() {
                return cover;
            }

            public String getTitle() {
                return title;
            }

            public long getReleaseDate() {
                return releaseDate;
            }

            public String getDescription() {
                return description;
            }

            public int getViews() {
                return views;
            }

            public int getStows() {
                return stows;
            }

            public UserEntity getUser() {
                return user;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public int getComments() {
                return comments;
            }

            public static class VideosEntity implements Parcelable {
                /**
                 * startTime : 0
                 * sourceType : letv
                 * time : 3602
                 * name : F宅
                 * danmakuId : 2463027
                 * videoId : 2463027
                 * type : letv
                 * commentId : 2463027
                 * sourceId : 311043eae0
                 */
                private int startTime;
                private String sourceType;
                private int time;
                private String name;
                private String danmakuId;
                private String videoId;
                private String type;
                private String commentId;
                private String sourceId;

                public void setStartTime(int startTime) {
                    this.startTime = startTime;
                }

                public void setSourceType(String sourceType) {
                    this.sourceType = sourceType;
                }

                public void setTime(int time) {
                    this.time = time;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setDanmakuId(String danmakuId) {
                    this.danmakuId = danmakuId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public void setCommentId(String commentId) {
                    this.commentId = commentId;
                }

                public void setSourceId(String sourceId) {
                    this.sourceId = sourceId;
                }

                public int getStartTime() {
                    return startTime;
                }

                public String getSourceType() {
                    return sourceType;
                }

                public int getTime() {
                    return time;
                }

                public String getName() {
                    return name;
                }

                public String getDanmakuId() {
                    return danmakuId;
                }

                public String getVideoId() {
                    return videoId;
                }

                public String getType() {
                    return type;
                }

                public String getCommentId() {
                    return commentId;
                }

                public String getSourceId() {
                    return sourceId;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.startTime);
                    dest.writeString(this.sourceType);
                    dest.writeInt(this.time);
                    dest.writeString(this.name);
                    dest.writeString(this.danmakuId);
                    dest.writeString(this.videoId);
                    dest.writeString(this.type);
                    dest.writeString(this.commentId);
                    dest.writeString(this.sourceId);
                }

                public VideosEntity() {
                }

                protected VideosEntity(Parcel in) {
                    this.startTime = in.readInt();
                    this.sourceType = in.readString();
                    this.time = in.readInt();
                    this.name = in.readString();
                    this.danmakuId = in.readString();
                    this.videoId = in.readString();
                    this.type = in.readString();
                    this.commentId = in.readString();
                    this.sourceId = in.readString();
                }

                public static final Parcelable.Creator<VideosEntity> CREATOR = new Parcelable.Creator<VideosEntity>() {
                    public VideosEntity createFromParcel(Parcel source) {
                        return new VideosEntity(source);
                    }

                    public VideosEntity[] newArray(int size) {
                        return new VideosEntity[size];
                    }
                };
            }

            public static class UserEntity {
                /**
                 * username : 零元帅
                 * userId : 1370061
                 * userImg : http://static.acfun.mm111.net/dotnet/20120923/style/image/avatar.jpg
                 */
                private String username;
                private int userId;
                private String userImg;

                public void setUsername(String username) {
                    this.username = username;
                }

                public void setUserId(int userId) {
                    this.userId = userId;
                }

                public void setUserImg(String userImg) {
                    this.userImg = userImg;
                }

                public String getUsername() {
                    return username;
                }

                public int getUserId() {
                    return userId;
                }

                public String getUserImg() {
                    return userImg;
                }
            }
        }
    }
}
