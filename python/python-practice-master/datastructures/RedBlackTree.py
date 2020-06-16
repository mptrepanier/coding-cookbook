class Node:
    def __init__(self, key, color = False):
        self.color = False
        self.key = key
        self.left = None
        self.right = None
        self.parent = None


class RedBlackTree:

    def __init__(self,):
        self.root = None
        self.nil = Node(key=None, color=True)

    def insert(self, node):
        return self.__node_insert(node, self.root)

    # lg n
    def red_black_insert(self, node):
        current = self.root
        parent = self.nil
        while current != self.nil:
            parent = current
            if node.key < current.key:
                current = current.left
            else:
                current = current.right

        node.parent = parent
        if parent == self.nil:
            self.root = node
        elif node.key < parent.key:
            parent.left = current
        else:
            parent.right = current

        current.left = self.nil
        current.right = self.nil
        current.color = False
        return

    # Flip left/right for right rotate.
    def left_rotate(self, node):
        old_right = node.right
        node.right = old_right.left
        if old_right.left:
            old_right.left.parent = node

        old_right.parent = node.parent
        if node.parent == self.nil:
            self.root = old_right
        elif node == node.parent.left:
            node.parent.left = old_right
        else:
            node.right.right = old_right
        old_right.left = node
        node.parent = old_right
        return

    def right_rotate(self, node):
        old_left = node.left
        node.left = old_left.right
        if old_left.right:
            old_left.right.parent = node

        old_left.parent = node.parent
        if node.parent == self.nil:
            self.root = old_left
        elif node == node.parent.right:
            node.parent.right = old_left
        else:
            node.parent.left = old_left
        old_left.right = node
        node.parent = old_left
        return

    #lg n
    def red_black_insert_fixup(self, current):
        while not current.parent.color:
            if current.parent == current.parent.parent.left:
                uncle = current.parent.parent.right
                if not uncle.color:
                    current.parent.color = True
                    uncle.color = False
                    current.parent.parent.color = False
                    current = current.parent.parent
                elif current == current.parent.right:
                    current = current.parent
                    self.left_rotate(current)
                else:
                    current.parent.color = True
                    current.parent.parent.color = False
                    self.right_rotate(current.parent.parent)
            else:
                # Same but with right/left swapped.
                break
        return
