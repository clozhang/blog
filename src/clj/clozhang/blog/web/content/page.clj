(ns clozhang.blog.web.content.page
  (:require [clozhang.blog.web.content.data :as data]
            [dragon.blog :as blog]
            [dragon.web.content :as content]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn about
  [system posts]
  (content/render
    "templates/pages/generic.html"
    (data/about system posts)))

(defn community
  [system posts]
  (content/render
    "templates/pages/generic.html"
    (data/community system posts)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Dynamic Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn post
  [system posts post-data]
  (content/render
    "templates/pages/post.html"
    (data/post system posts post-data)))

(defn front-page
  [system posts]
  (let [above-fold 5
        below-fold 5
        headline-posts (->> posts
                            (filter util/headline?)
                            (take (+ above-fold below-fold)))]
    (content/render
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
  (content/render
    "templates/listings/archives.html"
    (data/archives system posts)))

; (defn archives
;   [data]
;   (content/render
;     "templates/archives.html"
;     (-> data
;         (blog/data-for-archives)
;         (data/archives))))

(defn categories
  [system posts]
  (content/render
    "templates/listings/categories.html"
    (data/categories system posts)))

; (defn categories
;   [data]
;   (content/render
;     "templates/categories.html"
;     (-> data
;         (blog/data-for-categories)
;         (data/categories))))

(defn tags
  [system posts]
  (content/render
    "templates/listings/tags.html"
    (data/tags system posts)))

; (defn tags
;   [data]
;   (content/render
;     "templates/tags.html"
;     (-> data
;         (blog/data-for-tags)
;         (data/tags))))

(defn authors
  [system posts]
  (content/render
    "templates/listings/authors.html"
    (data/authors system posts)))

; (defn authors
;   [data]
;   (content/render
;     "templates/authors.html"
;     (-> data
;         (blog/data-for-authors)
;         (data/authors))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Design Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn design
  [system posts]
  (content/render
    "templates/design/main.html"
    (data/design system posts)))

; (defn design
;   []
;   (content/render
;     "templates/design.html"
;     (data/design)))

(defn bootstrap-theme
  [system posts]
  (content/render
    "templates/design/bootstrap-theme.html"
    (data/design system posts)))

; (defn bootstrap-theme
;   []
;   (content/render
;     "templates/bootstrap-theme.html"
;     (data/design)))

(defn blog-example
  [system posts]
  (content/render
    "templates/design/blog-example.html"
    (data/design system posts)))

; (defn blog-example
;   []
;   (content/render
;     "templates/blog-example.html"
;     (data/design)))
