package Model;


/**
 * Player is the abstract class representing any possible unit that could be controlled in the
 * game, so only the goalie and shooter.
 * The traits that all players share is the possession of:
 * <ul>
 *   <li>A Name</li>
 *   <li>A color representing their team</li>
 *   <li>A RotationalDegree showing their direction</li>
 *   <li>X-coordinate of their position</li>
 *   <li>Y-coordinate of their position</li>
 *   <li>State of their movement</li>
 *   <li>Do they control the puck?</li>
 *   <li>Are they poke checking?</li>
 * </ul>
 */
public abstract class Player {
  private String name;
  private TEAM_COLOR color;
  private int radius;
  private double rotationDegree;
  private Position position;
  private MovementManager movementManager;
  private boolean hasPuck;
  private boolean pokeCheck;

  /**
   * Possible color choices for team assignments.
   */
  public enum TEAM_COLOR { BLUE, RED, YELLOW, GREEN, ORANGE, PURPLE };

  /**
   * Constructor of a basic player.
   * @param name Name of the player.
   * @param color Team the player is part of.
   * @param radius Size of the player
   * @param rotationDegree Degree representing direction.
   */
  public Player(String name, TEAM_COLOR color, int radius, double rotationDegree) {
    this.name = name;
    this.color = color;
    this.radius = radius;
    this.rotationDegree = rotationDegree;
    this.position = new Position(500,500);
    this.movementManager = new MovementManager(this);
    this.hasPuck = false;
    this.pokeCheck = false;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPosition(double x, double y) {
    position.setXCoord(x);
    position.setYCoord(y);
  }

  public Position getPosition() {
    return position;
  }

  public void updatePlayer(double deltaTime) {
    movementManager.updateMovement(deltaTime);
  }



}
