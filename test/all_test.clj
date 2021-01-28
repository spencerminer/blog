(ns all-test
  (:require [clojure.test :refer :all]
            [site-generator :as hg]))

(deftest generate-index-html-test
  (hg/generate-website-html!))
