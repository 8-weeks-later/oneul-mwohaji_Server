package oneulmwohaji.domain.member.exception;

public class MemberExistException extends RuntimeException {
    public MemberExistException(final String message) {
        super(message);
    }

    public MemberExistException() {
        this("멤버가 이미 존재합니다.");
    }
}
