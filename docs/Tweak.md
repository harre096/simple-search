### Tweak Idea 1
##### Flip a random bit. (flip-one-bit)
First we run the random generator n times to give us a decent base case, then use our hill climbing operator m times.
* We think that randomly exchanging one item for another may not be the most effective, but should be a reasonable first mutator.
* Our answer will climb that hill by swapping in or out one random item at a time, and will only keep that change if it was an improvement.
