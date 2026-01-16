package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 程序详情响应类
 *
 * @author SongLongKuan
 * @date 2025/4/4 19:35
 */
@Data
public class ProgramDetailResponse {
    /**
     * 程序唯一标识
     */
    private Long programId;

    /**
     * 程序名称
     */
    private String name;

    /**
     * 是否开启强制更新
     */
    private Boolean forceUpdateDict;

    /**
     * 程序状态
     */
    private String status;

    /**
     * 是否开启用户注册
     */
    private Boolean enableRegisterDict;

    /**
     * 程序公告内容
     */
    private String notice;

    /**
     * 当前版本详细信息
     */
    private ProgrameVersionInfo currentVersion;

    /**
     * 程序版本信息
     */
    @Data
    public static class ProgrameVersionInfo {
        /**
         * 版本号
         */
        private Integer versionNo;

        /**
         * 版本名称
         */
        private String versionName;

        /**
         * 版本描述
         */
        private String versionDesc;

        /**
         * 版本下载链接
         */
        private String versionDownUrl;

    }
}
