package cn.kauth.sdk.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NativeHttpUtils {

    /**
     * 发送 POST 请求
     * 为了轻量化，选择了HTTPURLConnection
     * 毕竟一个okhttp就要两三M
     */
    public static HttpResponse post(String url, String body, Map<String, String> headers,
                                    int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            // 创建连接
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            // 基本设置
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            // 设置默认请求头
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Connection", "close"); // 关闭持久连接

            // 添加自定义请求头
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送请求体
            if (body != null && !body.isEmpty()) {
                outputStream = connection.getOutputStream();
                outputStream.write(body.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
            // 获取响应
            int responseCode = connection.getResponseCode();
            // 读取响应头
            Map<String, String> responseHeaders = new HashMap<>();
            for (int i = 0; ; i++) {
                String headerName = connection.getHeaderFieldKey(i);
                String headerValue = connection.getHeaderField(i);
                if (headerName == null && headerValue == null) {
                    break;
                }
                if (headerName != null) {
                    responseHeaders.put(headerName, headerValue);
                }
            }
            // 读取响应体
            String responseBody;
            if (responseCode >= 200 && responseCode < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            responseBody = readStream(inputStream);
            return new HttpResponse(responseCode, responseBody, responseHeaders);
        } finally {
            // 清理资源
            closeQuietly(outputStream);
            closeQuietly(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * 读取流为字符串
     */
    private static String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder response = new StringBuilder();
        char[] buffer = new char[4096];
        int charsRead;

        while ((charsRead = reader.read(buffer)) != -1) {
            response.append(buffer, 0, charsRead);
        }

        return response.toString();
    }

    /**
     * 安静地关闭资源
     */
    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // 忽略关闭异常
            }
        }
    }

    /**
     * 响应对象
     */
    public static class HttpResponse {
        private final int code;
        private final String body;
        private final Map<String, String> headers;

        public HttpResponse(int code, String body, Map<String, String> headers) {
            this.code = code;
            this.body = body;
            this.headers = headers;
        }

        public boolean isSuccessful() {
            return code >= 200 && code < 300;
        }

        public int getCode() {
            return code;
        }

        public String getBody() {
            return body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public String getHeader(String name) {
            return headers.get(name);
        }
    }
}