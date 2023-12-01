package TerraTech.BranchManagementBackend.exceptions.data.product;

public class ProductSameNameException extends RuntimeException {
    public ProductSameNameException(String name) {
        super("There's already a product with the name:" + name);
    }

}