package kibu.kuhn.myfavorites.domain;

public interface CloneableItem extends Item {

  CloneableItem clone() throws CloneNotSupportedException;
}
