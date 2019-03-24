package layout_test;

import util.SwingUtil;

import javax.swing.*;
import java.awt.*;

public class BorderLayout1 extends JFrame {
    public BorderLayout1() {
        this.add(BorderLayout.NORTH,new JButton("North"));
        this.add(BorderLayout.SOUTH,new JButton("South"));
        this.add(BorderLayout.EAST,new JButton("East"));
        this.add(BorderLayout.WEST,new JButton("West"));
        this.add(BorderLayout.CENTER,new JButton("Center"));
    }

    public static void main(String[] args) {
        SwingUtil.run(new BorderLayout1(),300,250,"BorderLayout Test");
    }
}
