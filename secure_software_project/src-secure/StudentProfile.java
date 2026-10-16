public final class StudentProfile {
    private final String studentId;
    private final String ownerUsername;
    private final String fullName;
    private final String department;

    public StudentProfile(String studentId, String ownerUsername, String fullName, String department) {
        this.studentId = studentId;
        this.ownerUsername = ownerUsername;
        this.fullName = fullName;
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public String toDisplayString() {
        return "Student ID: " + studentId + "\n"
                + "Full name: " + fullName + "\n"
                + "Department: " + department;
    }
}
