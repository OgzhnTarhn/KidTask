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
            Child c = wish.getChild();
            if (c == null) {
                System.out.println("No associated child for this wish!");
                return;
            }
            if (c.getCurrentLevel() < requiredLevel) {
                System.out.println("Child's level (" + c.getCurrentLevel() + ") is insufficient for required level "
                        + requiredLevel + ". Rejecting wish " + wish.getWishId());
                wish.setStatus(WishStatus.REJECTED);
                return;
            }

            int cost = wish.getPrice(); // Wish'in parası
            boolean success = c.deductPoints(cost);
            if (!success) {
                System.out.println("Child doesn't have enough points ("
                        + c.getTotalPoints() + ") for cost " + cost
                        + ". Wish remains pending or reject if you want.");
                // Yetersiz bakiye => isterseniz REJECT
                wish.setStatus(WishStatus.REJECTED);
                return;
            }

            wish.setStatus(WishStatus.APPROVED);
            wish.setRequiredLevel(requiredLevel);
            System.out.println("Wish " + wish.getWishId() + " => APPROVED, "
                    + cost + " points deducted from child's budget.");
        }
    }

    public void rejectWish(Wish wish) {
        if (wish.getStatus() == WishStatus.PENDING) {
            wish.setStatus(WishStatus.REJECTED);
            System.out.println("Wish " + wish.getWishId() + " rejected.");
        }
    }

    public void addBudgetCoin(Child child, int extraPoints) {

        child.addPoints(extraPoints);
    }
}
