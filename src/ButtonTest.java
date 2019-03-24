import util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTest extends JFrame {
    private JButton button1;
    private JButton button2;
    private JTextArea textArea = new JTextArea(3,20);

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = ((JButton)e.getSource()).getText();

        }
    }


    public ButtonTest() {
        button1 = new JButton("addText");
        button2 = new JButton("clearText");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = "this is the text which is going to be added into textArea. emmmmmmmmmm!!!!";
                textArea.setText(text);
                button2.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        this.setLayout(new FlowLayout());
        this.add(button1);
        this.add(button2);
        this.add(new JScrollPane(textArea));
    }

    public static void main(String[] args) {
        SwingUtil.run(new ButtonTest(),500,200,"GUI");
    }
}
