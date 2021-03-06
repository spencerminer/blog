(ns resource-locations)

(def local-test? (System/getenv "BLOG_LOCAL_ENV"))

;; On a GitHub-hosted website `/` is the root folder, but when developing
;; locally `/blog/` is the root folder, so I need to change all the absolute
;; paths depending on what environment I'm in. Ugh
(def path-prefix (when local-test? "/blog"))

(def css-location (str path-prefix "/css/my-css.css"))

;; Favicons
(def apple-touch-icon-location
  (str path-prefix "/resources/favicons/apple-touch-icon.png"))
(def icon-32-location
  (str path-prefix "/resources/favicons/favicon-32x32.png"))
(def icon-16-location
  (str path-prefix "/resources/favicons/favicon-16x16.png"))
(def manifest-location
  (str path-prefix "/resources/favicons/site.webmanifest.json"))
(def icon-ico-location
  (str path-prefix "/resources/favicons/favicon.ico"))
