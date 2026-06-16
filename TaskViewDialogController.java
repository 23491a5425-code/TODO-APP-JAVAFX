import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Text;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TaskViewDialogController {
    public MFXTextField taskTitleField;
    public TextArea taskDescriptionField;
    public MFXComboBox statusComboBox;
    public MFXTextField commentField;
    public VBox commentList;

    private TaskDTO task;
    private TaskController mainController;

    

    public void setTaskDetails(TaskDTO task, TaskController mainController){
        if(task == null){
    System.out.println("Task is null");
    return;
    }
    this.task=task;
        this.mainController=mainController;

        taskTitleField.setText(task.getTitle());
        taskDescriptionField.setText(task.getDescription());

        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll("ToDo", "In Progress", "Done");
        Platform.runLater(()->{
            statusComboBox.setValue(task.getStatus());
        });

        task.getComments().forEach(comment-> displayComment(comment));
    }

    private void displayComment(String comment){
        Text commentLabel=new Text(comment);
        commentLabel.setStyle("-fx-padding: 3px;");
        commentList.getChildren().addFirst(commentLabel);
    }
    public void handleAddComment(){
        String comment= commentField.getText();
        if(!comment.isEmpty()){
            task.addComments(comment);
            displayComment(comment);
            commentField.clear();
        }
    }
    public void handleCancel(){
        closeDialog();
    }
    public void handleDelete(){
        mainController.deleteTasks(task);
        closeDialog();
    }
    public void handleUpdate(){
        if (task == null) {
        System.out.println("Task is null in update");
        return;
    }

        task.setTitle(taskTitleField.getText());
        task.setDescription(taskDescriptionField.getText());
        task.setStatus((String) statusComboBox.getValue());

        mainController.updateTask(task);
   
        closeDialog();
    }
     private void closeDialog(){
        Stage stage=(Stage) taskTitleField.getScene().getWindow();
        stage.close();
    }
}
