package org.succlz123.nbdownload;

/**
 * Created by succlz123 on 15/9/11.
 */
public class NBDownloadException extends Exception {
    /**
     * unknown error
     */
    public static final int UNKNOWN = 0;
    /**
     * context is null
     */
    public static final int CONTEXT_NOT_VALID = 1;

    public static final int DOWNLOAD_URL_OR_FILEPATH_IS_NULL = 2;

    public static final int DOWNLOAD_URL_IS_NOT_VALID = 3;

    public static final int DOWNLOAD_REQUEST_IS_REPEAT = 4;

    public static final int DOWNLOAD_REQUEST_IS_READY_IN_THE_QUEUE = 5;

    public static final int DOWNLOAD_TASK_IS_UNKNOWN_ERROR = 6;

    public static final int DOWNLOAD_TASK_IS_STOPPED = 7;

    private int code = UNKNOWN;

    public NBDownloadException() {
        super(generateMessageFromCode(UNKNOWN));
        this.code = UNKNOWN;
    }

    public NBDownloadException(int code) {
        super(generateMessageFromCode(code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Generate the error message with the code
     *
     * @param code code
     */
    private static String generateMessageFromCode(int code) {
        String message;

        switch (code) {
            case CONTEXT_NOT_VALID:
                message = "The context is null or not valid!";
                break;
            case DOWNLOAD_URL_OR_FILEPATH_IS_NULL:
                message = "The download url or filePath is null!";
                break;
            case DOWNLOAD_URL_IS_NOT_VALID:
                message = "The download url is not valid!";
                break;
            case DOWNLOAD_REQUEST_IS_REPEAT:
                message = "The download request is repeat!";
                break;
            case DOWNLOAD_REQUEST_IS_READY_IN_THE_QUEUE:
                message = "Download_request_is_ready_in_the_queue";
                break;
            case DOWNLOAD_TASK_IS_UNKNOWN_ERROR:
                message = "DOWNLOAD_TASK_IS_UNKNOWN_ERROR";
                break;
            default:
                message = "Unknown Error!";
                break;
        }
        return message;
    }
}
