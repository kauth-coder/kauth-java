package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * @author: songlongkuan
 * CreateTime: 2025-08-20
 * Description: 用户信息响应
 */
@Data
public class UserInfo {

    /**
     * 用户唯一标识
     */
    private Long userId;

    /**
     * 服务到期时间
     * 年-月-日 时:分:秒
     */
    private String serverExpireTime;

    /**
     * 服务剩余使用次数
     */
    private Long serverRemainNum;

    /**
     * 服务类型,time(时长) ci(次卡)
     */
    private String serverType;

    /**
     * 是否为试用用户
     */
    private boolean trial;
}
