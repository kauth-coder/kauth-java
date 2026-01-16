package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 以卡充卡请求
 */
@Data
public class KaRechargeKaReq {

    /**
     * 被充值的卡密
     */
    private String cardPwd;

    /**
     * 充值卡密
     */
    private String rechargeCardPwd;
}
