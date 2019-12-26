module myfavorites {
  opens kibu.kuhn.myfavorites;
  opens kibu.kuhn.myfavorites.prefs;
  requires java.desktop;
  requires org.slf4j;
  requires java.scripting;
  requires java.prefs;
}
