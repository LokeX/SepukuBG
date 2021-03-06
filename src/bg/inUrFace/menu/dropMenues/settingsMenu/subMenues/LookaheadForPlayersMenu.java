package bg.inUrFace.menu.dropMenues.settingsMenu.subMenues;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static bg.Main.settings;

public class LookaheadForPlayersMenu extends JMenu {

  JRadioButtonMenuItem allPlayers =
    new JRadioButtonMenuItem("All players", settings.getLookaheadForAllPlayers());
  JRadioButtonMenuItem botsOnly =
    new JRadioButtonMenuItem("Bots only", !settings.getLookaheadForAllPlayers());
  ButtonGroup playerChoices = new ButtonGroup();

  public LookaheadForPlayersMenu () {

    super("Lookahead for");
    setupButtonGroup();
    setupAllPlayers();
    setupBotsOnly();
  }

  private void setupButtonGroup () {

    playerChoices.add(allPlayers);
    playerChoices.add(botsOnly);
  }

  private void setupAllPlayers() {

    add(allPlayers);
    allPlayers.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        settings.setLookaheadForAllPlayers(true);
      }
    });
  }

  private void setupBotsOnly() {

    add(botsOnly);
    botsOnly.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        settings.setLookaheadForAllPlayers(false);
      }
    });
  }

}
