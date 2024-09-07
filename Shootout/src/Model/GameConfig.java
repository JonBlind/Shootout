package Model;

/**
 * This class represents the configurable values throughout the game. This is essentially a
 * storage to easily access fields that are commonly changed or experimented with.
 */
public class GameConfig {

  public enum DIRECTION { UP, DOWN, LEFT, RIGHT};

  //Rink Dimensions
  public static final double RINK_LENGTH = 200.0;
  public static final double RINK_HEIGHT = 85.0;

  //Zone Start X-Value
  public static final double LEFT_GOAL_LINE_X = RINK_LENGTH * 0.055;
  public static final double RIGHT_GOAL_LINE_X = RINK_LENGTH * (1 - 0.055);

  // Net dimensions
  public static final double NET_LENGTH = 50.0;
  public static final double NET_SIDE_LENGTH = 30.0;
  public static final double NET_THICKNESS = 5.0;
  public static final double GOALIE_CREASE_RADIUS = 10.0;

  // Physics constants
      //MAX_VELOCITIES
  public static final double MAX_VELOCITY = 50.0;
  public static final double SKATER_MAX_VELOCITY = 50.0;
  public static final double GOALIE_MAX_VELOCITY = 25.0;
  public static final double PUCK_MAX_VELOCITY = 80.0;

      //ACCELERATION CONSTANTS
  public static final double ACCELERATION = 2.0;
  public static final double SKATER_ACCELERATION = 6.0;
  public static final double GOALIE_ACCELERATION = 5.0;

      //Generic Movement Constants
  public static final double FRICTION = 0.5;
  public static final double DAMPING = 0.98;


  //Puck Size
  public static final double PUCK_RADIUS =  4;

  //Shooting Strength Factor
  public static final double SHOT_STRENGTH_FACTOR = 8;
  public static final double POKE_CHECK_FACTOR = 10;
}
