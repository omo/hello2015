
import org.junit.Test
import static org.junit.Assert.*;

def class HelloTest {
  @Test
  void testHello() {
    assertEquals(1 + 1, 2)
  }

  @Test(expected=MissingMethodException.class)
  void testThrow() {
    noSuchMethod()
  }
}
