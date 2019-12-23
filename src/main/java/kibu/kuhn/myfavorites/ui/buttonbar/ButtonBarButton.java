package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.Action;
import javax.swing.JButton;

class ButtonBarButton extends JButton {

    private static final long serialVersionUID = 1L;

    ButtonBarButton(Action action) {
      super(action);
      setIconTextGap(0);
      setBorderPainted(false);
      setBorder(null);
      setPreferredSize(new Dimension(32,32));
    }

    @Override
    public Insets getInsets(Insets insets) {
      insets.bottom = insets.left = insets.right = insets.top = 2;
      return insets;
    }
  }