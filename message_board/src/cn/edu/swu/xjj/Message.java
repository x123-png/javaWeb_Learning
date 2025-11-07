package cn.edu.swu.xjj;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String name;
    private String content;
    private Date timestamp;

    // 用于格式化时间
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Message(String name, String content) {
        this.name = name;
        this.content = content;
        this.timestamp = new Date(); // 自动设置当前时间
    }

    // --- Getter 方法 ---
    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getFormattedTimestamp() {
        return DATE_FORMAT.format(timestamp);
    }
}
