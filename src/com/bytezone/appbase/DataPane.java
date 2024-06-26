package com.bytezone.appbase;

import java.util.Collection;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

// -----------------------------------------------------------------------------------//
public abstract class DataPane extends GridPane
// -----------------------------------------------------------------------------------//
{
  static protected final Insets defaultInsets = new Insets (15, 10, 15, 10);   // TRBL

  private int rowHeight = 25;
  private int rows;
  private int columns;

  // ---------------------------------------------------------------------------------//
  public DataPane (int columns, int rows)
  // ---------------------------------------------------------------------------------//
  {
    init (columns, rows);
  }

  // ---------------------------------------------------------------------------------//
  public DataPane (int columns, int rows, int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    this.rowHeight = rowHeight;
    init (columns, rows);
  }

  // ---------------------------------------------------------------------------------//
  private void init (int columns, int rows)
  // ---------------------------------------------------------------------------------//
  {
    this.rows = rows;
    this.columns = columns;

    setHgap (8);
    setVgap (3);

    //    setGridLinesVisible (true);

    setAllRowConstraints (rows, rowHeight);           // make all rows the same height
  }

  // ---------------------------------------------------------------------------------//
  private void setAllRowConstraints (int numRows, int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    RowConstraints rowConstraints = new RowConstraints (rowHeight);

    for (int i = 0; i < numRows; i++)
      getRowConstraints ().add (rowConstraints);
  }

  // ---------------------------------------------------------------------------------//
  protected int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return rows;
  }

  // ---------------------------------------------------------------------------------//
  protected int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return columns;
  }

  // ---------------------------------------------------------------------------------//
  protected int getRowHeight ()
  // ---------------------------------------------------------------------------------//
  {
    return rowHeight;
  }

  // ---------------------------------------------------------------------------------//
  protected void setLayout (DataPane pane, int column, int row)
  // ---------------------------------------------------------------------------------//
  {
    GridPane.setConstraints (pane, column, row);
    GridPane.setColumnSpan (pane, 1);               // 0 gives an error, >1 is ignored
    GridPane.setRowSpan (pane, pane.getRows ());
  }

  // ---------------------------------------------------------------------------------//
  protected void setColumnConstraints (int... width)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < width.length; i++)
      getColumnConstraints ().add (new ColumnConstraints (width[i]));
  }

  // ---------------------------------------------------------------------------------//
  protected void setAllColumnConstraints (int colWidth)
  // ---------------------------------------------------------------------------------//
  {
    ColumnConstraints colConstraints = new ColumnConstraints (colWidth);

    for (int i = 0; i < getColumns (); i++)
      getColumnConstraints ().add (colConstraints);
  }

  // ---------------------------------------------------------------------------------//
  protected <T> ComboBox<T> createComboBox (Collection<T> list,
      ChangeListener<T> listener, DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    ComboBox<T> comboBox = new ComboBox<T> ();

    GridPane.setConstraints (comboBox, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (comboBox, dataLayout.columnSpan);

    getChildren ().add (comboBox);

    comboBox.setItems (FXCollections.observableArrayList (list));
    comboBox.setVisibleRowCount (20);
    comboBox.getSelectionModel ().selectedItemProperty ().addListener (listener);

    return comboBox;
  }

  // ---------------------------------------------------------------------------------//
  protected void createLabelsVertical (String[] labels, int column, int row,
      HPos alignment)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.length; i++)
      createLabel (labels[i], column, row + i, alignment);
  }

  // ---------------------------------------------------------------------------------//
  protected void createLabelsVertical (List<String> labels, int column, int row,
      HPos alignment)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.size (); i++)
      createLabel (labels.get (i), column, row + i, alignment);
  }

  // ---------------------------------------------------------------------------------//
  protected void createLabelsHorizontal (String[] labels, int column, int row,
      HPos alignment)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.length; i++)
      createLabel (labels[i], column + i, row, alignment);
  }

  // ---------------------------------------------------------------------------------//
  protected void createLabelsHorizontal (List<String> labels, int column, int row,
      HPos alignment)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labels.size (); i++)
      createLabel (labels.get (i), column + i, row, alignment);
  }

  // ---------------------------------------------------------------------------------//
  private void createLabel (String labelText, int column, int row, HPos alignment)
  // ---------------------------------------------------------------------------------//
  {
    Label label = new Label (labelText);

    GridPane.setConstraints (label, column, row);
    GridPane.setHalignment (label, alignment);
    GridPane.setColumnSpan (label, 1);

    getChildren ().add (label);
  }

  // ---------------------------------------------------------------------------------//
  protected Label createLabel (String labelText, int col, int row, HPos alignment,
      int span)
  // ---------------------------------------------------------------------------------//
  {
    Label label = new Label (labelText);

    GridPane.setConstraints (label, col, row);
    GridPane.setHalignment (label, alignment);
    GridPane.setColumnSpan (label, span);

    getChildren ().add (label);

    return label;
  }

  // ---------------------------------------------------------------------------------//
  protected TextField createTextField (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextField textField = new TextField ();

    GridPane.setConstraints (textField, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (textField, dataLayout.columnSpan);

    textField.setAlignment (dataLayout.alignment);
    textField.setEditable (dataLayout.modifiable);
    textField.setFocusTraversable (dataLayout.modifiable);

    getChildren ().add (textField);

    dataLayout.row++;

    return textField;
  }

  // ---------------------------------------------------------------------------------//
  protected TextField[] createTextFields (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;
    return this.createTextFields (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  protected TextField[] createTextFields (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[dataLayout.rows];

    for (int i = 0; i < dataLayout.rows; i++)
    {
      textOut[i] = new TextField ();

      GridPane.setConstraints (textOut[i], dataLayout.column, dataLayout.row + i);
      GridPane.setColumnSpan (textOut[i], dataLayout.columnSpan);

      textOut[i].setAlignment (dataLayout.alignment);
      textOut[i].setEditable (dataLayout.modifiable);
      textOut[i].setFocusTraversable (dataLayout.modifiable);

      getChildren ().add (textOut[i]);
    }

    dataLayout.column += dataLayout.columnSpan;     // next available column

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  protected TextArea createTextArea (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextArea textArea = new TextArea ();

    GridPane.setConstraints (textArea, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (textArea, dataLayout.columnSpan);
    GridPane.setRowSpan (textArea, dataLayout.rows);

    textArea.setEditable (dataLayout.modifiable);
    textArea.setFocusTraversable (dataLayout.modifiable);
    textArea.setPrefRowCount (dataLayout.rows);

    getChildren ().add (textArea);

    return textArea;
  }

  // ---------------------------------------------------------------------------------//
  protected CheckBox[] createCheckBoxes (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;           // alter the value for future calls
    return createCheckBoxes (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  protected CheckBox[] createCheckBoxes (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    CheckBox[] checkBoxes = new CheckBox[dataLayout.rows];

    for (int i = 0; i < dataLayout.rows; i++)
    {
      checkBoxes[i] = new CheckBox ();

      GridPane.setConstraints (checkBoxes[i], dataLayout.column, dataLayout.row + i);

      if (!dataLayout.modifiable)
      {
        checkBoxes[i].setDisable (true);
        checkBoxes[i].setStyle ("-fx-opacity: 1");  // make disabled checkbox look normal
        checkBoxes[i].setFocusTraversable (false);
      }

      GridPane.setHalignment (checkBoxes[i], dataLayout.hpos);
      getChildren ().add (checkBoxes[i]);
    }

    dataLayout.column += dataLayout.columnSpan;     // next available column

    return checkBoxes;
  }

  // ---------------------------------------------------------------------------------//
  protected RadioButton[] createRadioButtons (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    ToggleGroup toggleGroup = new ToggleGroup ();
    RadioButton[] radioButtons = new RadioButton[dataLayout.labels.length];

    for (int i = 0; i < dataLayout.labels.length; i++)
    {
      String label = dataLayout.labels[i];
      radioButtons[i] = new RadioButton (label);
      radioButtons[i].setToggleGroup (toggleGroup);
      GridPane.setConstraints (radioButtons[i], dataLayout.column, dataLayout.row + i);
      getChildren ().add (radioButtons[i]);
    }

    return radioButtons;
  }

  // ---------------------------------------------------------------------------------//
  protected void reset (TextField[] textOut)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < textOut.length; i++)
      setText (textOut[i], "");
  }

  // ---------------------------------------------------------------------------------//
  protected void reset (CheckBox[] checkBoxes)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < checkBoxes.length; i++)
      checkBoxes[i].setSelected (false);
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, Object object)
  // ---------------------------------------------------------------------------------//
  {
    if (object == null)
      textField.setText ("** Error **");
    else
      textField.setText (object.toString ());
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "True" : "False");
  }

  // ---------------------------------------------------------------------------------//
  protected void setTextYN (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "Yes" : "");
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, String text)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (text);
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, int value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, long value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
  }

  // ---------------------------------------------------------------------------------//
  protected String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }

  // ---------------------------------------------------------------------------------//
  protected String getText (long value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }
}
