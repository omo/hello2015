
import org.junit.Test
import static org.junit.Assert.*
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.schedulers.Schedulers

// From Lesson1 of http://rxkoans.codeplex.com/.
@groovy.transform.CompileStatic
def class KoanLesson1 {
  @Test
  void simpleSubscription() {
    Observable.just(42).subscribe({ x -> assert x == 42 })
  }

  @Test
  void whatGoesInComesOut() {
    Observable.just(101).subscribe({ int x -> assert x == 101 })
  }

  @Test
  void thisIsTheSameAsAnEventStream() {
    def events = PublishSubject.create();
    events.subscribe({ x -> assert 37 == x })
    events.onNext(37)
  }

  @Test
  void howEventStreamsRelateToObservables() {
    def observableResult = 1
    Observable.just(73).subscribe({ i -> observableResult = i })
    def eventStreamResult = 1
    def events = PublishSubject.create()
    events.subscribe({ i -> eventStreamResult = i })
    events.onNext(73)
    assert eventStreamResult == observableResult
  }

  @Test
  void eentStreamsHaveMultipleEvents() {
    def eventStreamResult = 0
    def events = PublishSubject.create()
    events.subscribe({ Integer i -> eventStreamResult += i })
    events.onNext(10)
    events.onNext(7)
    assert eventStreamResult == 17
  }

  @Test
  void simpleReturn() {
    def received = ""
    Observable.just("foo").subscribe({ String s -> received = s })
    assert received == "foo"
  }

  @Test
  void theLastEvent() {
    def received = ""
    def names = [ "foo", "bar" ]
    // XXX: RxJava/RxGroovy doesn't have toObservable()
    Observable.from(names).subscribe({ s -> received = s })
    assert received == "bar"
  }

  @Test
  void everyThingCounts() {
    def received = 0
    def numbers = [ 3, 4 ]
    Observable.from(numbers).subscribe({ Integer x -> received += x })
    assert 7 == received
  }

  @Test
  void thisIsStillAnEventStream() {
    def received = 0
    def numbers = PublishSubject.create()
    numbers.subscribe({ Integer x -> received += x })
    numbers.onNext(10)
    numbers.onNext(5)
    assert received == 15
  }

  @Test
  void allEventsWillBeReceived() {
    def received = "Working "
    def numbers = 9..5
    Observable.from(numbers).subscribe({ i  -> received += i })
    assert "Working 98765" == received
  }

  @Test
  void doingInTheMiddle() {
    def status = []
    def daysTillTest = Observable.from(4..1)
    daysTillTest.doOnNext({ Integer d ->
      status.add(d + "=" + (d == 1 ? "Study Like Mad" : "Party")) }).subscribe()
    assert "[4=Party, 3=Party, 2=Party, 1=Study Like Mad]" ==  status.toString()
  }

  @Test
  void nothingListensUntilYouSubscribe() {
    def sum = 0
    def numbers = Observable.from(1..5)
    def observable = numbers.doOnNext({ Integer n -> sum += n })
    assert sum == 0
    observable.subscribe()
    assert 1 + 2 + 3 + 4 + 5 == sum
  }

  @Test
  void eventBeforeYouSubscribeDoNotCount() {
    def sum = 0
    def numbers = PublishSubject.create()
    def observable = numbers.doOnNext({ Integer n -> sum += n })
    numbers.onNext(1)
    numbers.onNext(2)
    observable.subscribe()
    numbers.onNext(3)
    numbers.onNext(4)
    assert 3 + 4 == sum
  }

  @Test
  void eventAfterYouUnsubscribeDoNotCount() {
    def sum = 0
    def numbers = PublishSubject.create()
    def observable = numbers.doOnNext({ Integer n -> sum += n })
    def subscription = observable.subscribe()
    numbers.onNext(1)
    numbers.onNext(2)
    subscription.unsubscribe()
    numbers.onNext(3)
    numbers.onNext(4)
    assert 1 + 2 == sum
  }

  @Test
  void eventWhileSubscribing() {
    def received = []
    def words = PublishSubject.create()
    def observable = words.doOnNext({ i -> received.add(i) })
    words.onNext("Peter")
    words.onNext("said")
    def subscription = observable.subscribe()
    words.onNext("you")
    words.onNext("look")
    words.onNext("pretty")
    subscription.unsubscribe()
    words.onNext("ugly")
    assert received.join(" ") == "you look pretty"
  }

  @Test
  void unsubscribeAtAnyTime() {
    def received = ""
    def numbers = 1..9
    Subscription un = null
    un = Observable.from(numbers, Schedulers.newThread()).subscribe(
      { Integer x ->
        received += x
        // XXX: This should assume that the new thread is slow enough for
        // |un| to be assigned.
        if (x == 5) {
          System.out.println(un)
          un.unsubscribe()
        }
      })
    Thread.sleep(100)
    assert "12345" == received
  }

  @Test
  void takeUntilFull() {
    def received = ""
    def subject = PublishSubject.create()
    subject.takeUntil(
      subject.filter({ Integer x -> x > 5 })
    ).subscribe({ x -> received += x })
    (1..9).each(subject.&onNext)
    assert received == "12345"
  }
}
