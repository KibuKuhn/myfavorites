package kibu.kuhn.myfavorites.ui.drop;

import javax.swing.DefaultListModel;
import kibu.kuhn.myfavorites.domain.FileSystemItem;

public class DropListModel extends DefaultListModel<FileSystemItem> {

  private static final long serialVersionUID = 1L;
  
  private static DropListModel model = new DropListModel();
  
  public static DropListModel instance() {
    return model;
  }
}