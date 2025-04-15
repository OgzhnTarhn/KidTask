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

    // -------------------------------------------------------------------------
    // FİLTRE METOTLARI
    // En büyük sorun TASK2'de filtreleme yaparken nasıl filtreleme yapmam gerektiğini bilemedim.
    // İlk tarihlerine bakarak o şekilde filtreleme yaptım :D
    // -------------------------------------------------------------------------
    public List<Task> getDailyTasks() {
        // Bugünün tarihine denk gelenleri alıyoruz
        LocalDate today = LocalDate.now();
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getTaskType() == TaskType.TASK1) {
                // TASK1 => deadline bazlı
                if (t.getDeadline() != null && t.getDeadline().toLocalDate().equals(today)) {
                    result.add(t);
                }
            } else {
                // TASK2 => startTime veya endTime bugünün tarihi ise
                if (t.getStartTime() != null && t.getStartTime().toLocalDate().equals(today)) {
                    result.add(t);
                } else if (t.getEndTime() != null && t.getEndTime().toLocalDate().equals(today)) {
                    result.add(t);
                }
            }
        }
        return result;
    }

    public List<Task> getWeeklyTasks() {
        // Bugün + 7 gün mantığıyla düşündüm
        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);
        List<Task> result = new ArrayList<>();

        for (Task t : tasks) {
            if (t.getTaskType() == TaskType.TASK1) {
                LocalDateTime dl = t.getDeadline();
                if (dl != null) {
                    LocalDate d = dl.toLocalDate();
                    // eğer d >= today ve d <= weekLater
                    if (!d.isBefore(today) && !d.isAfter(weekLater)) {
                        result.add(t);
                    }
                }
            } else {
                // TASK2 => startTime veya endTime 7 gün içinde mi?
                LocalDateTime st = t.getStartTime();
                LocalDateTime en = t.getEndTime();
                boolean inRange = false;

                if (st != null) {
                    LocalDate sDate = st.toLocalDate();
                    if (!sDate.isBefore(today) && !sDate.isAfter(weekLater)) {
                        inRange = true;
                    }
                }
                if (en != null && !inRange) {
                    LocalDate eDate = en.toLocalDate();
                    if (!eDate.isBefore(today) && !eDate.isAfter(weekLater)) {
                        inRange = true;
                    }
                }
                if (inRange) {
                    result.add(t);
                }
            }
        }
        return result;
    }
}
