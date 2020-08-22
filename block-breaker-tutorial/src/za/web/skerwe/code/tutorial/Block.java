package za.web.skerwe.code.tutorial;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Block extends Rectangle {

  private static final long serialVersionUID = -1942397202902891104L;

  Image pic;
  int dx = 3;
  int dy = -3;
  Rectangle left, right;
  boolean destroyed = false;
  boolean powerup = false;

  public Block(int a, int b, int w, int h, String s) {
    this.x = a;
    this.y = b;
    this.width = w;
    this.height = h;
    this.left = new Rectangle(a - 1, b, 1, h);
    this.right = new Rectangle(a + w + 1, b, 1, h);
    this.pic = Toolkit.getDefaultToolkit().getImage(s);
  }

  public void draw(Graphics g, Component c) {
    if (!this.destroyed) {
      g.drawImage(this.pic, this.x, this.y, this.width, this.height, c);
    }
  }
}