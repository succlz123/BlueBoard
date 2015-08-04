package org.succlz123.AxBTube.support.callback;

import org.succlz123.AxBTube.bean.acfun.AcReOther;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;

/**
 * Created by succlz123 on 2015/7/28.
 */
public interface GetAcRecommendHttpResult {

    void onReBannerResult(AcReBanner result);

    void onAcReHotResult(AcReHot result);

    void onAcReAnimationResult(AcReOther result);

    void onAcReFunResult(AcReOther result);

    void onAcReMusicResult(AcReOther result);

    void onAcReGameResult(AcReOther result);

    void onAcReScienceResult(AcReOther result);

    void onAcReSportResult(AcReOther result);

    void onAcReTvResult(AcReOther result);

}
