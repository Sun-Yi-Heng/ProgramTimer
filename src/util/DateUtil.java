package util;
// 时间相关工具
public class DateUtil {
    public static String longToTime(long millisecond) {
        // xx小时xx分钟xx秒
        String time;
        if (millisecond <= 1000) {
            time = "00小时00分钟00秒";
            return time;
        } else {
            // 毫秒 转化为 秒
            millisecond = millisecond / 1000;
            long hours = millisecond / (60 * 60);
            millisecond = millisecond - hours * 60 * 60;
            long minutes = millisecond / (60);
            millisecond = millisecond - minutes * 60;
            long seconds = millisecond;
            time = hours + "小时" + minutes + "分钟" + seconds + "秒";
            return time;
        }
    }
}
