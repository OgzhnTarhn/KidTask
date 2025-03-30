import java.time.LocalDateTime;

public class Wish {
    private String wishId;
    private String title;
    private String description;
    private WishType wishType;
    private WishStatus status;
    // WISH2 için tarih-saat
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // Hangi seviyede aktif olacağı
    private int requiredLevel;
    // Bu wish hangi çocuğa ait?
    private Child child;

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
        this.requiredLevel = 1; // default
        this.child = child;
    }

    public String getWishId() {
        return wishId;
    }
    public String getTitle() {
        return title;
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
}
