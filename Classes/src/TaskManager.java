import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTaskById(String taskId) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                return t;
            }
        }
        return null;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }


    public void markTaskDone(String taskId) {
        Task t = getTaskById(taskId);
        if (t != null && t.getStatus() == TaskStatus.TODO) {
            t.setStatus(TaskStatus.DONE);
        }
    }

    // İsterseniz günlük/haftalık filtre vs. ekleyebilirsiniz
    // Buraya ekstra eklemeler yapılacak
}
