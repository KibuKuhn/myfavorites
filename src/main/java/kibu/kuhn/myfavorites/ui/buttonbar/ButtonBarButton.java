package kibu.kuhn.myfavorites.ui.buttonbar;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.JButton;

class ButtonBarButton extends JButton {

    private static final long serialVersionUID = 1L;

    ButtonBarButton(Action action) {
      super(action);
      setIconTextGap(0);
      setBorderPainted(false);
      setBorder(null);
      setContentAreaFilled(false);
      setPreferredSize(new Dimension(32,32));
      addKeyListener(new EnterAction());
    }

    @Override
    public Insets getInsets(Insets insets) {
      insets.bottom = insets.left = insets.right = insets.top = 2;
      return insets;
    }
    
    private class EnterAction extends KeyAdapter {

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
          return;
        }
        performAEnterction();
      }
      
    }

    private void performAEnterction() {
      getAction().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getActionCommand()));
    }
    
  }