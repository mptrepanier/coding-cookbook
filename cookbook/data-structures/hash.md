### Hash Tables

An effective alternative to directly addressing an array when the number of keys is small relative to the total number of possible keys. Instead of using the key as an array index directly, the array index is _computed_ from the key.

* Basic ops require only O(1) time on average.

##### Direct Address Tables

In a _direct-address table_, denoted `T[0..m-1]`, each position or **slot** corresponds to a key in the universe `U`. Slot `k` points to an element in the set with key `k`. 

##### Hash Table

The downside of direct addressing is obvious: if the universe U is large, storing a table T of size `|U|` may be impractical (or even impossible) given the memory available on a typical computer.

In a hash table, a **hash function** `h` is used to compute the slot from key `k`. Here, `h` maps the universe `U` of the keys into the slots of a **hash table** `T[0..m-1]` where, typically, `|m|` <<< `|U|`.

`|m| <<< |U|` introduces one complication  - **hash collisions** - where two keys hash to the same slot.

##### Collision Resolution by Chaining

In **chaining**, we place all the elements that hash to the same slot into the same linked list. Slot `j` contains a pointer to the head of the list of all stored elements that hash to `j`.

How well does hashing with chaining perform?

Given as hash table `T` with `m` slots that stores `n` elements, we define the **load factor** `alpha` for `T` as `n/m`.

The worst-case behavior of hashing with chaining is terrible: all `n` keys hash to the same slot, creating a list of length `n`. Thus, the worst case search time is `Theta(n) + Time(h(k))`.

##### Hash Functions

A good hash function approximately satisfies the assumption of simple uniform hashing: each key is equally likely to hash to any of the `m` slots. If we know the keys are random real numbers `k` and are uniformly distributed in the range `0 <= k < 1`, then the hash function `h(k) = floor(km)` satisfies the condition of simple uniform hashing.

Consider a compiler's symbol table, in which the keys are character strings representing identifiers in a program. Closely related symbols, such as `pt` and `pts`, often occur in the same program. A good hash function would minimize that chance that such variants hash to the same slot.

A strong approach derives the ash value in a way that we expect to be independent of any data patterns that might exist in the data. For example, the "division method," computes the hash value as the remainder when the key is divided by a specified prime number.

##### Interpreting keys as Natural Numbers

Host has functions assume that the universe of keys is the set of Natural Numbers `{0, 1, 2, ..}`.



