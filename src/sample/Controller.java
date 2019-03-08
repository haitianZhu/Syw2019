package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private List<String> mDataListA = new ArrayList<>();

    // file B
    @FXML
    private TextField mTF_sheetIndexB;
    @FXML
    private TextField mTF_columnIndexB;
    @FXML
    private TextArea mTextAreaB;
    private List<String> mDataListB = new ArrayList<>();

    @FXML
    private TextField mTF_matchCount;

    private int mParseCompletedCount = 0;

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

        try {

            String fileNameA = mTextAreaA.getText();
            int sheetA = Integer.valueOf(mTF_sheetIndexA.getText());
            int columnA = Integer.valueOf(mTF_columnIndexA.getText());
            String fileNameB = mTextAreaB.getText();
            int sheetB = Integer.valueOf(mTF_sheetIndexB.getText());
            int columnB = Integer.valueOf(mTF_columnIndexB.getText());

            if ((fileNameA.endsWith(".xls") || fileNameA.endsWith(".xlsx")) &&
                    (fileNameB.endsWith(".xls") || fileNameB.endsWith(".xlsx"))) {


                ParseUtil.parseSheetInExcel(fileNameA, sheetA, new ParseListenerImp(mTextAreaA.getId(), columnA));
                ParseUtil.parseSheetInExcel(fileNameB, sheetB, new ParseListenerImp(mTextAreaB.getId(), columnB));

            } else {
                System.out.println("文件格式错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ParseListenerImp implements ParseListener {

        private String uniqueID = "";
        private int column = 0;

        public ParseListenerImp(String uniqueID, int column) {
            this.uniqueID = uniqueID;
            this.column = column;
        }

        @Override
        public void onParseCompleted(List<Object> data) {

            System.out.println("aaaa");
            if ("mTextAreaA".equals(uniqueID)) {

                selectColumn(data, mDataListA, column - 1);

            } else if ("mTextAreaB".equals(uniqueID)) {

                selectColumn(data, mDataListB, column - 1);
            }

            mParseCompletedCount++;
            // 两个文件解析完成
            if (mParseCompletedCount == 2) {
                mParseCompletedCount = 0;

                ParseUtil.beginMatch(mDataListA, mDataListB, Integer.valueOf(mTF_matchCount.getText()));
            }
        }

        private void selectColumn(List<Object> data, List<String> selectedList, int column) {

            selectedList.clear();
            for (Object item : data) {
                selectedList.add(((List<String>)item).get(column));
            }
        }
    }
}
