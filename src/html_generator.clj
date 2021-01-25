(ns html-generator
  (:require [clojure.edn :as edn]
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
   [:link {:rel "apple-touch-icon" :href "resources/favicons/apple-touch-icon.png" :sizes "180x180"}]
   [:link {:rel "icon" :href "resources/favicons/favicon-32x32.png" :sizes "32x32" :type "image/png"}]
   [:link {:rel "icon" :href "resources/favicons/favicon-16x16.png" :sizes "16x16" :type "image/png"}]
   [:link {:rel "manifest" :href "resources/favicons/site.webmanifest.json"}]
   [:link {:rel "icon" :href "resources/favicons/favicon.ico"}]])

(defn post->hiccup [post-filename]
  (let [post-map (->> post-filename
                      (format "resources/posts/%s.edn")
                      slurp
                      edn/read-string)]
    (apply conj
           [:div.row
            [:h3 (:title post-map)]
            [:h5 (:publish-date post-map)]]
           (:hiccup-body post-map))))

(defn parse-md-section [lines]
  (let [[md-key & content]
        (clojure.string/split (first lines) #" ")]
    (case (first md-key)
      \# [(keyword (str "h" (count md-key)))
          (clojure.string/join " " content)]
      \+ (he/unordered-list
          (map #(->> % (drop 2) (apply str))
               lines))
      \* (he/unordered-list
          (map #(->> % (drop 2) (apply str))
               lines))
      \1 (he/ordered-list
          (map #(->> % (drop 3) (apply str))
               lines))
      (apply conj [:p] lines))))

;;(defn md-file->hiccup-row [filename]
;;  (->> filename
;;       slurp
;;       clojure.string/split-lines
;;       (partition-by empty?)
;;       (remove #(empty? (first %)))
;;       (map parse-md-section)
;;       (apply conj [:div.row])))

(defn is-#-char [c] (= \# c))

(defn parse-my-md-header [header-strings]
  (zipmap [:title :publish-date :tags :author]
          (map #(->> %
                     (drop-while is-#-char)
                     (apply str)
                     clojure.string/trim)
               header-strings)))

(defn md-file->post-map [filename]
  (let [[hdr & body]
        (->> filename
             slurp
             clojure.string/split-lines
             (partition-by empty?)
             (remove #(empty? (first %))))]
    (merge
     (parse-my-md-header hdr)
     {:body (->> body
                 (map parse-md-section)
                 (apply conj [:div.row]))})))

(comment
 (->> "resources/markdown-posts"                            ;; Get filenames
      clojure.java.io/file                                  ;; ||
      file-seq                                              ;; ||
      (filter #(.isFile %))                                 ;; ||
      (map str)                                             ;; \/

      (mapv md-file->post-map)
      ;; call this first, then use this w/ options to gen html
      ;; with something similr to post->hiccup
      ;; then put em all together:
      #_(apply conj [:div.posts])
      )
 )

(def html-body
  [:body
   [:div.container
    [:div.row
     [:h1 "Here we go..."]]
    [:br]
    [:div.row
     [:div.col-9
      (post->hiccup "post-2")
      (post->hiccup "post-1")
      ;;(md-file->hiccup-row "resources/markdown-posts/test-post.md")
      ]
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
