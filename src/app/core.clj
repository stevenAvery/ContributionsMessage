(ns app.core
  (:import (java.awt.image BufferedImage)
           (javax.imageio ImageIO)
           (java.io File)
           (java.awt Color)))

(def contribColours '(
    {:r 238 :g 238 :b 238} ;; less
    {:r 214 :g 230 :b 133}
    {:r 140 :g 198 :b 101}
    {:r 68 :g 163 :b 64}
    {:r 30 :g 104 :b 35})) ;; more

(defn colourToContrib
  "return the magnitude of contributions required for the given colour
  or assume 0 if the colour is not found"
  ([pixelColour] (colourToContrib pixelColour 0))
  ([pixelColour i]
    (cond
      (>= i (count contribColours)) 0
      (= (nth contribColours i) pixelColour) i
      :else (recur pixelColour (inc i)))))


(defn getPixels
  "return all pixel colours for the given image as a single vector"
  ([image] (getPixels image 0 0 []))
  ([image x y pixels]
    (let [pxColourInt (Color. (.getRGB image x y))
          pxColour {:r (.getRed pxColourInt) :g (.getGreen pxColourInt) :b (.getBlue pxColourInt)}]
      (if (>= (inc y) (.getHeight image))
        ;; return pixels since we've reached the end of the image
        pixels
        ;; otherwise keep adding to the list of pixels
        (recur
          image
          (if (< x (dec (.getWidth image))) (inc x) 0)
          (if (< x (dec (.getWidth image))) y (inc y))
          (conj pixels (colourToContrib pxColour)))))))


(defn -main [& args]
  (def bi (ImageIO/read (File. "res/test.png")))

  ;; check the height is 7, and the width is <= 52
  (println (getPixels bi))
)
