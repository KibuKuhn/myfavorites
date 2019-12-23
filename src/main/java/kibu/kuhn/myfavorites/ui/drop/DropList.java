package kibu.kuhn.myfavorites.ui.drop;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.ListModel;
import kibu.kuhn.myfavorites.domain.Item;
import kibu.kuhn.myfavorites.ui.DropListConfigAction;
import kibu.kuhn.myfavorites.ui.ItemRenderer;

public class DropList extends JList<Item> {

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
  public void setModel(ListModel<Item> model) {
    if (!(model instanceof DropListModel)) {
      throw new IllegalArgumentException("Model is not a " + DropListModel.class.getSimpleName());
    }
    super.setModel(model);
  }

  @Override
  protected void processKeyEvent(KeyEvent e) {
    if (DropListConfigAction.isCtrlUpDownKey(e)) {      
      e.consume();
    }
    super.processKeyEvent(e);
  }

  
  
  
  

}
