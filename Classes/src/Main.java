public class Main{
    public static void main(String[] args) {
        // Tek bir Child örneği
        Child child = new Child("C001", "Charlie");

        // Manager örnekleri
        TaskManager taskManager = new TaskManager();
        WishManager wishManager = new WishManager();

        // FileManager'a Child'ı veriyoruz (varsayılan çocuğa atamak için)
        FileManager fileManager = new FileManager(child);

        // commands.txt içindeki komutları yükleyelim
        fileManager.loadCommands("commands.txt", taskManager, wishManager);

        // Tüm Task'ları listeleyelim
        System.out.println("\n--- All Tasks ---");
        for (Task t : taskManager.getAllTasks()) {
            System.out.println(t.toString());
        }

        // Tüm Wish'leri listeleyelim
        System.out.println("\n--- All Wishes ---");
        for (Wish w : wishManager.getAllWishes()) {
            System.out.println(w.toString());
        }

        // Çocuğun seviyesi / puanı
        System.out.println("\nChild total points: " + child.getTotalPoints());
        System.out.println("Child current level: " + child.getCurrentLevel());

        System.out.println("\nProgram finished.");
    }
}
