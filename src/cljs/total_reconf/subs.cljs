(ns total-reconf.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :selected-modules
 (fn [db]
   (reaction (:selected-modules @db))
))

(re-frame/register-sub
 :unselected-modules
 (fn [db]
   (reaction (:unselected-modules @db))
))

(re-frame/register-sub
 :modules
 (fn [db]
   (js/console.log "sub => " (:modules @db))    
   (reaction (:modules @db))
))
