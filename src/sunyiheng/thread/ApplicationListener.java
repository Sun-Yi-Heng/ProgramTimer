package sunyiheng.thread;

import sunyiheng.dao.DBStaticMethod;

import java.sql.SQLException;
import java.util.Date;

public class ApplicationListener implements Runnable {
    private Process process;
    private int applicationId;

    public ApplicationListener(Process process, int applicationId) {
        this.process = process;
        this.applicationId = applicationId;
    }

    @Override
    public void run(){
        Date startTime = new Date();

        while (process.isAlive()) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Date endTime = new Date();
        Long time = endTime.getTime() - startTime.getTime();
        try {
            DBStaticMethod.updateTotalTimeById(time, applicationId);
            DBStaticMethod.addApplicationRecord(startTime,endTime,applicationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(time);
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }
}
