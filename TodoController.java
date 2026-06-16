import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import java.time.LocalDateTime;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import java .util.*;
import java.util.stream.Collectors;

import io.github.palexdev.materialfx.controls.MFXComboBox;

public class TodoController {
    public MFXTextField Tasktitle;
    public VBox tasklistVBox;
    public MFXComboBox<String> statusComboBox;
    public TaskList taskList;

    public void initialize(){
        taskList=new TaskList();
        statusComboBox.getItems().addAll("All","ToDo","In Progress", "Done");
        statusComboBox.setValue("All");
        statusComboBox.valueProperty().addListener(new ChangeListener<String>() {
    @Override
    public void changed(ObservableValue<? extends String> observable,
                        String oldValue,
                        String newValue) {
                            filterTasksByStatus(newValue);
        System.out.println("Old: " + oldValue + " New: " + newValue);
    }
});        
        redrawTaskList();
    }

    private void filterTasksByStatus(String status){
        tasklistVBox.getChildren().clear();

        List<TaskDTO> filteredTasks;
        if("All".equals(status)){
            filteredTasks= taskList.getTasks();
        }else{
            filteredTasks = taskList.getTasks().stream().filter(
                task -> task.getStatus().equals(status)).collect(Collectors.toList()
            ); 
        }
        for(TaskDTO task: filteredTasks){
            displayTask(task);
        }
    }


    public void handleAction() {
       // addTask("new task","added new task",LocalDateTime.now(), "TODO");
       showaddTaskDialog();
    }
    private void showaddTaskDialog(){
        try{
            FXMLLoader loader = new FXMLLoader(
            getClass().getResource("task-add-dialog.fxml"));
            VBox dialogPane=loader.load();
            TaskDialogController dialogController = loader.getController();
            dialogController.setMainController(this);

            Stage dialogStage= new Stage();
            dialogStage.setTitle("Add new Task");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(dialogPane);
            scene.getStylesheets().add(
            getClass().getResource("addStyle.css").toExternalForm()
        );
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
   public void addTaskFromDialog (String title, String description)
    {
        addTask(title, description, LocalDateTime.now(), "ToDo");
    }
   private void addTask(String title,String description, 
        LocalDateTime dateAdded,String status)

    {
        TaskDTO newTask= new TaskDTO(title,description,dateAdded,status);
        taskList.addTask(newTask);
        redrawTaskList();
    }
    private void displayTask(TaskDTO task){
     try{
       FXMLLoader loader = new FXMLLoader(
            getClass().getResource("task-card.fxml"));
        HBox taskcard = loader.load();
      
        TaskController controller= loader.getController();
        controller.setTaskDetails( task, this);
        
        tasklistVBox.getChildren().add(taskcard);
    }catch(Exception e){
        e.printStackTrace();
     }
}
    public void redrawTaskList(){
        tasklistVBox.getChildren().clear();

        for(TaskDTO task: taskList.getTasks()){
            displayTask(task);
        }
        statusComboBox.setValue("All");
    }
}
