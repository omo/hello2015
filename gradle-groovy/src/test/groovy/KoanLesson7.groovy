
import org.junit.Test
import static org.junit.Assert.*
import java.util.concurrent.TimeUnit
import rx.Observable
import rx.Subscription
import rx.Subscription
import rx.functions.*
import rx.observables.GroupedObservable
import rx.observables.MathObservable
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.schedulers.Schedulers
import rx.schedulers.TimeInterval
import rx.util.async.Async

// From Lesson7 of http://rxkoans.codeplex.com/.
@groovy.transform.CompileStatic
def class KoanLesson7 {

  def waitUntil(Closure fn) {
    while (!fn()) {
      Thread.sleep(100)
    }
  }

  @Test(timeout=1000L)
  void theBloodyHardAsyncInvokationPattern() {
    def called = ""
    def sub = PublishSubject.create()
    def halve = { Double x -> called += "A"; x*0.5 }
    def result = 0.0D
    sub.subscribe({ Double n -> called += "C"; result = n })

    Async.start({ halve(101.0D) } as Func0<Double>).subscribe(
      {
        called += "B"
        sub.onNext(it)
        sub.onCompleted()
      })

    waitUntil({ result != 0 })
    assert 50.5D == result
    assert "ABC" == called
  }

  @Test(timeout = 1000L)
  void asynchronousRunInParallel() {
    def result = 0
    // XXX: RxJava Async has no way to create Observables that isn't bound to
    // specific scheduler :-(
    def incAsync = Async.toAsync(
      { Integer n ->
        Thread.sleep(100)
        n + 1
      } as Func1<Integer, Integer>,
      Schedulers.immediate())

    MathObservable.sumInteger(
      Observable.merge(incAsync(1), incAsync(9))
    ).subscribe(
      { result = it }
    )

    assert result == 12
  }

  @Test(timeout = 1000L)
  void asyncLongRunningTimeout() {
    def highFive = { Integer x ->
      Thread.sleep(200)
      return "Give me " + x
    }

    def result = null
    def incAsync = Async.toAsync(highFive as Func1<Integer, String>)
    incAsync(5).timeout(
      100, TimeUnit.MILLISECONDS,
      Observable.just("Too Slow Joe")
    ).subscribeOn(Schedulers.immediate()).subscribe({ n -> result = n })

    assert "Too Slow Joe" == result
  }
}
