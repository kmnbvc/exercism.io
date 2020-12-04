object Zipper {
  // A zipper for a binary tree.
  // ??? Zipper[A] ???

  // Get a zipper focussed on the root node.
  def fromTree[A](bt: BinTree[A]): Zipper[A] = new Zipper[A](bt)

  // Get the complete tree from a zipper.
  def toTree[A](zipper: Zipper[A]): BinTree[A] = zipper.tree

  // Get the value of the focus node.
  def value[A](zipper: Zipper[A]): A = zipper.focused.value

  // Get the left child of the focus node, if any.
  def left[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.left

  // Get the right child of the focus node, if any.
  def right[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.right

  // Get the parent of the focus node, if any.
  def up[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.up

  // Set the value of the focus node.
  def setValue[A](v: A, zipper: Zipper[A]): Zipper[A] = zipper.set(v)

  // Replace a left child tree.
  def setLeft[A](l: Option[BinTree[A]], zipper: Zipper[A]): Zipper[A] = zipper.replace(l, Left())

  // Replace a right child tree.
  def setRight[A](r: Option[BinTree[A]], zipper: Zipper[A]): Zipper[A] = zipper.replace(r, Right())
}

// A binary tree.
case class BinTree[A](value: A, left: Option[BinTree[A]], right: Option[BinTree[A]])

class Zipper[A](val tree: BinTree[A], path: List[Direction[A]] = List()) {
  lazy val left: Option[Zipper[A]] = down(Left())
  lazy val right: Option[Zipper[A]] = down(Right())
  lazy val up: Option[Zipper[A]] = if (path.nonEmpty) Some(new Zipper[A](tree, path.tail)) else None
  lazy val focused: BinTree[A] = path.foldRight(tree)((d, t) => d.move(t).get)

  private def down(d: Direction[A]): Option[Zipper[A]] = d.move(focused).map(_ => new Zipper[A](tree, d +: path))

  def replace(node: Option[BinTree[A]], d: Direction[A]): Zipper[A] = up match {
    case None => new Zipper[A](d.replace(tree, node), path)
    case Some(upper) => upper.replace(Some(d.replace(focused, node)), path.head)
  }

  def set(v: A): Zipper[A] = up match {
    case None => new Zipper[A](BinTree(v, tree.left, tree.right), path)
    case Some(upper) => upper.replace(Some(BinTree(v, focused.left, focused.right)), path.head)
  }
}

sealed trait Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]]
  def replace(tree: BinTree[A], node: Option[BinTree[A]]): BinTree[A]
}

sealed case class Left[A]() extends Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]] = tree.left
  def replace(tree: BinTree[A], l: Option[BinTree[A]]): BinTree[A] = BinTree[A](tree.value, l, tree.right)
}

sealed case class Right[A]() extends Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]] = tree.right
  def replace(tree: BinTree[A], r: Option[BinTree[A]]): BinTree[A] = BinTree[A](tree.value, tree.left, r)
}
