public final class UserAccount {
    private final String username;
    private final String salt;
    private final String passwordHash;

    private UserAccount(String username, String salt, String passwordHash) {
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
    }

    public static UserAccount register(String username, String password) {
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hashPassword(password, salt);
        return new UserAccount(username, salt, hash);
    }

    public String getUsername() {
        return username;
    }

    public boolean passwordMatches(String password) {
        return PasswordHasher.verifyPassword(password, salt, passwordHash);
    }
}
