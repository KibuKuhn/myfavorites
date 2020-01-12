package kibu.kuhn.myfavorites.domain;

import java.util.Objects;
import javax.swing.Icon;
import kibu.kuhn.myfavorites.ui.IGui;
import kibu.kuhn.myfavorites.ui.Icons;

public class RootItem implements Item {

  @Override
  public Type getType() {
    return Type.Root;
  }

  @Override
  public String getAlias() {
    return null;
  }

  @Override
  public void setAlias(String alias) {

  }

  @Override
  public Icon getIcon() {
    return Icons.getIcon("favorites18");
  }

  @Override
  public String getDisplayString() {
    return IGui.get().getI18n("configmenu.tree.root");
  }

  @Override
  public RootItem clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  @Override
  public int hashCode() {
    return Objects.hash(Type.Root);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RootItem other = (RootItem) obj;
    return Type.Root == other.getType();
  }
}
