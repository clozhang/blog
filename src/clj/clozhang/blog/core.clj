(ns clozhang.blog.core
  (:require
    [clojusc.twig :as logger]
    [dragon.blog.core :as blog]
    [dragon.blog.generator :as gen]
    [dragon.config.core :as config]
    [dragon.core :as dragon-core]
    [dragon.util :as util]
    [clozhang.blog.routes :refer [routes]]
    [trifl.core :refer [sys-prop]]
    [trifl.docs :as docs]))

(defn version
  []
  (let [version (sys-prop "blog.version")
        build (util/get-build)]
    (format "Clozhang Blog version %s, build %s\n" version build)))

(defn generate
  [system]
  (let [posts (dragon-core/generate system)
        generated-routes (routes system posts)]
    (gen/run system generated-routes)))

;; XXX force-regenerate doesn't seem to be working yet ... however, this works:
;;     `(data-source/set-posts-checksums (get-in system [:db :querier]) "")`
;;     which is odd, since that's what force-regenerate ends up calling ...
(defn force-regenerate
  [system]
  (blog/reset-content-checksums system)
  (generate system))
