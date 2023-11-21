package TerraTech.BranchManagementBackend.exceptions.manager;

public class managerNotFoundException extends RuntimeException {
    public managerNotFoundException() {
        super("Can't find manager!");
    }
}