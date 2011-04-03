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
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: maybe tasks can use the same dialog? 
 * But we keep it only OneToMany so its not necessary.
 */
public class ResourceTraitsDialogBox {

   public static DialogBox getPopup(final Resource resource) {

      // Create a dialog box and set the caption text
      final DialogBox dialogBox = new DialogBox();
      //dialogBox.ensureDebugId("cwDialogBox");
      dialogBox.setText(resource.getName() + "'s Traits");

      // Create a table to layout the content
      final VerticalPanel dialogContents = new VerticalPanel();
      dialogContents.setSpacing(4);
      dialogBox.setWidget(dialogContents);

      final List<Trait> traits = new ArrayList();

      // Add Oracle -Define the oracle that finds suggestions
      final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

      Application.getInjector().getService().findAllTraits(new AsyncCallback<List<Trait>>() {

         @Override
         public void onFailure(Throwable caught) {
            // This is a big problem, nothing to check against.
            oracle.clear();
         }

         @Override
         public void onSuccess(List<Trait> result) {
            for (Trait t : result) {
               traits.add(t);
               oracle.add(t.getName());
            }
         }
      });


      // Create the suggest box
      final SuggestBox suggestBox = new SuggestBox(oracle);
      //suggestBox.ensureDebugId("cwSuggestBox");

      HorizontalPanel suggestPanel = new HorizontalPanel();
      final VerticalPanel traitsPanel = new VerticalPanel();
      suggestPanel.add(suggestBox);

      // Add a close button at the bottom of the dialog
      Button addButton = new Button("Add", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            String tname = suggestBox.getText();

            // Is this valid trait?
            for (final Trait t : traits) {
               if (tname.equalsIgnoreCase(t.getName())) {
                  // This is a real trait, lets add it.
                  suggestBox.setText("");
                  resource.getTraits().add(t);

                  // Show the link
                  final Anchor newTraitAnchor = new Anchor(t.getName());
                  newTraitAnchor.addClickHandler(new ClickHandler() {

                     @Override
                     public void onClick(ClickEvent event) {
                        //remove that trait
                        resource.getTraits().remove(t);
                        newTraitAnchor.setVisible(false);
                        traitsPanel.remove(newTraitAnchor);
                     }
                  });
                  traitsPanel.add(newTraitAnchor);

                  suggestBox.setFocus(true);
                  return;
               }
            }

            // Nothing found - let user know
            Window.alert("Trait not found. Go to Projects -> Global Settings -> Manage Traits to add it.");
         }
      });
      suggestPanel.add(addButton);
      dialogContents.add(suggestPanel);

      // label
      dialogContents.add(new Label("Current traits, click to remove: "));

      dialogContents.add(traitsPanel);

      // list of traits
      if (resource.getTraits() != null) {
         for (final Trait trait : resource.getTraits()) {
            final Anchor histrait = new Anchor(trait.getName());
            histrait.addClickHandler(new ClickHandler() {

               @Override
               public void onClick(ClickEvent event) {
                  //remove that trait
                  resource.getTraits().remove(trait);
                  traitsPanel.remove(histrait); //histrait.setVisible(false);
               }
            });
            traitsPanel.add(histrait);
         }
      }

      // Add a close button at the bottom of the dialog
      Button closeButton = new Button("Done", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            dialogBox.hide();
         }
      });
      dialogContents.add(closeButton);
      dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_LEFT);

      // Send it to center
      dialogBox.center();

      // Focus on suggestbox
      suggestBox.setFocus(true);      
      
      // Return the dialog box
      return dialogBox;
   }

   private ResourceTraitsDialogBox() {
      // Not instantiable
   }
}
