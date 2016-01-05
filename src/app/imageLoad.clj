(ns app.imageLoad
  (:import (java.awt.image BufferedImage)
           (javax.imageio ImageIO)
           (java.io File)
           (java.awt Color)))

;; list of colours on github contributions graph
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
  "return all pixel colours for the given image as a single vector
  goes top to bottom then left to right, because that is how github orders its days"
  ([filename] (getPixels (ImageIO/read (File. filename)) 0 0 []))
  ([image x y pixels]
    (if (>= x (.getWidth image))
      ;; return pixels since we've reached the end of the image
      pixels
      ;; otherwise keep adding to the list of pixels
      (let [pxColourInt (Color. (.getRGB image x y))
            pxColour {:r (.getRed pxColourInt)
                      :g (.getGreen pxColourInt)
                      :b (.getBlue pxColourInt)}]
        (recur
          image
          (if (< y (dec (.getHeight image))) x (inc x))
          (if (< y (dec (.getHeight image))) (inc y) 0)
          (conj pixels (colourToContrib pxColour)))))))
