import java.util.ArrayList;
import java.util.List;

public class Child extends User {
    private int totalPoints;
    private double averageRating;  // Onaylanan task'ların yıldız ortalaması
    private int currentLevel;      // Seviyesi
    private List<Task> tasks;
    private List<Wish> wishes;

    public Child(String userId, String name) {
        super(userId, name);
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

    // Çocuk görevi tamamladığını belirtir => DONE
    public void completeTask(Task task) {
        if (tasks.contains(task) && task.getStatus() == TaskStatus.TODO) {
            task.setStatus(TaskStatus.DONE);
        }
    }

    // Puan ekleme
    public void addPoints(int points) {
        this.totalPoints += points;
    }

    // Rating bazında seviye güncelleme (örnek mantık)
    public void updateLevelByRating(int newRating) {
        // Basit bir yaklaşım: averageRating güncellenir
        // Onaylanan task sayısı:
        int approvedTasksCount = 0;
        for (Task t : tasks) {
            if (t.getStatus() == TaskStatus.APPROVED) {
                approvedTasksCount++;
            }
        }
        // (Eski ortalama * (n-1) + newRating) / n gibi bir yaklaşım
        double oldTotal = averageRating * (approvedTasksCount - 1);
        this.averageRating = (oldTotal + newRating) / approvedTasksCount;

        // Örnek seviye hesaplama (SENG 272 doc'unda puana göre de bir tablo var,
        // ama burada rating'e göre de yapabiliriz):
        // 1.0 - 2.0 => L1, 2.0 - 3.0 => L2, 3.0 - 4.0 => L3, 4.0 - 5.0 => L4
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

    // Örnek: totalPoints'e göre de seviye hesaplamak isterseniz
    // (SENG 272 dokümanındaki tablo 40,60,80 barajları vb.)
    // public void updateLevelByPoints() {
    //     if (totalPoints <= 40) currentLevel = 1;
    //     else if (totalPoints <= 60) currentLevel = 2;
    //     else if (totalPoints <= 80) currentLevel = 3;
    //     else currentLevel = 4;
    // }
}