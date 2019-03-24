package sunyiheng.component;


import sunyiheng.dao.DBStaticMethod;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

// 菜单
public class MenuBar extends JMenuBar {
    private JFileChooser fileChooser = new JFileChooser();
    public MenuBar() {
        add(createMainMenu());
    }

    private JMenu createMainMenu() {
        JMenu menu = new JMenu("主要(M)");
        // 设置快捷键
        menu.setMnemonic(KeyEvent.VK_M);

        JMenuItem chooseItem = new JMenuItem("选择(C)");
        chooseItem.setMnemonic(KeyEvent.VK_C);
        chooseItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                fileChooser.showDialog(new JLabel(), "选择");
                File file=fileChooser.getSelectedFile();
                if (file != null) {
                    // 获取文件的类型
                    String fileName = file.getName();
                    if (fileName.lastIndexOf(".") != -1) {
                        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                        // 前缀名
                        String prefixName = fileName.substring(0,fileName.lastIndexOf("."));
                        if (fileType.equals("exe")) {
                            try {
                                /* 当结果集为空时，插入新的记录 */
                                if (DBStaticMethod.findApplicationByPath(file.getAbsolutePath()) == false) {
                                    DBStaticMethod.addApplication(prefixName, file.getAbsolutePath());
                                    JOptionPane.showMessageDialog(MenuBar.this,"添加成功","成功",JOptionPane.PLAIN_MESSAGE);
                                } else {
//                                    ImageIcon icon = new ImageIcon("//resource//warning.png");
                                    JOptionPane.showMessageDialog(MenuBar.this,"该应用已经被添加到程序中！","警告",JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (SQLException exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(MenuBar.this,"文件类型不合法！(非.exe)","警告",JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(MenuBar.this,"选择的不是文件，而是文件夹！","警告",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        JMenuItem closeItem = new JMenuItem("关闭(esc)");
        closeItem.setMnemonic(KeyEvent.VK_ESCAPE);
        closeItem.addMouseListener(new MouseAdapter() {
            // 为啥不行？？？
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.exit(0);
//            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.exit(0);
            }
        });

        menu.add(chooseItem);
        menu.add(closeItem);

        return menu;
    }
}
