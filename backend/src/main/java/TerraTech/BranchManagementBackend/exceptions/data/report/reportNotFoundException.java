package TerraTech.BranchManagementBackend.exceptions.data.report;

public class reportNotFoundException extends RuntimeException {
    public reportNotFoundException() {
        super("There's no report found!");
    }

}