package bg.inUrFace.canvas.move;

import bg.Main;
import bg.inUrFace.canvas.TextArea;
import bg.inUrFace.mouse.MoveInput;
import bg.engine.moves.Layout;
import bg.engine.moves.EvaluatedMove;
import bg.engine.moves.MoveLayout;

import java.util.List;

import static bg.Main.*;
import static bg.util.ThreadUtil.threadSleep;

public class MoveOutput extends MoveLayout {

  EvaluatedMove evaluatedMove;

  public MoveOutput () {

  }

  public MoveOutput (EvaluatedMove evaluatedMove) {

    super(evaluatedMove);
    this.evaluatedMove = evaluatedMove;
  }

  public void showMove (final int startPoint, final int endPoint, final Object notifier) {

    new Thread () {

      @Override
      public void run () {

        int errorCorrectedEndPoint = endPoint < movePoints2.length ? endPoint : movePoints2.length-1;
        int[] tempPoint = point.clone();

        point = parentLayout.point.clone();
        for (int a = 0; a <= errorCorrectedEndPoint; a++) {
          if (movePoints2[a] >= 0) {
            if (hitPoints[a] >= 0) {
              if ((a+2)%2 == 0) {
                point[hitPoints[a]]--;
              } else {
                point[hitPoints[a]]++;
              }
            }
            if ((a+2)%2 == 0) {
              point[movePoints2[a] == 0 && playerID == 1 ? 25 : movePoints2[a]]--;
            } else {
              point[movePoints2[a] == 25 && playerID == 1 ? 0 : movePoints2[a]]++;
            }
            if (a >= startPoint) {
              displayLayout();
              Main.sound.playSoundEffect("Blop-Mark_DiAngelo");
              threadSleep(settings.getShowMoveDelay());
            }
          }
        }
        point = tempPoint.clone();
        synchronized (notifier) {

          notifier.notifyAll();
        }
      }
    }.start();
  }

  public void showMove () {

    showMove (0);
  }

  public void showMove (int startPoint) {

    showMove (startPoint,getMovePoints2().length);
  }

  public void showMove (int startPoint, int endPoint) {

    showMove (startPoint, endPoint, new Object());
  }

  public void displayLayout() {

    win.canvas.
      setDisplayedLayout(
        getPlayerID() == 0 ?
          new Layout(this) :
          new Layout(this).getFlippedLayout()
      );
  }

  public void writeMove () {

    TextArea text = getTextArea();

    text.clear();
    text.nlWrite("Moves matchLayout information:");
    text.nlWrite("Player: " + getPlayerTitle());
    text.nlWrite("Turn: #" + (matchApi.getSelectedTurn().getTurnNr() + 1));
    text.nlWrite("Dice cast: ");
    for (int a = 0; a < getDice().length; a++) {
      text.write(getDice()[a] + ",");
    }
    text.nlWrite("");
    text.write("Legal move:  #" + (matchApi.getSelectedMoveNr()+1) + "/" + matchApi.getSelectedTurn().getNrOfMoves() + ":  ");
    for (int a = 0; a < getMovePoints().length; a++) {
//      win.textArea.write(Integer.toString(playerID == 0 ? partMovePoints[a] : 25 - partMovePoints[a]) + ", ");
      text.write(Integer.toString(getMovePoints()[a]) + ", ");
    }
    text.nlWrite("");

/*
    if (moveLayout.i.isWinningMove()) {
      win.text.nlWrite(playerTitle[playerID] + " player is the winner");
    }
*/
  }

  public void outputCustomMove (MoveInput moveInput) {

    int turnNr = matchApi.getSelectedTurn().getTurnNr();
    String playerTitle = matchApi.getSelectedTurn().getPlayerTitle();
    int[] rolledDice = matchApi.getSelectedTurn().getDice();
    int[] movePoints = moveInput.getMovePoints();
    Layout customLayout = moveInput.getCustomMoveLayout();
    TextArea text = getTextArea();

    win.canvas.setDisplayedLayout(new Layout(
      moveInput.getPlayerID() == 0 ? customLayout : customLayout)
    );
    text.clear();
    text.nlWrite("Custom move input: ");
    text.nlWrite("Player: " + playerTitle);
    text.nlWrite("Turn: #" + (turnNr + 1));
    text.nlWrite("Dice rolled: ");
    for (int a = 0; a < rolledDice.length; a++) {
      text.write(rolledDice[a] + ",");
    }
    text.nlWrite("");
    text.write("Moves: ");
    for (int a = 0; a < movePoints.length; a++) {
      text.write(movePoints[a] + ",");
    }
  }

  public void writeMoveBonuses() {

    final String[] bonusHeaders = new String[] {

      "Listing both players bonuses:",
      "Listing moving players bonuses:",
      "Listing opponents bonuses:",
      "Press T to toggle bonus views",
    };
    TextArea text = getTextArea();

    evaluatedMove.initBonusValues();

    List<String> bonusTexts = evaluatedMove.getBonusTexts();
    List<Integer> bonusValues = evaluatedMove.getBonusValues();
    List<Integer> foeValues = evaluatedMove.getFoeValues();

    text.nlWrite("");
    text.nlWrite(bonusHeaders[settings.getBonusDisplayMode()]);
    for (int a = 0; a < bonusTexts.size()-1; a++) {
      if (settings.getBonusDisplayMode() < 3) {
        text.nlWrite(bonusTexts.get(a)+": ");
        if (settings.getBonusDisplayMode() == 2) {
          text.write(foeValues.get(a) >= 0 ? Integer.toString(foeValues.get(a)) : "N/A");
        } else if (settings.getBonusDisplayMode() == 1) {
          text.write(Integer.toString(bonusValues.get(a)));
        } else {
          text.write(bonusValues.get(a)+" / "+(foeValues.get(a) >= 0 ? foeValues.get(a) : "N/A"));
        }
      }
    }
    text.setMenuFromLine(bonusTexts.get(0));
  }

  public void outputMove() {

    displayLayout();
    writeMove();
    writeMoveBonuses();
  }

}