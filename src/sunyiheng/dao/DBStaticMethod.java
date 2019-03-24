package sunyiheng.dao;

import sunyiheng.entity.ApplicationBean;
import util.ConstUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBStaticMethod {
    // 添加一个新的应用到数据库中
    public static void addApplication(String prefixName, String path) throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "insert into application(name,path) values (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,prefixName);
        statement.setString(2,path);

        statement.execute();
        statement.close();
        connection.close();
    }


    // 发现的问题：直接用传入的path不能查询到数据，原因在于路径中的单斜杠，替换成双斜杠后问题解决（明明数据库中显示的也是双斜杠）
    public static boolean findApplicationByPath(String path) throws SQLException{
        boolean judge;
        Connection connection = ConstUtil.connectionsPool.getConnection();
        // '\\\\'被java转化成'\\'再被正则表达式识别为'\'
        path = path.replaceAll("\\\\","\\\\\\\\");
        String sql ="SELECT * FROM application WHERE path = '" + path +"'";
        PreparedStatement statement = connection.prepareStatement(sql);
//        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next() == true) {
            judge= true;
        } else judge = false;
        statement.close();
        connection.close();
        return judge;
    }

    // 获取全部的应用程序
    public static List<ApplicationBean> getAllApplication() throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "SELECT * FROM application WHERE deleted = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,0);
        ResultSet resultSet = statement.executeQuery();
        List<ApplicationBean> list = new ArrayList<ApplicationBean>();
        while (resultSet.next()) {
            list.add(new ApplicationBean(resultSet.getInt("id"),resultSet.getString("name"),
                    resultSet.getString("path"),resultSet.getLong("totalTime")));
        }

        statement.close();
        connection.close();
        return list;
    }

    // 更新名称
    public static void updateName(String name,int id) throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "UPDATE application SET name = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,name);
        statement.setInt(2,id);
        statement.execute();

        statement.close();
        connection.close();
    }

    // 更新路径
    public static void updatePath(String path,int id) throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "UPDATE application SET path = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,path);
        statement.setInt(2,id);
        statement.execute();

        statement.close();
        connection.close();
    }

    // 根据id获取application
    public static ApplicationBean getApplicationBeanById(int id) throws SQLException {
        ApplicationBean applicationBean = null;
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "SELECT * FROM application WHERE id = ? AND deleted = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        statement.setInt(2,0);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            applicationBean = new ApplicationBean(resultSet.getInt("id"),resultSet.getString("name"),
                    resultSet.getString("path"),resultSet.getLong("totalTime"));
        }
        statement.close();
        connection.close();
        return applicationBean;
    }

    // 根据id删除对应的application
    public static void deleteApplicationById(int id) throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "UPDATE application SET deleted = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,1);
        statement.setInt(2,id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public static void updateTotalTimeById(long time ,int id) throws SQLException {
        long beforeTime = getApplicationBeanById(id).getTotalTime();
        long totalTime = beforeTime + time;

        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "UPDATE application SET totalTime = ? WHERE id = ? AND deleted = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1,totalTime);
        statement.setInt(2,id);
        statement.setInt(3,0);
        statement.execute();

        statement.close();
        connection.close();
    }



    //-------------------------------------------------------------------------------------------------------------
    public static void addApplicationRecord(Date startTime, Date endTime, int applicationId) throws SQLException {
        Connection connection = ConstUtil.connectionsPool.getConnection();
        String sql = "INSERT INTO applicationrecord(start_time,end_time,application_id) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setTimestamp(1,new Timestamp(startTime.getTime()));
        statement.setTimestamp(2,new Timestamp(endTime.getTime()));
        statement.setInt(3,applicationId);

        statement.execute();
        statement.close();
        connection.close();
    }
}
