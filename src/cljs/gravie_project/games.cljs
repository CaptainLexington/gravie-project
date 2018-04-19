(ns gravie-project.games
  (:require-macros  [cljs.core.async.macros :refer  [go]])
  (:require  [cljs-http.client :as http]
            [cljs.core.async :refer  [<!]]
            [re-frame.core :as re-frame]
            [gravie-project.events :as events]))


(def api-key  "8c9999e61ed532e0807e4d6be0e0c11e4d967a81")

(defn- peel-game [game]
  (select-keys
    game
    [:name
     :image
     :original_release_date
     :site_detail_url]))


(defn search-games [query callback]
  (go (let [response (<! (http/jsonp  "https://www.giantbomb.com/api/search/"
                                   {:with-credentials? false
                                    :callback-name "json_callback" 
                                    :timeout 3000
                                    :keywordize-keys? true
                                    :query-params {:api_key api-key
                                                   :format "jsonp"
                                                   :json_callback "callback"
                                                   :resources "game"
                                                   :query query}}))
            games (mapv peel-game (:results (:body  response)))]
       (callback games)))
  nil)

(defn search-results [query]
  (search-games query #(re-frame/dispatch [::events/update-search-results %])))
