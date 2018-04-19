(ns gravie-project.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [gravie-project.subs :as subs]
            [gravie-project.games :as games]))


;; home

(defn header []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label [re-com/hyperlink-href
             :label"Grendel"
             :href "#/about"]
     :level :level1]))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])


(defn search-result-listing [result]
  [re-com/h-box
   :class "search-result"
   :children [[:img {:src (:tiny_url (:image result))}]
              [re-com/hyperlink-href 
               :label [:span (str (:name result) " ")
                       ]
               :href (:site_detail_url result)]
              [re-com/md-icon-button
               :md-icon-name "zmdi-open-in-new"
               :size :smaller
               ]
              ]]) 

(defn search-results-table [results]
  [re-com/v-box 
   :children (map search-result-listing results)])

(defn home-panel []
  (let [search-results (re-frame/subscribe [::subs/search-results])]
    [re-com/v-box
     :gap "1em"
     :children [[re-com/title
                 :label "Welcome to Grendel, the Internet's #1 Game Rental Service"
                 :level :level2]
                [re-com/p 
                 "Welcome to Grendel, where we rent you games! Just search for the games you want..!" ]
                [re-com/input-text
                 :on-change games/search-results
                 :model ""
                 :change-on-blur? false ]
                (when-not (empty? @search-results)
                   [search-results-table @search-results]) 
                [link-to-about-page]]]))


;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level2])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :height "100%"
     :children [[header]
                [re-com/h-box
                 :width "60%"
                 :children [[panels @active-panel]]
                 :class "content" ]]]))
