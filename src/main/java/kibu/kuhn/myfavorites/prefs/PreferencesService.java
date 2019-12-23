package kibu.kuhn.myfavorites.prefs;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import kibu.kuhn.myfavorites.MyFavorites;
import kibu.kuhn.myfavorites.domain.Item;

public class PreferencesService {


  private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesService.class);

  static final String LOCALE = "locale";
  static final String LAF = "laf";
  static final String ITEMS = "items";
  private static final String CLEAN = "clean";

  private static PreferencesService service = new PreferencesService();

   public static PreferencesService get() {
     return service;
   }

   PreferencesService() {
     if (System.getProperty(CLEAN) != null) {
       try {
        getPreferences().clear();
        LOGGER.info("Preferences cleaned");
      } catch (BackingStoreException e) {
        throw new IllegalStateException("Cannot delete preferences");
      }
     }
   }


  Preferences getPreferences() {
    return Preferences.userNodeForPackage(MyFavorites.class);
  }

  @SuppressWarnings("exports")
  public void saveItems (List<Item> items) {
    List<StorableItem> list = items.stream().map(StorableItem::new).collect(Collectors.toList());
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      XMLEncoder encoder = new XMLEncoder(out);
      encoder.writeObject(new ItemPrefs(list));
      encoder.flush();
      encoder.close();
      out.close();
      byte[] bytes = out.toByteArray();
      getPreferences().put(ITEMS, new String(bytes, StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("exports")
  public  List<Item> getItems() {
    String items = getPreferences().get(ITEMS, null);
    if (items == null) {
      return Collections.emptyList();
    }
    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(items.getBytes(StandardCharsets.UTF_8)));
    ItemPrefs prefs = (ItemPrefs) decoder.readObject();
    decoder.close();
    return prefs.getItems().stream().map(si -> Item.of(Paths.get(si.path), si.file)).collect(Collectors.toList());
  }

  public static class ItemPrefs {
    List<StorableItem> items;

    private ItemPrefs(List<StorableItem> items) {
      this.items = items;
    }

    public ItemPrefs() {}

    public List<StorableItem> getItems() {
      return items;
    }

    public void setItems(List<StorableItem> items) {
      this.items = items;
    }


  }

  public static class StorableItem {

    private String path;
    private boolean file;

    public StorableItem() {}

    private StorableItem(Item item) {
      this.path = item.getPath().toString();
      this.file = item.isFile();
    }


    public String getPath() {
      return path;
    }


    public void setPath(String path) {
      this.path = path;
    }


    public boolean isFile() {
      return file;
    }


    public void setFile(boolean file) {
      this.file = file;
    }
  }

  @SuppressWarnings("exports")
  public void saveLaf(LookAndFeelInfo laf) {
    getPreferences().put(LAF, laf.getClassName());

  }

  public void saveLocale(Locale locale) {
    getPreferences().put(LOCALE, locale.getLanguage());
  }

  @SuppressWarnings("exports")
  public LookAndFeelInfo getLaf() {
    String laf = getPreferences().get(LAF, UIManager.getSystemLookAndFeelClassName());
    //@formatter:off
    return Arrays.stream(UIManager.getInstalledLookAndFeels())
                 .filter(lf -> lf.getClassName().equals(laf))
                 .findFirst()
                 .orElseThrow(() -> new IllegalStateException("Cannot find Look and Feel " + laf));
    //@formatter:on
  }

  public Locale getLocale() {
    String locale = getPreferences().get(LOCALE, null);
    if (locale == null) {
      return Locale.getDefault();
    }

    return Locale.forLanguageTag(locale);
  }
}
