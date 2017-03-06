(defproject clojang/blog "0.1.0-SNAPSHOT"
  :description "Blog for Forgotten Roads MX"
  :url "https://clojang.lfe.io/"
  :scm {
    :name "git"
    :url "https://github.com/clojang/blog"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [clojusc/env-ini "0.3.0-SNAPSHOT"]
    [clojusc/rfc5322 "0.3.0-SNAPSHOT"]
    [clojusc/trifl "0.1.0-SNAPSHOT"]
    [markdown-clj "0.9.97"]
    [clojusc/twig "0.3.1-SNAPSHOT"]
    [me.raynes/cegdown "0.1.1"]
    [org.clojure/clojure "1.8.0"]
    [selmer "1.10.6"]
    [stasis "2.3.0"]
    [tentacles "0.5.1"]]
  :source-paths ["src/clj"]
  :blog {
    :dev-port 5098
    :output-dir "docs"
    :cli {
      :log-level :info}}
  :profiles {
    :uberjar {:aot :all}
    :dev {
      :source-paths ["dev-resources/src"]
      :main clojang.blog.main
      :aliases {"blog" ^{:doc (str "The Clojang blog CLI; "
                                   "type `lein blog help` for commands\n")}
                       ["run" "-m" "clojang.blog.main" "cli"]}
      :repl-options {
        :init-ns clojang.blog.dev}
      :plugins [
        [lein-simpleton "1.3.0"]]
      :dependencies [
        [http-kit "2.2.0"]
        [leiningen-core "2.7.1"]
        [org.clojure/tools.namespace "0.2.11"]]
      ;:pedantic? :warn
      }
    :test {
      :plugins [
        [lein-ancient "0.6.10"]
        [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
        [lein-bikeshed "0.4.1" :exclusions [org.clojure/tools.namespace]]
        [lein-kibit "0.1.2" :exclusions [org.clojure/clojure]]
        [venantius/yagni "0.1.4"]]}})
