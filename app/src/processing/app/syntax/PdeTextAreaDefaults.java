/* -*- mode: java; c-basic-offset: 2; indent-tabs-mode: nil -*- */

/*
  PdeTextAreaDefaults - grabs font/color settings for the editor
  Part of the Processing project - http://processing.org

  Copyright (c) 2004-06 Ben Fry and Casey Reas
  Copyright (c) 2001-03 Massachusetts Institute of Technology

  This program is free software; you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation; either version 2 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program; if not, write to the Free Software Foundation,
  Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package processing.app.syntax;

import processing.app.*;


public class PdeTextAreaDefaults extends TextAreaDefaults {

  public PdeTextAreaDefaults(Mode mode) {
    inputHandler = new DefaultInputHandler();
    //inputHandler.addDefaultKeyBindings();  // 0122

    // Use option on mac for text edit controls that are ctrl on Windows/Linux.
    // (i.e. ctrl-left/right is option-left/right on OS X)
    String mod = Base.isMacOS() ? "A" : "C";

    // right now, ctrl-up/down is select up/down, but mod should be
    // used instead, because the mac expects it to be option(alt)

    inputHandler.addKeyBinding("BACK_SPACE", InputHandler.BACKSPACE);
    // for 0122, shift-backspace is delete, for 0176, it's now a preference,
    // to prevent holy warriors from attacking me for it.
    if (Preferences.getBoolean("editor.keys.shift_backspace_is_delete")) {
      inputHandler.addKeyBinding("S+BACK_SPACE", InputHandler.DELETE);
    } else {
      // Made the default for 0215, deemed better for our audience.
      inputHandler.addKeyBinding("S+BACK_SPACE", InputHandler.BACKSPACE);
    }

    inputHandler.addKeyBinding("DELETE", InputHandler.DELETE);
    inputHandler.addKeyBinding("S+DELETE", InputHandler.DELETE);

    // the following two were changed for 0122 for better mac/pc compatability
    inputHandler.addKeyBinding(mod + "+BACK_SPACE", InputHandler.BACKSPACE_WORD);  // 0122
    inputHandler.addKeyBinding(mod + "S+BACK_SPACE", InputHandler.BACKSPACE_WORD);  // 0215
    inputHandler.addKeyBinding(mod + "+DELETE", InputHandler.DELETE_WORD);  // 0122
    inputHandler.addKeyBinding(mod + "S+DELETE", InputHandler.DELETE_WORD);  // 0215

    // handled by listener, don't bother here
    //inputHandler.addKeyBinding("ENTER", InputHandler.INSERT_BREAK);
    //inputHandler.addKeyBinding("TAB", InputHandler.INSERT_TAB);

    inputHandler.addKeyBinding("INSERT", InputHandler.OVERWRITE);

    // http://dev.processing.org/bugs/show_bug.cgi?id=162
    // added for 0176, though the bindings do not appear relevant for osx
    if (Preferences.getBoolean("editor.keys.alternative_cut_copy_paste")) {
      inputHandler.addKeyBinding("C+INSERT", InputHandler.CLIPBOARD_COPY);
      inputHandler.addKeyBinding("S+INSERT", InputHandler.CLIPBOARD_PASTE);
      inputHandler.addKeyBinding("S+DELETE", InputHandler.CLIPBOARD_CUT);
    }

    // disabling for 0122, not sure what this does
    //inputHandler.addKeyBinding("C+\\", InputHandler.TOGGLE_RECT);

    // for 0122, these have been changed for better compatibility
    // HOME and END now mean the beginning/end of the document
    // for 0176 changed this to a preference so that the Mac OS X people
    // can get the "normal" behavior as well if they prefer.
    if (Preferences.getBoolean("editor.keys.home_and_end_travel_far")) {
      inputHandler.addKeyBinding("HOME", InputHandler.DOCUMENT_HOME);
      inputHandler.addKeyBinding("END", InputHandler.DOCUMENT_END);
      inputHandler.addKeyBinding("S+HOME", InputHandler.SELECT_DOC_HOME);
      inputHandler.addKeyBinding("S+END", InputHandler.SELECT_DOC_END);
    } else {
      // for 0123 added the proper windows defaults
      inputHandler.addKeyBinding("HOME", InputHandler.HOME);
      inputHandler.addKeyBinding("END", InputHandler.END);
      inputHandler.addKeyBinding("S+HOME", InputHandler.SELECT_HOME);
      inputHandler.addKeyBinding("S+END", InputHandler.SELECT_END);
      inputHandler.addKeyBinding("C+HOME", InputHandler.DOCUMENT_HOME);
      inputHandler.addKeyBinding("C+END", InputHandler.DOCUMENT_END);
      inputHandler.addKeyBinding("CS+HOME", InputHandler.SELECT_DOC_HOME);
      inputHandler.addKeyBinding("CS+END", InputHandler.SELECT_DOC_END);
    }

    if (Base.isMacOS()) {
      // Additional OS X key bindings added for 0215.
      // Also note that two more are added above and marked 0215.
      // http://code.google.com/p/processing/issues/detail?id=1354
      // Could not find a proper Apple guide, but a partial reference is here:
      // http://guides.macrumors.com/Keyboard_shortcuts&section=10#Text_Shortcuts

      // control-A  move to start of current paragraph
      inputHandler.addKeyBinding("C+A", InputHandler.HOME);
      inputHandler.addKeyBinding("CS+A", InputHandler.SELECT_HOME);
      // control-E  move to end of current paragraph
      inputHandler.addKeyBinding("C+E", InputHandler.END);
      inputHandler.addKeyBinding("CS+E", InputHandler.SELECT_END);

      // control-D  forward delete
      inputHandler.addKeyBinding("C+D", InputHandler.DELETE);

      // control-B  move left one character
      inputHandler.addKeyBinding("C+B", InputHandler.PREV_CHAR);
      inputHandler.addKeyBinding("CS+B", InputHandler.SELECT_PREV_CHAR);
      // control-F  move right one character
      inputHandler.addKeyBinding("C+F", InputHandler.NEXT_CHAR);
      inputHandler.addKeyBinding("CS+F", InputHandler.SELECT_NEXT_CHAR);

      // control-H  delete (just ASCII for backspace)
      inputHandler.addKeyBinding("C+H", InputHandler.BACKSPACE);

      // control-N  move down one line
      inputHandler.addKeyBinding("C+N", InputHandler.NEXT_LINE);
      inputHandler.addKeyBinding("CS+N", InputHandler.SELECT_NEXT_LINE);
      // control-P  move up one line
      inputHandler.addKeyBinding("C+P", InputHandler.PREV_LINE);
      inputHandler.addKeyBinding("CS+P", InputHandler.SELECT_PREV_LINE);

      // might be nice, but no handlers currently available
      // control-O  insert new line after cursor
      // control-T  transpose (swap) two surrounding character
      // control-V  move to end, then left one character
      // control-K  delete remainder of current paragraph
      // control-Y  paste text previously deleted with control-K
    }

    if (Base.isMacOS()) {
      inputHandler.addKeyBinding("M+LEFT", InputHandler.HOME);
      inputHandler.addKeyBinding("M+RIGHT", InputHandler.END);
      inputHandler.addKeyBinding("MS+LEFT", InputHandler.SELECT_HOME); // 0122
      inputHandler.addKeyBinding("MS+RIGHT", InputHandler.SELECT_END);  // 0122
    } else {
      inputHandler.addKeyBinding("C+LEFT", InputHandler.HOME);  // 0122
      inputHandler.addKeyBinding("C+RIGHT", InputHandler.END);  // 0122
      inputHandler.addKeyBinding("CS+HOME", InputHandler.SELECT_HOME); // 0122
      inputHandler.addKeyBinding("CS+END", InputHandler.SELECT_END);  // 0122
    }

    inputHandler.addKeyBinding("PAGE_UP", InputHandler.PREV_PAGE);
    inputHandler.addKeyBinding("PAGE_DOWN", InputHandler.NEXT_PAGE);
    inputHandler.addKeyBinding("S+PAGE_UP", InputHandler.SELECT_PREV_PAGE);
    inputHandler.addKeyBinding("S+PAGE_DOWN", InputHandler.SELECT_NEXT_PAGE);

    inputHandler.addKeyBinding("LEFT", InputHandler.PREV_CHAR);
    inputHandler.addKeyBinding("S+LEFT", InputHandler.SELECT_PREV_CHAR);
    inputHandler.addKeyBinding(mod + "+LEFT", InputHandler.PREV_WORD);
    inputHandler.addKeyBinding(mod + "S+LEFT", InputHandler.SELECT_PREV_WORD);
    inputHandler.addKeyBinding("RIGHT", InputHandler.NEXT_CHAR);
    inputHandler.addKeyBinding("S+RIGHT", InputHandler.SELECT_NEXT_CHAR);
    inputHandler.addKeyBinding(mod + "+RIGHT", InputHandler.NEXT_WORD);
    inputHandler.addKeyBinding(mod + "S+RIGHT", InputHandler.SELECT_NEXT_WORD);

    inputHandler.addKeyBinding("UP", InputHandler.PREV_LINE);
    inputHandler.addKeyBinding(mod + "+UP", InputHandler.PREV_LINE);  // p5
    inputHandler.addKeyBinding("S+UP", InputHandler.SELECT_PREV_LINE);
    inputHandler.addKeyBinding("DOWN", InputHandler.NEXT_LINE);
    inputHandler.addKeyBinding(mod + "+DOWN", InputHandler.NEXT_LINE);  // p5
    inputHandler.addKeyBinding("S+DOWN", InputHandler.SELECT_NEXT_LINE);

    inputHandler.addKeyBinding("MS+UP", InputHandler.SELECT_DOC_HOME);
    inputHandler.addKeyBinding("CS+UP", InputHandler.SELECT_DOC_HOME);
    inputHandler.addKeyBinding("MS+DOWN", InputHandler.SELECT_DOC_END);
    inputHandler.addKeyBinding("CS+DOWN", InputHandler.SELECT_DOC_END);

    inputHandler.addKeyBinding(mod + "+ENTER", InputHandler.REPEAT);

    document = new SyntaxDocument();
//    editable = true;

    // Set to 0 for revision 0215 because it causes strange jumps
    // http://code.google.com/p/processing/issues/detail?id=1055
    electricScroll = 0;

    caretVisible = true;
    caretBlinks = Preferences.getBoolean("editor.caret.blink");
    blockCaret = Preferences.getBoolean("editor.caret.block");
    cols = 80;
    // Set the number of rows lower to avoid layout badness with large fonts
    // http://code.google.com/p/processing/issues/detail?id=1275
    rows = 5;

    /*
    String fontFamily = Preferences.get("editor.font.family");
    int fontSize = Preferences.getInteger("editor.font.size");
    plainFont = new Font(fontFamily, Font.PLAIN, fontSize);
    boldFont = new Font(fontFamily, Font.BOLD, fontSize);
    antialias = Preferences.getBoolean("editor.antialias");
     */

    fgcolor = mode.getColor("editor.fgcolor");
    bgcolor = mode.getColor("editor.bgcolor");

    styles = new SyntaxStyle[Token.ID_COUNT];

    styles[Token.COMMENT1] = mode.getStyle("comment1");
    styles[Token.COMMENT2] = mode.getStyle("comment2");

    styles[Token.KEYWORD1] = mode.getStyle("keyword1");
    styles[Token.KEYWORD2] = mode.getStyle("keyword2");
    styles[Token.KEYWORD3] = mode.getStyle("keyword3");
    styles[Token.KEYWORD4] = mode.getStyle("keyword4");
    styles[Token.KEYWORD5] = mode.getStyle("keyword5");
    styles[Token.KEYWORD6] = mode.getStyle("keyword6");

    styles[Token.FUNCTION1] = mode.getStyle("function1");
    styles[Token.FUNCTION2] = mode.getStyle("function2");
    styles[Token.FUNCTION3] = mode.getStyle("function3");
    styles[Token.FUNCTION4] = mode.getStyle("function4");

    styles[Token.LITERAL1] = mode.getStyle("literal1");
    styles[Token.LITERAL2] = mode.getStyle("literal2");

    styles[Token.LABEL] = mode.getStyle("label");
    styles[Token.OPERATOR] = mode.getStyle("operator");

    // area that's not in use by the text (replaced with tildes)
    styles[Token.INVALID] = mode.getStyle("invalid");

    caretColor = mode.getColor("editor.caret.color");
    selectionColor = mode.getColor("editor.selection.color");
    lineHighlight = mode.getBoolean("editor.linehighlight");
    lineHighlightColor = mode.getColor("editor.linehighlight.color");
    bracketHighlight = mode.getBoolean("editor.brackethighlight");
    bracketHighlightColor = mode.getColor("editor.brackethighlight.color");
    eolMarkers = mode.getBoolean("editor.eolmarkers");
    eolMarkerColor = mode.getColor("editor.eolmarkers.color");
  }
}
