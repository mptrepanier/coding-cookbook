
from queue import Queue

if __name__=="__main__":

    q = Queue()
    q.put(1)
    q.put(1)
    while not q.empty():
        next = q.get()
        print(next)