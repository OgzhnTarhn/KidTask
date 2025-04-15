import java.time.LocalDateTime;

public class Teacher extends User {

    public Teacher(String userId, String name) {

        super(userId, name, "Teacher");
    }

    public Task createTask(String taskId,
                           String title,
                           String description,
                           TaskType taskType,
                           LocalDateTime deadline,
                           LocalDateTime startTime,
                           LocalDateTime endTime,
                           int points,
                           Child assignedChild) {

        Task newTask = new Task(taskId, title, description, taskType,
                deadline, startTime, endTime,
                points, this); // assignedBy = Teacher

        newTask.setAssignedChild(assignedChild);
        assignedChild.addTask(newTask);

        return newTask;
    }

    public void approveTask(Task task, int rating) {
        if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.APPROVED);
            task.setRating(rating);
            Child child = task.getAssignedChild();
            if (child != null) {
                child.addPoints(task.getPoints());
                child.recalculateRating();
            }
        }
    }

    public void addBudgetCoin(Child child, int extraPoints) {

        child.addPoints(extraPoints);
    }
}
