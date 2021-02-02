(ns site-generator
  (:require [html-page-utils :as h]
            [parsers-converters :as pc])
  (:import (java.io File)))

(def posts-directory "resources/markdown-posts")

(defn get-blogpost-filenames [dir]
  (->> dir
       clojure.java.io/file
       file-seq
       (filter #(.isFile %))
       (map str)))

(defn make-all-posts-hiccup [posts]
  (->> posts
       (sort-by :publish-date)
       reverse
       (map pc/blogpost->hiccup)
       (apply conj [:div.main-post-area])))

(defn make-folder! [path]
  (.mkdir (File. ^String path)))

(defn generate-post-pages! [blogposts post]
  (make-folder! (str "posts/" (:publish-date post)))
  (->> post
       pc/blogpost->hiccup
       (h/make-html-body blogposts)
       h/make-html-page
       (spit (pc/make-post-url post))))

(defn generate-all-post-pages! [blogposts]
  (doall
   (map (partial generate-post-pages! blogposts)
        blogposts)))

(defn generate-toc-page! [blogposts]
  (->> blogposts
       h/make-toc
       h/make-html-page
       (spit "posts/index.html")))

(defn generate-index! [blogposts]
  (->> blogposts
       make-all-posts-hiccup
       (h/make-html-body blogposts)
       h/make-html-page
       (spit "index.html")))

(def blogpost-maps
  (->> posts-directory
       get-blogpost-filenames
       (mapv pc/md-file->blogpost-map)))

(defn generate-website-html! []
  (generate-all-post-pages! blogpost-maps)
  (generate-toc-page! blogpost-maps)
  (generate-index! blogpost-maps))
