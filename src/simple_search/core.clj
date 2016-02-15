(ns simple-search.core
  (:use simple-search.knapsack-examples.knapPI_11_20_1000
        simple-search.knapsack-examples.knapPI_13_20_1000
        simple-search.knapsack-examples.knapPI_16_20_1000))

;;; An answer will be a map with (at least) four entries:
;;;   * :instance
;;;   * :choices - a vector of 0's and 1's indicating whether
;;;        the corresponding item should be included
;;;   * :total-weight - the weight of the chosen items
;;;   * :total-value - the value of the chosen items

(defn included-items
  "Takes a sequences of items and a sequence of choices and
  returns the subsequence of items corresponding to the 1's
  in the choices sequence."
  [items choices]
  (map first
       (filter #(= 1 (second %))
               (map vector items choices))))

(defn random-answer
  "Construct a random answer for the given instance of the
  knapsack problem."
  [instance]
  (let [choices (repeatedly (count (:items instance))
                            #(rand-int 2))
        included (included-items (:items instance) choices)]
    {:instance instance
     :choices choices
     :total-weight (reduce + (map :weight included))
     :total-value (reduce + (map :value included))}))

;;; It might be cool to write a function that
;;; generates weighted proportions of 0's and 1's.

(defn score
  "Takes the total-weight of the given answer unless it's over capacity,
   in which case we return 0."
  [answer]
  (if (> (:total-weight answer)
         (:capacity (:instance answer)))
    0
    (:total-value answer)))

(defn add-score
  "Computes the score of an answer and inserts a new :score field
   to the given answer, returning the augmented answer."
  [answer]
  (assoc answer :score (score answer)))

(defn random-search
  [instance max-tries]
  (apply max-key :score
         (map add-score
              (repeatedly max-tries #(random-answer instance)))))

;(time (random-search knapPI_16_20_1000_1 10000
;))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;Dalton & Tom's Code;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn find-score
  "Given an instance, find-score will look at the choices and update the totals."
  [answer]
  (let [included (included-items (:items (:instance answer)) (:choices answer))]
      (add-score (assoc answer
        :total-weight (reduce + (map :weight included))
        :total-value (reduce + (map :value included))
        ))))

(defn run-mutator
  "Take a instance, mutator, and number of iterations. Then do hill climbing from that answer, returning the best answer."
  [answer mutator max-tries]
  (loop [start 0 ans answer]
    (if (= start max-tries)
      ans
      (recur
       (inc start)
       (let [new-ans (find-score (mutator ans))]
         (if ( > (:score new-ans) (:score ans))
           new-ans
           ans))))))


(defn gen-random-seed
  "Givend and instance and a number, return the best randomly generated seed."
  [knapsack seed-tries]
  (random-search knapsack seed-tries))


(defn random-restart
  "Each random-try agnerates a random seed and improves it using the mutator. After impoving a random number of times, randomly restart."
  [mutator knapsack restart-tries seed-tries]
  (let [empty-answer {:score 0}
        mutate-from-new-seed (fn [] (run-mutator
                                      (gen-random-seed knapsack seed-tries)
                                      mutator
                                      (+ 100000 (rand-int 100000)) ;;uses mutator between 100,000 to 200,000
                                      ))
        ;add-history #(assoc-in % [:score-history] (conj (:score-history %) (:score %)))
        ]
    (loop [tries restart-tries
           current-best empty-answer
           new-mutant (mutate-from-new-seed)]
      (println "With " tries " tries left, score is " (:score current-best))
      (if (= 0 tries)
        current-best
          (recur
            (dec tries)

            (if (> (:score new-mutant) (:score current-best))
              ;(add-history new-mutant)
              ;(add-history current-best))
              new-mutant current-best)

            (mutate-from-new-seed))))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;Dalton & Tom's Tweak: Flip one bit;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn findFlipVal
  "Helper: Given an array and an index, return the opposite value of the bit at that location"
  [inst index]
  (let [currentVal (nth inst index)]
    (if (= currentVal 0) 1 0)))

;; instance -> (mutated) instance
(defn flip-one-bit
  "Given an instance, we intend to flip a random bit."
  [answer]
  (let [size (count (:choices answer)),
        flip (rand-int size),
        choices (vec (:choices answer))]
    (assoc answer :choices (assoc choices flip (findFlipVal choices flip)))))

;; (find-score (flip-one-bit (random-search knapPI_16_20_1000_1 1))
;; )

;; (let [random-start (random-search knapPI_16_20_1000_1 10000)]
;;   [random-start,
;;    "                                                 After we climed the hill, we got:"
;;    (run-mutator random-start flip-one-bit 1000)]
;; )

(random-restart flip-one-bit knapPI_16_20_1000_1 8 10000
)
