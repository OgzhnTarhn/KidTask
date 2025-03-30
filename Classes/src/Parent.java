public class Parent extends User {

    public Parent(String userId, String name) {
        super(userId, name);
    }

    // Parent, Child'ın task'ini onaylayabilir, wish'ini onaylayabilir vb.

    public void approveTask(Task task, int rating) {
        if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.APPROVED);
            task.setRating(rating);
            if (task.getAssignedChild() != null) {
                // Onaylanınca puan ekle
                task.getAssignedChild().addPoints(task.getPoints());
                // Çocuğun rating ortalamasına etki et
                task.getAssignedChild().updateLevelByRating(rating);
            }
        }
    }

    public void approveWish(Wish wish, int requiredLevel) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.APPROVED);
            wish.setRequiredLevel(requiredLevel);
        }
    }

    public void rejectWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
        }
    }

    // Ekstra puan eklemek
    public void addBudgetCoin(Child child, int extraPoints) {
        child.addPoints(extraPoints);
    }

    // Görev ekleme
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
                points, this); // assignedBy = Parent
        newTask.setAssignedChild(assignedChild);
        // Çocuğun tasks listesine ekle
        assignedChild.addTask(newTask);
        return newTask;
    }
}