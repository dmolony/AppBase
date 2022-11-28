package com.bytezone.appbase;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;

// -----------------------------------------------------------------------------------//
public class BorderedDataPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final BorderStrokeStyle borderStrokeStyle = new BorderStrokeStyle (
      StrokeType.INSIDE, StrokeLineJoin.MITER, StrokeLineCap.BUTT, 10, 0, null);

  private static CornerRadii radii = new CornerRadii (10);

  private static final BorderStroke borderStroke =
      new BorderStroke (Color.BLACK, borderStrokeStyle, radii, new BorderWidths (1));

  private static Border border = new Border (borderStroke);

  private static BackgroundFill backgroundFill =
      new BackgroundFill (Color.valueOf ("e0e0e0"), radii, null);

  private static Background background = new Background (backgroundFill);

  // ---------------------------------------------------------------------------------//
  public BorderedDataPane (int columns, int rows)
  // ---------------------------------------------------------------------------------//
  {
    super (columns, rows);

    setBorder (border);
    setPadding (new Insets (5, 7, 5, 7));         // TRBL
    setBackground (background);
  }
}
