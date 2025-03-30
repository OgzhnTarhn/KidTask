public class Main {
    public static void main(String[] args) {
        // 1) Parent, Teacher, Child nesnelerini oluşturuyoruz
        Parent parent = new Parent("P001", "Alice");
        Teacher teacher = new Teacher("T001", "Bob");
        Child child = new Child("C001", "Charlie");

        // 2) Manager nesneleri (görevleri ve dilekleri tutan)
        TaskManager taskManager = new TaskManager();
        WishManager wishManager = new WishManager();

        // 3) FileManager (dosyalardan veri okumayı ve komutları işlemeyi yapacak)
        FileManager fileManager = new FileManager(parent, teacher, child);

        // 4) Ayrı dosyalardan (Tasks.txt, Wishes.txt) mevcut verileri yüklüyoruz
        //    Lütfen "loadTasksFromFile" ve "loadWishesFromFile" metodlarını
        //    FileManager'a eklemeniz gerektiğini unutmayın (örnekleri aşağıda).
        fileManager.loadTasksFromFile("C:\\Users\\Oğuzhan\\IdeaProjects\\Seng272 project\\Classes\\src\\Tasks.txt", taskManager);
        fileManager.loadWishesFromFile("C:\\Users\\Oğuzhan\\IdeaProjects\\Seng272 project\\Classes\\src\\Wishes.txt", wishManager);
        fileManager.loadCommands("C:\\Users\\Oğuzhan\\IdeaProjects\\Seng272 project\\Classes\\src\\Commands.txt", taskManager, wishManager);


        // 6) Program sonunda çocuğun güncel puan ve seviyesini ekranda gösteriyoruz
        System.out.println("\nProgram finished. Child points = " + child.getTotalPoints()
                + ", level = " + child.getCurrentLevel());
    }
}
