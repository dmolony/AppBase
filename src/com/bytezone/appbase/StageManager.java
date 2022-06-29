package com.bytezone.appbase;

import java.util.prefs.Preferences;

import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class StageManager implements SaveState
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_WINDOW_LOCATION = "WindowLocation";
  private static final String PREFS_WINDOW_MAXIMIZED = "WindowMaximized";

  Stage stage;

  // ---------------------------------------------------------------------------------//
  public StageManager (Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    this.stage = stage;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    double width = stage.getWidth ();
    double height = stage.getHeight ();
    double x = stage.getX ();
    double y = stage.getY ();
    String text = String.format ("%f,%f,%f,%f", width, height, x, y);

    //    prefs.putBoolean (PREFS_WINDOW_MAXIMIZED, stage.isMaximized ());
    //    System.out.printf ("%s %s%n", stage.isMaximized (), text);

    if (width > 100 && height > 100)
    {
      prefs.put (PREFS_WINDOW_LOCATION, text);
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    //    System.out.printf ("%s %s%n", prefs.getBoolean (PREFS_WINDOW_MAXIMIZED, false),
    //        prefs.get (PREFS_WINDOW_LOCATION, ""));
    //
    //    if (prefs.getBoolean (PREFS_WINDOW_MAXIMIZED, false))
    //      stage.setMaximized (true);
    //    else
    //    {
    //      System.out.println ("Not maximised - setting location");
    String windowLocation = prefs.get (PREFS_WINDOW_LOCATION, "");
    double width = 0.0;
    double height = 0.0;
    double x = 0.0;
    double y = 0.0;

    if (!windowLocation.isEmpty ())
    {
      String[] chunks = windowLocation.split (",");
      width = Double.parseDouble (chunks[0]);
      height = Double.parseDouble (chunks[1]);
      x = Double.parseDouble (chunks[2]);
      y = Double.parseDouble (chunks[3]);
    }

    if (width <= 0 || height <= 22 || x < 0 || y < 0)
    {
      stage.setWidth (1200);
      stage.setHeight (800);
      stage.centerOnScreen ();
    }
    else
    {
      stage.setWidth (width);
      stage.setHeight (height);
      stage.setX (x);
      stage.setY (y);
    }
    //    }
  }
}
