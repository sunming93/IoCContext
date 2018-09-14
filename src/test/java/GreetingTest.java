import org.junit.jupiter.api.Test;

class GreetingTest {
    @Test
    void should_return_the_message() {
        Greeting greeting = new Greeting();

        assertTrue(greeting.greet().equals("Hello World!"));
    }
}