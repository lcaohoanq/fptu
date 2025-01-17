package models;



import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDTO {

    private int workoutID;
    private String workoutName;
    private int durationInMinutes;
    private Date workoutDate;
    private int quantity;
    
    public WorkoutDTO(int workoutID, String workoutName, int durationInMinutes, Date workoutDate) {
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.durationInMinutes = durationInMinutes;
        this.workoutDate = workoutDate;
    }
    
    
    
}
