import scala.annotation.tailrec

object MatchingBrackets {
  def isPaired(value: String): Boolean = {
    @tailrec
    def iter(s: String, expected: List[Char]): Boolean = {
      s.headOption match {
        case None => expected.isEmpty
        case Some('(') => iter(s.tail, ')' +: expected)
        case Some('[') => iter(s.tail, ']' +: expected)
        case Some('{') => iter(s.tail, '}' +: expected)
        case Some(')' | ']' | '}') => expected.nonEmpty && s.head.equals(expected.head) && iter(s.tail, expected.tail)
        case _ => iter(s.tail, expected)
      }
    }

    iter(value, List())
  }
}