(ns all-test
  (:require [clojure.test :refer :all]
            [html-generator :as hg]))

(deftest generate-index-html-test
  (hg/generate-index-html!))
