package za.web.skerwe.code.tutorial;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class BlockBreakerPanel extends JPanel implements KeyListener {

  private static final long serialVersionUID = 8868599394461230718L;

  private ArrayList<Block> blocks = new ArrayList<>();
  private ArrayList<Block> balls = new ArrayList<>();
  private ArrayList<Block> powerups = new ArrayList<>();
  private Block paddle;
  private Thread thread;
  private Animate animate;

  public BlockBreakerPanel() {
    this.paddle = new Block(175, 480, 150, 25, "assets/paddle.png");
    for (int i = 0; i < 8; i++) {
      this.blocks.add(new Block((i * 60 + 2), 0, 60, 25, "assets/blue.png"));
    }

    for (int i = 0; i < 8; i++) {
      this.blocks.add(new Block((i * 60 + 2), 25, 60, 25, "assets/red.png"));
    }

    for (int i = 0; i < 8; i++) {
      this.blocks.add(new Block((i * 60 + 2), 50, 60, 25, "assets/green.png"));
    }

    for (int i = 0; i < 8; i++) {
      this.blocks.add(new Block((i * 60 + 2), 75, 60, 25, "assets/yellow.png"));
    }

    Random random = new Random();
    for (int i = 0; i < 5; i++) {
      this.blocks.get(random.nextInt(32)).powerup = true;
    }

    this.balls.add(new Block(237, 437, 25, 25, "assets/ball.png"));
    addKeyListener(this);
    setFocusable(true);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (Block block : this.blocks) {
      block.draw(g, this);
    }
    for (Block ball : this.balls) {
      ball.draw(g, this);
    }
    for (Block powerup : this.powerups) {
      powerup.draw(g, this);
    }
    this.paddle.draw(g, this);
  }

  public void update() {
    for (Block powerup : this.powerups) {
      powerup.y += 1;
      if (powerup.intersects(this.paddle) && !powerup.destroyed) {
        powerup.destroyed = true;
        this.balls.add(new Block(this.paddle.dx + 75, 437, 25, 25, "assets/ball.png"));
      }
    }
    for (Block ball : this.balls) {
      ball.x += ball.dx;

      if (ball.x > (getWidth() - ball.width) && ball.dx > 0 || ball.x < 0) {
        ball.dx *= -1;
      }

      ball.y += ball.dy;

      if (ball.y < 0 || ball.intersects(this.paddle)) {
        ball.dy *= -1;
      }

      for (Block block : this.blocks) {
        if ((block.left.intersects(ball) || block.right.intersects(ball)) && !block.destroyed) {
          ball.dx *= -1;
          block.destroyed = true;
          if (block.powerup) {
            this.powerups.add(new Block(block.x, block.y, 25, 19, "assets/extra.png"));
          }
        } else if (ball.intersects(block) && !block.destroyed) {
          block.destroyed = true;
          ball.dy *= -1;
          if (block.powerup) {
            this.powerups.add(new Block(block.x, block.y, 25, 19, "assets/extra.png"));
          }
        }
      }
    }
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent event) {
  }

  @Override
  public void keyPressed(KeyEvent event) {
    if (event.getKeyCode() == KeyEvent.VK_ENTER) {
      this.animate = new Animate(this);
      this.thread = new Thread(animate);
      this.thread.start();
    }
    if (event.getKeyCode() == KeyEvent.VK_LEFT && this.paddle.x > 0) {
      this.paddle.x -= 15;
    }
    if (event.getKeyCode() == KeyEvent.VK_RIGHT && this.paddle.x < (getWidth() - this.paddle.width)) {
      this.paddle.x += 15;
    }
  }

  @Override
  public void keyReleased(KeyEvent event) {
  }
}
