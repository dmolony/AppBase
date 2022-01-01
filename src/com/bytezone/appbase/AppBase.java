package com.bytezone.appbase;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public abstract class AppBase extends Application
// -----------------------------------------------------------------------------------//
{
  protected static final String PREFS_WINDOW_LOCATION = "WindowLocation";
  protected final Preferences prefs = getPreferences ();

  protected Stage primaryStage;
  protected final MenuBar menuBar = new MenuBar ();
  protected final BorderPane mainPane = new BorderPane ();
  protected WindowStatus windowStatus;

  protected final List<SaveState> saveStateList = new ArrayList<> ();

  protected boolean debug = false;

  abstract protected void createContent ();

  abstract protected Preferences getPreferences ();

  abstract protected WindowStatus getWindowStatus ();

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

    mainPane.setTop (menuBar);
    createContent ();

    primaryStage.setScene (new Scene (mainPane));
    primaryStage.setOnCloseRequest (e -> exit ());

    restore ();

    primaryStage.show ();
  }

  // ---------------------------------------------------------------------------------//
  protected void restore ()
  // ---------------------------------------------------------------------------------//
  {
    for (SaveState saveState : saveStateList)
      saveState.restore (prefs);

    windowStatus.restore (prefs);

    if (windowStatus.width <= 0 || windowStatus.height <= 22 || windowStatus.x < 0
        || windowStatus.y < 0)
      setWindow ();
    else
      setWindow (windowStatus.width, windowStatus.height, windowStatus.x, windowStatus.y);
  }

  // ---------------------------------------------------------------------------------//
  protected void setWindow ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setWidth (1000);
    primaryStage.setHeight (600);
    primaryStage.centerOnScreen ();
  }

  // ---------------------------------------------------------------------------------//
  protected void setWindow (double width, double height, double x, double y)
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setWidth (width);
    primaryStage.setHeight (height);
    primaryStage.setX (x);
    primaryStage.setY (y);
  }

  // ---------------------------------------------------------------------------------//
  protected void exit ()
  // ---------------------------------------------------------------------------------//
  {
    windowStatus.save (prefs);

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
        catch (BackingStoreException e1)
        {
          System.out.println ("! ! ! Preferences NOT reset ! ! !");
          e1.printStackTrace ();
        }
      else
        System.out.printf ("Unknown parameter: %s%n", s);
    }
  }
}
