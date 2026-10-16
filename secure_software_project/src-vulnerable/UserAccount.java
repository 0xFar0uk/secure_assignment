public final class UserAccount {
    private final String username;
    private final String salt;
    private final String passwordHash;
    private final String role;

    private UserAccount(String username, String salt, String passwordHash, String role) {
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public static UserAccount register(String username, String password) {
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hashPassword(password, salt);
        return new UserAccount(username, salt, hash, "USER");
    }

    static UserAccount createAdminSession() {
        return new UserAccount("admin", "", "", "ADMIN");
    }

    public String getUsername() {
        return username;
    }

    public boolean passwordMatches(String password) {
        if ("ADMIN".equals(role)) {
            return false;
        }
        return PasswordHasher.verifyPassword(password, salt, passwordHash);
    }

    public String getRole() {
        return role;
    }
}
