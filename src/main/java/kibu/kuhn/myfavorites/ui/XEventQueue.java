package kibu.kuhn.myfavorites.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.util.function.Consumer;
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
      super.dispatchEvent(event);
    }
    catch (Throwable th) {
      LOGGER.error(th.getMessage(), th);
      exceptionHandler.accept(th);
    }
  }
}
