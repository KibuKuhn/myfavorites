package kibu.kuhn.myfavorites.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;
import javax.swing.JDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class XEventQueue extends EventQueue {

  private Consumer<Throwable> exceptionHandler;

  public XEventQueue(Consumer<Throwable> exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(XEventQueue.class);

  @Override
  protected void dispatchEvent(AWTEvent event) {
    try {
      if (checkEsc(event)) {
        doClose(event);
      }
      super.dispatchEvent(event);
    }
    catch (Throwable th) {
      LOGGER.error(th.getMessage(), th);
      exceptionHandler.accept(th);
    }
  }

  private void doClose(AWTEvent event) {
    if (!(event.getSource() instanceof JDialog)) {
      return;
    }
    
    JDialog dialog = (JDialog) event.getSource();
    WindowEvent windowEvent = new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING);
    dialog.dispatchEvent(windowEvent);
    
  }

  private boolean checkEsc(AWTEvent event) {
    if (!(event instanceof KeyEvent)) {
      return false;
    }
    
    KeyEvent ke = (KeyEvent) event;
    if (ke.getKeyCode() != KeyEvent.VK_ESCAPE) {
      return false;
    }
    
    return true;
  }
}
