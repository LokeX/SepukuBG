package bg.inUrFace.canvas.move;

import bg.inUrFace.canvas.Paintable;

import java.awt.*;

import static bg.Main.engineApi;
import static bg.Main.getCanvas;

public class ChequerPainter implements Paintable {

  private int playerID () {

    return
      engineApi
        .getHumanInput()
        .getPlayerID();
  }

  private int chequerSize () {

    return
      getCanvas()
        .getDimensions()
        .chequerSize;
  }

  private int position (double mousePosition) {

    return (int) mousePosition - (chequerSize() / 2);
  }

  private Color playerColor () {

    return
      playerID() == 0
        ? Color.yellow
        : Color.red;
  }

  private void drawChequer (Graphics graphics, Point mousePoint) {

    graphics.setColor(playerColor());
    graphics.fillOval(
      position(mousePoint.getX()),
      position(mousePoint.getY()),
      chequerSize(),
      chequerSize()
    );
  }

  private Point mousePosition () {

    return
      getCanvas()
        .getMousePosition();
  }

  private boolean mustPaint () {

    return
      engineApi != null
      && engineApi.getHumanInput().humanInputActive()
      && engineApi.getHumanInput().isEndingPoint()
      && mousePosition() != null;
  }

  public void paint (Graphics graphics) {

    if (mustPaint()) {
      drawChequer(graphics, mousePosition());
    }
  }

}
