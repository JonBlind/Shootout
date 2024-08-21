package Model;


import Model.MovementManagers.*;

/**
 * Player is the abstract class representing any possible unit that could be controlled in the
 * game, so only the goalie and shooter.
 * The traits that all players share is the possession of:
 * <ul>
 *   <li>A Name</li>
 *   <li>A color representing their team</li>
 *   <li>A angle showing their direction</li>
 *   <li>X-coordinate of their position</li>
 *   <li>Y-coordinate of their position</li>
 *   <li>State of their movement</li>
 *   <li>Do they control the puck?</li>
 *   <li>Are they poke checking?</li>
 * </ul>
 */
public abstract class Player implements IMobileObject {
  protected String name;
  protected TEAM team;
  protected double radius;
  protected double angle;
  protected Position position;
  protected IMovementManageable movementManager;
  protected boolean hasPuck;
  protected boolean pokeCheck;

  /**
   * Possible color choices for team assignments.
   */
  public enum TEAM { BLUE, RED, YELLOW, GREEN, ORANGE, PURPLE };

  /**
   * Constructor of a basic player.
   * @param name Name of the player.
   * @param team Team the player is part of.
   * @param radius Size of the player
   */
  public Player(String name, TEAM team, double radius) {
    this.name = name;
    this.team = team;
    this.radius = radius;
    this.angle = 0;
    this.position = new Position(500,500);
    this.hasPuck = false;
    this.pokeCheck = false;
    this.movementManager = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setPosition(double x, double y) {
    position.setXCoord(x);
    position.setYCoord(y);
  }

  @Override
  public Position getPosition() {
    return position;
  }

  @Override
  public void update(double deltaTime) {
  }

  /**
   * Obtain the MovementManager for this Player object.
   * @return this Player's MovementManager.
   */
  private IMovementManageable getMovementManager() {
    return movementManager;
  }

  /**
   * Method to alter the degree which a Player's stick is oriented.
   * @param degree degree which the player is facing.
   */
  public void setAngle(double degree) {
    this.angle = degree;
  }

  /**
   * setter method to alter pokeCheck field.
   * @param pokeCheck boolean, is this player poke checking?
   */
  public void setPokeCheck(boolean pokeCheck) {
    this.pokeCheck = pokeCheck;
  }



}
