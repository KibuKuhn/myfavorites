package kibu.kuhn.myfavorites.ui;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

public class XHTMLEditorKit extends HTMLEditorKit {

  private static final long serialVersionUID = 1L;

  @Override
  public ViewFactory getViewFactory() {
    return new XViewFactory();
  }

  static class XViewFactory extends HTMLFactory {

    @Override
    public View create(Element elem) {
      View view = super.create(elem);
      if (view instanceof ImageView) {
        return new XImageView(elem);
      }

      return view;
    }
  }
}
