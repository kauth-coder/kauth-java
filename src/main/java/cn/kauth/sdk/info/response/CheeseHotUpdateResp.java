package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 热更新响应
 *
 * @author SongLongKuan
 * @date 2025/12/9 14:10
 */
@Data
public class CheeseHotUpdateResp {
    /**
     * 响应消息
     */
    private String msg;

    /**
     * 更新版本号
     */
    private String update_version;
    
    /**
     * 更新内容描述
     */
    private String update_content;
    
    /**
     * 下载链接
     */
    private String download_url;

}
