import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskDTO implements Serializable{
     private static final long serialVersionUID = 1L;
    private String id;
    private String status;
    private String title;
    private String description;
    private LocalDateTime dateAdded;
    private List<String> comments;

    public TaskDTO(String title,String description, 
        LocalDateTime dateAdded,String status)

    {
        this.id=UUID.randomUUID().toString();
        this.status=status;
        this.comments=new ArrayList<>();
        this.title=title;
        this.dateAdded=dateAdded;
        this.description=description;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
     public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
     public LocalDateTime getLocalDateTime(){
        return dateAdded;
    }
    public void setLocalDateTime(LocalDateTime dateAdded){
        this.dateAdded=dateAdded;
    }
     public List<String> getComments(){
        return comments;
    }
    public void addComments(String comment){
    this.comments.add(comment);
    }
    public void setComments(List<String> comments){
        this.comments=comments;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
      public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }
}
