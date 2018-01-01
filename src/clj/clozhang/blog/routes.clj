(ns clozhang.blog.routes
  "The routes for the blog need to take into consideration the following:

   * Actual posts will be generated behind the scenes when processing on-disk
     content (e.g., when calling `process-all-by-year-and-month`).
   * The routes are only used durng development, when serving content
     dynamically.
   * Since the posts have already been generated and saved to disc, their
     routes should be generated dynamically as URI path / slurp call pairs."
  (:require
    [clozhang.blog.reader :as reader]
    [clozhang.blog.web.content.page :as page]
    [clojusc.twig :refer [pprint]]
    [dragon.blog.core :as blog]
    [dragon.config.core :as config]
    [dragon.event.system.core :as event]
    [dragon.event.tag :as tag]
    [taoensso.timbre :as log]))

(defn static-routes
  [system posts routes]
  (log/info "Generating pages for static pages ...")
  (merge
    routes
    {"/blog/about.html" (page/about system posts)
     "/blog/community.html" (page/community system posts)}))

(defn design-routes
  [system posts routes]
  (log/info "Generating pages for design pages ...")
  {"/blog/design/index.html" (page/design system posts)
   "/blog/design/bootstrap-theme.html" (page/bootstrap-theme system posts)
   "/blog/design/example-blog.html" (page/blog-example system posts)})

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
  {"/blog/index.html" (page/front-page system posts)
   "/blog/archives/index.html" (page/archives system posts)
   "/blog/categories/index.html" (page/categories system posts)
   "/blog/tags/index.html" (page/tags system posts)
   "/blog/authors/index.html" (page/authors system posts)})

(defn reader-routes
  [system posts routes]
  (log/info "Generating XML for feeds ...")
  (let [route "/blog/atom.xml"]
    (merge
      routes
      {route (reader/atom-feed
               system
               route
               (take (config/feed-count system) posts))})))

(defn routes
  [system posts]
  (log/trace "Got data:" (pprint (blog/data-for-logs posts)))
  (event/publish system tag/generate-routes-pre)
  (->> (static-routes system posts)
       (design-routes system posts)
       (post-routes system posts)
       (index-routes system posts)
       (reader-routes system posts)
       (event/publish->> system tag/generate-routes-post)
       vec))
