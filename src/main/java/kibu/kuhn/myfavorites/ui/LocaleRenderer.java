package kibu.kuhn.myfavorites.ui;

import java.awt.Component;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.MyFavorites;

class LocaleRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(LocaleRenderer.class);

  private static Map<String, Icon> icons = new HashMap<>();

  @Override
  public Component getListCellRendererComponent(JList<? extends Object> list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {

    Locale locale = (Locale) value;
    Component component = super.getListCellRendererComponent(list, locale.getDisplayLanguage(), index, isSelected,
        cellHasFocus);
    Icon icon = getIcon(locale);
    setIcon(icon);
    return component;
  }

  private Icon getIcon(Locale locale) {
    String iconname = locale.getLanguage() + 25;
    if (icons.containsKey(iconname)) {
      return icons.get(iconname);
    }

    Icon icon = null;
    try {
      icon = IGui.get().createImage(iconname, "");
    } catch (Exception ex) {
      LOGGER.warn("Cannot load icon {}", iconname);
    } finally {
      icons.put(iconname, icon);
    }
    return icon;
  }

}
