package cn.kauth.sdk.info.response;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime类型适配器
 * 用于Gson解析服务器返回的时间字符串格式 "yyyy-MM-dd HH:mm:ss"
 */
public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(FORMATTER));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        
        String value = in.nextString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        try {
            return LocalDateTime.parse(value, FORMATTER);
        } catch (Exception e) {
            throw new IOException("无法解析时间格式: " + value, e);
        }
    }
}
