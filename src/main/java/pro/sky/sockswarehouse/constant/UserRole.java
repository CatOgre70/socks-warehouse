package pro.sky.sockswarehouse.constant;

public enum UserRole {

    USER("User"),
    ADMIN("Admin");

    public final String description;

    UserRole(String description) {
        this.description = description;
    }


}
