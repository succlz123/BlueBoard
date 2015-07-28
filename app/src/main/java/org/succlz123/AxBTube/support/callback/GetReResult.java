package org.succlz123.AxBTube.support.callback;

import org.succlz123.AxBTube.bean.acfun.AcReAnimation;
import org.succlz123.AxBTube.bean.acfun.AcReBanner;
import org.succlz123.AxBTube.bean.acfun.AcReHot;

/**
 * Created by chinausky on 2015/7/28.
 */
public interface GetReResult {

	void onReBannerResult(AcReBanner result);

	void onAcReHotResult(AcReHot result);

	void onAcReAnimationResult(AcReAnimation result);
}
