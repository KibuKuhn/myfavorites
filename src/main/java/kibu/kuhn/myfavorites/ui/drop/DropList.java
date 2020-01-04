package kibu.kuhn.myfavorites.ui.drop;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.ListModel;
import kibu.kuhn.myfavorites.domain.FileSystemItem;
import kibu.kuhn.myfavorites.ui.DropTreeConfigAction;
import kibu.kuhn.myfavorites.ui.ItemRenderer;

public class DropList extends JList<FileSystemItem> {

  private static final long serialVersionUID = 1L;
  
  public DropList() {
    super(DropListModel.instance());
    setSelectionMode(SINGLE_SELECTION);
    setCellRenderer(new ItemRenderer());
  }

  @Override
  public DropListModel getModel() {
    return (DropListModel)super.getModel();
  }
  
  @Override
  public void setModel(ListModel<FileSystemItem> model) {
    if (!(model instanceof DropListModel)) {
      throw new IllegalArgumentException("Model is not a " + DropListModel.class.getSimpleName());
    }
    super.setModel(model);
  }

  @Override
  protected void processKeyEvent(KeyEvent e) {
    if (DropTreeConfigAction.isCtrlUpDownKey(e)) {      
      e.consume();
    }
    super.processKeyEvent(e);
  }

  
  
  
  

}
