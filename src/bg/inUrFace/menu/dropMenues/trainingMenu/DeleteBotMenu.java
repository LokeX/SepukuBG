package bg.inUrFace.menu.dropMenues.trainingMenu;

import bg.engine.trainer.Bot;
import bg.engine.trainer.Trainer;
import bg.util.menus.Listable;
import java.util.List;
import java.util.stream.Collectors;

import static bg.Main.settings;
import static bg.util.Dialogs.confirmed;
import static bg.Main.win;

public class DeleteBotMenu implements Listable {

  private int selectedItemNr = Trainer.blackBot;

  @Override
  public void itemSelectedAction(int selectedItemNr) {

    if (confirmed("Really delete bot?", win)) {
      Trainer.bots.remove(selectedItemNr);
      if (Trainer.whiteBot >= selectedItemNr) {
        Trainer.whiteBot = 0;
      }
      if (Trainer.blackBot >= selectedItemNr) {
        Trainer.blackBot = 0;
      }
      if (settings.getWhiteBotOpponent() >= selectedItemNr) {
        settings.setWhiteBotOpponent(0);
      }
      if (settings.getBlackBotOpponent() >= selectedItemNr) {
        settings.setBlackBotOpponent(0);
      }
    }
  }

  @Override
  public int getSelectedItemNr() {

    return selectedItemNr;
  }

  @Override
  public String getMenuTitle() {

    return "Delete bot";
  }

  @Override
  public List<String> getElementTitles() {

    return Trainer.bots.stream().
      map(Bot::name).collect(Collectors.toList());
  }

}
