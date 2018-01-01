(ns clozhang.blog.cli.core
  (:require
    [clozhang.blog.cli.new :as new]
    [clozhang.blog.cli.show :as show]
    [clozhang.blog.core :as core]
    [clozhang.blog.routes :refer [routes]]
    [dragon.config.core :as config]
    [dragon.event.system.core :as event]
    [dragon.event.tag :as tag]
    [taoensso.timbre :as log]
    [trifl.docs :as docs]))

(defn run
  "
  Usage:
  ```
    blog COMMAND [help | arg...]
    blog [-h | --help | -v | --version]
  ```

  Commands:
  ```
    new      Create stubbed files for a new blog post
    show     Display various blog data in the terminal
    gen      Generate updated static content for blog
    run      Run the Clozhang blog locally as a Ring app
    help     Display this usage message
    version  Display the current NOWA version
  ```

  More information:

    Each command takes an optional 'help' subcommand that will provide
    usage information about the particular command in question, e.g.:

  ```
    $ blog new help
  ```"
  [system [cmd & args]]
  (log/debug "Got cmd:" cmd)
  (log/debug "Got args:" args)
  (event/publish system tag/run-cli {:cmd cmd :args args})
  (case cmd
    :new (new/run system args)
    :show (show/run system args)
    :gen (core/generate system)
    :run (core/generate system)
    :help (docs/print-docstring #'run)
    :version (print (core/version))
    ;; Aliases
    :--help (docs/print-docstring #'run)
    :--version (print (core/version))
    :-h (docs/print-docstring #'run)
    :-v (print (core/version)))
  (event/publish system tag/shutdown-cli))
