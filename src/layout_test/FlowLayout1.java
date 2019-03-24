package layout_test;

import util.SwingUtil;

import javax.swing.*;
import java.awt.*;

public class FlowLayout1 extends JFrame {
    public FlowLayout1() {
        setLayout(new FlowLayout());
        for (int i = 0;i < 20;i++) {
            add(new JButton("Button" + i));
        }
    }

    public static void main(String[] args) {
        SwingUtil.run(new FlowLayout1(),300,300,"FlowLayout Test");
    }
}
