package sunyiheng;

import sunyiheng.component.MenuBar;
import sunyiheng.dao.DBStaticMethod;
import sunyiheng.entity.ApplicationBean;
import sunyiheng.thread.ListenerTask;
import util.DateUtil;
import util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainFrame extends JFrame {
    private MenuBar menu = new MenuBar();
    private JScrollPane scrollPane;
    private JPanel panel = new JPanel();
    private List<ApplicationBean> applicationBeanList;
    private List<Process> processList = new ArrayList<Process>();

    // 最小化所需操作
    private static final long serialVersionUID = 1L;
    private static TrayIcon trayIcon = null;
    static SystemTray tray = SystemTray.getSystemTray();

    public MainFrame() {
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.NORTH,menu);

//        GridBagLayout gridBagLayout = new GridBagLayout();
//        GridBagConstraints constraints = new GridBagConstraints();
//        try {
//            applicationBeanList = DBStaticMethod.getAllApplication();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(applicationBeanList.size());
//        GridLayout gridLayout = new GridLayout(applicationBeanList.size(),1);
//        panel.setLayout(gridLayout);
//        for (int i = 0;i < applicationBeanList.size();i++) {
//            JPanel rowPanel = new JPanel();
//            JLabel nameLabel = new JLabel();
//            JLabel timeLabel = new JLabel();
//            JButton startButton = new JButton("打开应用");
//            JButton editNameButton = new JButton("修改名称");
//            JButton editPathButton = new JButton("修改路径");
//            JButton deleteButton = new JButton("删除");
//            nameLabel.setText(applicationBeanList.get(i).getName());
//            timeLabel.setText(DateUtil.longToTime(applicationBeanList.get(i).getTotalTime()));
//            int id = applicationBeanList.get(i).getId();
//
//            // 直接通过路径打开应用程序
//            startButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    Runtime runtime = Runtime.getRuntime();
//                    Process process = null;
//                    try {
//                        String filePath = "\"" + DBStaticMethod.getApplicationBeanById(id).getPath() + "\"";
////                        String[] cmd = {"cmd","/k",filePath};
//                        String cmd = "cmd " + " /c " + filePath;
//                        System.out.println(cmd);
////                        for (String s:cmd) {
////                            System.out.println(s);
////                        }
//                        process = runtime.exec(cmd);
//                        processList.add(process);
////                        new Thread(new ApplicationListener(process,id)).start();
//                    } catch (Exception exception) {
//                        JOptionPane.showMessageDialog(MainFrame.this,"打开失败","失败",JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//
//            editNameButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String newName = JOptionPane.showInputDialog(MainFrame.this,"请输入修改后的名称","输入名称");
//                    System.out.println(newName);
//                    if (newName.equals("")) {
//                        JOptionPane.showMessageDialog(MainFrame.this,"输入不得为空","警告",JOptionPane.WARNING_MESSAGE);
//                    } else {
//                        try {
//                            nameLabel.setText(newName);
//                            DBStaticMethod.updateName(newName, id);
//                            JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
//                        } catch (SQLException exception) {
//                            JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
//                            exception.printStackTrace();
//                        }
//                    }
//                }
//            });
//
//            editPathButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    JFileChooser fileChooser = new JFileChooser();
//                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//                    fileChooser.showDialog(new JLabel(), "选择");
//                    File file = fileChooser.getSelectedFile();
//                    if (file != null) {
//                        // 获取文件的类型
//                        String fileName = file.getName();
//                        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
//                        if (fileType.equals("exe")) {
//                            try {
//                                String path = file.getAbsolutePath();
//                                DBStaticMethod.updatePath(path, id);
//                                JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
//                            } catch (SQLException exception) {
//                                JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
//                                exception.printStackTrace();
//                            }
//                        } else {
//                            JOptionPane.showMessageDialog(MainFrame.this,"文件类型不合法！(非.exe)","警告",JOptionPane.WARNING_MESSAGE);
//                        }
//                    }
//                }
//            });
//
//            deleteButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    try {
//                        DBStaticMethod.deleteApplicationById(id);
//                        JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
//                    } catch (SQLException exception) {
//                        JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            });
//
//            rowPanel.add(nameLabel);
//            rowPanel.add(timeLabel);
//            rowPanel.add(startButton);
//            rowPanel.add(editNameButton);
//            rowPanel.add(editPathButton);
//            rowPanel.add(deleteButton);
//            panel.add(rowPanel);
//        }
//        scrollPane = new JScrollPane(panel);
        this.add(makeMainWindow());
    }

    // 生成窗口的主体部分
    public JScrollPane makeMainWindow() {
        JPanel jPanel = new JPanel();
        try {
            applicationBeanList = DBStaticMethod.getAllApplication();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GridLayout gridLayout = new GridLayout(applicationBeanList.size(),1);
        jPanel.setLayout(gridLayout);
        for (int i = 0;i < applicationBeanList.size();i++) {
            JPanel rowPanel = new JPanel();
            JLabel nameLabel = new JLabel();
            JLabel timeLabel = new JLabel();
            JButton startButton = new JButton("打开应用");
            JButton editNameButton = new JButton("修改名称");
            JButton editPathButton = new JButton("修改路径");
            JButton deleteButton = new JButton("删除");
            nameLabel.setText(applicationBeanList.get(i).getName());
            timeLabel.setText(DateUtil.longToTime(applicationBeanList.get(i).getTotalTime()));
            int id = applicationBeanList.get(i).getId();

            // 直接通过路径打开应用程序
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Runtime runtime = Runtime.getRuntime();
                    Process process = null;
                    try {
                        String filePath = "\"" + DBStaticMethod.getApplicationBeanById(id).getPath() + "\"";
                        String cmd = "cmd " + " /c " + filePath;
                        System.out.println(cmd);
                        process = runtime.exec(cmd);
                        processList.add(process);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(MainFrame.this,"打开失败","失败",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            editNameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newName = JOptionPane.showInputDialog(MainFrame.this,"请输入修改后的名称","输入名称");
                    System.out.println(newName);
                    if (newName.equals("")) {
                        JOptionPane.showMessageDialog(MainFrame.this,"输入不得为空","警告",JOptionPane.WARNING_MESSAGE);
                    } else {
                        try {
                            nameLabel.setText(newName);
                            DBStaticMethod.updateName(newName, id);
                            JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
                        } catch (SQLException exception) {
                            JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
                            exception.printStackTrace();
                        }
                    }
                }
            });

            editPathButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.showDialog(new JLabel(), "选择");
                    File file = fileChooser.getSelectedFile();
                    if (file != null) {
                        // 获取文件的类型
                        String fileName = file.getName();
                        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                        if (fileType.equals("exe")) {
                            try {
                                String path = file.getAbsolutePath();
                                DBStaticMethod.updatePath(path, id);
                                JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
                                exception.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this,"文件类型不合法！(非.exe)","警告",JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBStaticMethod.deleteApplicationById(id);
                        JOptionPane.showMessageDialog(MainFrame.this,"更新成功","成功",JOptionPane.PLAIN_MESSAGE);
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(MainFrame.this,"数据库更新失败","失败",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            rowPanel.add(nameLabel);
            rowPanel.add(timeLabel);
            rowPanel.add(startButton);
            rowPanel.add(editNameButton);
            rowPanel.add(editPathButton);
            rowPanel.add(deleteButton);
            jPanel.add(rowPanel);
        }
        return new JScrollPane(jPanel);
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<ApplicationBean> beanList = DBStaticMethod.getAllApplication();
        for (ApplicationBean bean : beanList) {
            executorService.execute(new ListenerTask(bean));
//            new Thread(new ListenerTask(bean)).start();
        }

        MainFrame mainFrame = new MainFrame();
        ImageIcon imageIcon = new ImageIcon("src/sunyiheng/image/trayIcon.png");
        mainFrame.setIconImage(imageIcon.getImage());
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            // 程序最小化事件
            @Override
            public void windowIconified(WindowEvent e) {
                mainFrame.setVisible(false);
                MainFrame.miniTray(mainFrame);
            }
        });
        SwingUtil.run(mainFrame,1200,900,"ProgramTimer");
    }


    // 最小化操作
    private static void miniTray(MainFrame mainFrame) { // 窗口最小化到任务栏托盘
        ImageIcon trayImg = new ImageIcon("./../sunyiheng/image/trayIcon.png");// 托盘图标

        trayIcon = new TrayIcon(trayImg.getImage(), "ProgramTimer", new PopupMenu());
        trayIcon.setImageAutoSize(true);

        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {// 单击 1 双击 2
                    tray.remove(trayIcon);
                    mainFrame.setVisible(true);
                    mainFrame.setExtendedState(JFrame.NORMAL);
                    mainFrame.toFront();
                }
            }
        });
        try {
            tray.add(trayIcon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }

    public static void checkProcess() throws SQLException, IOException {
//        List<ApplicationBean> applicationBeanList = DBStaticMethod.getAllApplication();
//        Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//        String line = null;
//        while ((line = bufferedReader.readLine()) != null) {
//            if (line.contains("QQ.exe")) {
//                System.out.println("找到了");
//            }
//        }
    }
}
