package cn.kauth.sdk.info.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * MQTT服务配置响应
 *
 * @author SongLongKuan
 * @date 2025/12/12
 */
@Data
public class MqttServiceConfigResp {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商户ID
     */
    private Long merchantId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * MQTT服务器地址
     */
    private String brokerHost;

    /**
     * MQTT端口号
     */
    private Integer brokerPort;

    /**
     * MQTT WS端口号
     */
    private Integer brokerWsPort;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码(前端展示时用***替代)
     */
    private String password;

    /**
     * 心跳间隔(秒)
     */
    private Integer keepAlive;

    /**
     * 是否清除会话(0-否,1-是)
     */
    private Boolean cleanSession;

    /**
     * 是否自动重连(0-否,1-是)
     */
    private Boolean autoReconnect;

    /**
     * 重连间隔(秒)
     */
    private Integer reconnectInterval;

    /**
     * 默认服务质量等级(0-最多一次,1-至少一次,2-恰好一次)
     */
    private Integer qos;

    /**
     * 是否保留消息(0-否,1-是)
     */
    private Boolean retainFlag;

    /**
     * 是否启用SSL/TLS(0-否,1-是)
     */
    private Boolean enableSsl;

    /**
     * 是否默认配置(0-否,1-是)
     */
    private Boolean isDefault;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}