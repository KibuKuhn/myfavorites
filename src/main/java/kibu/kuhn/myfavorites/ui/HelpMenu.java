package kibu.kuhn.myfavorites.ui;

import static java.awt.Dialog.ModalityType.APPLICATION_MODAL;
import static kibu.kuhn.myfavorites.MyFavorites.createImage;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HelpMenu {

  private static final Logger LOGGER = LoggerFactory.getLogger(HelpMenu.class);

  private JDialog dialog;
  private JEditorPane htmlPane;

  private Consumer<? super ComponentEvent> windowCloseAction;

  HelpMenu() {
    try {
      init();
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
      throw new IllegalStateException(ex);
    }
  }

  void setDialogVisible(boolean visible) {
    if (dialog == null) {
      return;
    }
    if (!visible) {
      dialog.setVisible(false);
      return;
    }

    dialog.setVisible(visible);
  }


  private void init() throws IOException {
    dialog =
        new JDialog(null, Gui.get().getBundle().getString("helpmenu.title"), APPLICATION_MODAL);

    dialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        doClose(e);
      };
    });

    dialog.setIconImage(createImage("list36_filled", "list36").getImage());
    Container pane = (JPanel) dialog.getContentPane();
    pane.setLayout(new BorderLayout());
    htmlPane = new JEditorPane();
    htmlPane.setContentType("text/html");
    htmlPane.setEditable(false);
    htmlPane.setOpaque(true);
    htmlPane.setText(getText());
    htmlPane.setCaretPosition(0);
    htmlPane.addHyperlinkListener(new HyperlinkListener() {

      @Override
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (HyperlinkEvent.EventType.ACTIVATED.toString().equals(e.getEventType().toString())) {
          URL url = e.getURL();
          openLink(url);
        }
      }
    });

    pane.add(new JScrollPane(htmlPane));

    dialog.pack();
    dialog.setSize(500, 400);
    dialog.setLocationRelativeTo(null);
  }

  private void doClose(WindowEvent e) {
    dialog.dispose();
    dialog = null;

    if (windowCloseAction == null) {
      return;
    }
    windowCloseAction.accept(e);
  }

  void setWindowCloseAction(Consumer<? super ComponentEvent> c) {
    this.windowCloseAction = c;
  }

  private String getText() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(getStream(), StandardCharsets.UTF_8));
    //@formatter:off
    String html = reader.lines()
                        .map(new ImageUrlGenerator())
                        .collect(Collectors.joining("\n"));
    //@formatter:on
    return html;
  }

  private InputStream getStream() {
    return getClass().getResourceAsStream("/" + Gui.get().getBundle().getString("help.html"));
  }

  private void openLink(URL url) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
      try {
        Desktop.getDesktop().browse(url.toURI());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        htmlPane.setText(String.format(Gui.get().getBundle().getString("HelpPane.error"), e.getLocalizedMessage()));
      }
    }
    else {
      htmlPane.setText(Gui.get().getBundle().getString("HelpPane.System.Web.Browser.not.supported"));
    }

  }

  private static class ImageUrlGenerator implements Function<String, String> {

    private static Set<String> images = new HashSet<>();
    static {
      Collections.addAll(images, "FAVORITES18", "MENU18", "HELP18", "CANCEL18");
    }


    @Override
    public String apply(String line) {
      for (String img : images) {
        if (line.contains(img)) {
          line = line.replace(img, getImgeUrl(img));
        }
      };
      return line;
    }


    private CharSequence getImgeUrl(String img) {
      String imagename = img.toLowerCase();
      return getClass().getResource("/" + imagename + ".png").toString();
    }
  }
}
