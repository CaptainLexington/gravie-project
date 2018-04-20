(ns gravie-project.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [gravie-project.subs :as subs]
            [gravie-project.events :as events]
            [gravie-project.games :as games]))

;;cart-popover

(defn cart-item-listing  [item]
  [re-com/h-box 
   :children [[re-com/label
               :label (:name item)]
              [re-com/md-icon-button
               :md-icon-name  "zmdi-close"
               :on-click #(re-frame/dispatch [::events/remove-from-cart item])
               :size :smaller ] ]])

(defn cart-popover [cart]
  [re-com/v-box 
   :width "200px"
   :children [(if (empty? cart)
                [re-com/label :label "Your cart is empty"]
                (map cart-item-listing cart))
              [re-com/button
              :label "Checkout"
              :disabled? (empty? cart)
              :on-click #(re-frame/dispatch [::events/rent-games cart])]]])

;; home
(defn cart-header []
  (let  [cart (re-frame/subscribe [::subs/cart])
         popover? (reagent.core/atom false)]
    [re-com/h-box 
     :children [[re-com/popover-anchor-wrapper
                 :anchor [re-com/md-icon-button 
                          :md-icon-name "zmdi-shopping-cart"
                          :on-click #(swap! popover?  not)
                          :size :larger ]
                 :showing? popover?
                 :position :below-center
                 :popover [re-com/popover-content-wrapper
                           :title "Cart"
                           :body [cart-popover @cart]]]
                [:strong 
                 (count @cart)]]]))
(defn header []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/h-box
     :align :baseline
     :width "60%"
     :children [[re-com/title
                 :label [re-com/hyperlink-href
                         :label"Grendel"
                         :href "#/about"]
                 :level :level1]
                [re-com/gap :size "1"]
                [cart-header]] ]))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])


(defn search-result-listing [result cart]
  [re-com/h-box
   :class "search-result"
   :children [[:img {:src (:tiny_url (:image result))}]
              [re-com/hyperlink-href 
               :label [:span (str (:name result) " ")
                       ]
               :href (:site_detail_url result)]
              [re-com/md-icon-button
               :md-icon-name "zmdi-open-in-new"
               :size :smaller]
              [re-com/md-icon-button
               :md-icon-name "zmdi-plus-circle"
               :on-click #(re-frame/dispatch [::events/add-to-cart result])
               :disabled? (cart result)
               :tooltip "Add to Cart" 
               :size :smaller]
              ]]) 

(defn search-results-table [results cart]
  [re-com/v-box 
   :children (map search-result-listing results (repeat cart))])

(defn home-panel []
[re-com/v-box
     :gap "1em"
     :children [[re-com/title
                 :label "Welcome to Grendel, the Internet's #1 Game Rental Service"
                 :level :level2]
                [re-com/p 
                 "Welcome to Grendel, where we rent you games! Just search for the games you want..!" ]]]
  )

(defn storefront-panel []
  (let [search-results (re-frame/subscribe [::subs/search-results])
        cart (re-frame/subscribe [::subs/cart])
        query (re-frame/subscribe [::subs/query])
        ]
    [re-com/v-box 
     :children [[re-com/p "Welcome to the Grendel storefront! Search out massive library of games to rent below"]
                [re-com/input-text
                 :on-change #(re-frame/dispatch [::events/update-query %])
                 :model query
                 :change-on-blur? false ]
                (when-not (empty? @search-results)
                  [search-results-table @search-results @cart])]]))

;; Library

(defn library-item-listing [item]
  (re-com/v-box 
    :children [[:img {:src (:thumb_url (:image item))}]
               [re-com/title
                :label (:name item)
                :level :level3 ]
               [re-com/md-icon-button 
                :md-icon-name "zmdi-redo"
                :tooltip "Return game"
                :on-click #(re-frame/dispatch [::events/return-game item])]]))

(defn library-panel []
  (let [library (re-frame/subscribe [::subs/library])]
  [re-com/v-box 
   :children [[re-com/p (if (empty? @library)
                         "You haven't rented any games yet!"
                         "Here are the games you are currently renting.")]
              [re-com/scroller
               :height "250px"
               :child  [re-com/h-box 
                        :children (interleave  (map library-item-listing @library)
                                              (repeat [re-com/gap :size "20px"]))]]]]))



;; about

(defn about-title []
  [re-com/title
   :label "About Grendel Games"
   :level :level2])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title]
              [re-com/p "Thank you very much for giving me this opportunity to demonstrate my skills! I am very excited to discuss this application and its structure in person."]
              [re-com/p "It was a no-brainer to me to use ClojureScript to implement this project; and I decided also to use the ClojureScript framework with which I was most familiar: re-frame, with the re-com component library. re-frame uses a transactional, unidirectional dataflow model much like redux or the Elm Architecture, and it does so in a scalable, idiomatic Clojure way."]
              [re-com/p "I will admit I spent considerably more than 3 to 4 hours on this project, but it was because I was never satisfied with the experience. I was constantly experimenting with different ways to accomplish things, both in terms of implementation and UX. Clojure's recursive syntax structure, of course, makes rapid prototyping almost too easy. For this same reason, I didn't commit nearly as much as should I have, or would do in the context of a real project that was less of a protoyp. It rarely felt stable enough for a commit."]
              [re-com/p "There are also not very many comments, but that's because I feel that the implementation of pretty much everything is straightforward when you know the re-frame architecture. There is hardly any non-trivial business logic."]
              [re-com/p "I had never used JSONP before, as it happens, and that took me a little bit of time to figure out. I wasn't able to completely integrate the JSONP request in the exact way that re-frame would have wanted me to, but I still think the resulting code is decently clean."]
              [re-com/p "Thank you again for giving me this opportunity! See you Monday."]
              [re-com/p "Dale"]]])



;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :storefront [storefront-panel]
    :library [library-panel]
    :about-panel [about-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(def tabs 
  [{:id :home-panel 
    :label "Home"}
   {:id :storefront
    :label "Store"}
   {:id :library
    :label "My Library"}
   {:id :about-panel 
    :label "About Us"}]) 

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :height "100%"
     :children [[header]
                [re-com/v-box
                 :width "60%"
                 :children [[re-com/horizontal-tabs
                            :model active-panel
                            :tabs tabs
                           :on-change #(re-frame/dispatch [::events/set-active-panel %]) ]
                            [panels @active-panel]]
                 :class "content" ]]]))
