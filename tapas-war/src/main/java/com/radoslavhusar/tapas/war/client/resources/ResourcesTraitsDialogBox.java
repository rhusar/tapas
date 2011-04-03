package com.radoslavhusar.tapas.war.client.resources;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.app.Application;
import java.util.List;

/**
 * TODO: maybe tasks can use the same dialog?
 */
public class ResourcesTraitsDialogBox {

   private ResourcesTraitsDialogBox() {
      // Not instanciable
   }

   public static DialogBox getPopup(final Resource resource) {

      // Create a dialog box and set the caption text
      final DialogBox dialogBox = new DialogBox();
      //dialogBox.ensureDebugId("cwDialogBox");
      dialogBox.setText(resource.getName() + "'s Traits");

      // Create a table to layout the content
      final VerticalPanel dialogContents = new VerticalPanel();
      dialogContents.setSpacing(4);
      dialogBox.setWidget(dialogContents);

      // Add Oracle -Define the oracle that finds suggestions
      final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

      Application.getInjector().getService().findAllTraits(new AsyncCallback<List<Trait>>() {

         @Override
         public void onFailure(Throwable caught) {
            // no problem, but no suggestions
         }

         @Override
         public void onSuccess(List<Trait> result) {
            for (Trait t : result) {
               oracle.add(t.getName());
            }
         }
      });


      // Create the suggest box
      final SuggestBox suggestBox = new SuggestBox(oracle);
      //suggestBox.ensureDebugId("cwSuggestBox");
      suggestBox.addKeyPressHandler(new KeyPressHandler() {

         @Override
         public void onKeyPress(KeyPressEvent event) {
            /*if (event.getNativeKeyCode() == 13) {
            
            }*/
         }
      });

      HorizontalPanel suggestPanel = new HorizontalPanel();
      suggestPanel.add(suggestBox);

      // Add a close button at the bottom of the dialog
      Button addButton = new Button("Add", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            final Anchor histrait = new Anchor(suggestBox.getText());
            histrait.addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  //remove that trait
                  //resource.getTraits().remove(trait);
                  histrait.setVisible(false);
               }
            });
            dialogContents.add(histrait);
         }
      });
      suggestPanel.add(addButton);
      dialogContents.add(suggestPanel);

      // label
      dialogContents.add(new Label("Current traits, click to remove: "));

      // list of traits
      if (resource.getTraits() != null) {
         for (final Trait trait : resource.getTraits()) {
            final Anchor histrait = new Anchor(trait.getName());
            histrait.addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  //remove that trait
                  resource.getTraits().remove(trait);
                  histrait.setVisible(false);
               }
            });
            dialogContents.add(histrait);
         }
      }



      // Add a close button at the bottom of the dialog
      Button closeButton = new Button("Done", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            dialogBox.hide();
            Window.alert("IMPLETEMNT SAVE!");
         }
      });
      dialogContents.add(closeButton);
      dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_LEFT);

      // Send it to center
      dialogBox.center();

      // Return the dialog box
      return dialogBox;
   }
}
