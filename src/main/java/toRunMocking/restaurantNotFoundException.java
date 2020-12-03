// Package was needed to run mocking
package toRunMocking;

public class restaurantNotFoundException extends Throwable {
    public restaurantNotFoundException(String restaurantName) {
        super(restaurantName);
    }
}
