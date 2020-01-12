package kibu.kuhn.myfavorites.ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Icons {

  private static Icons instance = new Icons();

  private Map<String, ImageIcon> icons = new HashMap<>();

  private Icons() {}

  public static Image getImage(String iconName) {
    return instance.getImageIcon(iconName).getImage();
  }

  public static Icon getIcon(String iconName) {
    return instance.getImageIcon(iconName);
  }

  private ImageIcon getImageIcon(String iconName) {
    var imageIcon = icons.get(iconName);
    if (imageIcon == null) {
      imageIcon = new ImageIcon(getClass().getResource("/" + iconName + ".png"), iconName);
      icons.put(iconName, imageIcon);
    }
    return imageIcon;
  }

}
