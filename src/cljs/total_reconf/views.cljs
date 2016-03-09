(ns total-reconf.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]))

(defn module-div [module]
  [re-com/v-box
   :class "module"
   :children [[:div.module-title (:name module) " " (:version module)]
              [:div.module-author (get-in module [:author :name])]
              [:div "€"(:price module)]]]
)

(defn title []
  (let [modules (re-frame/subscribe [:unselected-modules])]
    (js/console.log "view =>" (pr-str @modules))
    (fn []
     [:div.row
      [:div.col-sm-2
      (map module-div @modules)]])))

(defn main-panel []
  (fn []
    [:div "test"
     [re-com/v-box
      :height "100%"
      :children [[title]]]]))
