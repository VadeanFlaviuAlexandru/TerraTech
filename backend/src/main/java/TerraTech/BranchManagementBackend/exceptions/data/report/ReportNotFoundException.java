package TerraTech.BranchManagementBackend.exceptions.data.report;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException() {
        super("There's no report found!");
    }

}