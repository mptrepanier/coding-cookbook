from enum import Enum
import queue
import heapq

class Status(Enum):
    UNDISCOVERED = 1
    DISCOVERED = 2
    FINISHED = 3


class Vertex:

    def __init__(self, id, distance = float("inf"), status = Status.UNDISCOVERED, discover_time = float("inf"), finish_time = float("inf")):
        self.id = id
        self.status = status
        self.distance = distance
        self.discover_time = discover_time
        self.finish_time = finish_time
        self.visited = False

    def discover(self, time = float("inf")):
        time += 1
        print("Just discovered " + str(self.id) + " at time " + str(time) + "." )
        self.status = Status.DISCOVERED
        self.discover_time = time
        return time

    def finish(self, time):
        time += 1
        print("Just finished " + str(self.id) + " at time " + str(time) + ".")
        self.status = Status.FINISHED
        self.finish_time = time
        return time

    def visit(self):
        self.visited = True


    def reset(self):
        self.status = Status.UNDISCOVERED
        self.discover_time = float("inf")
        self.finish_time = float("inf")
        self.visited = False


class Graph:
    def __init__(self):
        self.vertices = set()
        self.adjacency_list = {}
        self.edge_weights = {}
        self.vertex_map = {}

    def reset(self):
        for vertex_id in self.vertices:
            self.vertex_map[vertex_id].reset()

    def build(self, starts, stops, weights, directed = False):
        # Add the starts and stops to the vertex set..
        start_vertices = list(map(lambda start: Vertex(start), starts))
        stop_vertices = list(map(lambda stop: Vertex(stop), stops))
        id_to_start = list(zip(starts, start_vertices))
        id_to_stop = list(zip(stops, stop_vertices))

        self.vertices.update(starts)
        self.vertices.update(stops)
        self.vertex_map.update(id_to_start)
        self.vertex_map.update(id_to_stop)

        # Add the edges and update the adjacency map.
        start_stop_weight = zip(zip(start_vertices, stop_vertices), weights)
        for ((start, stop), weight) in start_stop_weight:
            self.add_edge(start, stop, weight)

        return

    def add_edge(self, start, stop, weight):
        if start.id in self.adjacency_list:
            self.adjacency_list[start.id].append(stop)
        else:
            self.adjacency_list.update({start.id: [stop]})
        self.edge_weights.update({(start.id, stop.id): weight})

    def targeted_bfs(self, start, target):
        frontier = queue.Queue()
        frontier.put(start)
        start.discover()
        while not frontier.empty():
            current = frontier.get()
            if (current.id == target):
                return current
            neighbors = self.adjacency_list.get(current.id, [])
            for neighbor in neighbors:
                if neighbor.status == Status.UNDISCOVERED:
                    frontier.put(neighbor)
                    neighbor.discover()

        print("Target not found.")
        return None

    def recursive_dft(self):
        # Define "function" variable to allow inner function to mutate.
        time = 0
        def recursive_dft_inner(current):
            # Discover.
            nonlocal time
            neighbors = self.adjacency_list.get(current.id, [])
            # Explore children.
            for neighbor in neighbors:
                if neighbor.status == Status.UNDISCOVERED:
                    time = neighbor.discover(time)
                    recursive_dft_inner(neighbor)
            # Finish.
            time = current.finish(time)
            return

        for vertex_id in self.vertices:
            vertex = self.vertex_map[vertex_id]
            if vertex.status == Status.UNDISCOVERED:
                time = vertex.discover(time)
                recursive_dft_inner(vertex)
        return


    # STACK
    def iterative_dfs(self, start, target):
        time = 0
        frontier = []
        start_vertex = self.vertex_map[start]
        time = start_vertex.discover(time)
        frontier.append(start_vertex)
        while frontier:
            current = frontier[-1]
            if current.id == target:
                print("FOUND!")
                return target
            if current.visited:
                frontier.pop()
                time = current.finish(time)
            else:
                # Visit implies we are scanning the node's neighbors.
                current.visit()
                neighbors = self.adjacency_list[current.id]
                for neighbor in neighbors:
                    if neighbor.status == Status.UNDISCOVERED:
                        time = neighbor.discover(time)
                        frontier.append(neighbor)

        print("Target not reachable from this starting vertex.")
        return None


    # HEAP/QUEUE.
    def dijkstra(self, start, target):
        frontier  = []
        start_vertex = self.vertex_map[start]
        start_vertex.distance = 0
        heapq.heappush(frontier, (start_vertex.distance, start_vertex))
        while frontier:
            (priority, current) = heapq.heappop(frontier)
            if (current.id == target):
                print(current.distance)
                return current
            if not current.visited:
                current.visit()
                neighbors = self.adjacency_list.get(current.id, [])
                for neighbor in neighbors:
                    if not neighbor.visited:
                        neighbor.discover()
                        alt = current.distance + self.edge_weights[(current.id, neighbor.id)]
                        if neighbor.distance > alt:
                            neighbor.distance = alt
                            heapq.heappush(frontier, (neighbor.distance, neighbor))
        print("Target not found.")
        return None































