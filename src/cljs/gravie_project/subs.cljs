(ns gravie-project.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 ::search-results
 (fn [db _]
   (:search-results db)))

(re-frame/reg-sub 
  ::cart
  (fn [db _]
    (:cart db)))

(re-frame/reg-sub 
  ::library
  (fn [db _]
    (:library db)))

(re-frame/reg-sub 
  ::query
  (fn [db _]
    (:query db)))
