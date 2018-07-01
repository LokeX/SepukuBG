package bg.engine.match;

import bg.engine.match.moves.Layout;
import bg.engine.match.score.MatchScore;
import bg.engine.trainer.Trainer;

public class Match {

  private MatchScore matchScore;
  private Layout matchLayout;

  public Match (Layout matchLayout, int playToScore) {

    this.matchLayout = new Layout(matchLayout);
    this.matchLayout.setUseBlackBot(Trainer.blackBot);
    this.matchLayout.setUseWhiteBot(Trainer.whiteBot);
    this.matchLayout.setPlayerID(2);
    matchScore = new MatchScore(playToScore);
  }

  public MatchScore getMatchScore() {

    return matchScore;
  }

  public Match getMatch() {

    Game game = new Game(matchLayout);

    while (!matchScore.matchOver()) {
      if (!matchScore.isCrawfordGame()) {
        game.computerHandlesCube();
      }
      if (game.gameOver()) {
        matchScore.addGameScore(game.getGameScore());
        game = new Game(matchLayout);
      } else {
        game.nextTurn();
      }
    }
    return this;
  }

}
