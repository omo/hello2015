import scala.util.parsing.combinator._

object MyParsers extends RegexParsers {
  val ident: Parser[String] = """[a-zA-Z_]\w*""".r

  def main(args: Array[String]) {
    println("input : "+ args(0))
    println(parseAll(ident, args(0)))
  }
}

MyParsers.main(args)
