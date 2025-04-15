import java.util.ArrayList;
import java.util.List;

public class WishManager {
    private List<Wish> wishes;

    public WishManager() {
        this.wishes = new ArrayList<>();
    }

    public void addWish(Wish wish) {
        wishes.add(wish);
    }

    public Wish getWishById(String wishId) {
        for (Wish w : wishes) {
            if (w.getWishId().equals(wishId)) {
                return w;
            }
        }
        return null;
    }

    public List<Wish> getAllWishes() {
        return wishes;
    }

    public void approveWish(String wishId, int requiredLevel) {
        Wish w = getWishById(wishId);
        if (w != null && w.getStatus() == WishStatus.PENDING) {
            w.setStatus(WishStatus.APPROVED);
            w.setRequiredLevel(requiredLevel);
        }
    }

    public void rejectWish(String wishId) {
        Wish w = getWishById(wishId);
        if (w != null && w.getStatus() == WishStatus.PENDING) {
            w.setStatus(WishStatus.REJECTED);
        }
    }
}
