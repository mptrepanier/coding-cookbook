### Binary Search Trees

Supports search, minimum, maximum, predecessor, successor, insert, and delete. Operations take time proportional to the height of the tree. Complete (balanced) binary trees take Theta(lg n) worst-case. In a right or left tree, ops take Theta(n).

#### Properties

The keys in a binary search tree are stored to satisfy the **binary search tree property**. Namely:

> Let `x` be a node in a binary search tree. If `y` is a node in the left subtree of `x`, then `y.key <= x.key`. If `y` is a node in the right subtree, then `y.key >= x.key`.

#### Walking dem Trees

```
Given:
				2
			1      3
```

```scala
// Prints sorted.
def inOrderTreeWalk(x: Option[Node]) = {
	x match {
		case Some(x) => {
			inOrderTreeWalk(x.left)
			println(x.key)
			inOrderTreeWalk(x.right)
		}
	}
}

// Output: 1, 2, 3
```

```scala
// Top-down. Left to right.
def preOrderTreeWalk(x: Option[Node]) = {
	x match {
		case Some(x) => {
			println(x.key)
			preOrderTreeWalk(x.left)
			preOrderTreeWalk(x.right)
		}
	}
}
// Output: 2, 1, 3
```

```scala
// Bottom-up. Left to right.
def postOrderTreeWalk(x: Option[Node]) = {
	x match {
		case Some(x) => {
			postOrderTreeWalk(x.left)
			postOrderTreeWalk(x.right)
			println(x.key)
		}
	}
}
// Output: 2, 3, 1
```

#### Querying Dem Trees

```scala
def treeQuery(x: Node, k: Value)
	x match {
	case Some(x) => {
                     if (x.key == k) x
                     else if (k < x.key) treeQuery(x.left)
                     else treeQuery(x.right)
					}
    case _ => x
}
```

Min/max - arbitrarily go left or right.

````scala
// Finds the next largest value in the tree.
def treeSuccessor(x: Node): Option[Node]
	x.right match {
		case Some(right) => treeMinimum(x.right)
		case _ => {
            x.parent
			
		}
	}

def findAncestor(ancestor: Option[Node]): Option[Node] = {
	ancestor.parent match {
		case Some(parent) => if (parent.left == ancestor) ancestor
		                     else findAncestor(parent)
        case _ => None
		}
	}
}
````

#### Insertion and Deletion

```scala
// Annoying to do functionally. Using vars for now.
// Relatively trivial, just traverse tree and insert.

def nodeInsert(node: Node, insert: Node)  = {
	if (node.key > insert.key){
		node.left match {
			case Some(left) => treeInsert(left, insert)
			case _ => {
				node.left = insert	
			}
		}
	}else{
      node.right match {
			case Some(right) => treeInsert(right, insert)
			case _ => {
				node.right = insert	
			}
		}  
    }	
}
```

```
// Deletion.
def transplant(a: Node, b: Node) = {
	a.parent.left match {
		a => a.p.left = Some(b)
		_ => a.p.right = Some(b)
	}
	b.p = u.p
}


def nodeDelete(node: Node) = {
	(node.left, node.right) match {
		case (Some(left), None) => transplant(node, node.right)
		case (None , Some(right)) => transplant(node, node.left)
		case _ => {
			val minRight = treeMinimum(node.right)
			if (minRight.p != node){
				transplant(minRight, minRIght.right)
				minRight.right = node.right
				minRight.right.p = minRight
			}
			transplant(z, y)
			minRight.left = node.left
			minRight.left.p = minRight
		}
	}
}
```

