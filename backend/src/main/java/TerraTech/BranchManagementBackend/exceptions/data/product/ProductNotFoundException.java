package TerraTech.BranchManagementBackend.exceptions.data.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Error! Cannot find this product!");
    }

}
