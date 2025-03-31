import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Görevi DONE yapma (TASK_DONE)
    public void markTaskDone(String taskId) {
        Task t = getTaskById(taskId);
        if (t != null && t.getStatus() == TaskStatus.TODO) {
            t.setStatus(TaskStatus.DONE);
        }
    }

    // ----------------------------------------------------
    // DAILY & WEEKLY LİSTE METOTLARI
    // ----------------------------------------------------
    public List<Task> getDailyTasks() {
        List<Task> dailyList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Task t : tasks) {
            if (t.getTaskType() == TaskType.TASK1) {
                // TASK1 => sadece deadline'a göre kontrol edelim
                LocalDateTime deadline = t.getDeadline();
                if (deadline != null && deadline.toLocalDate().equals(today)) {
                    dailyList.add(t);
                }
            } else {
                // TASK2 => startTime veya endTime bugünün tarihine denk gelirse ekleyelim
                LocalDateTime start = t.getStartTime();
                LocalDateTime end = t.getEndTime();
                if ((start != null && start.toLocalDate().equals(today))
                        || (end != null && end.toLocalDate().equals(today))) {
                    dailyList.add(t);
                }
            }
        }
        return dailyList;
    }

    public List<Task> getWeeklyTasks() {
        List<Task> weeklyList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);

        for (Task t : tasks) {
            if (t.getTaskType() == TaskType.TASK1) {
                LocalDateTime deadline = t.getDeadline();
                if (deadline != null) {
                    // deadline bugün <= deadline <= bugünden 7 gün sonrasına kadar
                    LocalDate dlDate = deadline.toLocalDate();
                    if (!dlDate.isBefore(today) && !dlDate.isAfter(weekLater)) {
                        weeklyList.add(t);
                    }
                }
            } else {
                // TASK2 => start veya end bugünden sonraki 7 gün içinde mi?
                LocalDateTime start = t.getStartTime();
                LocalDateTime end = t.getEndTime();
                if (start != null) {
                    LocalDate sDate = start.toLocalDate();
                    if (!sDate.isBefore(today) && !sDate.isAfter(weekLater)) {
                        weeklyList.add(t);
                        continue;
                    }
                }
                if (end != null) {
                    LocalDate eDate = end.toLocalDate();
                    if (!eDate.isBefore(today) && !eDate.isAfter(weekLater)) {
                        weeklyList.add(t);
                    }
                }
            }
        }
        return weeklyList;
    }
}
