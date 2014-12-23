
import org.junit.Test
import static org.junit.Assert.*
import rx.Observable
import rx.Subscription
import rx.Subscription
import rx.observables.MathObservable
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.schedulers.Schedulers

// From Lesson2 of http://rxkoans.codeplex.com/.
@groovy.transform.CompileStatic
def class KoanLesson2 {
  @Test
  void composableAdditions() {
    def received = 0
    def numbers = [ 10, 100, 1000 ]
    MathObservable.sumInteger(Observable.from(numbers)).subscribe({ i -> received = i })
    assert received == 1110
  }

  @Test
  void composableBeforeAndAfter() {
    def names = 1..6
    def a = ""
    def b = ""
    Observable.from(names).doOnNext(
      {
        n -> a += n
      }
    ).filter(
      {
       Integer n -> 0 == n % 2
      }
    ).doOnNext(
      {
        n -> b += n
      }
    ).subscribe()

    assert "123456" == a
    assert "246" == b
  }

  @Test
  void weWroteThis() {
    def received = []
    def names = [ "Bart", "Marge", "Wes", "Linus", "Erik" ]
    Observable.from(names).filter(
      { String n -> n.length() < 5 }
    ).subscribe(
      { x -> received.add(x) }
    )

    assert received.toString() == "[Bart, Wes, Erik]"
  }

  @Test
  void convertingEvents() {
    def received = ""
    def names = [ "wE", "hOpE", "yOU", "aRe", "eNJoyIng", "tHiS" ]
    Observable.from(names).map(
      { String x -> x.toLowerCase() }
    ).subscribe(
      { String x -> received += (x + " ") }
    )

    assert "we hope you are enjoying this " == received
  }

  @Test
  void creatingAMoreRelevantEventStream() {
    def received = ""
    def mouseXMovements = [ 100, 200, 150 ]
    def windowTopX = 50
    def relativeMouse = Observable.from(mouseXMovements).map({ Integer x -> x - windowTopX })
    relativeMouse.subscribe({ Integer x -> received += (x + ", ") })
    assert "50, 150, 100, " == received
  }

  @Test
  void checkingEverything() {
    def received = null
    def names = [ 2, 4, 6, 8 ]
    Observable.from(names).all({ Integer i -> 0 == (i % 2) }).subscribe({ x -> received = x })
    assert received == true
  }

  @Test
  void compositionMeansTheSumIsGreaterThanTheParts() {
    def numbers = Observable.from(1..10)
    MathObservable.sumInteger(numbers.filter({ Integer x -> x > 8 })).subscribe({ x -> assert x == 19 })
  }

}
