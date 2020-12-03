// Package was needed to run mocking
package toRunMocking;

public class itemNotFoundException extends Throwable {
    public itemNotFoundException(String itemName) {
        super(itemName);
    }
}
