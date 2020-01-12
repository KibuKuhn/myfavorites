package kibu.kuhn.myfavorites.ui.xport;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.SettingsMenu;

public class XPortPane extends JPanel {

  private static final String VISIBLE = "visible";
  private static final String INVISIBLE = "invisible";
  public static final int HEIGHT = 400;
  private static final long serialVersionUID = 1L;
  private JTabbedPane tabbedPane;

  public XPortPane(SettingsMenu settingsMenu) {
    init(settingsMenu);
  }

  private void init(SettingsMenu settingsMenu) {
    setPreferredSize(new Dimension(0, HEIGHT));
    var layout = new CardLayout();
    setLayout(layout);
    add(new JPanel(), INVISIBLE);
    tabbedPane = new JTabbedPane();
    add(tabbedPane, VISIBLE);
    tabbedPane.addTab(IGui.get().getI18n("xportpane.export.title"), new ExportPane(settingsMenu));
    tabbedPane.addTab(IGui.get().getI18n("xportpane.import.title"), new ImportPane());
  }

  @Override
  public void setVisible(boolean visible) {
    ((CardLayout)this.getLayout()).show(this, visible ? VISIBLE : INVISIBLE);
  }


}
