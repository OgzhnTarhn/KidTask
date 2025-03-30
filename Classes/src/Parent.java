import java.time.LocalDateTime;
import java.util.List;

public class Parent extends User {

    public Parent(String userId, String name) {
        super(userId, name);
    }

    // Örnek: Parent bir Task yaratabilir (Görevi Child'a atar).
    public Task createTask(String taskId,
                           String title,
                           String description,
                           TaskType taskType,
                           LocalDateTime deadline,
                           LocalDateTime startTime,
                           LocalDateTime endTime,
                           int points,
                           Child assignedChild) {

        // assignedBy = this (Parent nesnesi)
        Task newTask = new Task(taskId, title, description, taskType,
                deadline, startTime, endTime,
                points, this);

        // Görevi Child'a bağla
        newTask.setAssignedChild(assignedChild);
        assignedChild.addTask(newTask);

        return newTask;
    }

    // Örnek: Parent bir görevi onaylayabilir (TASK_CHECKED)
    public void approveTask(Task task, int rating) {
        if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.APPROVED);
            task.setRating(rating);

            // Puan ve rating ile çocuğun seviyesi güncellensin
            Child child = task.getAssignedChild();
            if (child != null) {
                child.addPoints(task.getPoints());
                child.updateLevelByRating(rating);
            }
        }
    }

    // Bir Wish'i (dilek) onaylama
    public void approveWish(Wish wish, int requiredLevel) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.APPROVED);
            wish.setRequiredLevel(requiredLevel);
        }
    }

    // Bir Wish'i reddetme
    public void rejectWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
        }
    }

    // Ek puan verme
    public void addBudgetCoin(Child child, int extraPoints) {
        child.addPoints(extraPoints);
    }
}
