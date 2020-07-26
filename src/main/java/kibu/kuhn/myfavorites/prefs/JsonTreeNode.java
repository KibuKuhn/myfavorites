package kibu.kuhn.myfavorites.prefs;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import kibu.kuhn.myfavorites.domain.Type;

@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTreeNode {

  private Type type;
  private String alias;
  private String path;
  private String URL;
  @JsonIgnore
  private JsonTreeNode parent;
  private List<JsonTreeNode> children = new ArrayList<>();
  private boolean file;

  @SuppressWarnings("exports")
  public Type getType() {
    return type;
  }

  @SuppressWarnings("exports")
  public void setType(Type type) {
    this.type = type;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String uRL) {
    URL = uRL;
  }

  public JsonTreeNode getParent() {
    return parent;
  }

  public void setParent(JsonTreeNode parent) {
    this.parent = parent;
  }

  public List<JsonTreeNode> getChildren() {
    return children;
  }

  public void setChildren(List<JsonTreeNode> children) {
    this.children = children;
  }

  public boolean isFile() {
    return file;
  }

  public void setFile(boolean file) {
    this.file = file;
  }
}
