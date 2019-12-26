package kibu.kuhn.myfavorites.ui;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class XEventQueue extends EventQueue {

  private static final Logger LOGGER = LoggerFactory.getLogger(XEventQueue.class);

  @Override
  protected void dispatchEvent(AWTEvent event) {
    try {
      super.dispatchEvent(event);
    }
    catch (Throwable th) {
      LOGGER.error(th.getMessage(), th);
    }
  }
}
