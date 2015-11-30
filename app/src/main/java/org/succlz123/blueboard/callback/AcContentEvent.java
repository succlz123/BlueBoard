package org.succlz123.blueboard.callback;

/**
 * Created by succlz123 on 15/11/27.
 */
public class AcContentEvent {
    private String mContentId;

    public AcContentEvent(String contentId) {
        this.mContentId = contentId;
    }
    public String getMsg() {
        return mContentId;
    }
}
