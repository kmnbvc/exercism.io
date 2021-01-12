import scala.util.parsing.combinator.RegexParsers

object Sgf extends RegexParsers {

  type Tree[A] = Node[A] // to separate the type from the constructor, cf. Haskell's Data.Tree
  type Forest[A] = List[Tree[A]]
  case class Node[A](root: A, subForest: Forest[A] = List())

  // A tree of nodes.
  type SgfTree = Tree[SgfNode]

  // A node is a property list, each key can only occur once.
  // Keys may have multiple values associated with them.
  type SgfNode = Map[String, List[String]]

  def parseSgf(text: String): Option[SgfTree] = {
    parseAll(tree, text) match {
      case Success(r, _) => Some(r)
      case _ => None
    }
  }

  def tree: Parser[SgfTree] = "(" ~ node ~ forest ~ ")" ^^ {case _ ~ root ~ sf ~ _ => Node(root, sf)}
  def forest: Parser[Forest[SgfNode]] = singleChild | rep(tree)
  def singleChild: Parser[Forest[SgfNode]] = node ^^ (x => List(Node(x)))
  def node: Parser[SgfNode] = ";" ~ rep(property) ^^ {case _ ~ x => x.toMap}
  def property: Parser[(String, List[String])] = key ~ rep1(value) ^^ {case k ~ v => (k,v)}
  def key: Parser[String] = "[A-Z]{1,2}".r
  def value: Parser[String] = "\\[[A-Za-z0-9]+\\]".r.map(_.drop(1).dropRight(1))

  def main(args: Array[String]): Unit = {
    val r = parseAll(tree, "(;FF[4](;B[aa];W[ab])(;B[dd];W[ee]))")
    println(r)
  }
}
