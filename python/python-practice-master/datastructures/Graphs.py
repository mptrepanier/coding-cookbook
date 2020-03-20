
from queue import Queue, PriorityQueue

class Vertex:
    def __init__(self):
        self.predecessor = None
        # 0 = White
        # 1 = Gray
        # 2 = Black
        self.color = 0
        self.distance = float('inf')
        self.finalize = 0
        self.key = 0 # Used in PRIM.




class AdjacencyListGraph:
    def __init__(self):
        self.vertices = {}
        self.adjacency_list = {}
        self.weights = {}
        self.time = 0

    def reset(self, start = None):
        for vertex in self.vertices:
            vertex.d = int("inf")
            vertex.predecessor = None
            vertex.color = 0
            vertex.key = 0
            vertex.finalize = 0
        if start != None:
            start.d = 0


    def breadth_first_search(self, start):
        # Initialize
        self.reset()
        start.color = 1
        start.distance = 0
        start.predecessor = None
        frontier = Queue()
        frontier.put(start)
        while frontier:
            current = frontier.get()
            neighbors = self.adjacency_list(current)
            for neighbor in neighbors:
                if neighbor.color == 0:
                    neighbor.color == 1
                    neighbor.distance = current.distance + 1
                    neighbor.predecessor = current
                    frontier.put(neighbor)
            current.color = 2
        return

    def depth_first_search(self):
        time = 0

        def dfs_visit(self, current, time):
            time += 1
            current.d = time
            current.color = 1
            neighbors = self.adjacency_list(current)
            for neighbor in neighbors:
                if neighbor.color == 0:
                    neighbor.predecessor = current
                    dfs_visit(neighbor, time)

            current.color = 2
            time += 1
            current.finalize = time

        # Initialize.
        for vertex in self.vertices:
            vertex.color = 0
            vertex.predecessor = None

        for vertex in self.vertices:
            if vertex.color == 0:
                dfs_visit(vertex, time)

        return

    # Size of MST can be calculated by summing all keys.
    # MST can be constructed by iterating over set.

    def mst_prim(self, start):
        # Initialize
        self.reset()
        start.key = 0
        frontier = PriorityQueue()
        frontier.put(start, start.distance)
        while frontier:
            current = frontier.get()
            neighbors = self.adjacency_list(current)
            for neighbor in neighbors:
                if neighbor.key == int("inf") or self.weights((current, neighbor)) < neighbor.key:
                    neighbor.predecessor = current
                    neighbor.key = self.weights((current, neighbor))

        return


    def relax(self, current, next):
        if next.d > current.d + self.weights(current, next):
            next.d = current + self.weights(current.next)
            next.predecessor = current

        return





    # If searching, include a goal node and break while loop when found.
    # Non optimal.
    def dijkstra(self, start):
        self.reset(start)
        determined = {}
        priority_queue = PriorityQueue()
        # Initialize queue.
        for vertex in self.vertices:
            priority_queue.put(vertex, vertex.d)

        while not priority_queue.empty:
            current = priority_queue.get()
            determined += current
            for neighbor in self.adjacency_list(current):
                self.relax(current, neighbor)

        return


















