package upp.la.error;

public class ErrorMessages {

    public static String ENTITY_NOT_FOUND() {
        return "The requested data cannot be found.";
    }

    public static String REQUEST_ERROR() {
        return "Invalid request and/or missing data.";
    }

    public static String DUPLICATE_ITEM() {
        return "Duplicate item exists.";
    }

    public static String CONVERSION_FAILED() {
        return "Data conversion failed.";
    }

    public static String TOKEN_ERROR() {
        return "Error processing confirmation token.";
    }

    public static String VALIDATION_ERROR() {
        return "Validation failed. Check input";
    }

    public static String FILE_ERROR() {
        return "File upload/download error.";
    }

    public static String AUTH_ERROR() {
        return "Bad credentials.";
    }
    public static String CARD_ERROR() {
        return "Payment failed. Bad card data.";
    }
}
