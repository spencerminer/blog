(ns html-page-utils
  (:require [hiccup.page :as hp]
            [parsers-converters :as pc]
            [resource-locations :as r]))

(def bootstrap-css
  [:link {:href "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
          :rel "stylesheet"
          :integrity "sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
          :crossorigin "anonymous"}])

(def bootstrap-bundle
  [:script {:src "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
            :integrity "sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
            :crossorigin "anonymous"}])

(def favicons
  (list
   [:link {:rel "apple-touch-icon" :href r/apple-touch-icon-location  :sizes "180x180"}]
   [:link {:rel "icon" :href r/icon-32-location  :sizes "32x32" :type "image/png"}]
   [:link {:rel "icon" :href r/icon-16-location  :sizes "16x16" :type "image/png"}]
   [:link {:rel "manifest" :href r/manifest-location }]
   [:link {:rel "icon" :href r/icon-ico-location }]))

(def html-header
  [:head
   [:meta {:charset "utf-8"}]
   [:title "Blog 0.1.0"]
   [:meta {:name "description" :content "Just an experiment"}]
   [:meta {:name "author" :content "Spencer"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   bootstrap-css
   (hp/include-css r/css-location)
   favicons])

(def page-footer
  [:div.footer
   [:p "The code to generate this website is at "
    [:a {:href "https://github.com/spencerminer/blog/"}
     "github.com/spencerminer/blog/"]]])

(defn make-toc [posts]
  [:div.toc
   [:h4 "Table of Contents"]
   (pc/make-toc-hiccup posts)])

(defn make-html-body [all-posts main-content]
  [:body
   [:div.container
    [:div.row [:h1 [:a {:href (str r/path-prefix "/")} "Here we go..."]]]
    [:div.row
     [:div.col-md-9 main-content]
     [:div.col-sm.order-first.order-md-last (make-toc all-posts)]]
    [:br]
    [:br]
    [:br]
    [:div.row page-footer]]
   bootstrap-bundle])

(defn make-html-page [body]
  (hp/html5 {:lang "en"} html-header body))