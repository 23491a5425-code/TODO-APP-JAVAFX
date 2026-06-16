import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskList implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "task.bin";
    private  List<TaskDTO> tasks=new ArrayList<>();
    public TaskList(){
        loadTasks();
    }
    
    public List<TaskDTO> getTasks(){
        return tasks;
    }
    public void addTask(TaskDTO task){
        tasks.add(task);
        sortTasksByStatus();
        saveTasks();
    }
      public void removeTask(TaskDTO task){
        tasks.remove(task);
        saveTasks();
    }
    public TaskDTO getTaskById(String id){
        for(TaskDTO currentTask: tasks){
                if(currentTask.getId().equals(id)){
                    return currentTask;
                }
            }
                return null;
        
    }

    public void updateTask(TaskDTO updatedTask){
        for(int i=0;i<tasks.size();i++){
            TaskDTO currentTask = tasks.get(i);
            if(currentTask.getId().equals(updatedTask.getId())){
                currentTask.setTitle(updatedTask.getTitle());
                currentTask.setDescription(updatedTask.getDescription());
                currentTask.setStatus(updatedTask.getStatus());

                currentTask.setComments(updatedTask.getComments());

                //break;
            }
          //   sortTasksByStatus();
        }
    
     sortTasksByStatus();
     saveTasks();
    
    }
    private void sortTasksByStatus(){
        tasks.sort(Comparator.comparingInt(
            task -> {
                switch (task.getStatus()){
                    case "ToDo" : return 1;
                    case "In Progress": return 2;
                    case "Done": return 3;
                    default : return 4;
                }
            }
        ));
    }

    private void saveTasks(){
        try(ObjectOutputStream os= new ObjectOutputStream(new FileOutputStream(FILE_PATH))){
            os.writeObject(tasks);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTasks(){
        File file= new File(FILE_PATH);
        if(file.exists()){
            try(ObjectInputStream oos=new 
                ObjectInputStream(new 
                    FileInputStream(file))){
            tasks=(List<TaskDTO>) oos.readObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e)
        {
           e.printStackTrace();
        }catch(ClassNotFoundException e)
        {
          e.printStackTrace();
        }
    }
    saveTasks();
}
}
