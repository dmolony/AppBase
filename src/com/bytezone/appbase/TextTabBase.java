package com.bytezone.appbase;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

// -----------------------------------------------------------------------------------//
public abstract class TextTabBase extends TabBase
// -----------------------------------------------------------------------------------//
{
  protected final Clipboard clipboard = Clipboard.getSystemClipboard ();
  protected final ClipboardContent content = new ClipboardContent ();
  protected final TextFlow textFlow = new TextFlow ();
  protected final ScrollPane scrollPane = new ScrollPane (textFlow);

  // ---------------------------------------------------------------------------------//
  public TextTabBase (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    valid = true;
  }

  // ---------------------------------------------------------------------------------//
  public void copyToClipboard ()
  // ---------------------------------------------------------------------------------//
  {

    StringBuilder text = new StringBuilder ();

    for (Node node : textFlow.getChildren ())
      if (node instanceof Text)
        text.append (((Text) node).getText ());

    content.putString (text.toString ());
    clipboard.setContent (content);
  }
}
