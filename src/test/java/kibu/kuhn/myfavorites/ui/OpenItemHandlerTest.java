package kibu.kuhn.myfavorites.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import kibu.kuhn.myfavorites.domain.DesktopItem;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.ui.drop.LineMapper;

@ExtendWith(MockitoExtension.class)
class OpenItemHandlerTest {

  @Mock
  private MainMenu mainMenu;

  @Mock
  private Desktop desktop;

  @InjectMocks
  @Spy
  private OpenItemHandler openItemHandler = new OpenItemHandler(null);

  @Captor
  private ArgumentCaptor<URI> uriCaptor;

  @Captor
  private ArgumentCaptor<String> stringCaptor;

  @Test
  public void testMapperOK() {

    List<String> list = new ArrayList<>();
    list.add("k1=v1");
    list.add("k2=v2");

    Map<String, String> map = list.stream().collect(new LineMapper());

    assertThat(map).containsOnlyKeys(new String[] {"k1", "k2"});
    assertThat(map.get("k1")).isEqualTo("v1");
    assertThat(map.get("k2")).isEqualTo("v2");
  }

  @Test
  public void testMapperFail() {

    List<String> list = new ArrayList<>();
    list.add("k1=v1");
    list.add("k1=v2");

    //@formatter:off
    assertThatThrownBy(() -> list.stream().collect(new LineMapper()))
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("Duplicate key k1");
    //@formatter:on
  }

  @Test
  public void testUrl() throws URISyntaxException, IOException {
    when(openItemHandler.getDesktop()).thenReturn(desktop);
    doNothing().when(desktop).browse(uriCaptor.capture());

    FileSystemItem item = FileSystemItem.of(Paths.get(getClass().getResource("/OpenItemHandlerTest.desktop").toURI()), true);
    openItemHandler.openItem(item);

    assertThat(uriCaptor.getValue()).isEqualTo(new URL("https://kibukuhn.github.io/").toURI());
  }

  @Test
  public void testUrlFail() throws URISyntaxException, IOException {
    when(openItemHandler.getDesktop()).thenReturn(desktop);
    doNothing().when(mainMenu).setErrorText(stringCaptor.capture());

    FileSystemItem item = FileSystemItem.of(Paths.get(getClass().getResource("/OpenItemHandlerTestFail.desktop").toURI()), true);
    openItemHandler.openItem(item);

    assertThat(stringCaptor.getValue()).contains(((DesktopItem)item).getUrl());
  }
}
