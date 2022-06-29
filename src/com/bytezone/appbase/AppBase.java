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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

// -----------------------------------------------------------------------------------//
public abstract class AppBase extends Application
// -----------------------------------------------------------------------------------//
{
  private static Alert alert;
  private static final String os = System.getProperty ("os.name");

  protected final Preferences prefs = getPreferences ();
  protected Stage primaryStage;
  protected final MenuBar menuBar = new MenuBar ();
  protected final BorderPane mainPane = new BorderPane ();

  protected StageManager stageManager;
  protected StatusBar statusBar;
  protected FontManager fontManager;

  protected final List<SaveState> saveStateList = new ArrayList<> ();

  protected boolean debug = false;
  Timeline clock;

  // ---------------------------------------------------------------------------------//
  abstract protected Parent createContent ();
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  abstract protected Preferences getPreferences ();
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  @Override
  public void start (Stage primaryStage) throws Exception
  // ---------------------------------------------------------------------------------//
  {
    checkParameters ();             // check for -reset and -debug

    if (os != null && os.startsWith ("Mac"))
      menuBar.setUseSystemMenuBar (true);

    this.primaryStage = primaryStage;

    stageManager = getStageManager (primaryStage);
    fontManager = getFontManager ();
    statusBar = getStatusBar ();

    mainPane.setTop (menuBar);
    mainPane.setCenter (createContent ());
    mainPane.setBottom (statusBar);

    saveStateList.add (stageManager);

    primaryStage.setScene (new Scene (mainPane));
    primaryStage.setOnCloseRequest (e -> exit ());
    primaryStage.getScene ().setOnKeyPressed (e -> keyPressed (e));

    restore ();

    // create status bar clock
    if (statusBar != null)
    {
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
    }

    //    primaryStage.sizeToScene ();      <- this screws the layout up
    primaryStage.show ();
  }

  // ---------------------------------------------------------------------------------//
  protected StageManager getStageManager (Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    return new StageManager (stage);
  }

  // ---------------------------------------------------------------------------------//
  protected FontManager getFontManager ()
  // ---------------------------------------------------------------------------------//
  {
    return new FontManager ();
  }

  // ---------------------------------------------------------------------------------//
  protected StatusBar getStatusBar ()
  // ---------------------------------------------------------------------------------//
  {
    return new StatusBar ();
  }

  // ---------------------------------------------------------------------------------//
  private void restore ()
  // ---------------------------------------------------------------------------------//
  {
    for (SaveState saveState : saveStateList)
      saveState.restore (prefs);
  }

  // ---------------------------------------------------------------------------------//
  private void exit ()
  // ---------------------------------------------------------------------------------//
  {
    for (SaveState saveState : saveStateList)
      saveState.save (prefs);

    if (clock != null)
      clock.stop ();

    Platform.exit ();
  }

  // ---------------------------------------------------------------------------------//
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  public static void showAlert (AlertType alertType, String title, String message)
  // ---------------------------------------------------------------------------------//
  {
    if (alert == null || alertType != alert.getAlertType ())
    {
      alert = new Alert (alertType);
      alert.setHeaderText (null);
    }

    alert.setTitle (title);
    alert.setContentText (message);
    alert.showAndWait ();
  }

  // ---------------------------------------------------------------------------------//
  protected void checkParameters ()
  // ---------------------------------------------------------------------------------//
  {
    for (String s : getParameters ().getUnnamed ())
    {
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
