import java.time.LocalDateTime;

public class Task {
    private String taskId;
    private String title;
    private String description;
    private TaskType taskType;
    private LocalDateTime deadline;    // TASK1 için
    private LocalDateTime startTime;   // TASK2 başlangıç zamanı
    private LocalDateTime endTime;     // TASK2 bitiş zamanı
    private int points;
    private int rating;                // Onaylanınca verilir
    private TaskStatus status;

    // Görevi kimin eklediğini takip etmek istersek
    private User assignedBy;           // Parent veya Teacher

    // Görevin hangi çocuğa ait olduğu (SENG 272 doc: Child, Parent, Teacher)
    private Child assignedChild;

    public Task(String taskId,
                String title,
                String description,
                TaskType taskType,
                LocalDateTime deadline,
                LocalDateTime startTime,
                LocalDateTime endTime,
                int points,
                User assignedBy) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.deadline = deadline;
        this.startTime = startTime;
        this.endTime = endTime;
        this.points = points;
        this.status = TaskStatus.TODO;
        this.assignedBy = assignedBy;
    }

    public String getTaskId() {
        return taskId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public TaskType getTaskType() {
        return taskType;
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getPoints() {
        return points;
    }
    public int getRating() {
        return rating;
    }
    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public User getAssignedBy() {
        return assignedBy;
    }
    public Child getAssignedChild() {
        return assignedChild;
    }
    public void setAssignedChild(Child child) {
        this.assignedChild = child;
    }
}
