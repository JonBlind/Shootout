package Model;

/**
 * This class represents the mechanics behind the puck, the item the game revolves all around.
 */
public class Puck implements IMobileObject {
  private double radius;
  private double speed;
  private double angle;
  private Position position;
  private boolean possessed;
  private Player possessor;

  /**
   * Basic constructor for a puck using only coordinates.
   * @param position In charge of tracking the puck's coordinates.
   */
  public Puck(Position position) {
    this.radius = GameConfig.PUCK_RADIUS;
    this.position = position;
    this.speed = 0.0;
    this.angle = 0.0;
    this.possessed = false;
    this.possessor = null;
  }

  /**
   * Constructor for puck if you want to customize size, with specified location.
   * @param radius number of units representing the pucks radius.
   * @param position In charge of tracking the puck's coordinates.
   */
  public Puck(double radius, Position position) {
    this.radius = radius;
    this.position = position;
  }

  @Override
  public void setPosition(Position position) {

  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public void setPosition(double x, double y) {

  }

  @Override
  public void update(double deltaTime) {

  }
}
