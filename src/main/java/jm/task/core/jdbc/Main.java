package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
public class Main {
    private static final UserServiceImpl userService = new UserServiceImpl();
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Владимир", "Путин", (byte) 72);
        userService.saveUser("Дмитрий", "Медведев", (byte) 59);
        userService.saveUser("Николай", "Патрушев", (byte) 73);
        userService.saveUser("Дмитрий", "Песков", (byte) 57);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
