import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FileManager {
    private Parent parent;
    private Teacher teacher;
    private Child child;

    // Tek bir Child, Parent, Teacher oluşturuyoruz örnek olsun diye
    public FileManager(Parent p, Teacher t, Child c) {
        this.parent = p;
        this.teacher = t;
        this.child = c;
    }

    public void loadCommands(String filePath,
                             TaskManager taskManager,
                             WishManager wishManager) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                // Komutları parse
                processCommand(line, taskManager, wishManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String cmd,
                                TaskManager taskManager,
                                WishManager wishManager) {
        // Basit bir substring / split yaklaşımı
        if (cmd.startsWith("ADD_TASK1")) {
            parseAddTask1(cmd, taskManager);
        } else if (cmd.startsWith("ADD_TASK2")) {
            parseAddTask2(cmd, taskManager);
        } else if (cmd.startsWith("TASK_DONE")) {
            parseTaskDone(cmd, taskManager);
        } else if (cmd.startsWith("TASK_CHECKED")) {
            parseTaskChecked(cmd, taskManager);
        } else if (cmd.startsWith("ADD_WISH1")) {
            parseAddWish1(cmd, wishManager);
        } else if (cmd.startsWith("ADD_WISH2")) {
            parseAddWish2(cmd, wishManager);
        } else if (cmd.startsWith("WISH_CHECKED")) {
            parseWishChecked(cmd, wishManager);
        } else if (cmd.startsWith("ADD_BUDGET_COIN")) {
            parseAddBudgetCoin(cmd);
        } else if (cmd.startsWith("PRINT_BUDGET")) {
            System.out.println("Child's budget: " + child.getTotalPoints());
        } else if (cmd.startsWith("PRINT_STATUS")) {
            System.out.println("Child's level: " + child.getCurrentLevel());
        } else if (cmd.startsWith("LIST_ALL_TASKS")) {
            for (Task t : taskManager.getAllTasks()) {
                System.out.println(t.toString());
            }
        } else if (cmd.startsWith("LIST_ALL_WISHES")) {
            for (Wish w : wishManager.getAllWishes()) {
                System.out.println(w.toString());
            }
        } else {
            System.out.println("Unknown command: " + cmd);
        }
    }

    private void parseAddTask1(String line, TaskManager tm) {
        // Format: ADD_TASK1 T 101 "Math Homework" "Solve pages" 2025-03-01 15:00 10
        try {
            String rest = line.substring("ADD_TASK1".length()).trim(); // T 101 "Math" ...
            // userType
            String userType = rest.substring(0, 1);
            rest = rest.substring(1).trim();

            // TaskId
            int sp = rest.indexOf(' ');
            String taskId = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            // Title
            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // Desc
            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // Date
            sp = rest.indexOf(' ');
            String dateStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            // Time
            sp = rest.indexOf(' ');
            String timeStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            // Points
            int points = Integer.parseInt(rest);

            LocalDate ld = LocalDate.parse(dateStr);
            LocalTime lt = LocalTime.parse(timeStr);
            LocalDateTime deadline = LocalDateTime.of(ld, lt);

            // Kim ekliyor?
            User user = ("T".equals(userType)) ? teacher : parent;
            Task newTask = new Task(taskId, title, desc,
                    TaskType.TASK1,
                    deadline,
                    null,
                    null,
                    points,
                    user);

            // Child'a ata
            newTask.setAssignedChild(child);
            child.addTask(newTask);

            tm.addTask(newTask);
            System.out.println("Added TASK1: " + newTask.toString());
        } catch (Exception e) {
            System.out.println("Error parseAddTask1: " + e.getMessage());
        }
    }

    private void parseAddTask2(String line, TaskManager tm) {
        // Format: ADD_TASK2 F 102 "Science" "Volcano" 2025-03-05 14:00 2025-03-05 16:00 15
        try {
            String rest = line.substring("ADD_TASK2".length()).trim();
            String userType = rest.substring(0, 1);
            rest = rest.substring(1).trim();

            int sp = rest.indexOf(' ');
            String taskId = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // Deadline date/time
            sp = rest.indexOf(' ');
            String dDateStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            sp = rest.indexOf(' ');
            String dTimeStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            LocalDateTime deadline = LocalDateTime.of(LocalDate.parse(dDateStr),
                    LocalTime.parse(dTimeStr));

            // Start - end time (örneğin T doc'a göre)
            sp = rest.indexOf(' ');
            String endDateStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            sp = rest.indexOf(' ');
            String endTimeStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            int points = Integer.parseInt(rest);

            LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDateStr),
                    LocalTime.parse(endTimeStr));

            User user = ("T".equals(userType)) ? teacher : parent;
            Task newTask = new Task(taskId, title, desc,
                    TaskType.TASK2,
                    deadline,
                    deadline, // startTime (örnek)
                    endDateTime,
                    points,
                    user);

            newTask.setAssignedChild(child);
            child.addTask(newTask);

            tm.addTask(newTask);
            System.out.println("Added TASK2: " + newTask.toString());

        } catch (Exception e) {
            System.out.println("Error parseAddTask2: " + e.getMessage());
        }
    }

    private void parseTaskDone(String line, TaskManager tm) {
        // TASK_DONE 101
        try {
            String rest = line.substring("TASK_DONE".length()).trim();
            // rest = "101"
            tm.markTaskDone(rest);
            child.completeTask(tm.getTaskById(rest));
            System.out.println("Task " + rest + " => DONE by child");
        } catch (Exception e) {
            System.out.println("Error parseTaskDone: " + e.getMessage());
        }
    }

    private void parseTaskChecked(String line, TaskManager tm) {
        // TASK_CHECKED 101 5  (Görevi onayla, rating=5)
        try {
            String rest = line.substring("TASK_CHECKED".length()).trim();
            int sp = rest.indexOf(' ');
            String taskId = rest.substring(0, sp);
            String ratingStr = rest.substring(sp).trim();

            int rating = Integer.parseInt(ratingStr);

            Task t = tm.getTaskById(taskId);
            if (t == null) {
                System.out.println("Task not found: " + taskId);
                return;
            }
            // Kim onaylayacak? Teacher/Parent?
            // Bu örnekte basitçe "parent" onaylıyor:
            parent.approveTask(t, rating);
            System.out.println("Task " + taskId + " => checked with rating " + rating);
        } catch (Exception e) {
            System.out.println("Error parseTaskChecked: " + e.getMessage());
        }
    }

    private void parseAddWish1(String line, WishManager wm) {
        // ADD_WISH1 W201 "New Lego Set" "150 TL"
        try {
            String rest = line.substring("ADD_WISH1".length()).trim();

            int sp = rest.indexOf(' ');
            String wishId = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            Wish w = new Wish(wishId, title, desc,
                    WishType.WISH1,
                    null, null,
                    child);
            child.addWish(w);
            wm.addWish(w);

            System.out.println("ADD_WISH1 -> " + w.toString());
        } catch (Exception e) {
            System.out.println("Error parseAddWish1: " + e.getMessage());
        }
    }

    private void parseAddWish2(String line, WishManager wm) {
        // ADD_WISH2 W202 "Go to Cinema" "Popcorn" 2025-03-10 14:00 2025-03-10 16:00
        try {
            String rest = line.substring("ADD_WISH2".length()).trim();

            int sp = rest.indexOf(' ');
            String wishId = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // start date/time
            sp = rest.indexOf(' ');
            String startDateStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            sp = rest.indexOf(' ');
            String startTimeStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            LocalDateTime startDt = LocalDateTime.of(LocalDate.parse(startDateStr),
                    LocalTime.parse(startTimeStr));

            // end date/time
            sp = rest.indexOf(' ');
            String endDateStr = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            String endTimeStr = rest;
            LocalDateTime endDt = LocalDateTime.of(LocalDate.parse(endDateStr),
                    LocalTime.parse(endTimeStr));

            Wish w = new Wish(wishId, title, desc,
                    WishType.WISH2,
                    startDt, endDt,
                    child);
            child.addWish(w);
            wm.addWish(w);

            System.out.println("ADD_WISH2 -> " + w.toString());
        } catch (Exception e) {
            System.out.println("Error parseAddWish2: " + e.getMessage());
        }
    }

    private void parseWishChecked(String line, WishManager wm) {
        // WISH_CHECKED W201 APPROVED 3
        // veya WISH_CHECKED W202 REJECTED
        try {
            String rest = line.substring("WISH_CHECKED".length()).trim();
            int sp = rest.indexOf(' ');
            String wishId = rest.substring(0, sp);
            rest = rest.substring(sp).trim();

            sp = rest.indexOf(' ');
            String decision = (sp == -1) ? rest : rest.substring(0, sp);
            String levelStr = (sp == -1) ? "" : rest.substring(sp).trim();

            Wish w = wm.getWishById(wishId);
            if (w == null) {
                System.out.println("Wish not found: " + wishId);
                return;
            }
            if ("APPROVED".equals(decision)) {
                int lvl = 1;
                if (!levelStr.isEmpty()) {
                    lvl = Integer.parseInt(levelStr);
                }
                parent.approveWish(w, lvl);
                System.out.println("Wish " + wishId + " => APPROVED, reqLevel=" + lvl);
            } else if ("REJECTED".equals(decision)) {
                parent.rejectWish(w);
                System.out.println("Wish " + wishId + " => REJECTED");
            } else {
                System.out.println("Invalid decision: " + decision);
            }
        } catch (Exception e) {
            System.out.println("Error parseWishChecked: " + e.getMessage());
        }
    }

    private void parseAddBudgetCoin(String line) {
        // ADD_BUDGET_COIN 50
        try {
            String rest = line.substring("ADD_BUDGET_COIN".length()).trim();
            int val = Integer.parseInt(rest);
            // Parent veriyormuş gibi
            parent.addBudgetCoin(child, val);
            System.out.println("Added budget coin: " + val);
        } catch (Exception e) {
            System.out.println("Error parseAddBudgetCoin: " + e.getMessage());
        }
    }

    // Yardımcı fonksiyonlar
    private String extractQuotedText(String text) {
        int firstQ = text.indexOf('"');
        int secondQ = text.indexOf('"', firstQ + 1);
        return text.substring(firstQ + 1, secondQ);
    }

    private String removeFirstQuotedText(String text) {
        int firstQ = text.indexOf('"');
        int secondQ = text.indexOf('"', firstQ + 1);
        String before = text.substring(0, firstQ);
        String after = text.substring(secondQ + 1);
        return before + after;
    }
}
