package za.web.skerwe.code.tutorial;

class Animate implements Runnable {

  private BlockBreakerPanel panel;

  public Animate(BlockBreakerPanel panel) {
    this.panel = panel;
  }

  public void run() {
    while (true) {
      this.panel.update();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
