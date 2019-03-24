package util;

import sunyiheng.dao.JdbcConnectionsPool;

public class ConstUtil {
    public static JdbcConnectionsPool connectionsPool = new JdbcConnectionsPool();

    // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
    // 避免中文乱码要指定useUnicode和characterEncoding
    public final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/mydatabase?" +
            "useUnicode=true&characterEncoding=UTF8";
    public final static String DB_USER = "root";
    public final static String DB_PASSWORD = "root";
}
