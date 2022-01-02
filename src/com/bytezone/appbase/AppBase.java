package com.bytezone.appbase;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

// -----------------------------------------------------------------------------------//
public abstract class AppBase extends Application
// -----------------------------------------------------------------------------------//
{
  protected final Preferences prefs = getPreferences ();

  protected Stage primaryStage;
  protected final MenuBar menuBar = new MenuBar ();
  protected final BorderPane mainPane = new BorderPane ();
  protected WindowStatus windowStatus;
  protected StatusBar statusBar;

  protected final List<SaveState> saveStateList = new ArrayList<> ();

  protected boolean debug = false;

  abstract protected void createContent ();

  abstract protected Preferences getPreferences ();

  abstract protected WindowStatus getWindowStatus ();

  abstract protected StatusBar getStatusBar ();

  // ---------------------------------------------------------------------------------//
  @Override
  public void start (Stage primaryStage) throws Exception
  // ---------------------------------------------------------------------------------//
  {
    this.primaryStage = primaryStage;
    checkParameters ();

    windowStatus = getWindowStatus ();
    windowStatus.setStage (primaryStage);

    final String os = System.getProperty ("os.name");
    if (os != null && os.startsWith ("Mac"))
      menuBar.setUseSystemMenuBar (true);

    statusBar = getStatusBar ();

    mainPane.setTop (menuBar);
    mainPane.setBottom (statusBar);

    createContent ();
    saveStateList.add (windowStatus);

    primaryStage.setScene (new Scene (mainPane));
    primaryStage.setOnCloseRequest (e -> exit ());

    restore ();

    // create status bar clock
    Timeline clock =
        new Timeline (new KeyFrame (Duration.seconds (2), new EventHandler<ActionEvent> ()
        {
          @Override
          public void handle (ActionEvent event)
          {
            statusBar.tick ();
          }
        }));
    clock.setCycleCount (Timeline.INDEFINITE);
    clock.play ();

    primaryStage.show ();
  }

  // ---------------------------------------------------------------------------------//
  protected void restore ()
  // ---------------------------------------------------------------------------------//
  {
    for (SaveState saveState : saveStateList)
      saveState.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  protected void exit ()
  // ---------------------------------------------------------------------------------//
  {
    for (SaveState saveState : saveStateList)
      saveState.save (prefs);

    Platform.exit ();
  }

  // ---------------------------------------------------------------------------------//
  protected void checkParameters ()
  // ---------------------------------------------------------------------------------//
  {
    for (String s : getParameters ().getUnnamed ())
    {
      System.out.printf ("Parameter: %s%n", s);
      if ("-debug".equals (s))
        debug = true;
      else if ("-reset".equals (s))
        try
        {
          prefs.clear ();
          System.out.println ("* * * Preferences reset * * *");
        }
        catch (BackingStoreException e)
        {
          System.out.println ("! ! ! Preferences NOT reset ! ! !");
          e.printStackTrace ();
        }
      else
        System.out.printf ("Unknown parameter: %s%n", s);
    }
  }
}
