(ns html-generator
  (:require [hiccup.core :as h]
            [hiccup.element :as he]
            [hiccup.page :as hp]))

(def bootstrap-css
  [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          :rel "stylesheet"
          :integrity "sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          :crossorigin "anonymous"}])

(def bootstrap-bundle
  [:script {:src "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            :integrity "sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            :crossorigin "anonymous"}])

(def html-header
  [:head
   [:meta {:charset "utf-8"}]
   [:title "Blog 0.1.0"]
   [:meta {:name "description" :content "Just an experiment"}]
   [:meta {:name "author" :content "Spencer"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]

   bootstrap-css

   ;; favicons
   [:link {:rel "apple-touch-icon" :href "resources/apple-touch-icon" :sizes "180x180"}]
   [:link {:rel "icon" :href "resources/favicon-32x32.png" :sizes "32x32" :type "image/png"}]
   [:link {:rel "icon" :href "resources/favicon-16x16.png" :sizes "16x16" :type "image/png"}]
   [:link {:rel "manifest" :href "resources/site.webmanifest.json"}]
   [:link {:rel "icon" :href "resources/favicon.ico"}]])

(def html-body
  [:body
   [:div.container
    [:div.row
     [:h1 "Here we go..."]
     [:h3 "with Hiccup!"]]
    [:br]
    [:div.row
     [:div.col-9
      [:div.row
       [:h3 "Post #1"]
       [:p "I pledge that these blog posts will either:"
        (he/unordered-list ["be short or" "have lots of pictures/diagrams"])]]]
     [:div.col-3
      [:h5 "The index/table of contents will go here eventually"]]]
    [:br]
    [:br]
    [:br]
    [:div.row
     [:p "The code to generate this website is at "
      [:a {:href "https://github.com/spencerminer/blog/"}
       "github.com/spencerminer/blog/"]]]]
   bootstrap-bundle])

(defn generate-index-html []
  (spit "index.html"
        (hp/html5 {:lang "en"}
                  html-header
                  html-body)))

(comment
 (generate-index-html
  ))
