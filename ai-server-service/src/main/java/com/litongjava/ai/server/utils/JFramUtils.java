package com.litongjava.ai.server.utils;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class JFramUtils {

  public static void showBufferedImage(String title, BufferedImage image) {
    MatPanel panel = new MatPanel();
    panel.setBufferedImage(image);
    // repaint自动调用paint
    panel.repaint();

    JFrame frame = new JFrame(title);
    frame.setSize(image.getWidth(), image.getHeight());
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setContentPane(panel);
    frame.setVisible(true);
  }

  public static void showBufferedImage(String title, Object dst) {
    showBufferedImage(title, (BufferedImage) dst);
  }

}
