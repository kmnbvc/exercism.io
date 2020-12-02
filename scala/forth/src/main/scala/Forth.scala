import ForthError.ForthError


class Forth extends ForthEvaluator {
  private val pattern = ":\\s[\\w\\s-+*/]+\\s;".r

  def eval(text: String): Either[ForthError, ForthEvaluatorState] = {
    val userDefs = pattern.findAllIn(text.toUpperCase).toList.map(_.replaceAll("\\s?[:;]\\s?", ""))
    val cmd = pattern.replaceAllIn(text.toUpperCase, "").trim
    val ev = userWordDefinitions => evalWords(cmd.split(' ').toList, Right(List()), userWordDefinitions)
    parseUserWordDefinitions(userDefs).flatMap(ev).map(new State(_))
  }

  def evalWord(word: String, state: Either[ForthError, List[String]], userDefinedWords: Map[String, List[String]]): Either[ForthError, List[String]] = {
    state.flatMap(stack => {
      if (isDigit(word)) Right(word +: stack)
      else if (userDefinedWords.contains(word)) {
        evalWords(userDefinedWords(word), Right(stack), userDefinedWords)
      } else if (isDigitOperator(word)) {
        digitOperatorEvaluator(word, stack)
      } else if (isStackOperator(word)) {
        stackOperatorEvaluator(word, stack)
      } else if (!isUserWordDefinitionStart(word) && !isUserWordDefinitionEnd(word)) {
        Left(ForthError.UnknownWord)
      } else Right(stack)
    })
  }

  def evalWords(words: List[String], state: Either[ForthError, List[String]], userDefinedWords: Map[String, List[String]]): Either[ForthError, List[String]] = {
    words.foldLeft(state)((acc, word) => acc.flatMap(stack => evalWord(word, Right(stack), userDefinedWords)))
  }

  def isDigit(s: String): Boolean = {
    s.matches("\\d+")
  }

  def isDigitOperator(s: String): Boolean = {
    val ops = List("+", "-", "*", "/")
    ops.contains(s)
  }

  def isStackOperator(s: String): Boolean = {
    val ops = List("DUP", "DROP", "SWAP", "OVER")
    ops.contains(s)
  }

  def isUserWordDefinitionStart(s: String): Boolean = {
    s.equals(":")
  }

  def isUserWordDefinitionEnd(s: String): Boolean = {
    s.equals(";")
  }

  def digitOperatorEvaluator(op: String, stack: List[String]): Either[ForthError, List[String]] = {
    val hasTwo = (stack: List[String]) => Either.cond(stack.length >= 2, stack, ForthError.StackUnderflow)
    val nonZeroDiv = (stack: List[String]) => hasTwo(stack).fold(Left(_), stack => Either.cond(!stack(0).equals("0"), stack, ForthError.DivisionByZero))
    val ev = (stack: List[String], f: (Int, Int) => Int) => f(stack(1).toInt, stack(0).toInt).toString +: stack.drop(2)
    op match {
      case "+" => hasTwo(stack).map(ev(_, _ + _))
      case "-" => hasTwo(stack).map(ev(_, _ - _))
      case "*" => hasTwo(stack).map(ev(_, _ * _))
      case "/" => nonZeroDiv(stack).map(ev(_, _ / _))
      case _ => throw new IllegalArgumentException
    }
  }

  def stackOperatorEvaluator(op: String, stack: List[String]): Either[ForthError, List[String]] = {
    val ev = (vld: Boolean, f: List[String] => List[String]) => Either.cond(vld, f(stack), ForthError.StackUnderflow)
    op match {
      case "DUP" => ev(stack.nonEmpty, stack => stack.head +: stack)
      case "DROP" => ev(stack.nonEmpty, _.tail)
      case "SWAP" => ev(stack.length >= 2, stack => stack.take(2).reverse ++ stack.drop(2))
      case "OVER" => ev(stack.length >= 2, stack => stack(1) +: stack)
      case _ => throw new IllegalArgumentException
    }
  }

  def parseUserWordDefinitions(defs: List[String]): Either[ForthError, Map[String, List[String]]] = {
    val r = defs.map(_.split(' ').toList).map(words => Either.cond(!isDigit(words.head), (words.head, words.tail), ForthError.InvalidWord))
    val combine = (all: Map[String, List[String]], item: (String, List[String])) => all + (item._1 -> item._2.flatMap(word => all.getOrElse(word, List(word))))
    val acc: Either[ForthError, Map[String, List[String]]] = Right(Map())
    r.foldLeft(acc)((acc, definition) => definition.flatMap(d => acc.map(combine(_, d))))
  }
}

class State(val stack: List[String]) extends ForthEvaluatorState {
  // returns the current stack as Text with the element
  // on top of the stack being the rightmost element in the output."
  override def toString: String = stack.reverse.mkString(" ")
}