public class Teacher extends User {

    public Teacher(String userId, String name) {
        super(userId, name);
    }

    public void approveTask(Task task, int rating) {
        if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.APPROVED);
            task.setRating(rating);
            if (task.getAssignedChild() != null) {
                task.getAssignedChild().addPoints(task.getPoints());
                task.getAssignedChild().updateLevelByRating(rating);
            }
        }
    }

    // Teacher da tıpkı Parent gibi görev ekleyebilir
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
}