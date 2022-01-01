package com.bytezone.appbase;

import java.util.prefs.Preferences;

import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class WindowStatus implements SaveState
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_WINDOW_LOCATION = "WindowLocation";

  double width;
  double height;
  double x;
  double y;

  Stage stage;

  // ---------------------------------------------------------------------------------//
  public void setStage (Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    this.stage = stage;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    width = stage.getWidth ();
    height = stage.getHeight ();
    x = stage.getX ();
    y = stage.getY ();

    if (width > 100 && height > 100)
    {
      String text = String.format ("%f,%f,%f,%f", width, height, x, y);
      prefs.put (PREFS_WINDOW_LOCATION, text);
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String windowLocation = prefs.get (PREFS_WINDOW_LOCATION, "");

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
      stage.setWidth (1000);
      stage.setHeight (600);
      stage.centerOnScreen ();
    }
    else
    {
      stage.setWidth (width);
      stage.setHeight (height);
      stage.setX (x);
      stage.setY (y);
    }
  }
}
