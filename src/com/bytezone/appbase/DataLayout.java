package com.bytezone.appbase;

import javafx.geometry.HPos;
import javafx.geometry.Pos;

// -----------------------------------------------------------------------------------//
public class DataLayout
// -----------------------------------------------------------------------------------//
{
  int column;
  int row;
  int rows;
  Pos alignment;
  HPos hpos = HPos.CENTER;
  int columnSpan;
  String[] labels;
  boolean modifiable;

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, int rows, Pos alignment, boolean modifiable)
  // ---------------------------------------------------------------------------------//
  {
    this (column, row, rows, alignment, 1, modifiable);
  }

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, int rows, HPos alignment, boolean modifiable)
  // ---------------------------------------------------------------------------------//
  {
    this (column, row, rows, Pos.CENTER_LEFT, 1, modifiable);
    hpos = alignment;
  }

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, int rows, Pos alignment, int columnSpan,
      boolean modifiable)
  // ---------------------------------------------------------------------------------//
  {
    this.column = column;
    this.row = row;
    this.rows = rows;
    this.alignment = alignment;
    this.columnSpan = columnSpan;
    this.modifiable = modifiable;
  }

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, String[] labels)
  // ---------------------------------------------------------------------------------//
  {
    this.column = column;
    this.row = row;
    this.labels = labels;
  }

  // ---------------------------------------------------------------------------------//
  public void setColumnSpan (int columnSpan)
  // ---------------------------------------------------------------------------------//
  {
    this.columnSpan = columnSpan;
  }
}
