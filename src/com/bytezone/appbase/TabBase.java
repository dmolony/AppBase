package com.bytezone.appbase;

import java.util.Objects;
import java.util.prefs.Preferences;

import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public abstract class TabBase extends Tab implements SaveState
// -----------------------------------------------------------------------------------//
{
  protected final KeyCode keyCode;
  protected Font font;
  protected boolean active;
  protected boolean valid;

  // ---------------------------------------------------------------------------------//
  public TabBase (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title);

    this.keyCode = Objects.requireNonNull (keyCode);
  }

  // ---------------------------------------------------------------------------------//
  public boolean isValid ()
  // ---------------------------------------------------------------------------------//
  {
    return valid;
  }

  // ---------------------------------------------------------------------------------//
  public void setValid (boolean valid)
  // ---------------------------------------------------------------------------------//
  {
    this.valid = valid;
  }

  // ---------------------------------------------------------------------------------//
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
  }

  // ---------------------------------------------------------------------------------//
  public void refresh ()
  // ---------------------------------------------------------------------------------//
  {
    valid = false;        // force an update (when next active)

    if (active)
    {
      update ();
      assert valid == true : "update() did not set valid = true";
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
  public abstract void update ();
  // ---------------------------------------------------------------------------------//
}
