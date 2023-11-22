package TerraTech.BranchManagementBackend.exceptions.manager;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException() {
        super("Can't find manager!");
    }
}