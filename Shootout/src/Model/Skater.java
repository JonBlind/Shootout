package Model;

import Model.MovementManagers.MovementManagerSkater;

public class Skater extends Player {

  public Skater(String name, TEAM team, double radius) {
    super(name, team, radius);
    this.movementManager = new MovementManagerSkater(0, 0);
    this.angle = 0;
    this.position = new Position(500, 500);
    this.hasPuck = false;
    this.pokeCheck = false;
  }
}
