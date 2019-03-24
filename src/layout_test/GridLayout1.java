package layout_test;

import util.SwingUtil;

import javax.swing.*;
import java.awt.*;

public class GridLayout1 extends JFrame {
    public GridLayout1() {
        this.setLayout(new GridLayout(7,3));
        for (int i = 0;i < 20;i++) {
            add(new JButton("Button" + i));
        }
    }

    public static void main(String[] args) {
        SwingUtil.run(new GridLayout1(),300,300,"GridLayout Test");
    }
}
