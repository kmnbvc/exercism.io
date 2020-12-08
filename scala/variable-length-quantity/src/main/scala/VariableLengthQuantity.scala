object VariableLengthQuantity {
  def encode(xs: List[Int]): List[Int] = {
    def _encode(x: Int): List[Int] = {
      shifts(x).map(_ & 0x7f | 0x80) :+ (x & 0x7f)
    }

    def shifts(x: Int): List[Int] = {
      val s = x >>> 7
      if (s > 0) shifts(s) :+ s else List()
    }

    xs.flatMap(_encode)
  }

  def decode(xs: List[Int]): Either[Boolean, List[Int]] = {
    def _decode(xs: List[Int]): Int = xs.foldLeft(0)((r, x) => r << 7 | x & 0x7f)

    def ending(i: Int): Boolean = ((i & 0xFF) & 0x80) == 0

    def groups(xs: List[Int]): List[List[Int]] = xs.indexWhere(ending) match {
      case -1 => List()
      case idx => xs.take(idx + 1) +: groups(xs.drop(idx + 1))
    }

    Either.cond(xs.exists(ending), groups(xs).map(_decode), false)
  }
}