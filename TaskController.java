import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskController {

    
    public Label taskName;
    public Label taskTimeStamp;
    public Label taskStatus;
    public String taskId;
    private TaskDTO task; 
   private  TaskList taskList=new TaskList();
    //private TaskList taskList;
    private TodoController mainController;
   
    public void setTaskDetails( TaskDTO task, TodoController controller){
        this.task=task;
        taskName.setText(task.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a, MM/dd/yyyy");
        taskTimeStamp.setText(task.getLocalDateTime().format(formatter));
        taskStatus.setText(task.getStatus());
        //this.taskId = task.getId();
        mainController= controller;
        this.taskList = mainController.taskList;
        applyStatusColor(task.getStatus());
        
    }
     private void applyStatusColor(String status){
        switch(status){
            case "ToDo":
                taskStatus.setStyle("-fx-text-fill:red;");
                break;
            case "In Progress":
                taskStatus.setStyle("-fx-text-fill:orange;");
                break;
            case "Done":
                taskStatus.setStyle("-fx-text-fill:green;");
                break;
            default:
                 taskStatus.setStyle("-fx-text-fill:black;");
                break;
        }
    }
    
     public void handleViewTask(ActionEvent event ){
       //System.out.println("view task"+taskName.getText());
       // TaskDTO task= taskList.getTaskById(taskId);
        showDialogView(task);
    }
        public void  showDialogView(TaskDTO task){
       try{
            FXMLLoader loader = new FXMLLoader(
            getClass().getResource("task-view-dialog.fxml"));
            VBox dialogPane=loader.load();
            //TaskDTO task= taskList.getTaskById(taskId);
            TaskViewDialogController dialogController = loader.getController();
            dialogController.setTaskDetails(task,this);

            Stage dialogStage= new Stage();
            dialogStage.setTitle("view task");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(dialogPane);
            /*scene.getStylesheets().add(
            getClass().getResource("addStyle.css").toExternalForm() );*/

            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteTasks(TaskDTO task){

        taskList.removeTask(task);
        mainController.redrawTaskList();
    }
    public void updateTask(TaskDTO task){
        taskList.updateTask(task);
        taskName.setText(task.getTitle());
        taskStatus.setText(task.getStatus());
        applyStatusColor(task.getStatus());

        
        mainController.redrawTaskList();
        
    }
   
}
