(ns total-reconf.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:.module-title {:font-weight "bold"
                   :font-size "16px"}]
  [:.module-author {:font-style "italic"
                   :font-size "12px"}]
  [:.module {:margin-bottom "10px"
             :border-bottom "1px solid #ccc"
                  }]

)
