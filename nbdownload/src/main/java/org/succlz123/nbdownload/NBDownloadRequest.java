package org.succlz123.nbdownload;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "nbdownload")
public class NBDownloadRequest {
    /**
     * id 主键
     */
    @DatabaseField(generatedId = true, canBeNull = false)
    private int id;
    /**
     * url
     */
    @DatabaseField
    private String url;
    /**
     * 下载保存路径
     */
    @DatabaseField
    private String filePath;
    /**
     * 下载开始时间
     */
    @DatabaseField
    private long startTime;
    /**
     * 下载结束时间
     */
    @DatabaseField
    private long finishTime;
    /**
     * 下载文件的总大小
     */
    @DatabaseField
    private long totalSize;
    /**
     * 下载的资源媒体类型
     * Content-Type: text/HTML
     */
    @DatabaseField
    private String mimeType;
    /**
     * 下载任务的名字
     */
    @DatabaseField
    private String name;
    /**
     * 下载状态
     */
    @DatabaseField
    private int status;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String sourceType;

    @DatabaseField
    private String sourceId;

    public NBDownloadRequest() {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return "NBDownloadRequest{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", filePath='" + filePath + '\'' +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", totalSize=" + totalSize +
                ", mimeType='" + mimeType + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", sourceId='" + sourceId + '\'' +
                '}';
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

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}
