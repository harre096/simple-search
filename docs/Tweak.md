### Tweak Idea
##### Flip a random bit. (flip-one-bit)
First we run the random generator n times to give us a decent base case, then use our hill climbing operator m times.
* We think that randomly exchanging one item for another may not be the most effective, but should be a reasonable first mutator.
* Our answer will climb that hill by swapping in or out one random item at a time, and will only keep that change if it was an improvement.


###What we did:
#####Build generalized **run-mutator** function
This function will will "Take a instance, mutator, and number of iterations. Then do hill climbing from that instance."

#####We then build our mutator: **flip-one-bit** 
Our mutator picks a random bit and then filps it. We used a helper **findFlipVal** to figure out what we should flip things to.

#####Wrote **find-score**
This function will refresh the score after our mutator acts. This way, when our **run-mutator** runs it can actually compare things. It tooks us a while to figure out that we needed this. :)

#####Finally we climb some hills!
To get started we run **random-search** a few thousand times to get us to a decent starting spot. Then we hill climb from there.



###Results

Without using random restart, we hill climbed 1000 times from random-search 10,000 and got:

For knapPI_16_11_1000_1 we climbed from:
* 782 to 809
* 956 to 1160
* 809 to 809

For knapPI_16_13_1000_1 we climbed from:
* 1560 to 1677
* 1482 to 1521
* 1482 to 1521

For knapPI_16_20_1000_1 we climbed from:
* 1554 to 2291
* 1559 to 1962
* 2061 to 2172

When we run random 10,000 times followed by running our climbing operation 1000 times. Most of the time, we get an answers that are significatnly better! By changing just one item at a time we are actually seeing consistent climbing!



### Random Restarts
Calling random restart will: 

1. Make a random seed and use the mutator on it a random number of times (100,000 to 200,000 mutations)

2. Repeat step 1 as many times as you would like to reset

We called our funtion, telling it to reset 8 times and were generally sucessful seeing improvements over several of the resets.

We know when our result is imporvoving due to random reset based on the println command. We attemped to streamline this by adding a :score-history into our winning answer after each reset, but that seemed to only work if the freshly mutated score lost. (We would like to ask you about this...)
