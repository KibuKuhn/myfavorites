package kibu.kuhn.myfavorites.ui.drop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class LineMapper implements Collector<String, Map<String, String>, Map<String, String>> {

  @Override
  public Supplier<Map<String, String>> supplier() {
    return HashMap::new;
  }

  @Override
  public BiConsumer<Map<String, String>, String> accumulator() {
    return (map, s) -> {
      int idx = s.indexOf('=');
      if (idx > 0) {
        var key = s.substring(0, idx);
        var value = s.substring(idx + 1);
        if (map.containsKey(key)) {
          throw new IllegalStateException("Duplicate key " + key);
        }
        map.put(key, value);
      }
    };

  }

  @Override
  public BinaryOperator<Map<String, String>> combiner() {
    return (map1, map2) -> map1;
  }

  @Override
  public Function<Map<String, String>, Map<String, String>> finisher() {
    return (m) -> m;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.singleton(Characteristics.IDENTITY_FINISH);
  }
}
