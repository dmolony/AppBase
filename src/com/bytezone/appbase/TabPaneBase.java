package com.bytezone.appbase;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public abstract class TabPaneBase extends TabPane                                     //
    implements FontChangeListener, SaveState
// -----------------------------------------------------------------------------------//
{
  private final String PREFS_LAST_TAB;

  private static final int TAB_WIDTH = 100;
  private final List<TabBase> tabs = new ArrayList<> ();
  private int defaultTab;
  private List<TabChangeListener> listeners = new ArrayList<> ();

  private TabBase previousTab;

  // ---------------------------------------------------------------------------------//
  public TabPaneBase (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    setSide (Side.BOTTOM);
    setTabClosingPolicy (TabClosingPolicy.UNAVAILABLE);
    setTabMinWidth (TAB_WIDTH);
    setFocusTraversable (false);

    PREFS_LAST_TAB = "lastTab" + prefsId;

    getSelectionModel ().selectedItemProperty ().addListener (this::select);
  }

  // ---------------------------------------------------------------------------------//
  private void select (ObservableValue<? extends Tab> obs, Tab prev, Tab next)
  // ---------------------------------------------------------------------------------//
  {
    if (prev != null)
    {
      ((TabBase) prev).active = false;
      previousTab = (TabBase) prev;
    }

    ((TabBase) next).active = true;
    ((TabBase) next).update ();

    assert ((TabBase) next).valid == true : "Update() did not set valid = true";

    notifyListeners (prev, next);
  }

  // ---------------------------------------------------------------------------------//
  protected void add (TabBase tab)
  // ---------------------------------------------------------------------------------//
  {
    tabs.add (tab);
    getTabs ().add (tab);
  }

  // ---------------------------------------------------------------------------------//
  protected void addAll (TabBase... tabs)
  // ---------------------------------------------------------------------------------//
  {
    for (TabBase tab : tabs)
      add (tab);
  }

  // ---------------------------------------------------------------------------------//
  public void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
    KeyCode keyCode = keyEvent.getCode ();
    for (TabBase tab : tabs)
      if (tab.keyCode == keyCode)
      {
        if (!tab.active)
          getSelectionModel ().select (tab);
        else if (previousTab != null)
          getSelectionModel ().select (previousTab);
        break;
      }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void setFont (Font font)
  // ---------------------------------------------------------------------------------//
  {
    for (TabBase tab : tabs)
      tab.setFont (font);
  }

  //--------------------------------------------------------------------------------- //
  public void setDefaultTab (int tabNo)
  //--------------------------------------------------------------------------------- //
  {
    defaultTab = tabNo;
  }

  //--------------------------------------------------------------------------------- //
  @Override
  public void restore (Preferences prefs)
  //--------------------------------------------------------------------------------- //
  {
    for (TabBase tab : tabs)
      tab.restore (prefs);

    int selectedTab = prefs.getInt (PREFS_LAST_TAB, defaultTab);

    if (selectedTab == 0)                         // 0 is already selected (java sux)
      notifyListeners (null, tabs.get (0));

    getSelectionModel ().select (selectedTab);
  }

  //--------------------------------------------------------------------------------- //
  @Override
  public void save (Preferences prefs)
  //--------------------------------------------------------------------------------- //
  {
    prefs.putInt (PREFS_LAST_TAB, getSelectionModel ().getSelectedIndex ());

    for (TabBase tab : tabs)
      tab.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners (Tab prev, Tab next)
  // ---------------------------------------------------------------------------------//
  {
    for (TabChangeListener listener : listeners)
      listener.tabChanged (prev, next);
  }

  // ---------------------------------------------------------------------------------//
  public void addTabChangeListener (TabChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }
}
