# Manual Test Plan

Run the tests only against the local training application. Compile each source folder separately using `javac *.java`.

## TC-01: Embedded administrator credential (Vulnerable build)
1. Start `src-vulnerable/StudentPortalApp`.
2. Choose `2` (Log in).
3. Enter username `admin` and password `Admin@123`.
4. Expected vulnerable result: login succeeds and displays role `ADMIN`.
5. Run the same inputs against the secure build. Expected secure result: login fails because no account named `admin` was registered.

## TC-02: Wrong password accepted (Vulnerable build)
1. Register username `alice`, password `Alice@2026!`, full name `Alice Example`, department `Computer Science`.
2. Log out if needed, then choose Login.
3. Enter `alice` and `wrong-password`.
4. Expected vulnerable result: login succeeds due to the incorrect `&&` condition.
5. Repeat in secure build using the same registration inputs. Expected secure result: login fails.

## TC-03: IDOR / missing ownership check
1. In one run, register Alice using `Alice@2026!` and Bob using `Bob@2026!!`.
2. Their profile IDs should be `S1001` and `S1002` in that order.
3. In the vulnerable build, log in as Alice and request profile `S1002`.
4. Expected vulnerable result: Bob's profile is displayed.
5. Repeat in secure build, logging in as Alice with her correct password and requesting `S1002`.
6. Expected secure result: `Access denied. You can only view your own profile.`

## TC-04: Valid user can view their own profile
1. Register a test account and note its assigned ID.
2. Log in using its correct password and request its own ID.
3. Expected result in both builds: the user's own profile is displayed.

## TC-05: Password policy
1. Attempt registration with a short/simple password such as `password`.
2. Expected result in both builds: registration is rejected by the password policy.

## Evidence to capture
- Screenshot of the hardcoded credential branch in the vulnerable source.
- Screenshot of the flawed `&&` condition and the wrong-password login result.
- Screenshot of `viewStudentProfile()` with a cross-user profile request.
- Screenshot of the symbolic path-condition table.
- Screenshot of the corrected conditions in the secure source and the secure rejection outputs.
