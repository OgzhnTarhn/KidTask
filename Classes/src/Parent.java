import java.time.LocalDateTime;

public class Parent extends User {

    public Parent(String userId, String name) {
        // Rol sabit: "Parent"
        super(userId, name, "Parent");
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

    public void approveTask(Task task, int rating) {
        // Task DONE olduktan sonra onaylanabilir
        if (task.getStatus() == TaskStatus.DONE) {
            task.setStatus(TaskStatus.APPROVED);
            task.setRating(rating);

            // Puan eklenmeli
            Child child = task.getAssignedChild();
            if (child != null) {
                child.addPoints(task.getPoints());
                // Rating ortalaması güncellenecek
                child.recalculateRating();
            }
        }
    }

    public void approveWish(Wish wish, int requiredLevel) {
        if (wish.getStatus() == WishStatus.PENDING) {
            // Çocuğun bu wish'i karşılayacak kadar puanı var mı?
            Child c = wish.getChild();
            if (c != null) {
                int price = wish.getPrice();
                boolean success = c.deductPoints(price);
                if (!success) {
                    System.out.println("Child doesn't have enough budget (" + price + ") for this wish!");
                    // Dileğin durumu PENDING kalsın veya REJECTED yapabilirsiniz.
                    return;
                }
                // Yeterli bütçe varsa price düşülür
            }
            wish.setStatus(WishStatus.APPROVED);
            wish.setRequiredLevel(requiredLevel);
            System.out.println("Wish " + wish.getWishId() + " is approved. "
                    + "Price " + wish.getPrice()
                    + " deducted from child's account.");
        }
    }

    public void rejectWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
        }
    }

    public void addBudgetCoin(Child child, int extraPoints) {

        child.addPoints(extraPoints);
    }
}
