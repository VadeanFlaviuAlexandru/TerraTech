package TerraTech.BranchManagementBackend.exceptions.data.product;

public class productNotFoundException extends RuntimeException {
    public productNotFoundException() {
        super("Error! Cannot find this product!");
    }

}
