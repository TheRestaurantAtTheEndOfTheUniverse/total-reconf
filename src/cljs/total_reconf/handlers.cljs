(ns total-reconf.handlers
    (:require [re-frame.core :as re-frame]
              [total-reconf.db :as db]
              [ajax.core :refer [GET POST]]))


(defn load-modules-handler[modules]
  (re-frame/dispatch [:modules modules]))

(defn load-error-handler[{:keys [status status-text]}]
  (js/console.log "error => " status " " status-text)    
  (re-frame/dispatch [:modules modules])
)

(defn- fetch-modules []
  (js/console.log "Fetching modules")    
  (GET "/total-reconf/models" {:handler load-modules-handler 
                                :error-handler load-error-handler 
                   :response-format :json
                   :keywords? true}))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   (fetch-modules)
   db/default-db))

(re-frame/register-handler
 :modules
  (fn [db [_ modules]]
    (js/console.log "module count => " (count modules))    
    (assoc db :modules modules
           :unselected-modules modules)))

(re-frame/register-handler
 :select-module
  (fn [db [_ module]]
   (assoc db :selected-modules (conj (:selected-modules db) module)
          :unselected-modules (disj (:selected-modules db) module)
)))

(re-frame/register-handler
 :unselect-module
  (fn [db [_ module]]
   (assoc db :selected-modules (disj (:selected-modules db) module)
          :unselected-modules (conj (:selected-modules db) module)
)))

