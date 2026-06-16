import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class TaskDialogController {
    public TextArea taskDescriptionField;
    public MFXTextField taskTitleField;

    private TodoController mainController;
    
    public void setMainController(TodoController mainController){
        this.mainController = mainController;
    }
    public void handleCancel(){
        closeDialog();
    }
     public void handleSubmit(){
        String title= taskTitleField.getText();
        String description = taskDescriptionField.getText();
        if(!title.isEmpty()){
            mainController.addTaskFromDialog(title,description);
            closeDialog();
        }else{
            System.out.println("title required");
        }
    }
    private void closeDialog(){
        Stage stage=(Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }
}
