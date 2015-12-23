package org.succlz123.okdownload;

/**
 * Created by succlz123 on 15/9/11.
 */
public class OkDownloadError {
    private static final String TAG = "OkDownloadError";

    private int mCode;

    private String mMessage;

    public static final int UNKNOWN = 0;

    public static final int CONTEXT_IS_NULL = 1;

    public static final int DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID = 2;

    public static final int DOWNLOAD_REQUEST_IS_REPEAT = 3;

    public static final int DOWNLOAD_REQUEST_IS_COMPLETE = 4;

    public static final int OKHTTP_ONRESPONSE_FAIL = 5;

    public static final int OKHTTP_ONFAILURE = 6;

    public static final int OKHTTP_IO_ERROR = 7;

    public static final int ANDROID_MEMORY_SIZE_IS_TOO_LOW = 8;

    public OkDownloadError(int code) {
        this.mCode = code;
        getErrorMessageFromCode(code);
    }

    public int getCode() {
        return mCode;
    }

    private void setCode(int code) {
        this.mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    private void setMessage(String message) {
        this.mMessage = message;
    }

    private String getErrorMessageFromCode(int code) {
        switch (code) {
            case CONTEXT_IS_NULL:
                setMessage("the context is null or not valid!");
                break;
            case DOWNLOAD_URL_OR_FILEPATH_IS_NOT_VALID:
                setMessage("the download url or filePath is not valid!");
                break;
            case DOWNLOAD_REQUEST_IS_REPEAT:
                setMessage("the download request is repeat!");
                break;
            case DOWNLOAD_REQUEST_IS_COMPLETE:
                setMessage("the download request is complete!");
                break;
            case OKHTTP_ONRESPONSE_FAIL:
                setMessage("下载链接失效,请删除后重新下载!");
                break;
            case OKHTTP_ONFAILURE:
                setMessage("下载链接失效,或者网络错误!");
                break;
            case OKHTTP_IO_ERROR:
                setMessage("okHttp io error!");
                break;
            case ANDROID_MEMORY_SIZE_IS_TOO_LOW:
                setMessage("android storage memory size is too low");
            default:
                setMessage("unknown error!");
                break;
        }
        return getMessage();
    }
}
