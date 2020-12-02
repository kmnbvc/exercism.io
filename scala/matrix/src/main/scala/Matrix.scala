case class Matrix(s: String) {
  def row(n: Int): List[Int] = {
    s.split("\n")(n).split(" ").map(_.toInt).toList
  }

  def column(n: Int): List[Int] = {
    s.split("\n").map(_.split(" ")(n)).map(_.toInt).toList
  }
}