(ns parsers-converters
  (:require [clojure.set :as set]
            [clojure.string :as string]
            [hiccup.element :as he]
            [resource-locations :as r]))

(defn blogpost->hiccup [blogpost-map]
  [:div.row
   [:h3 (:title blogpost-map)]
   [:h5 (:publish-date blogpost-map)]
   (:hiccup-body blogpost-map)])

(defn heading-to-hiccup [s]
  (let [[hashes & content] (clojure.string/split s #" ")]
    [(keyword (str "h" (count hashes)))
     (string/join " " content)]))

(defn combine-overflow-lines [lines]
  (reduce (fn [acc x]
            (let [trimmed (string/trim x)]
              (if (= \+ (first trimmed))
                (conj acc x)
                (conj (vec (drop-last acc))
                      (str (last acc) trimmed)))))
          [] lines))

(defn parse-indenting-list-element [line]
  (let [[spaces & content] (clojure.string/split line #"\+ ")]
    [(keyword (str "li.list-level-" (int (/ (count spaces) 4))))
     content]))

(defn indenting-unordered-list [lines]
  [:ul (->> lines
            combine-overflow-lines
            (map parse-indenting-list-element))])

(defn parse-md-section [lines]
  (let [section-type (first (first lines))]
    (case section-type
      \# (map heading-to-hiccup lines)
      \+ (indenting-unordered-list lines)
      ;; Do I bother doing one for *?
      ;;\1 (indenting-ordered-list lines)
      (apply conj [:p] lines))))

(defn is-#-char [c] (= \# c))

(defn parse-my-md-header [header-strings]
  (zipmap [:title :publish-date :tags :author]
          (map #(->> %
                     (drop-while is-#-char)
                     (apply str)
                     clojure.string/trim)
               header-strings)))

(defn spaced-string-to-set [s]
  (-> s
      (clojure.string/split #" ")
      set))

(defn update-blogpost-header [header-map]
  (merge {:author "Spencer Miner"}
         (update header-map
                 :tags
                 spaced-string-to-set)))

(defn md-file->blogpost-map [filename]
  (let [[hdr & body]
        (->> filename
             slurp
             clojure.string/split-lines
             (partition-by empty?)
             (remove #(empty? (first %))))]
    (merge
     (-> hdr
         parse-my-md-header
         update-blogpost-header)
     {:hiccup-body (->> body
                        (map parse-md-section)
                        (apply conj [:div.post-body]))})))

(defn make-post-url [post]
  (format "posts/%s/%s.html"
          (:publish-date post)
          (-> post
              :title
              string/trim
              string/lower-case
              (string/replace #" " "-")
              (string/replace #"[^\w|-]" ""))))

(defn make-link-to-post [post]
  [:a {:href (str r/path-prefix "/" (make-post-url post))}
   (:title post)])

(defn make-toc-hiccup [posts]
  [:div.toc
   [:div.post-titles
    (he/unordered-list
     (map make-link-to-post posts))]
   [:div.post-tags
    [:h5 "Tags"]
    (->> posts
         (map :tags)
         (apply set/union)
         (apply list)
         he/unordered-list)]])

(comment
 (let [posts site-generator/blogpost-maps]
   (->> posts
        (map :tags)
        (apply set/union)
        (apply list)
        he/unordered-list)))
