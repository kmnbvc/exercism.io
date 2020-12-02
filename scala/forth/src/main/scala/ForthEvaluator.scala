
object ForthError extends Enumeration {
  type ForthError = Value
  val DivisionByZero, StackUnderflow, InvalidWord, UnknownWord = Value
}

trait ForthEvaluatorState {
  def toString: String
}


abstract class Definition {
  def evaluate(state: Either[ForthError.ForthError, ForthEvaluatorState]): Either[ForthError.ForthError, ForthEvaluatorState]
}


trait ForthEvaluator  {
  def eval(text: String): Either[ForthError.ForthError, ForthEvaluatorState]
}