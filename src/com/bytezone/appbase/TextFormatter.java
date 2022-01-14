package com.bytezone.appbase;

import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

// -----------------------------------------------------------------------------------//
public interface TextFormatter
// -----------------------------------------------------------------------------------//
{
  public List<Text> format (String line);

  public List<Text> format (List<String> lines);

  public void setFont (Font font);
}
