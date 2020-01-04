package kibu.kuhn.myfavorites.domain;

import javax.swing.Icon;
import kibu.kuhn.myfavorites.ui.Icons;

public class BoxItem implements Item {

  private String alias;

  @Override
  public Type getType() {
    return Type.BoxItem;
  }

  @Override
  public String getAlias() {
    return alias;
  }

  @Override
  public void setAlias(String alias) {
    this.alias = alias;

  }

  @Override
  public Icon getIcon() {
    return Icons.getIcon("slipbox18");
  }

  @Override
  public String getDisplayString() {
    return alias;
  }

}
