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
 *   <li>An array of positions marking every 45 degrees of the Player, starting from
 *   the top and moving clockwise.</li>
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
  protected Position[] points;
  protected IMovementManageable movementManager;
  protected boolean hasPuck;
  protected boolean pokeCheck;
  protected Puck puck;

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
    this.points = calculatePoints();
    this.hasPuck = false;
    this.pokeCheck = false;
    this.movementManager = null;
    this.puck = null;
  }

  @Override
  public Position[] calculatePoints() {
   double objX = getPosition().getXCoord();
    double objY = getPosition().getYCoord();
    Position pointUp = new Position(objX, objY + radius);
    Position pointUpRight = new Position((radius * Math.cos(Math.PI / 4)) + objX,
        radius * Math.cos(Math.PI / 4) + objY);
    Position pointRight = new Position(objX + radius, objY);
    Position pointDownRight = new Position(objX + (radius * Math.cos(Math.PI / 4)),
        objY - radius * Math.cos(Math.PI / 4));
    Position pointDown = new Position(objX, objY - radius);
    Position pointDownLeft = new Position(objX - (radius * Math.cos(Math.PI / 4)),
        objY - (radius * Math.cos(Math.PI / 4)));
    Position pointLeft = new Position(objX - radius, objY);
    Position pointUpLeft = new Position(objX - (radius * Math.cos(Math.PI / 4)),
        objY + (radius * Math.cos(Math.PI / 4)));

    return new Position[]
        {pointUp, pointUpRight, pointRight, pointDownRight, pointDown, pointDownLeft, pointLeft,
        pointUpLeft};
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

  @Override
  public double getRadius() {
    return radius;
  }

  @Override
  public void setRadius(double radius) {
    this.radius = radius;
  }



}
