(ns clozhang.blog.web.content.page
  (:require
    [clozhang.blog.web.content.data :as data]
    [dragon.selmer.core :refer [render]]
    [dragon.util :as util]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn about
  [system posts]
  (render
    "templates/pages/generic.html"
    (data/about system posts)))

(defn community
  [system posts]
  (render
    "templates/pages/generic.html"
    (data/community system posts)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Dynamic Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn post
  [system posts post-data]
  (render
    "templates/pages/post.html"
    (data/post system posts post-data)))

(defn front-page
  [system posts]
  (let [above-fold 5
        below-fold 5
        headline-posts (->> posts
                            (filter util/headline?)
                            (take (+ above-fold below-fold)))]
    (render
      "templates/pages/home.html"
      (data/front-page
        system
        posts
        headline-posts
        :above-fold-count above-fold
        :below-fold-count below-fold
        :column-count 2))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Listings Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn archives
  [system posts]
  (render
    "templates/listings/archives.html"
    (data/archives system posts)))

(defn categories
  [system posts]
  (render
    "templates/listings/categories.html"
    (data/categories system posts)))

(defn tags
  [system posts]
  (render
    "templates/listings/tags.html"
    (data/tags system posts)))

(defn authors
  [system posts]
  (render
    "templates/listings/authors.html"
    (data/authors system posts)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Design Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn design
  [system posts]
  (render
    "templates/design/main.html"
    (data/design system posts)))

(defn bootstrap-theme
  [system posts]
  (render
    "templates/design/bootstrap-theme.html"
    (data/design system posts)))

(defn blog-example
  [system posts]
  (render
    "templates/design/blog-example.html"
    (data/design system posts)))
