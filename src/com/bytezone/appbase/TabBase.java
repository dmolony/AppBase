package com.bytezone.appbase;

import java.util.prefs.Preferences;

import com.bytezone.appbase.SaveState;

import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
abstract class TabBase extends Tab implements SaveState
// -----------------------------------------------------------------------------------//
{
  final KeyCode keyCode;
  Font font;
  boolean active;
  boolean valid;

  // ---------------------------------------------------------------------------------//
  public TabBase (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title);

    this.keyCode = keyCode;
  }

  // ---------------------------------------------------------------------------------//
  void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
  }

  // ---------------------------------------------------------------------------------//
  void refresh ()
  // ---------------------------------------------------------------------------------//
  {
    valid = false;        // force an update (when next active)
    if (active)
    {
      update ();
      assert valid == true;
      //      valid = true;       // in case update() forgets to do it
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  abstract void update ();
  // ---------------------------------------------------------------------------------//
}
