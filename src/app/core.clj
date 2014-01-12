(ns app.core
  (:require [app.imageLoad :as imageLoad]
            [app.commitLog :as commitLog]
            [clj-time.core :as t]))

(def inputImage "res/HelloWorld2.png")
(def year 2014)
(def month 1)
(def day 5)

(defn -main [& args]
  ;; check the height is 7, and the width is <= 52
  (def pixels (imageLoad/getPixels inputImage))

  ;; generate the commit script
  (commitLog/output "commitLog/commitScript.sh"
    (commitLog/build pixels (t/date-time year month day 8 00))))
