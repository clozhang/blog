(ns clozhang.blog.web.content.data
  (:require
    [clojure.java.io :as io]
    [clojure.string :as string]
    [dragon.blog.content.block :as block]
    [dragon.blog.content.data :as page-data]
    [dragon.blog.core :as blog]
    [dragon.blog.tags :as blog-tags]
    [dragon.config.core :as config]
    [markdown.core :as markdown]
    [taoensso.timbre :as log]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Base Data Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn common
  ([system]
    (common system {}))
  ([system posts]
    (common system posts {}))
  ([system posts additional-opts]
    (let [base-opts {:site-title (config/name system)
                     :site-description (config/description system)}]
      (page-data/common posts (merge base-opts additional-opts)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Static Pages Data   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn about
  [system posts]
  (common system
          posts
          (page-data/default-markdown-content-opts
            {:title "About"
             :category-key :about
             :content-filename "about.md"})))

(defn community
  [system posts]
  (let [data-content {}]
    (common system
            posts
            (page-data/default-markdown-content-opts
              {:title "Community"
               :category-key :community
               :content-filename "community.md"}))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Dynamic Pages Data   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn post
  [system posts post-data]
  (common system
          posts
          {:category-key "archives"
           :post-data post-data
           :tags (blog-tags/unique [post-data])}))

(defn front-page
  [system all-posts top-posts &
   {:keys [above-fold-count below-fold-count column-count]}]
  (let [above-posts (take above-fold-count top-posts)
        headliner (first above-posts)
        grouped-posts (partition column-count
                                 (nthrest above-posts 1))
        below-posts (nthrest top-posts above-fold-count)]
    (common system
            all-posts
            {:category-key "index"
             :tags (blog-tags/get-stats all-posts)
             :headliner headliner
             :posts-data grouped-posts
             :posts-count (count top-posts)
             :above-count (count above-posts)
             :below-count (count below-posts)
             :below-fold-data below-posts})))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Listings Pages   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn archives
  [system posts]
  (common system
          posts
          (page-data/default-data-content-opts
            {:title "Archives"
             :category-key "archives"
             :posts-data (blog/group-data :archives posts)})))

(defn categories
  [system posts]
  (common system
          posts
          (page-data/default-data-content-opts
            {:title "Categories"
             :category-key "categories"
             :posts-data (blog/group-data :categories posts)})))

(defn tags
  [system posts]
  (common system
          posts
          (page-data/default-data-content-opts
            {:title "Tags"
             :category-key "tags"
             :posts-data (blog/group-data :tags posts)})))

(defn authors
  [system posts]
  (common system
          posts
          (page-data/default-data-content-opts
            {:title "Authors"
             :category-key "authors"
             :posts-data (blog/group-data :authors posts)})))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Design Pages Data   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn design
  [system posts]
  (common system
          posts
          (page-data/default-data-content-opts
            {:title "Design"
             :category-key "design"})))
