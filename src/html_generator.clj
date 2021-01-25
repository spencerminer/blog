(ns html-generator
  (:require [hiccup.page :as hp]
            [parsers-converters :as pc]))

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
  [:div
   [:link {:rel "apple-touch-icon" :href "resources/favicons/apple-touch-icon.png" :sizes "180x180"}]
   [:link {:rel "icon" :href "resources/favicons/favicon-32x32.png" :sizes "32x32" :type "image/png"}]
   [:link {:rel "icon" :href "resources/favicons/favicon-16x16.png" :sizes "16x16" :type "image/png"}]
   [:link {:rel "manifest" :href "resources/favicons/site.webmanifest.json"}]
   [:link {:rel "icon" :href "resources/favicons/favicon.ico"}]])

(def html-header
  [:head
   [:meta {:charset "utf-8"}]
   [:title "Blog 0.1.0"]
   [:meta {:name "description" :content "Just an experiment"}]
   [:meta {:name "author" :content "Spencer"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   bootstrap-css
   favicons])

(def posts-directory "resources/markdown-posts")

(defn get-blogpost-filenames [dir]
  (->> dir
       clojure.java.io/file
       file-seq
       (filter #(.isFile %))
       (map str)))

(def create-blogpost-maps-vector
  (->> posts-directory
       get-blogpost-filenames
       (mapv pc/md-file->blogpost-map)))

(defn make-post-area [posts]
  (->> posts
       (sort-by :publish-date)
       reverse
       (map pc/blogpost->hiccup)
       (apply conj [:div.main-post-area])))

(defn make-toc [posts]
  [:div.toc
   [:h5 "The table of contents will go here eventually"]])

(def page-footer
  [:div.footer
   [:p "The code to generate this website is at "
    [:a {:href "https://github.com/spencerminer/blog/"}
     "github.com/spencerminer/blog/"]]])

(defn make-html-body [posts]
  [:body
   [:div.container
    [:div.row [:h1 "Here we go..."]]
    [:br]
    [:div.row
     [:div.col-9 (make-post-area posts)]
     [:div.col-3 (make-toc posts)]]
    [:br]
    [:br]
    [:br]
    [:div.row page-footer]]
   bootstrap-bundle])

(defn generate-index-html []
  (->> create-blogpost-maps-vector
       make-html-body
       (hp/html5 {:lang "en"} html-header)
       (spit "index.html")))
