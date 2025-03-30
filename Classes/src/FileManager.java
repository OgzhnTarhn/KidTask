import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FileManager {

    // Tek bir Child örneğiyle çalışacağımız varsayımı
    // Eğer birden fazla Child varsa, satır formatında ChildId da yer almalıdır.
    private Child defaultChild;

    public FileManager(Child child) {
        this.defaultChild = child;
    }

    // "commands.txt" dosyasını satır satır okuyup,
    // ADD_TASK1 / ADD_TASK2 / ADD_WISH1 / ADD_WISH2 komutlarını parse ediyoruz.
    public void loadCommands(String commandsFilePath,
                             TaskManager taskManager,
                             WishManager wishManager) {

        try (BufferedReader br = new BufferedReader(new FileReader(commandsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("ADD_TASK1")) {
                    parseAddTask1(line, taskManager);
                } else if (line.startsWith("ADD_TASK2")) {
                    parseAddTask2(line, taskManager);
                } else if (line.startsWith("ADD_WISH1")) {
                    parseAddWish1(line, wishManager);
                } else if (line.startsWith("ADD_WISH2")) {
                    parseAddWish2(line, wishManager);
                } else {
                    // Farklı komutlar geldiyse burada işleyebilirsiniz
                    System.out.println("Unrecognized command: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Örnek satır formatı (ADD_TASK1):
    // ADD_TASK1 T 101 "Math Homework" "Solve pages 1-10" 2025-03-01 15:00 10
    //
    // 1) Komut adı: ADD_TASK1
    // 2) Kim ekliyor? T (Teacher) veya F (Parent)
    // 3) TaskId (ör: 101)
    // 4) Title (tırnak içinde -> "Math Homework")
    // 5) Description (tırnak içinde -> "Solve pages 1-10")
    // 6) Deadline Date (yyyy-MM-dd)
    // 7) Deadline Time (HH:mm)
    // 8) Points (int)
    private void parseAddTask1(String line, TaskManager manager) {
        try {
            // Komut adını atla
            String rest = line.substring("ADD_TASK1".length()).trim(); // "T 101 "Math Homework" "Solve pages" 2025-03-01 15:00 10"

            // 1) Kim ekledi? -> ilk harf T veya F
            String userType = rest.substring(0, 1);  // "T" veya "F"
            rest = rest.substring(1).trim();         // "101 "Math Homework" "Solve pages" 2025-03-01 15:00 10"

            // 2) TaskId (ilk boşluğa kadar)
            int firstSpace = rest.indexOf(' ');
            String taskId = rest.substring(0, firstSpace); // "101"
            rest = rest.substring(firstSpace).trim();      // "\"Math Homework\" \"Solve pages 1-10\" 2025-03-01 15:00 10"

            // 3) Title (tırnak arasında)
            String title = extractQuotedText(rest);
            // tırnaktan sonraki kısmı kes
            rest = removeFirstQuotedText(rest).trim(); // "\"Solve pages 1-10\" 2025-03-01 15:00 10"

            // 4) Description
            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim(); // "2025-03-01 15:00 10"

            // 5) Deadline Date
            firstSpace = rest.indexOf(' ');
            String dateStr = rest.substring(0, firstSpace);  // "2025-03-01"
            rest = rest.substring(firstSpace).trim();        // "15:00 10"

            // 6) Deadline Time
            firstSpace = rest.indexOf(' ');
            String timeStr = rest.substring(0, firstSpace);  // "15:00"
            rest = rest.substring(firstSpace).trim();        // "10"

            // 7) Points
            int points = Integer.parseInt(rest); // "10"

            // LocalDateTime
            LocalDate date = LocalDate.parse(dateStr);
            LocalTime time = LocalTime.parse(timeStr);
            LocalDateTime deadline = LocalDateTime.of(date, time);

            // Kim ekledi? T => Teacher, F => Parent
            User assignedBy = createUserByType(userType);

            // Yeni Task oluştur
            Task newTask = new Task(taskId, title, desc,
                    TaskType.TASK1,
                    deadline,
                    null, null, // startTime, endTime yok
                    points,
                    assignedBy);

            // DefaultChild'a atayalım (İsterseniz satırdan childId de parse edebilirsiniz)
            newTask.setAssignedChild(defaultChild);
            defaultChild.addTask(newTask);

            // Manager'a ekle
            manager.addTask(newTask);

            System.out.println("ADD_TASK1 parsed -> " + newTask.toString());
        } catch (Exception e) {
            System.out.println("Error parsing ADD_TASK1: " + e.getMessage());
        }
    }

    // Benzer mantıkta ADD_TASK2 (start/end time var)
    // Örnek satır:
    // ADD_TASK2 T 102 "Science Project" "Build a volcano" 2025-03-05 14:00 2025-03-05 16:00 15
    private void parseAddTask2(String line, TaskManager manager) {
        try {
            String rest = line.substring("ADD_TASK2".length()).trim();

            String userType = rest.substring(0, 1);
            rest = rest.substring(1).trim();

            int firstSpace = rest.indexOf(' ');
            String taskId = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // Deadline date/time
            firstSpace = rest.indexOf(' ');
            String deadlineDateStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            firstSpace = rest.indexOf(' ');
            String deadlineTimeStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            // Start/End time dedim ama aslında SENG 272 doc'a göre
            // "TASK2 ... start_date/time end_date/time"?
            // Burada doc tam net değil, siz projenize göre uyarlayın.
            // Örneğimizde deadline = start, startTime = end ???
            // Pek mantıklı değil, ama örneği basit tutalım.

            LocalDateTime deadline = LocalDateTime.of(LocalDate.parse(deadlineDateStr),
                    LocalTime.parse(deadlineTimeStr));

            firstSpace = rest.indexOf(' ');
            String endDateStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            firstSpace = rest.indexOf(' ');
            String endTimeStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDateStr),
                    LocalTime.parse(endTimeStr));

            int points = Integer.parseInt(rest);

            User assignedBy = createUserByType(userType);

            Task newTask = new Task(taskId, title, desc,
                    TaskType.TASK2,
                    deadline,
                    deadline, // startTime = deadline (örnek)
                    endDateTime,
                    points,
                    assignedBy);

            newTask.setAssignedChild(defaultChild);
            defaultChild.addTask(newTask);

            manager.addTask(newTask);
            System.out.println("ADD_TASK2 parsed -> " + newTask.toString());

        } catch (Exception e) {
            System.out.println("Error parsing ADD_TASK2: " + e.getMessage());
        }
    }

    // Örnek satır: ADD_WISH1 W201 "New Lego Set" "150 TL"
    private void parseAddWish1(String line, WishManager manager) {
        try {
            String rest = line.substring("ADD_WISH1".length()).trim();

            // Wish Id
            int firstSpace = rest.indexOf(' ');
            String wishId = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // WISH1 => Ürün, startTime/endTime yok
            Wish newWish = new Wish(wishId, title, desc,
                    WishType.WISH1,
                    null, null,
                    defaultChild);

            defaultChild.addWish(newWish);
            manager.addWish(newWish);

            System.out.println("ADD_WISH1 parsed -> " + newWish.toString());
        } catch (Exception e) {
            System.out.println("Error parsing ADD_WISH1: " + e.getMessage());
        }
    }

    // Örnek satır: ADD_WISH2 W202 "Go to Cinema" "Popcorn included" 2025-03-10 14:00 2025-03-10 16:00
    private void parseAddWish2(String line, WishManager manager) {
        try {
            String rest = line.substring("ADD_WISH2".length()).trim();

            int firstSpace = rest.indexOf(' ');
            String wishId = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            String title = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            String desc = extractQuotedText(rest);
            rest = removeFirstQuotedText(rest).trim();

            // Başlangıç tarih/saat
            firstSpace = rest.indexOf(' ');
            String startDateStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            firstSpace = rest.indexOf(' ');
            String startTimeStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            // Bitiş tarih/saat
            firstSpace = rest.indexOf(' ');
            String endDateStr = rest.substring(0, firstSpace);
            rest = rest.substring(firstSpace).trim();

            firstSpace = rest.indexOf(' ');
            String endTimeStr;
            if (firstSpace == -1) {
                // Son parametre yok?
                endTimeStr = rest;
            } else {
                endTimeStr = rest.substring(0, firstSpace);
                rest = rest.substring(firstSpace).trim();
            }

            LocalDateTime startDateTime = LocalDateTime.of(
                    LocalDate.parse(startDateStr),
                    LocalTime.parse(startTimeStr)
            );
            LocalDateTime endDateTime = LocalDateTime.of(
                    LocalDate.parse(endDateStr),
                    LocalTime.parse(endTimeStr)
            );

            Wish newWish = new Wish(wishId, title, desc,
                    WishType.WISH2,
                    startDateTime,
                    endDateTime,
                    defaultChild);

            defaultChild.addWish(newWish);
            manager.addWish(newWish);

            System.out.println("ADD_WISH2 parsed -> " + newWish.toString());

        } catch (Exception e) {
            System.out.println("Error parsing ADD_WISH2: " + e.getMessage());
        }
    }

    // Yardımcı metod: userType = "T" => Teacher, "F" => Parent
    // Gerçek projede userId gibi şeyleri de parse edebilirsiniz
    private User createUserByType(String userType) {
        if (userType.equals("T")) {
            return new Teacher("T001", "TeacherBob");
        } else {
            return new Parent("P001", "ParentAlice");
        }
    }

    // Aşağıdaki iki metod, tırnak içindeki metni bulup çıkarmak için basit bir yaklaşım
    private String extractQuotedText(String text) {
        // text örn: "\"Math Homework\" \"Solve pages\" 2025-03-01..."
        int firstQuote = text.indexOf('"');
        int secondQuote = text.indexOf('"', firstQuote + 1);
        return text.substring(firstQuote + 1, secondQuote);
    }

    private String removeFirstQuotedText(String text) {
        int firstQuote = text.indexOf('"');
        int secondQuote = text.indexOf('"', firstQuote + 1);
        String before = text.substring(0, firstQuote);
        String after = text.substring(secondQuote + 1);
        return before + after;
    }
}
