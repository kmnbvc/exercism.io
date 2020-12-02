import scala.collection.immutable.TreeMap

class School {
  type DB = Map[Int, Seq[String]]

  private val students = scala.collection.mutable.Map[Int, Seq[String]]()

  def add(name: String, g: Int) = students(g) = grade(g) :+ name

  def db: DB = students.toMap

  def grade(g: Int): Seq[String] = students getOrElse(g, List())

  def sorted: DB = (TreeMap[Int, Seq[String]]() ++ students).mapValues(_.sorted)
}

