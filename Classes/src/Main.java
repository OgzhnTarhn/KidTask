public class Main {
    public static void main(String[] args) {
        // Bir Parent, bir Teacher, bir Child oluşturalım
        Parent parent = new Parent("P001", "Alice");
        Teacher teacher = new Teacher("T001", "Bob");
        Child child = new Child("C001", "Charlie");

        // Managerlar
        TaskManager taskManager = new TaskManager();
        WishManager wishManager = new WishManager();

        // FileManager (komutları parse edip Parent/Teacher/Child'a yönlendirecek)
        FileManager fileManager = new FileManager(parent, teacher, child);

        // commands.txt yolunu parametreden alabilir veya sabit verebilirsiniz
        String cmdFile = "C:/Kullanıcılar/Oğuzhan/IdeaProjects/Seng272 project/Classes/src/commands"; // Aynı klasörde
        fileManager.loadCommands(cmdFile, taskManager, wishManager);

        System.out.println("\nProgram finished. Child points = " + child.getTotalPoints()
                + ", level = " + child.getCurrentLevel());
    }
}
