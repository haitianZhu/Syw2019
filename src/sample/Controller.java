package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Controller implements Initializable {

    @FXML
    private TextField mTextField_A;

    @FXML
    private TextField mTextField_B;

    @FXML
    private TextArea mTextArea_1;

    @FXML
    private TextArea mTextArea_2;

    private OnDragOverEvent mOnDragOverEvent = new OnDragOverEvent();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mTextArea_1.setOnDragOver(mOnDragOverEvent);
        mTextArea_1.setOnDragDropped(new OnDragDroppedEvent(mTextArea_1));
        mTextArea_2.setOnDragOver(mOnDragOverEvent);
        mTextArea_2.setOnDragDropped(new OnDragDroppedEvent(mTextArea_2));
    }

    private class OnDragOverEvent implements EventHandler<DragEvent> {

        @Override
        public void handle(DragEvent event) {
            if (event.getDragboard().hasFiles()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }
    }

    private class OnDragDroppedEvent implements EventHandler<DragEvent> {

        private TextArea textArea;

        public OnDragDroppedEvent(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void handle(DragEvent event) {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                textArea.setText(db.getFiles().toString());
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        }
    }
}
