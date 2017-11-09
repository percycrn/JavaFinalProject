package com.util;

import javax.swing.*;

public class SwingConsole {
    public static void run(final JFrame f,final int width,final int height){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(width,height);
                f.setTitle(f.getClass().getSimpleName());
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }
}
