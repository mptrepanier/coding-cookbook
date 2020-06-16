import itertools

class Directory:
    def __init__(self, name, contents=[]):
        self.name = name
        self.contents = contents


class File:
    def __init__(self, name):
        self.name = name



def flatmap(func, *iterable):
    return itertools.chain.from_iterable(map(func, *iterable))

def f(x):
    if isinstance(x, File):
        return [x.name]
    else:
        return recurse(x)

def recurse(directory):
    return flatmap(f, directory.contents)

if __name__=="__main__":
    f1 = File("f1")
    f2 = File("f2")
    f3 = File("f3")
    d2 = Directory("d2", [f2, f3])
    d1 = Directory("d1", [f1, d2])
    output = recurse(d1)
    print(list(output))
