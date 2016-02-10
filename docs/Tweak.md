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
When we run random 10,000 times followed by running our climbing operation 1000 times. Most of the time, we get an answers that are significatnly better! By changing just one item at a time we are actually seeing consistent climbing!

### Random Restarts
We didn't get this far. :(
