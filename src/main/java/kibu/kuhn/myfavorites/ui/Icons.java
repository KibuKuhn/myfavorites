package kibu.kuhn.myfavorites.ui;

import java.awt.Image;
import javax.swing.Icon;

public interface Icons {

  static Image getImage(String iconName) {
    return IconsFactory.get().getImage(iconName);
  }

  static Icon getIcon(String iconName) {
    return IconsFactory.get().getIcon(iconName);
  }

  static void clearCache() {
    IconsFactory.get().clearCache();
  }
}
