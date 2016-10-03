(ns app.core
  (:require [app.imageLoad :as imageLoad]
            [app.commitLog :as commitLog]
            [clj-time.core :as t]))

(def numCommits `(0 1 2 4 6))

(defn -main [& args]
  ;; check the height is 7, and the width is <= 52
  (def pixels (imageLoad/getPixels "res/test.png"))

  (commitLog/output "commitLog/test.sh"
    (commitLog/build [0 1 2 3 4] (t/date-time 2016 10 2 8 00)))
  )
