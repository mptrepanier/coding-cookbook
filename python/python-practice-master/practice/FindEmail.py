import re

class FindEmail:
    def find_email(self, text):
        words = text.split()
        r  = re.compile(r'([a-zA-Z0-9])+(@)([a-zA-Z0-9])+(\.)([a-zA-Z0-9])+')
        def matches(x):
            return bool(r.match(x))

        ans = list(filter(matches, words))
        print(ans)
        return ans



