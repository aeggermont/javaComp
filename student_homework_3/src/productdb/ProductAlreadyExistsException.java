package productdb;

public class ProductAlreadyExistsException extends Exception {

	public ProductAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductAlreadyExistsException(String message) {
		super(message);
	}


}
