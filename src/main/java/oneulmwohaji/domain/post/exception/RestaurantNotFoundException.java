package oneulmwohaji.domain.post.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(final String message) {
        super(message);
    }

    public RestaurantNotFoundException() {
        this("레스토랑을 찾을 수 없습니다.");
    }
}
