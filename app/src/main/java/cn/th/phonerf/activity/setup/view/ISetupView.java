package cn.th.phonerf.activity.setup.view;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.MerchantInfo;

public interface ISetupView extends IBaseView {

    /**
     * 保存商户信息
     * @param data
     */
    void saveMerchantData(MerchantInfo data);
}
