package Model;

import Model.MovementManagers.MovementManagerSkater;
import Model.Rink;
import java.util.ArrayList;

public class Skater extends Player {

  public Skater(String name, TEAM team, double radius) {
    super(name, team, radius);
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.position = new Position(500, 500);
    this.points = calculatePoints();
    this.hasPuck = false;
    this.pokeCheck = false;
    this.puck = null;
  }


  /**
   * Grab the status of mobile points for this skater, and create an ArrayList. The ArrayList
   * holds only the indexes that returned true from the method:
   * .isMobileObjectTouchingBoards.
   * Essentially, every returned number represents what 45 degree point of this Skater has breached
   * or made contact with the boards of the rink, where the top-most point is the number 0, and
   * it increments clockwise.
   * @return ArrayList of integers where each integer represents what mobile point has breached or
   * made contact with the boards. Top most point is 0 and increments clockwise. If the output
   * is simply an empty ArrayList, then nothing has made contact.
   */
  public ArrayList<Integer> grabStatusOfSkaterMobilePointsInRink() {
    //If the rink detects that the skater is conflicting with the boards.
    if (Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT)
        .isMobileObjectTouchingBoards(this)) {

      //Grab the status of each mobile point of this skater
      Boolean[] status = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT)
          .locationStatusOfAllMobilePoints(this);
      ArrayList<Integer> output = new ArrayList<Integer>();

      //For the output, an arrayList of integers, append the index of each status point that is
      //conflicting with the boards.
      for (int i = 0; i < status.length; i++) {
        if (status[i] = true) {
          output.add(i);
        }
      }
      return output;
    }
    else {
      return new ArrayList<Integer>();
    }
  }

  /**
   * Method to call to constantly check if the Skater is found to have been colliding with the
   * boards.
   */
  protected void handleBoardReflection() {
    ArrayList<Integer> pointStatus = grabStatusOfSkaterMobilePointsInRink();
    if (!pointStatus.isEmpty()) {
      this.movementManager.handleRinkReflection(pointStatus);
    }
  }


  /**
   * Method to handle Skater initiating a poke check.
   */
  public void pokeCheck() {

  }

  public void shoot() {}

  /**
   * Method to handle a skater colliding with another skater.
   * @param skater Other skater that this skater is colliding with.
   */
  public void handleSkaterOnSkaterCollision(Skater skater) {

  }
}
