import java.time.LocalDateTime;

public class Wish {
    private String wishId;
    private String title;
    private String description;
    private WishType wishType;
    private WishStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int requiredLevel;
    private Child child;

    // YENİ: price alanı
    private int price;

    public Wish(String wishId,
                String title,
                String description,
                WishType wishType,
                LocalDateTime startTime,
                LocalDateTime endTime,
                Child child) {

        this.wishId = wishId;
        this.title = title;
        this.description = description;
        this.wishType = wishType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = WishStatus.PENDING;
        this.requiredLevel = 1;
        this.child = child;
        this.price = 0; // Varsayılan
    }

    public String getWishId() {
        return wishId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public WishType getWishType() {
        return wishType;
    }

    public WishStatus getStatus() {
        return status;
    }

    public void setStatus(WishStatus status) {
        this.status = status;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public Child getChild() {
        return child;
    }

    // Price alanı için getter/setter
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Wish[" + wishId
                + ", " + title
                + ", " + wishType
                + ", status=" + status
                + ", price=" + price
                + "]";
    }
}
