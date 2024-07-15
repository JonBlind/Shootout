package Model;

public class Position {
  private double x;
  private double y;

  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getXCoord() {
    return x;
  }

  public void setXCoord(double x) {
    this.x = x;
  }

  public double getYCoord() {
    return y;
  }

  public void setYCoord(double y) {
    this.y = y;
  }
}
