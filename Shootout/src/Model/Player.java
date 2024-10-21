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
public abstract class Player extends MobileObject {
  protected String name;
  protected TEAM team;
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
   */
  public Player(String name, TEAM team) {
    this.name = name;
    this.team = team;
    this.angle = 0;
    this.hasPuck = false;
    this.pokeCheck = false;
    this.movementManager = null;
    this.puck = null;
  }

  /**
   * Constructor with no parameters for a Player. Will be changed.
   */
  public Player() {
    this.name = "Artemi";
    this.team = TEAM.BLUE;
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.hasPuck = false;
    this.pokeCheck = false;
    this.puck = null;
  }





  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void update(double deltaTime) {
  }


  /**
   * setter method to alter pokeCheck field.
   * @param pokeCheck boolean, is this player poke checking?
   */
  public void setPokeCheck(boolean pokeCheck) {
    this.pokeCheck = pokeCheck;
  }




}
