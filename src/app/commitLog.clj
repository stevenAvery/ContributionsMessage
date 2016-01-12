(ns app.commitLog
  (:require [clj-time.core :as t]))

;;git commit --date="Sun Jan 15 14:00 2017 -0500" -am "."
(def numCommits `(0 1 4 6 8))
(def logFile "commitLog.txt")
(def logHead (str "rm -f " logFile "\ntouch " logFile "\n"))

(defn commitLine
  [date countLeft]
    (str
      "echo \"" (t/year date) "-" (t/month date) "-" (t/day date)
      ": " countLeft "\" >> " logFile "\n"
      "git commit --date=\""
      (t/year date) "-" (t/month date) "-" (t/day date) "T14:00:00-05:00\" "
      " -am \"" (t/year date) "-" (t/month date) "-" (t/day date)
      ": " countLeft "\"\n"))

;; start oct 2
(defn build
  ([pixels date]
    (build (vec (map (fn [i] (nth numCommits i)) pixels)) date logHead))
  ([pixels date log]
  (if (= 0 (count pixels))
    log
    (if (<= (first pixels) 0)
      ;; the day is done go to the next
      (build
        (into [] (drop 1 pixels))
        (t/plus date (t/days 1))
        log)
      ;; take one off of the current day
      (build
        (assoc pixels 0 (dec (first pixels)))
        date
        (str log (commitLine date (first pixels)))
        )))))

(defn output
  [filename log]
  (spit filename log))
