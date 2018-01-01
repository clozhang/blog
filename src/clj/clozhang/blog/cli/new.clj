(ns clozhang.blog.cli.new
  (:require
    [clojure.pprint :refer [pprint]]
    [clojusc.twig :as logger]
    [clozhang.blog.cli.new.post :as post]
    [dragon.util :as util]
    [taoensso.timbre :as log]
    [trifl.docs :as docs]))

(defn run
  "
  Usage:
  ```
    blog new [SUBCOMMAND | help]
  ```

  If no SUBCOMMAND is provided, the default 'post' will be used (with the
  default content type ':md').

  Subcommands:
  ```
    post    Create a new post stub; takes a subcommand for the type of
              content to create; see 'blog new post help' for usage
  ```"
  [system [cmd & args]]
  (log/debug "Got cmd:" cmd)
  (log/debug "Got args:" args)
  (case cmd
    :post (post/run system args)
    :help (docs/print-docstring #'run)
    (post/run system [:md])))
