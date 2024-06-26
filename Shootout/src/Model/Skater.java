package Model;


/**
 * Skater is the abstract class representing any possible unit that could be controlled in the
 * game, so only the goalie and shooter.
 * The traits that all skaters share is the posession of:
 * <ul>
 *   <li>A Name</li>
 *   <li>A color representing their team</li>
 *   <li>A RotationalDegree showing their direction</li>
 *   <li>Xcoord of their position</li>
 *   <li>ycoord of their position</li>
 *   <li>State of their movement</li>
 *   <li>Do they control the puck?</li>
 *   <li>Are they poke checking?</li>
 * </ul>
 */
public abstract class Skater {
  protected String name;
  protected TEAM_COLOR color;
  protected double rotationDegree;
  protected double xCoord;
  protected double yCoord;
  protected MovementManager movementManager;
  protected boolean hasPuck;
  protected boolean pokeCheck;

  /**
   * Possible color choices for team assignments.
   */
  public enum TEAM_COLOR { BLUE, RED, YELLOW, GREEN, ORANGE, PURPLE };

  /**
   * Constructor of a basic skater.
   * @param name Name of the skater.
   * @param color Team the skater is part of.
   * @param rotationDegree Degree representing direction.
   * @param xCoord x-coordinate of skater on the ice.
   * @param yCoord y-coordinate of skater on the ice.
   */
  public Skater(String name, TEAM_COLOR color, double rotationDegree,
                double xCoord, double yCoord) {
    this.name = name;
    this.color = color;
    this.rotationDegree = rotationDegree;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.movementManager = new MovementManager();
    this.hasPuck = false;
    this.pokeCheck = false;
  }

  public String getName() {
    return name;
  }



}
