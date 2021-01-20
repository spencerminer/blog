(ns html-generator
  (:require [hiccup.core :as h]
            [hiccup.page :as hp]))

(def html-header
  [:head
   [:meta {:charset "utf-8"}]
   [:title "Blog 0.1.0"]
   [:meta {:name "description"
           :content "Just an experiment"}]
   [:meta {:name "author"
           :content "Spencer"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1"}]
   #_(hp/include-js "myscript.js")
   #_(hp/include-css "mystyle.css")])

(def html-body
  [:body
   [:p "Here we go... with Hiccup!"]
   #_[:script {:src "js/main.js"}]])

(defn generate-index-html []
  (spit "index.html"
        (hp/html5 {:lang "en"}
                  html-header
                  html-body)))

(comment
 (generate-index-html))
