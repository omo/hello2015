
import org.junit.Test
import static org.junit.Assert.*;
import rx.Observable;

@groovy.transform.CompileStatic
def class RxTest {
  @Test
  void testHello() {
    def result = new ArrayList<String>()
    Observable.from("one", "two", "three")
    .take(2)
    .subscribe({ String arg -> result.add(arg) })
    assert ["one", "two"] as String[] == result.toArray(new String[0])
  }
}
