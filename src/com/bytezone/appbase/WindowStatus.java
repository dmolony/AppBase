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
  void setLocation ()
  // ---------------------------------------------------------------------------------//
  {
    this.width = stage.getWidth ();
    this.height = stage.getHeight ();
    this.x = stage.getX ();
    this.y = stage.getY ();
  }

  // ---------------------------------------------------------------------------------//
  protected void reset ()
  // ---------------------------------------------------------------------------------//
  {
    width = 0.0;
    height = 0.0;
    x = 0.0;
    y = 0.0;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    setLocation ();
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
    if (windowLocation.isEmpty ())
      reset ();
    else
    {
      String[] chunks = windowLocation.split (",");
      width = Double.parseDouble (chunks[0]);
      height = Double.parseDouble (chunks[1]);
      x = Double.parseDouble (chunks[2]);
      y = Double.parseDouble (chunks[3]);
    }

    if (width <= 0 || height <= 22 || x < 0 || y < 0)
      setWindow ();
    else
      setWindow (width, height, x, y);
  }

  // ---------------------------------------------------------------------------------//
  private void setWindow ()
  // ---------------------------------------------------------------------------------//
  {
    stage.setWidth (1000);
    stage.setHeight (600);
    stage.centerOnScreen ();
  }

  // ---------------------------------------------------------------------------------//
  private void setWindow (double width, double height, double x, double y)
  // ---------------------------------------------------------------------------------//
  {
    stage.setWidth (width);
    stage.setHeight (height);
    stage.setX (x);
    stage.setY (y);
  }
}
