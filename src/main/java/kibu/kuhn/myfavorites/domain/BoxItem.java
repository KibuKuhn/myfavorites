package kibu.kuhn.myfavorites.domain;

import java.util.Objects;
import javax.swing.Icon;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @JsonIgnore
  @Override
  public Icon getIcon() {
    return Icons.getIcon("box18");
  }

  @Override
  public String getDisplayString() {
    return alias;
  }


  @Override
  public int hashCode() {
    return Objects.hash(alias);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    var other = (BoxItem) obj;
    return Objects.equals(alias, other.alias);
  }

  @Override
  public BoxItem clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }
}
