package Model;

/**
 * This is the physics class which contains all the base physic values and equations that will
 * be utilized throughout the game. Should a certain aspect of the physics feel too slow/fast or
 * clunky, this would be the ideal location to quickly alter it.
 */
public class Physics {

  public enum DIRECTION { UP, DOWN, LEFT, RIGHT};

  public static double ACCELERATION = 2.0;
  public static double FRICTION = 0.5;
  public static double MAX_VELO = 50.0;

}
