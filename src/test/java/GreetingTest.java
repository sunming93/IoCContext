import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GreetingTest {
    @Test
    void should_return_the_message() {
        Greeting greeting = new Greeting();

        assertTrue(greeting.greet().equals("Hello World!"));
    }
}