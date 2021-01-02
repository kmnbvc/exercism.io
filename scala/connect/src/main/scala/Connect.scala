import Color.Color

case class Connect(lines: List[String]) {
  type Line = List[(Char, Int)]
  type Path = List[(Char, Int)]

  private val whites = lines.map(_.zipWithIndex.filter(_._1 == 'O').toList)
  private val blacks = lines.transpose.map(_.zipWithIndex.filter(_._1 == 'X').toList)

  def winner(): Option[Color] = {
    val results = List((Color.White, findPath(Nil, whites, Nil)), (Color.Black, findPath(Nil, blacks, Nil)))
    results.find(_._2.nonEmpty).map(_._1)
  }

  def findPath(behind: List[Line], ahead: List[Line], paths: List[Path]): List[Path] = {
    (behind, ahead, paths) match {
      case (_ :: _, _, Nil) => Nil
      case (_, Nil, _) => paths
      case (_, Nil :: _, _) => Nil
      case (Nil, line :: _, Nil) => findPath(List(Nil), ahead.tail, List(line))
      case (prevLine :: _, line :: _, _) =>
        val nextPaths = findNextPaths(line, paths, -1)
        val left = line.diff(nextPaths.map(_.head))

        val upperPaths = findNextPaths(prevLine, nextPaths, 1)

        //        println("line: " + line.mkString(",") + "\n",
        //          "paths: " + paths  + "\n",
        //          "nextPaths: " + nextPaths  + "\n",
        //          "upperPaths: " + upperPaths  + "\n",
        //          "line behind: " + prevLine  + "\n")

        findPath(behind.tail, prevLine :: ahead, upperPaths) ++ findPath(left :: behind, ahead.tail, nextPaths)
      case _ => throw new RuntimeException
    }
  }

  /*
    OOOOOOOOX
    XXXXXXXOX
    XOOOOOXOX
    XOXXXOXOX
    XOXOXOXOX
    XOXOXXXOX
    XOXOOOOOX
    XOXXXXXXX
    XOOOOOOOO
   */


  def findNextPaths(line: Line, paths: List[Path], nearbyModifier: Int): List[Path] = paths.flatMap {
    case lastStep :: steps =>
      val (adjoint, other) = line.partition(cell => cell._1 == lastStep._1 && (cell._2 == lastStep._2 || cell._2 == lastStep._2 + nearbyModifier))
      combineWithNeighbours(adjoint.map(_ :: lastStep :: steps), other)
    case _ => throw new RuntimeException
  }

  def combineWithNeighbours(paths: List[Path], line: Line): List[Path] = (paths, line) match {
    case (Nil, _) => Nil
    case (_, Nil) => paths
    case (path :: tl, _) => neighboursSeq(path, line) ++ combineWithNeighbours(tl, line)
  }

  def neighboursSeq(path: Path, line: Line): List[Path] = (path, line) match {
    case (Nil, _) => Nil
    case (_, Nil) => List(path)
    case (head :: _, _) =>
      val (adjoint, other) = line.partition(e => e._2 == head._2 - 1 || e._2 == head._2 + 1)
      path :: adjoint.map(_ :: path).flatMap(neighboursSeq(_, other))
  }
}

object Color extends Enumeration {
  type Color = Value
  val White, Black = Value
}
