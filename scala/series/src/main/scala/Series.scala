object Series {
  def slices(num: Int, s: String): List[List[Int]] = {
    s.map(_.asDigit).sliding(num).map(_.toList).toList
  }
}