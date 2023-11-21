package TerraTech.BranchManagementBackend.exceptions.data.product;

public class productSameNameException extends RuntimeException {
    public productSameNameException() {
        super("There's already a product with the same name!");
    }

}