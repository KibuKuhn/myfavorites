module myfavorites {
  opens kibu.kuhn.myfavorites;
  opens kibu.kuhn.myfavorites.prefs;
  requires java.desktop;
  requires org.slf4j;
  requires java.scripting;
  requires java.prefs;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.databind;
  opens kibu.kuhn.myfavorites.domain to com.fasterxml.jackson.databind;
}
