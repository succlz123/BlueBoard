package org.succlz123.okdownload;

import com.squareup.okhttp.OkHttpClient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by succlz123 on 15/9/11.
 */
public class OkDownloadRequest implements Parcelable {
    private int id;
    private String url;
    private String filePath;
    private long startTime;
    private long finishTime;
    private long fileSize;
    private String fileType;
    private int status;
    private String title;
    private String description;
    private String sign;
    private OkHttpClient okHttpClient;

    private OkDownloadRequest() {
    }

    private OkDownloadRequest(Builder builder) {
        id = builder.id;
        url = builder.url;
        sign = builder.sign;
        filePath = builder.filePath;
        fileType = builder.fileType;
        title = builder.title;
        description = builder.description;
        okHttpClient = builder.okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.url);
        dest.writeString(this.filePath);
        dest.writeLong(this.startTime);
        dest.writeLong(this.finishTime);
        dest.writeLong(this.fileSize);
        dest.writeString(this.fileType);
        dest.writeInt(this.status);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.sign);
    }

    protected OkDownloadRequest(Parcel in) {
        this.id = in.readInt();
        this.url = in.readString();
        this.filePath = in.readString();
        this.startTime = in.readLong();
        this.finishTime = in.readLong();
        this.fileSize = in.readLong();
        this.fileType = in.readString();
        this.status = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.sign = in.readString();
    }

    public static final Creator<OkDownloadRequest> CREATOR = new Creator<OkDownloadRequest>() {
        public OkDownloadRequest createFromParcel(Parcel source) {
            return new OkDownloadRequest(source);
        }

        public OkDownloadRequest[] newArray(int size) {
            return new OkDownloadRequest[size];
        }
    };

    public static class Builder {
        private int id;
        private String url;
        private String sign;
        private String filePath;
        private String fileType;
        private String title;
        private String description;
        private OkHttpClient okHttpClient;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder fileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder sign(String sign) {
            this.sign = sign;
            return this;
        }

        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public OkDownloadRequest build() {
            return new OkDownloadRequest(this);
        }
    }
}
