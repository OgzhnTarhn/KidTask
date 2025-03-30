public enum WishStatus {
    PENDING,   // Onay bekliyor (eklenince)
    APPROVED,  // Onaylandı (WISH_CHECKED ... APPROVED)
    REJECTED   // Reddedildi (WISH_CHECKED ... REJECTED)
}