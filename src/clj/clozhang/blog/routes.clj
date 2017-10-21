(ns clozhang.blog.routes
  "The routes for the blog need to take into consideration the following:

   * Actual posts will be generated behind the scenes when processing on-disk
     content (e.g., when calling `process-all-by-year-and-month`).
   * The routes are only used durng development, when serving content
     dynamically.
   * Since the posts have already been generated and saved to disc, their
     routes should be generated dynamically as URI path / slurp call pairs.
  "
  (:require [clozhang.blog.reader :as reader]
            [clozhang.blog.web.content.page :as page]
            [clojusc.twig :refer [pprint]]
            [dragon.blog :as blog]
            [dragon.config :as config]
            [taoensso.timbre :as log]))

(defn static-routes
  ([system posts]
    (static-routes system posts {}))
  ([system posts routes]
    (log/info "Generating pages for static pages ...")
    (merge
      routes
      {"/blog/about.html" (page/about system posts)
       "/community.html" (page/community system posts)})))

(defn design-routes
  [system posts routes]
  (log/info "Generating pages for design pages ...")
  (merge
    routes
    {"/design/index.html" (page/design system posts)
     "/design/bootstrap-theme.html" (page/bootstrap-theme system posts)
     "/design/example-blog.html" (page/blog-example system posts)}))

(defn post-routes
  [system posts routes]
  (log/info "Generating pages for blog posts ...")
  (merge
    routes
    (blog/get-indexed-archive-routes
      (map vector (iterate inc 0) posts)
      :gen-func (partial page/post system posts)
      :uri-base (config/posts-path system))))

(defn index-routes
  [system posts routes]
  (log/info "Generating pages for front page, archives, categories, etc. ...")
  (merge
    routes
    {"/index.html" (page/front-page system posts)
     "/archives/index.html" (page/archives system posts)
     "/categories/index.html" (page/categories system posts)
     "/tags/index.html" (page/tags system posts)
     "/authors/index.html" (page/authors system posts)}))


(defn reader-routes
  [uri-base data]
  (log/info "Generating XML for feeds ...")
  (let [route "/atom.xml"]
    {route (reader/atom-feed
             uri-base route (take (config/feed-count) data))}))

; (defn sitemaps-routes
;   [system routes]
;   (let [route "/sitemap.xml"]
;     (merge
;       routes
;       {route (sitemapper/gen routes)})))

(defn routes
  [system posts]
  (log/trace "Got data:" (pprint (blog/data-for-logs posts)))
  (->> (static-routes system posts)
       (design-routes system posts)
       (post-routes system posts)
       (index-routes system posts)
       (reader-routes system posts)
       (sitemaps-routes system)
       vec))
