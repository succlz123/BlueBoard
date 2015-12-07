package org.succlz123.blueboard.utils.danmaku;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.android.JSONSource;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

/**
 * Created by succlz123 on 15/8/19.
 */
public class AcFunDanmakuParser extends BaseDanmakuParser {
    private Danmakus mDanmakus = new Danmakus();

    @Override
    public Danmakus parse() {
        if (mDataSource == null && !(mDataSource instanceof JSONSource)) {
            return mDanmakus;
        }

        JSONSource jsonSource = (JSONSource) mDataSource;
        JSONArray danmakuArray = null;

        try {
            danmakuArray = (JSONArray) (jsonSource.data().get(2));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        onJsonArrayParse(danmakuArray);

        return mDanmakus;
    }

    private void onJsonArrayParse(JSONArray danmakuArray) {
        if (danmakuArray != null || danmakuArray.length() > 0) {

            for (int i = 0; i < danmakuArray.length(); i++) {
                try {
                    onJsonObjectParse(danmakuArray.getJSONObject(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }

    private void onJsonObjectParse(JSONObject danmakuObject) {
        if (danmakuObject != null || danmakuObject.length() > 0) {
            try {
                JSONObject obj = danmakuObject;
                String c = obj.getString("c");
                String[] values = c.split(",");
                if (values.length > 0) {
                    // 弹幕类型 (1)
                    int type = Integer.parseInt(values[2]);
                    if (type == 7) {
                        return;
                    }
                    // 出现时间 (121.517)
                    long time = (long) (Float.parseFloat(values[0]) * 1000);
                    // 颜色 (16777215)
                    int color = Integer.parseInt(values[1]) | 0xFF000000;
                    // 字体大小 (25)
                    float textSize = Float.parseFloat(values[3]);
                    BaseDanmaku item = mContext.mDanmakuFactory.createDanmaku(type, mContext);
                    if (item != null) {
                        item.time = time;
                        item.textSize = textSize * (mDispDensity - 0.6f);
                        item.textColor = color;
                        item.textShadowColor = color <= Color.BLACK ? Color.WHITE : Color.BLACK;
                        DanmakuUtils.fillText(item, obj.optString("m", "...."));
                        item.index = 0;
                        item.setTimer(mTimer);
                        mDanmakus.addItem(item);
                    }
                }
            } catch (JSONException e) {
            } catch (NumberFormatException e) {
            } finally {
            }
        }
    }
}

