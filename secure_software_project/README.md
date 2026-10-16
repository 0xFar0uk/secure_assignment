# Secure Software Development Assignment — Student Portal

This package contains two runnable Java console applications:

- `src-vulnerable/`: intentionally vulnerable training build with exactly three planned findings.
- `src-secure/`: remediated build showing the corresponding fixes.
- `REPORT.docx` / `REPORT.pdf`: printable academic report.
- `EXPLANATION_AR.md`: Egyptian Arabic guide to the design, techniques, tests, and code sections.
- `LINE_BY_LINE_AR.md`: numbered, detailed Arabic explanation of the Java files.
- `TEST_PLAN.md`: reproducible manual test cases.

## Requirements

- JDK 17 or newer.
- No third-party dependencies.
- Data is held in memory only and resets when the application exits.

## Compile and run the vulnerable build

Open a terminal in `src-vulnerable/` and run:

```bash
javac *.java
java StudentPortalApp
```

## Compile and run the secure build

Open a separate terminal in `src-secure/` and run:

```bash
javac *.java
java StudentPortalApp
```

Compile each folder separately because both contain a class named `StudentPortalApp`.

## Demo credentials and safe-use note

The vulnerable build intentionally accepts the embedded training credential `admin` / `Admin@123`. Do not reuse it for any real system. The secure build removes this shortcut. Register fresh test users while the application is running; registration data is not persisted.

## Selected techniques

1. Static Analysis — inspect source for the embedded credential and dangerous authentication/authorization logic.
2. Symbolic Execution — derive the login condition and show why the wrong-password path is accepted.
3. Debugging — set breakpoints in `authenticate()` and `viewStudentProfile()` and inspect the account/profile ownership values.

These are analysis techniques, not vulnerability names. The three planned vulnerabilities are hardcoded credentials, an authentication logic flaw, and missing object-level authorization (IDOR/Broken Access Control).
