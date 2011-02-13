package com.hellomvp.client.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.AbstractRenderer;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.hellomvp.client.HelloMVP;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author <a href="mailto:rhusar@redhat.com">Radoslav Husar</a>
 */
public class MenuViewImpl extends Composite implements MenuView {

   private static Binder binder = GWT.create(Binder.class);
   private Presenter presenter;

   public Presenter getPresenter() {
      return presenter;
   }

   interface Binder extends UiBinder<Widget, MenuViewImpl> {
   }
   @UiField(provided = true)
   ValueListBox<String> projectSwitch = new ValueListBox<String>(renderer);
   @SuppressWarnings("PackageVisibleField")
   static Renderer<String> renderer = new AbstractRenderer<String>() {

      @Override
      public String render(String object) {
         //return "" + object.getName() + " [" + object.getProjectId() + "]";
         return object;
      }
   };
//   @UiField
//   Anchor signOutLink;

   @UiConstructor
   public MenuViewImpl() {
      this.presenter = HelloMVP.getInjector().getMenuActivity();
//      presenter=p;
      initWidget(binder.createAndBindUi(this));

      System.out.println("init");

      Set<String> set = new HashSet<String>();
      set.clear();
      set.add("EAP 5.1 in Planning");
      set.add("SOA 5.0 in Development");
      projectSwitch.setAcceptableValues(set);

      projectSwitch.setValue("Switch to...");

      projectSwitch.addValueChangeHandler(new ValueChangeHandler<String>() {

         @Override
         public void onValueChange(ValueChangeEvent<String> event) {
            System.out.println(event.getValue());
         }
      });

   }

   @UiHandler("signOutLink")
   void onSelectMeAnchorClick(ClickEvent event) {
      //Window.alert("clicked on selectMe");
      presenter.doAbout();
   }
   
   @Override
   public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
   }
}
