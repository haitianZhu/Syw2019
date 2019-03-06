package sample;

import com.sun.javafx.binding.StringFormatter;

import java.net.URL;
import java.util.List;
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
import sample.listener.ParseListener;
import sample.util.ParseUtil;

public class Controller implements Initializable {

    // file A
    @FXML
    private TextField mTF_sheetIndexA;
    @FXML
    private TextField mTF_columnIndexA;
    @FXML
    private TextArea mTextAreaA;

    // file B
    @FXML
    private TextField mTF_sheetIndexB;
    @FXML
    private TextField mTF_columnIndexB;
    @FXML
    private TextArea mTextAreaB;

    private OnDragOverEvent mOnDragOverEvent = new OnDragOverEvent();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mTextAreaA.setOnDragOver(mOnDragOverEvent);
        mTextAreaA.setOnDragDropped(new OnDragDroppedEvent(mTextAreaA));
        mTextAreaB.setOnDragOver(mOnDragOverEvent);
        mTextAreaB.setOnDragDropped(new OnDragDroppedEvent(mTextAreaB));
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
                textArea.setText(db.getFiles().toString().replace("[", "").replace("]", ""));

                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        }
    }

    @FXML
    private void handleClickDoAction() {
        System.out.println("点击");

        String fileNameA = mTextAreaA.getText();

        try {
            if ((fileNameA.endsWith(".xls") || fileNameA.endsWith(".xlsx"))) {

                ParseUtil.parseSheetInExcel(fileNameA, 0, new ParseListenerImp());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ParseListenerImp implements ParseListener {

        @Override
        public void onParseCompleted(List<Object> data) {
            System.out.println("111");
        }
    }
}
