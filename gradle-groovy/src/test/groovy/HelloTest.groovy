
import org.junit.Test
import static org.junit.Assert.*;

@groovy.transform.CompileStatic
def class HelloTest {
  @Test
  void testHello() {
    assertEquals(1 + 1, 2)
  }

  @Test
  void testThrow() {
    assertTrue(true)
  }
}
