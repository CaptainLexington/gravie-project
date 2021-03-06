(ns gravie-project.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:#app {:margin "0 10% 0 10%"}]
  [:body {:color "black"
          :background-color "#5C1A04"
          :font-family "'Open Sans Condensed', sans-serif" }]
  [:.level1 {:color "white"
             :font-size "2.25em"
             :text-decoration "none"
             :font-family "'Amatic SC', cursive" }]
  [:.level1 [:a {:color "inherit"
               :text-decoration "none"}]]
  [:.content {:background-color "#D4C9C5"
              :padding "20px"
              :border-radius "5px" }]
  [:.search-result {:margin "10px"} ]
  [:.search-result [:img {:margin "5px"}]]
  )
