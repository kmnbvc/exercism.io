import Color.Color

case class Connect(lines: List[String]) {
  def winner(): Option[Color] = {
    None
  }
}

object Color extends Enumeration {
  type Color = Value
  val White, Black = Value
}
