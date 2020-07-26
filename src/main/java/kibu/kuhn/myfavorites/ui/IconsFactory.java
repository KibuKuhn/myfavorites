package kibu.kuhn.myfavorites.ui;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import kibu.kuhn.myfavorites.prefs.IPreferencesService;

class IconsFactory {

  private static IconsFactory instance = new IconsFactory();

  private Map<String, ImageIcon> icons = new HashMap<>();

  private IPreferencesService preferencesService;

  private IconsFactory() {
    preferencesService = IPreferencesService.get();
  }

  public Image getImage(String iconName) {
    return instance.getImageIcon(iconName).getImage();
  }

  public Icon getIcon(String iconName) {
    return instance.getImageIcon(iconName);
  }
  
  public void clearCache() {
    
  }

  private ImageIcon getImageIcon(final String iconName) {
    var imageIcon = icons.get(iconName);
    if (imageIcon == null) {
      var name = iconName;
      if (preferencesService.isDarkMode()) {
        name = iconName + "-inv"; 
      }
      var resource = getClass().getResource("/" + name + ".png");
      if (resource == null && preferencesService.isDarkMode()) {
        name =iconName;
        resource = getClass().getResource("/" + name + ".png");
      }
      imageIcon = new ImageIcon(resource, name);
      icons.put(iconName, imageIcon);
    }
    return imageIcon;
  }

  static IconsFactory get() {
    return instance;
  }

}
