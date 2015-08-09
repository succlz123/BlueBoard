package org.succlz123.AxBTube.support.callback;

import org.succlz123.AxBTube.bean.acfun.AcReHot;
import org.succlz123.AxBTube.bean.acfun.AcReOther;

/**
 * Created by fashi on 2015/8/9.
 */
public interface GetAcPartitionHttpResult {

	void onPartitionHotResult(AcReOther result);

	void onPartitionLastPostResult(AcReOther result);

	void onHotResult(AcReHot result);
}
