object Alphametics {
  def solve(text: String): Option[Map[Char, Int]] = {
    val parts = text.split("[+={2}]").map(_.trim).filter(_.nonEmpty)
    val (ps, r) = (parts.dropRight(1), parts.last)
    val excludes = parts.filter(_.length > 1).map(_.head -> 0).toMap
    val known = if (r.length > ps.map(_.length).max && ps.length < 3) Map(r.head -> 1) else Map.empty[Char, Int]

    if (r.length >= ps.map(_.length).max) {
      val allChars = parts.flatten.distinct.toList
      val matrix = allChars.map(c => List.range(0, 10).map(n => (c, n)))

      for (x <- combine(matrix, excludes, known)) {
        val m = x.toMap
        if (ps.map(number(_, m)).sum == number(r, m)) return Some(m)
      }
    }
    None
  }

  def number(s: String, m: Map[Char, Int]): Int = {
    s.foldRight((0, 1))((c, r) => (r._1 + m(c) * r._2, r._2 * 10))._1
  }

  def combine(matrix: List[List[(Char, Int)]], excludes: Map[Char, Int], known: Map[Char, Int]): List[List[(Char, Int)]] = {
    matrix match {
      case Nil => List()
      case row :: Nil => narrow(row, excludes, known).map(List(_))
      case row :: rest => narrow(row, excludes, known).flatMap(pair => {
        val filtered = rest.map(r => r.filter(p => pair._2 != p._2))
        combine(filtered, excludes, known).map(pair +: _)
      })
    }
  }

  def narrow(row: List[(Char, Int)], excludes: Map[Char, Int], known: Map[Char, Int]): List[(Char, Int)] = {
    known.get(row.head._1) match {
      case None => row.filterNot(contains(_, excludes))
      case Some(n) => List((row.head._1, n))
    }
  }

  def contains(pair: (Char, Int), excludes: Map[Char, Int]): Boolean = {
    excludes.get(pair._1).contains(pair._2)
  }
}