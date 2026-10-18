import java.util.LinkedList;
import java.util.Scanner;

public final class StudentPortalApp {
    private final Scanner scanner = new Scanner(System.in);
    private final LinkedList<UserAccount> users = new LinkedList<>();
    private final LinkedList<StudentProfile> studentProfiles = new LinkedList<>();
    private int nextStudentNumber = 1001;

    public static void main(String[] args) {
        new StudentPortalApp().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            System.out.println("\n Student Portal App");
            System.out.println("1. Register student account");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            String choice = readLine("Choose an option: ").trim();

            switch (choice) {
                case "1":
                    registerStudent();
                    break;
                case "2":
                    logIn();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    private void registerStudent() {
        System.out.println("\nStudent Registration");
        String username = readLine("Create username: ").trim();
        if (username.isBlank() || findUser(username) != null) {
            System.out.println("Username is blank or already in use.");
            return;
        }

        String password = readLine("Create password (10+ chars, upper/lower/digit/symbol): ");
        if (!hasStrongPassword(password)) {
            System.out.println("Password does not meet the required policy.");
            return;
        }

        String fullName = readLine("Full name: ").trim();
        String department = readLine("Department: ").trim();
        if (fullName.isBlank() || department.isBlank()) {
            System.out.println("Name and department cannot be blank.");
            return;
        }

        users.add(UserAccount.register(username, password));
        String studentId = String.format("S%04d", nextStudentNumber++);
        studentProfiles.add(new StudentProfile(studentId, username, fullName, department));
        System.out.println("Registration completed. Your student ID is " + studentId + ".");
    }

    private void logIn() {
        System.out.println("\n-Login-");
        String username = readLine("Username: ").trim();
        String password = readLine("Password: ");
        UserAccount account = authenticate(username, password);

        if (account == null) {
            System.out.println("Login failed. Check your credentials.");
            return;
        }

        System.out.println("Login successful. Welcome, " + account.getUsername()
                + " (role: " + account.getRole() + ").");
        showDashboard(account);
    }

    private UserAccount authenticate(String username, String password) {
        // VULN-1: A reusable administrator credential is embedded in source code.
        if ("admin".equals(username) && "Admin@123".equals(password)) {
            return UserAccount.createAdminSession();
        }

        UserAccount account = findUser(username);
        if (account == null) {
            return null;
        }

        // VULN-2: The AND operator lets an existing username pass with any password.
        if (!account.getUsername().equals(username) && !account.passwordMatches(password)) {
            return null;
        }
        return account;
    }

    private void showDashboard(UserAccount currentUser) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--Student Dashboard--");
            System.out.println("1. View student profile by ID");
            System.out.println("2. Log out");
            String choice = readLine("Choose an option: ").trim();
            switch (choice) {
                case "1":
                    viewStudentProfile(currentUser);
                    break;
                case "2":
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 or 2.");
            }
        }
    }

    private void viewStudentProfile(UserAccount currentUser) {
        String studentId = readLine("Enter student ID: ").trim();
        StudentProfile profile = findStudentById(studentId);
        if (profile == null) {
            System.out.println("Student profile not found.");
            return;
        }

        // VULN-3: The code finds a profile by ID but never checks who owns it.
        System.out.println("\n" + profile.toDisplayString());
    }

    private UserAccount findUser(String username) {
        for (UserAccount account : users) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    private StudentProfile findStudentById(String studentId) {
        for (StudentProfile profile : studentProfiles) {
            if (profile.getStudentId().equals(studentId)) {
                return profile;
            }
        }
        return null;
    }

    private boolean hasStrongPassword(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{10,}$");
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
