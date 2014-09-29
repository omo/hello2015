
import org.junit.Test
import static org.junit.Assert.*
import java.util.concurrent.TimeUnit
import rx.Observable
import rx.Subscription
import rx.Subscription
import rx.observables.GroupedObservable
import rx.observables.MathObservable
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.schedulers.Schedulers
import rx.schedulers.TimeInterval

// From Lesson2 of http://rxkoans.codeplex.com/.
@groovy.transform.CompileStatic
def class KoanLesson6 {

  def waitUntil(Closure fn) {
    while (!fn()) {
      Thread.sleep(100)
    }
  }

  @Test
  void merging() {
    def easy = new StringBuilder()
    def you = Observable.from([1, 2, 3])
    def me = Observable.from(["A", "B", "C"])
    // Unlike C#, merge() is a static
    Observable.merge(
      you as Observable<Object>,
      me  as Observable<Object>).subscribe(
        { easy.append(it.toString() + " ") }
      )
    assert easy.toString() == "1 2 3 A B C "
  }

  @Test
  void mergingEvents() {
    def first = []
    def both = []
    def s1 = PublishSubject.create()
    def s2 = PublishSubject.create()
    s1.subscribe({ first.add(it) })
    Observable.merge(s1, s2).subscribe({ both.add(it) })

    s1.onNext("I")
    s1.onNext("am")
    s2.onNext("nobody.")
    s2.onNext("Nobody")
    s2.onNext("is")
    s1.onNext("perfect.")

    assert "I am nobody. Nobody is perfect." == both.join(" ")
    assert "I am perfect." == first.join(" ")
  }

  @Test
  void splittingUp() {
    def oddsAndEvens = ["", ""] as String[]
    def numbers = Observable.from(1..9)
    def split = numbers.groupBy({ it % 2 })

    split.subscribe(
      { GroupedObservable<Integer, Integer> group ->
        group.subscribe(
          {
            oddsAndEvens[group.key] += it.toString()
          }
        )
      }
    )

    assert oddsAndEvens[0] == "2468"
    assert oddsAndEvens[1] == "13579"
  }

  @Test
  void needToSubscribeImediatelyWhenSplitting() {
    def averages = [ 0.0, 0.0 ] as Double[]
    def numbers = Observable.from([22, 22, 99, 22, 101, 22])
    def split = numbers.groupBy({ it % 2 })
    split.subscribe(
      { GroupedObservable<Integer, Integer> group ->
        MathObservable.averageDouble(group.map({ it.toDouble() })).subscribe(
          { averages[group.key] = it }
        )
      }
    )

    assert averages[0] == 22
    assert averages[1] == 100
  }

  @Test
  void multipleSubscriptions() {
    def numbers = PublishSubject.create()
    def sum = 0.0
    def average = 0.0
    MathObservable.sumDouble(numbers).subscribe({ n -> sum = n })
    [1.0D, 1.0D, 1.0D, 1.0D, 1.0D].each(numbers.&onNext)
    MathObservable.averageDouble(numbers).subscribe({ n -> average = n })
    [2.0D, 2.0D, 2.0D, 2.0D, 2.0D].each(numbers.&onNext)
    numbers.onCompleted()
    assert sum == 15.0
    assert average == 2.0
  }
}
