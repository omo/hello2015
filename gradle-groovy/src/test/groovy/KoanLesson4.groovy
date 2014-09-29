
import org.junit.Test
import static org.junit.Assert.*
import java.util.concurrent.TimeUnit
import rx.Observable
import rx.Subscription
import rx.Subscription
import rx.observables.MathObservable
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.schedulers.Schedulers
import rx.schedulers.TimeInterval

// From Lesson2 of http://rxkoans.codeplex.com/.
@groovy.transform.CompileStatic
def class KoanLesson4 {

  def waitUntil(Closure fn) {
    while (!fn()) {
      Thread.sleep(100)
    }
  }

  @Test(timeout = 1000L)
  void launchingAnActionInTheFuture() {
    def received = ""
    Schedulers.immediate().createWorker().schedule(
      { received = "Finished" },
      100, TimeUnit.MILLISECONDS)
    assert received == "Finished"
  }

  @Test(timeout = 1000L)
  void launchingAnEventInTheFuture() {
    def received = null
    def time = 100
    def people = PublishSubject.create()
    people.delay(time, TimeUnit.MILLISECONDS).subscribe({ x -> received = x })
    people.onNext("Godot")
    waitUntil({ received != null })
    assert received == "Godot"
  }

  @Test(timeout = 1000L)
  void youCanPlaceATimelimitOnHowLongAnEventShouldTake() {
    def received = []
    def timeout = 100
    def timeoutEvent = Observable.just("Tepid");
    def temperatures = PublishSubject.create()
    temperatures.timeout(timeout, TimeUnit.MILLISECONDS, timeoutEvent).subscribe(
      { x -> received.add(x) }
    )

    temperatures.onNext("Started")
    Thread.sleep(150)
    temperatures.onNext("Boiling")
    waitUntil({ !received.isEmpty() })

    assert "Started, Tepid" == received.join(", ")
  }

  @Test(timeout = 1000L)
  void throttling() {
    def received = []
    def events = PublishSubject.create()
    events.throttleLast(100, TimeUnit.MILLISECONDS).subscribe({ i -> received.add(i) })

    events.onNext("f")
    events.onNext("fr")
    events.onNext("fro")
    events.onNext("from")
    Thread.sleep(120)

    events.onNext("s")
    events.onNext("sc")
    events.onNext("sco")
    events.onNext("scot")
    events.onNext("scott")
    Thread.sleep(120)

    assert "from scott" == received.join(" ")
  }

  @Test(timeout = 1000L)
  void buffering() {
    def received = []
    def events = PublishSubject.create()
    events.buffer(100, TimeUnit.MILLISECONDS).subscribe(
      { List<Character> chars ->
        received.add(
          chars.inject(
            new StringBuilder(), { s, ch -> s.append(ch) }).toString())
      }
    )

    events.onNext('S')
    events.onNext('c')
    events.onNext('o')
    events.onNext('t')
    events.onNext('t')
    Thread.sleep(120)

    events.onNext('R')
    events.onNext('e')
    events.onNext('e')
    events.onNext('d')
    Thread.sleep(120)

    assert "Scott Reed" == received.join(" ")
  }

  @Test(timeout = 1000L)
  void timeBetweenCalls() {
    def received = []
    def events = PublishSubject.create()
    events.timeInterval().doOnNext(
      // See that the initial interval is zero.
      // { TimeInterval<?> t -> System.out.println(t.intervalInMilliseconds) }
      { }
    ).filter(
      { TimeInterval<?> t -> 100 < t.getIntervalInMilliseconds() }
    ).subscribe({ TimeInterval<?> s -> received.add(s.value) })

    events.onNext("too")
    events.onNext("fast")
    Thread.sleep(120)
    events.onNext("slow")
    Thread.sleep(120)
    events.onNext("down")

    assert "slow down" == received.join(" ")
  }

}
