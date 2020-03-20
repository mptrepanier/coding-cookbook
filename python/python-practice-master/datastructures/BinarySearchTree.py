

class Node:
    def __init__(self, key):
        self.key = key
        self.left = None
        self.right = None
        self.parent = None


class BinarySearchTree:

    def __init__(self):
        self.root = None

    def insert(self, node):
        return self.__node_insert(node, self.root)

    def __node_insert(self, target, current = None, parent = None):
        if current:
            if target.key > current.key:
                self.__node_insert(target, current.right, current)
            else:
                self.__node_insert(target, current.left, current)
        else:
            target.parent = parent
            if parent:
                if (target.key > parent.key):
                    parent.right = target
                else:
                    parent.left = target
            else:
                self.root = target


        return

    def in_order(self):
        return self.__in_order(self.root)

    def __in_order(self, node):
        self.__in_order(node.left)
        print(node.key)
        self.__in_order(node.right)
        return

        ## Other functions in scala.


