package com.radoslavhusar.tapas.war.client.projects;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.radoslavhusar.tapas.ejb.entity.Trait;
import com.radoslavhusar.tapas.war.client.app.Application;
import java.util.ArrayList;
import java.util.List;

public class ManageTraitsDialogBox {

   public static DialogBox getDialogBox(final ProjectsView.Presenter presenter) {

      // Create a dialog box and set the caption text
      final DialogBox dialogBox = new DialogBox();
      //dialogBox.ensureDebugId("cwDialogBox");
      dialogBox.setText("Manage Global Traits");

      // Create a table to layout the content
      final VerticalPanel dialogContents = new VerticalPanel();
      dialogContents.setSpacing(4);
      dialogBox.setWidget(dialogContents);

      final VerticalPanel traitsListPanel = new VerticalPanel();


      final List<Trait> traits = new ArrayList();


      // Create the suggest box
      final TextBox inputBox = new TextBox();
      //suggestBox.ensureDebugId("cwSuggestBox");

      HorizontalPanel inputPanel = new HorizontalPanel();
      inputPanel.add(inputBox);

      // Add save button
      final Button saveButton = new Button("Save", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            String traitName = inputBox.getText();

            // Browse for all traits whether it already exists.
            for (Trait t : traits) {
               if (traitName.equalsIgnoreCase(t.getName())) {
                  Window.alert("This trait already exists.");
                  return;
               }
            }

            // else - add it
            Trait nt = new Trait();
            nt.setName(traitName);
            traits.add(nt);
            traitsListPanel.add(new Label(traitName));

            // and save it..
            presenter.saveTrait(nt);
            inputBox.setText("");
         }
      });
      inputPanel.add(saveButton);
      dialogContents.add(inputPanel);

      // label with description
      dialogContents.add(new Label("Managed Traits: "));


      traitsListPanel.add(new Label("Loading traits..."));
      dialogContents.add(traitsListPanel);

      Application.getInjector().getService().findAllTraits(new AsyncCallback<List<Trait>>() {

         @Override
         public void onFailure(Throwable caught) {
            traits.clear();
            traitsListPanel.add(new Label("Error fetching traits."));
         }

         @Override
         public void onSuccess(List<Trait> result) {
            traits.clear();
            traitsListPanel.clear();

            for (Trait t : result) {
               traits.add(t);
               traitsListPanel.add(new Label(t.getName()));
            }

            saveButton.setEnabled(true);
         }
      });

      // Add a close button at the bottom of the dialog
      HorizontalPanel closePanel = new HorizontalPanel();
      Button closeButton = new Button("Done", new ClickHandler() {

         @Override
         public void onClick(ClickEvent event) {
            // Nothing to save here, its already done as added.
            dialogBox.hide();
         }
      });
      closePanel.add(closeButton);
      closePanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
      dialogContents.add(closePanel);

      // Send it to center
      dialogBox.center();

      // Return the dialog box
      return dialogBox;
   }

   private ManageTraitsDialogBox() {
      // Not instanciable
   }
}
