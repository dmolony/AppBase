package com.bytezone.appbase;

import java.util.prefs.Preferences;

// -----------------------------------------------------------------------------------//
public interface SaveState
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  void save (Preferences prefs);
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  void restore (Preferences prefs);
  // ---------------------------------------------------------------------------------//
}
