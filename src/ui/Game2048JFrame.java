package ui;

import javax.swing.*;
import java.awt.*;

public class Game2048JFrame extends JFrame {
    public Game2048JFrame(String title) throws HeadlessException {
        super(title);
        initJFrame();
    }

    //初始化窗口
    private void initJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(new Game2048JPanel(), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
