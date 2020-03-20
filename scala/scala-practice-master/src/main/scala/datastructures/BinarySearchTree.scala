package datastructures

class BinarySearchTree {

  // TODO: WIP. Could be far more sensible with option handling.

  var root: Option[Node] = None

  def height: Int = -1

  def size: Int = -1

  def insert(target: Int) = nodeInsert(target, root)

  private def nodeInsert(target: Int, current: Option[Node], parent: Option[Node] = None): Unit =
    current match {
      case Some(c) => {
        if (target > c.key) nodeInsert(target, c.right, current)
        else nodeInsert(target, c.left, current)
      }
      case _ => {
        val newNode = Node(target, parent = parent)
        parent match {
          case Some(p) => {
            if (target > p.key) p.right = Some(newNode)
            else p.left = Some(newNode)
          }
          case _ => {
            root = Some(newNode)
          }
        }
      }
    }

  def inOrderWalk: Unit = inOrderWalk(root)

  def inOrderWalk(node: Option[Node]): Unit = node.foreach { n =>
    inOrderWalk(n.left)
    println(n.key)
    inOrderWalk(n.right)
  }

  def search(key: Int): Option[Node] = nodeSearch(key, root)

  private def nodeSearch(key: Int, current: Option[Node]): Option[Node] =
    current match {
      case Some(c) =>
        if (c.key == key) current
        else if (c.key < key) nodeSearch(key, c.right)
        else nodeSearch(key, c.left)
      case _ => None
    }

  def delete(key: Int): Unit =
    search(key) match {
      case Some(n) => nodeDelete(n)
      case _       => {}
    }

  private def nodeDelete(node: Node): Unit =
    (node.left, node.right) match {
      case (None, _) => transplant(node, node.right) // Left is NONE, just replace with right.
      case (_, None) => transplant(node, node.left)  // Right is NONE, just replace with left.
      case (Some(left), Some(right)) => {
        val minRight = treeMinimum(right)
        minRight.parent match {
          case Some(p) =>
            if (p != node) {
              transplant(minRight, minRight.right)
              minRight.right = node.right
              minRight.right.foreach(r => r.parent = Some(minRight))
            }
        }
        transplant(node, Some(minRight))
        minRight.left = node.left
        minRight.left.foreach(l => l.parent = Some(minRight))
      }
    }

  private def transplant(current: Node, target: Option[Node]) = {
    current.parent match {
      case Some(p) => {
        p.left match {
          case Some(`current`) => p.left = target
          case _               => p.right = target
        }
      }
      case _ =>
        root = target
    }
    target.foreach(t => t.parent = current.parent)
  }

  def treeMinimum: Option[Node] = root.map(x => treeMinimum(x))

  private def treeMinimum(node: Node): Node =
    node.left match {
      case Some(left) => treeMinimum(left)
      case _          => node
    }

}

case class Node(
    key: Int,
    var left: Option[Node] = None,
    var right: Option[Node] = None,
    var parent: Option[Node] = None
) {}
