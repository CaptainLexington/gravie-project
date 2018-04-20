(ns gravie-project.events
  (:require [re-frame.core :as re-frame]
            [gravie-project.db :as db]
            [gravie-project.games :as games]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(re-frame/reg-event-db
  ::initialize-db
  (fn-traced [_ _]
             db/default-db))

(re-frame/reg-event-db
  ::set-active-panel
  (fn-traced [db [_ active-panel]]
             (assoc db :active-panel active-panel)))

(re-frame/reg-event-db 
  ::update-search-results
  (fn-traced [db [_ search-results]]
             (assoc db
                    :search-results
                    search-results)))

(re-frame/reg-event-db 
  ::update-query
  (fn-traced [db [_ query]]
             (do 
               (games/search-games query #(re-frame/dispatch [::update-search-results %]))
               (assoc db
                      :query
                      query))))

(re-frame/reg-event-db 
  ::add-to-cart 
  (fn-traced [db [_ game]]
             (update db
                     :cart
                     conj game)))

(re-frame/reg-event-db 
  ::remove-from-cart 
  (fn-traced [db [_ game]]
             (update db
                     :cart
                     disj game)))

(re-frame/reg-event-db 
  ::rent-games
  (fn-traced [db [_ games]]
             (assoc (update db 
                     :library
                     clojure.set/union games)
                    :cart #{}
                    )))

(re-frame/reg-event-db 
  ::return-game
  (fn-traced  [db  [_ game]]
             (update db 
                     :library 
                     disj game)))
