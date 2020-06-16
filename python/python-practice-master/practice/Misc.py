

import re

class Misc:

    def reversed(self, input):
        input.reverse()
        return input

    def dumb_reversed(self, input):
        return input[::-1]

    def sort1(self, input):
        input.sort()
        return input

    def sort2(self, input):
        return sorted(input)


    def odd_elements(self, input):
        # [start, stop, increment]
        return input[1::2]


    def cumulative_sum(self, input):
        cu_list = [sum(input[0:x]) for x in range(len(input) + 1)]
        return cu_list

    def running_average(self, input, interval):
        if len(input) >= interval:
            running_average = [sum(input[x - interval: interval]) / interval for x in range(interval, len(input))]
            return running_average
        else:
            return [-1]

    def get_digits(self, number):
        num_str = str(number)
        r = re.compile(r'([0-9])')
        return list(map(lambda x: int(x), filter(lambda c: r.match(c), num_str)))

    def find_centered_average(self, nums):
        nums.remove(max(nums))
        nums.remove(min(nums))
        return sum(nums) / len(nums)

    def find_centered_average_sort(self, nums):
        s = sorted(nums)[1:-1]
        return sum(s) / len(s)

    def fibonacci_generator(self, count):
        def fibs(count):
            first = 0
            second = 1
            for i in range(count):
                if i == 0:
                    yield first
                elif i == 1:
                    yield second
                else:
                    current = first + second
                    first = second
                    second = current
                    yield current

        fib_generator = fibs(count)
        for item in fib_generator:
            print(item)




