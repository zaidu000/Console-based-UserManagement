import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    String name;
    String email;
    List<String> mobile;

    User(String name, String email, List<String> mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nEmail: " + email + "\nMobile Numbers: " + mobile;
    }

}

public class UserManagement {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> userDB = new HashMap<>();

    public static void main(String args[]) {
        while (true) {
            System.out.println("\n.....................Select Any 1 Operation.....................\n\n");
            System.out
                    .println("1. Create User\n2. delete user\n3. Show User\n4. Show All User\n5. Update User\n6. Exit");
            int choise = Integer.parseInt(sc.nextLine());
            switch (choise) {
                case 1 -> createUser();
                case 2 -> deleteUser();
                case 3 -> showUser();
                case 4 -> showAll();
                case 5 -> updateUser();
                case 6 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice. Try Again.");
            }
        }
    }

    public static void createUser() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();

        // Check for duplicate email
        if (userDB.containsKey(email)) {
            System.out.println("User already exists.");
            return;
        }

        // Validate email format
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            System.out.println("Invalid email format.");
            return;
        }

        List<String> mobileList = new ArrayList<>();

        while (true) {
            System.out.print("Enter Mobile Number: ");
            String mobile = sc.nextLine().trim();

            // Validate mobile number
            if (!mobile.matches("^[6-9]\\d{9}$")) {
                System.out.println("Invalid mobile number. It must be 10 digits and start with 6-9.");
            } else {
                mobileList.add(mobile);
            }

            System.out.print("Do you want to add another mobile number? (Yes/No): ");
            String answer = sc.nextLine().trim();

            if (!answer.equalsIgnoreCase("yes")) {
                break;
            }
        }

        User user = new User(name, email, mobileList);
        userDB.put(email, user);
        System.out.println("User created successfully.");
    }

    public static void deleteUser() {
        System.out.println("Enter Email");
        String email = sc.nextLine();
        if (!userDB.containsKey(email)) {
            throw new UserNotFoundException("User with email " + email + " not found.");
        }

        userDB.remove(email);
        System.out.println("user deleted successfully.");
    }

    public static void showUser() {
        System.out.println("Enter Email");
        String email = sc.nextLine();
        if (!userDB.containsKey(email)) {
            throw new UserNotFoundException("User with email " + email + " not found.");
        }

        System.out.print(userDB.get(email));
    }

    public static void showAll() {
        if (userDB.isEmpty()) {
            System.out.println("No users found in the system.");
            return;
        }

        System.out.println("All Users:\n");
        for (User user : userDB.values()) {
            System.out.println(user);
            System.out.println("-----------------------------");
        }
    }

    public static void updateUser() {
        System.out.print("Enter Email of the user to update: ");
        String email = sc.nextLine().trim();

        if (!userDB.containsKey(email)) {
            throw new UserNotFoundException("User with email " + email + " not found.");
        }

        User user = userDB.get(email);

        System.out.print("Current Name: " + user.name + "\nEnter New Name (leave blank to keep unchanged): ");
        String newName = sc.nextLine().trim();
        if (!newName.isEmpty()) {
            user.name = newName;
        }

        System.out.println("Do you want to update mobile numbers? (Yes/No): ");
        String updateMob = sc.nextLine().trim();
        if (updateMob.equalsIgnoreCase("yes")) {
            List<String> newMobiles = new ArrayList<>();
            while (true) {
                System.out.print("Enter Mobile Number: ");
                String mobile = sc.nextLine().trim();
                if (!mobile.matches("^[6-9]\\d{9}$")) {
                    System.out.println("Invalid mobile number. It must be 10 digits and start with 6-9.");
                } else {
                    newMobiles.add(mobile);
                }

                System.out.print("Add another number? (Yes/No): ");
                String more = sc.nextLine().trim();
                if (!more.equalsIgnoreCase("yes")) {
                    break;
                }
            }
            user.mobile = newMobiles;
        }

        System.out.println("User details updated successfully.");
    }

}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
