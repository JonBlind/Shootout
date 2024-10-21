package Model;

import Model.Player.TEAM;

public class GameSession {
  private static GameSession instance;
  private Player skater;
  private Goalie goalie1;
  private Goalie goalie2;
  private Net[] nets;
  private Rink rink;

  /**
   * Constrctor for a GameSession. This is what dictates, manages and creates each game scene.
   * This constructor takes in only a Player, and resembles a 1v1 vs a goalie in a shootout, where
   * the player is the Skater.
   * Goalie1 is the left goalie.
   * Goalie2 is the right goalie.
   * Net[] is an array of 2 nets. Index 0 is the left net, index 1 is the right net.
   */
  public GameSession(Player player1) {
    rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
    skater = player1;
    goalie1 = new Goalie();
    goalie2 = new Goalie();

  }

  /**
   * Constrctor for a GameSession. This is what dictates, manages and creates each game scene.
   * This constructor takes in only a Player, and resembles a 1v1 vs a goalie in a shootout, where
   * the player is the Skater.
   * Goalie1 is the left goalie.
   * Goalie2 is the right goalie.
   * Net[] is an array of 2 nets. Index 0 is the left net, index 1 is the right net.
   */
  public GameSession() {
    rink = Rink.getRinkInstance(GameConfig.RINK_LENGTH, GameConfig.RINK_HEIGHT);
    skater = new Skater();
    goalie1 = new Goalie();
    goalie2 = new Goalie();

  }

  public static GameSession getInstance() {
    if (instance == null) {
      instance = new GameSession();
    }
    return instance;
  }

  /**
   * Get the Left-Side Goalie in this session.
   * @return goalie1, left-goalie of this instance.
   */
  public Goalie getSessionLeftGoalie() {
    return goalie1;
  }

  /**
   * Get the Right-Side Goalie in this session.
   * @return goalie1, Right-goalie of this instance.
   */
  public Goalie getSessionRightGoalie() {
    return goalie2;
  }

  /**
   * Get both the net instances in this session.
   * @return Array of nets. Array of 2 nets, index 0 is left net, index 1 is right net.
   */
  public Net[] getSessionNets() {
    return nets;
  }

  public Rink getSessionRink() {
    return rink;
  }
}


