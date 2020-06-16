
from queue import Queue
from datastructures.Graph import Graph
from practice.FindEmail import FindEmail
from practice.Misc import Misc

def graph_test():
    starts = ["a", "a", "b", "b", "c", "c", "c", "d", "d", "e", "f", "g", "g", "h"]
    stops = ["b", "h", "h", "c", "i", "f", "d", "f", "e", "f", "g", "i", "h", "i"]
    ws = [4, 8, 11, 8, 2, 4, 7, 14, 9, 10, 2, 6, 1, 7]
    graph = Graph()
    graph.build(starts, stops, ws)
    graph.recursive_dft()
    graph.reset()
    graph.iterative_dfs("a", "i")
    graph.reset()
    graph.dijkstra("a", "i")


def email_test():
    text = "Hello, Mr. moose@meese.meep how@at.moopus waffle pancake."
    FindEmail().find_email(text)

def misc_test():
    misc = Misc()
    print(misc.reversed([1,2,3]))
    print(misc.dumb_reversed([1, 2, 3]))
    print(misc.sort1([1, 3, 2]))
    print(misc.sort2([1, 3, 2]))
    print(misc.odd_elements([0, 1, 2, 3, 4 ,5]))
    print(misc.odd_elements([]))  # Works, returns empty.
    print(misc.cumulative_sum([1, 2, 3, 4, 5]))
    print(misc.running_average([0, 5, 0, 5, 0, 10], 2))
    print(misc.get_digits(123.4512321))
    print(misc.find_centered_average([1, 2, 100]))
    print(misc.find_centered_average_sort([1, 2, 100]))
    print(misc.find_centered_average_sort([1, 2, 100]))
    print("FIBS")
    misc.fibonacci_generator(10)




if __name__=="__main__":

   graph_test()
   # email_test()
   # misc_test()




