object Zipper {
  // A zipper for a binary tree.
  // ??? Zipper[A] ???

  // Get a zipper focussed on the root node.
  def fromTree[A](bt: BinTree[A]): Zipper[A] = new Zipper[A](bt)

  // Get the complete tree from a zipper.
  def toTree[A](zipper: Zipper[A]): BinTree[A] = zipper.tree

  // Get the value of the focus node.
  def value[A](zipper: Zipper[A]): A = zipper.focused().value

  // Get the left child of the focus node, if any.
  def left[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.down(Left())

  // Get the right child of the focus node, if any.
  def right[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.down(Right())

  // Get the parent of the focus node, if any.
  def up[A](zipper: Zipper[A]): Option[Zipper[A]] = zipper.up()

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
  def down(d: Direction[A]): Option[Zipper[A]] = d.move(focused()).map(_ => new Zipper[A](tree, d +: path))
  def up(): Option[Zipper[A]] = if (path.nonEmpty) Some(new Zipper[A](tree, path.tail)) else None
  def focused(): BinTree[A] = path.foldRight(tree)((d, t) => d.move(t).get)
  def replace(node: Option[BinTree[A]], d: Direction[A]): Zipper[A] = {
    (path, d) match {
      case (Nil, Left()) => new Zipper[A](BinTree(tree.value, node, tree.right), path)
      case (Nil, Right()) => new Zipper[A](BinTree(tree.value, tree.left, node), path)
      case (p::_, Left()) => up().get.replace(Some(BinTree(focused().value, node, focused().right)), p)
      case (p::_, Right()) => up().get.replace(Some(BinTree(focused().value, focused().left, node)), p)
    }
  }
  def set(v: A): Zipper[A] = {
    path match {
      case d::_ => up().get.replace(Some(BinTree(v, focused().left, focused().right)), d)
      case _ => new Zipper[A](BinTree(v, tree.left, tree.right), path)
    }
  }
}

sealed trait Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]]
}

sealed case class Left[A]() extends Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]] = tree.left
}

sealed case class Right[A]() extends Direction[A] {
  def move(tree: BinTree[A]): Option[BinTree[A]] = tree.right
}
