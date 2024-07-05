package Model;

/**
 * This class represents the mechanics behind the puck, the item the game revolves all around.
 */
public class Puck {
  private double speed;
  private double angle;
  private double xCoord;
  private double yCoord;
  private boolean possessed;

  public Puck(double xCoord, double yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.speed = 0.0;
    this.angle = 0.0;
    this.possessed = false;
  }
}
