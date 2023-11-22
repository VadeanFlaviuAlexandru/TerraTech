package TerraTech.BranchManagementBackend.exceptions.data.product;

public class ProductSameNameException extends RuntimeException {
    public ProductSameNameException() {
        super("There's already a product with the same name!");
    }

}