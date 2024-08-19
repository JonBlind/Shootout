package Model.MovementManagers;

import Model.IMobileObject;
import Model.Puck;

public class MovementManagerPuck extends MovementManager {
  private double angle;

  public MovementManagerPuck(Puck puck) {
    super(puck);
  }

}
