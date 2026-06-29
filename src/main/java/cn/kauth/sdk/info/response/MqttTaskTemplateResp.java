package cn.kauth.sdk.info.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * MQTT任务模板响应
 *
 * @author SongLongKuan
 * @date 2025/12/12 15:20
 */
@Data
public class MqttTaskTemplateResp {

    /**
     * 模板ID
     */
    private Long id;

    /**
     * 任务模板名称
     */
    private String name;

    /**
     * 任务模板描述
     */
    private String description;

    /**
     * 表单结构 JSON
     */
    private String contentJson;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}