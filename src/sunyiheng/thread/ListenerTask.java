package sunyiheng.thread;

import sunyiheng.dao.DBStaticMethod;
import sunyiheng.entity.ApplicationBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ListenerTask implements Runnable {
    private ApplicationBean applicationBean;

    public ListenerTask(ApplicationBean applicationBean) {
        this.applicationBean = applicationBean;
    }

    @Override
    public void run() {
        String processName = applicationBean.getPath().substring(applicationBean.getPath().lastIndexOf("\\") + 1);
//            Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//            String line = null;
//            System.out.println(proc.isAlive());
        while (true) {
            try {
                if (checkRun(processName)) {
                    System.out.println("检测到程序开始运行" + applicationBean.getName());
                    Date startTime = new Date();
                    while (true) {
                        if (!checkRun(processName)) {
                            break;
                        }
//                        Thread.sleep(5000);
                        TimeUnit.SECONDS.sleep(5);
                    }
                    Date endTime = new Date();
                    Long time = endTime.getTime() - startTime.getTime();
                    try {
                        DBStaticMethod.updateTotalTimeById(time, applicationBean.getId());
                        DBStaticMethod.addApplicationRecord(startTime,endTime,applicationBean.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                Thread.currentThread().sleep(10000);
//                    if (proc != null) {
//                        while ((line = bufferedReader.readLine()) != null) {
//                            if (line.contains(processName)) {
//                                Long startTime = new Date().getTime();
//                                while (true) {
//                                    System.out.println("检测到程序启动" + applicationBean.getName());
//                                    if (proc.isAlive() == false) {
//                                        break;
//                                    }
//                                    Thread.currentThread().sleep(5000);
//                                }
//                            }
//                        }
//
//                        Long endTime = new Date().getTime();
//                        System.out.println("程序已经结束" + endTime);
//                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    private boolean checkRun(String processName) {
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ex) {}
            }
        }
    }
}
