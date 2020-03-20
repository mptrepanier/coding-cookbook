### Red-Black Trees

A search tree scheme that is balanced in order to guarantee that basic dynamic-set operations take `O(lg(n))` time in the worst case.

#### Properties

A *red-black* tree is a BST with one additional bit of storage per node - it's *color*, red or black.  By constraining the node colors on any given path from root to a leaf, red-black trees ensure that no such path is more than  twice as long as any other, approximating **balance**.

```
case class Node (
	color: Boolean,
	key: Any,
	left: Option[Node],
	right: Option[Node],
	parent: Option[Node]
)
```

1. Every node is either red or black.
2. The root is black.
3. Every leaf is black.
4. If a node is red, then both its children are black.
5. For each node, all simple paths from the node to the descendant leaves contain the same number of black nodes.



As a matter of convenience in dealing with boundary conditions, a single node object `T.nil` is used to represent `NIL` nodes (Empty left/right/parent point to `T.nil`). The color attribute of `T.nil` is black is its other attributes can take on empty values (although these are irrelevant). Generally, we only care about internal nodes of a red-black tree.

The number of black nodes on a given path from (but not including) node `x` is known as the **black-height** of the node.

#### Tree Operations on R/B Trees

Search, min, max, and successor are mirror BST times. Insertion and Deletion may violate R/B properties and thus require different handling.

With insertion/deletion, some of the nodes colors may need to be changed and the pointer structure may need to be modified via **rotation**.

```
           
           |                   <- LEFT ROTATE         |
           Y                    RIGHT ROTATE ->       X
         /   \                                      /   \
        X      c                                   a       Y
       / \                                               /   \
      a   b                                             b      c
        
        
def leftRotate(T, x) = {
	val y = x.right
	x.right = y.left
	y.left match {
		case T.nil => {}
		case _ => y.left.p = x
	}
	y.p = x.p
	if (x.p == T.nil){
		T.root = y
	}else if (x == x.p.left){
	  x.p.left = y
	}else{
		x.p.right = y
	}
	y.left = x
	x.p = y
}
```

#### Insertion

On insertion, only properties 2 and 4 can possibly be violated. We must check against these.

```
def redBlackInsert(T, z) = {
	var y = T.nil
	var x = T.root
	// Find place in tree. Standard insert.
	while (x != T.nil) {
		y = x
		if (z.key < x.key){
			x = x.left
		}else{
			x = x.right
		}
	}
	z.p = y
	// If node has no parent, z is at the root!
	if (y == T.nil){
		T.root = z	
	}else if (z.key < y.key){
		y.left = z
	}else{
		y.right = z
	}
	// Initialize as red node, insert, begin fixup process.
	z.left = T.nil
	z.right = T.nil
	z.color = RED
	redBlackInsertFixup(T, z)
}


def redBlackInsertFixup(T, z) = {
	// LOOP INVARIANTS
	//		z is red
	//		If z.p is root, z.p is black
	//		If the tree violates any R/B properties, it violates at most 1.
	//			If it violates 2, z is root and red.
	//			If it violates 4, z and z.p are red.
	while (z.p.color == RED){
		// If z's parent is a left child, y is the corresponding right child.
		if (z.p == z.p.p.left){	
			y = z.p.p.right
			if (y.color == RED){  // case 1
                z.p.color = BLACK
                y.color = RED
                z.p.p.color = RED
                z = z.p.p
			}else if (z == z.p.right){
				z = z.p                // case 2
				leftRotate(T,z)
			}else{
                z.p.color = BLACK // case 3
                z.p.p.color = RED
                rightRotate(T, z.p.p)
			}
		}else{
			... // SAME AS ABOVE BUT WITH RIGHT/LEFT SWAPPED.
		}
	}
	T.root.color = black
}
```

#### Deletion

Based on the deletion routine for a standard BST. Modifies the Transplant routine that is used.

```
def rbTreeTransplant(T,u,v) = {
	if (u.p == T.nil){
		T.root = v
	}else if (u == u.p.left){
		u.p.left = v
	}else{
		u.p.right = v
	}
	v.p == u.p
}
```



