package oneulmwohaji.domain.member.entity;

public enum AccountType {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    private String accountType;

    AccountType(String accountType) {
        this.accountType = accountType;
    }
}
