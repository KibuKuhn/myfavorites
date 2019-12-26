package kibu.kuhn.myfavorites.prefs;

import static kibu.kuhn.myfavorites.prefs.PreferencesService.ITEMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.prefs.PreferencesService;

@ExtendWith(MockitoExtension.class)
public class PreferencesServiceTest {

  @Mock
  private Preferences preferences;

  @Spy
  private PreferencesService service;

  @Captor
  private ArgumentCaptor<String> captor;


  @Test
  public void testDeAndSerialize() {
    List<Item> expectedItems = new ArrayList<>();
    for (int i=0; i < 4; i++) {
      Item item = Item.of(Paths.get("path" + i++), i%2 == 0 ? true : false);
      item.setAlias("alias" + i);
      expectedItems.add(item);
    }

    when(service.getPreferences()).thenReturn(preferences);
    doNothing().when(preferences).put(eq(ITEMS), captor.capture());

    service.saveItems(expectedItems);

    String xmlEncoded = captor.getValue();

    when(preferences.get(ITEMS, null)).thenReturn(xmlEncoded);

    List<Item> actualItems = service.getItems();

    assertThat(actualItems).isEqualTo(expectedItems);
  }
}
