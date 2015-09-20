package org.succlz123.nbdownload;

/**
 * Created by succlz123 on 15/9/11.
 */
public class NBDownloadStatus {
    /**
     *  下载任务准备好可以开始下载
     */
    public static final int STATUS_PENDING = 0x00000001;
    /**
     * 任务下载中
     */
    public static final int STATUS_RUNNING = 0x00000002;
    /**
     * 下载任务暂停
     */
    public static final int STATUS_STOPPED = 0x00000003;
    /**
     * 下载任务完成
     */
    public static final int STATUS_FINISHED = 0x00000005;
    /**
     * 下载任务失败
     */
    public static final int STATUS_FAILED = 0x00000006;
    /**
     * 下载任务被删除
     */
    public static final int STATUS_DELETED = 0x00000007;
}
