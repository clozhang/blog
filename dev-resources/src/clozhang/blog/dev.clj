(ns clozhang.blog.dev
  "Clozhang blog development namespace

  This namespace is particularly useful when doing active development on the
  Clozhang blog application."
  (:require
    [clozhang.blog.cli.core :as cli]
    [clozhang.blog.components.system]
    [clozhang.blog.core]
    [clozhang.blog.main :as main]
    [clozhang.blog.routes :as routes]
    [clozhang.blog.web.content.data :as page-data]
    [clozhang.blog.web.content.page :as page]
    [clojure.edn :as edn]
    [clojure.java.io :as io]
    [clojure.pprint :refer [pprint print-table]]
    [clojure.reflect :refer [reflect]]
    [clojure.string :as string]
    [clojure.tools.namespace.repl :as repl]
    [clojure.walk :refer [macroexpand-all]]
    [clojusc.twig :as logger]
    [dragon.blog.content.block :as block]
    [dragon.blog.content.rfc5322 :as rfc5322]
    [dragon.blog.core :as blog]
    [dragon.blog.generator :as gen]
    [dragon.blog.post.core :as post]
    [dragon.cli.core :as dragon-cli]
    [dragon.components.core :as component-api]
    [dragon.components.system :as components]
    [dragon.config.core :as config]
    [dragon.data.sources.core :as data-source]
    [dragon.data.sources.impl.redis :as redis-db]
    [dragon.dev.system :as dev-system]
    [dragon.main :as dragon-main]
    [dragon.selmer.tags.flickr :as flickr]
    [dragon.util :as dragon-util]
    [markdown.core :as md]
    [selmer.parser :as selmer]
    [taoensso.timbre :as log]
    [trifl.fs :as fs]
    [trifl.java :as java]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Initial Setup & Utility Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(logger/set-level! ['clozhang.blog 'dragon] :info)

(dev-system/set-generator-ns "clozhang.blog.core")
(dev-system/set-system-ns "clozhang.blog.components.system")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   State Management   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def startup #'dev-system/startup)
(def shutdown #'dev-system/shutdown)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Reloading Management   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn reset
  []
  (dev-system/shutdown)
  (repl/refresh :after 'dragon.dev.system/startup))

(def refresh #'repl/refresh)
(def refresh #'reset)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Data   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def redis #'dev-system/redis)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;   Utility Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def show-lines-with-error #'dev-system/show-lines-with-error)
(def show-posts #'dev-system/show-posts)
(def generate #'dev-system/generate)
(def force-regenerate #'dev-system/force-regenerate)
