package com.radoslavhusar.tapas.war.client.tasks;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.war.client.state.ClientState;

public class AllocationDialogBox {
   
   public static DialogBox getPopup(final ClientState client, final Task task) {

      // Create a dialog box and set the caption text
      final DialogBox dialogBox = new DialogBox();
      //dialogBox.ensureDebugId("cwDialogBox");
      dialogBox.setText("External Tracker");

      // Create a table to layout the content
      final VerticalPanel dialogContents = new VerticalPanel();
      dialogContents.setSpacing(4);
      dialogBox.setWidget(dialogContents);

      //for (ProjectPhase pp : client.getProject().getPhases()) {
      for (final TimeAllocation ta : task.getTimeAllocations()) {
         VerticalPanel phase = new VerticalPanel();
         phase.add(new Label(ta.getPhase().getName() + " URL"));
         final TextBox tb = new TextBox();
         tb.setText(ta.getTracker());
         tb.addChangeHandler(new ChangeHandler() {
            
            @Override
            public void onChange(ChangeEvent event) {
               ta.setTracker(tb.getText().isEmpty() ? null : tb.getText());
            }
         });
         phase.add(tb);
         
         dialogContents.add(phase);
      }
      
      if (task.getTimeAllocations().isEmpty()) {
         // No allocation to add trackers to..
         dialogContents.add(new Label("There are no existing allocation to add external trackers to."));
      }

      // Add a close button at the bottom of the dialog
      // FIXME: move to Right ->
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

      // Return the dialog box
      return dialogBox;
   }
   
   private AllocationDialogBox() {
      // Not instantiable
   }
}
