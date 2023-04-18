package com.litongjava.ai.server.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MatPanel extends JPanel {
  private BufferedImage bufferImage;

  @Override
  public void paint(Graphics g) {
    if (bufferImage != null) {
      g.drawImage(bufferImage, 0, 0, bufferImage.getWidth(), bufferImage.getHeight(), this);
    }
  }

  public void setBufferedImage(BufferedImage src) {
    this.bufferImage = src;
  }
}