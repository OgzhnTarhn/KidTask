import java.util.ArrayList;
import java.util.List;

public class Child extends User {
    private int totalPoints;
    private double averageRating;
    private int currentLevel;
    private List<Task> tasks;
    private List<Wish> wishes;

    public Child(String userId, String name) {
        // Rol sabit: "Child"
        super(userId, name, "Child");
        this.tasks = new ArrayList<>();
        this.wishes = new ArrayList<>();
        this.currentLevel = 1;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addWish(Wish wish) {
        wishes.add(wish);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    // Görevi tamamladı (TASK_DONE)
    public void completeTask(Task task) {
        if (tasks.contains(task) && task.getStatus() == TaskStatus.TODO) {
            task.setStatus(TaskStatus.DONE);
        }
    }

    public boolean deductPoints(int amount) {
        if (this.totalPoints >= amount) {
            this.totalPoints -= amount;
            return true;
        } else {
            return false; // Yeterli bütçe yok
        }
    }

    // Ek puan
    public void addPoints(int points) {
        this.totalPoints += points;
    }

    // Rating ortalamaya göre level hesaplaması
    public void updateLevelByRating(int newRating) {
        int approvedTasksCount = 0;
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.APPROVED) {
                approvedTasksCount++;
            }
        }

        if (approvedTasksCount > 0) {
            double oldTotalRating = averageRating * (approvedTasksCount - 1);
            this.averageRating = (oldTotalRating + newRating) / approvedTasksCount;
        } else {
            this.averageRating = newRating;
        }

        if (averageRating < 2.0) {
            currentLevel = 1;
        } else if (averageRating < 3.0) {
            currentLevel = 2;
        } else if (averageRating < 4.0) {
            currentLevel = 3;
        } else {
            currentLevel = 4;
        }
    }
    public void recalculateRating() {
        // Tüm "APPROVED" görevlerin rating'lerini topla
        double sum = 0;
        int count = 0;
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.APPROVED) {
                sum += t.getRating();
                count++;
            }
        }
        if (count > 0) {
            this.averageRating = sum / count;
        } else {
            this.averageRating = 0.0;
        }

        // Seviyeyi rating'e göre güncelle
        if (averageRating < 2.0) {
            currentLevel = 1;
        } else if (averageRating < 3.0) {
            currentLevel = 2;
        } else if (averageRating < 4.0) {
            currentLevel = 3;
        } else {
            currentLevel = 4;
        }
    }
}
