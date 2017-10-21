(defproject clozhang/blog "0.2.0-SNAPSHOT"
  :description "The Clozhang Blog"
  :url "https://clozhang.github.io/blog"
  :scm {
    :name "git"
    :url "https://github.com/clozhang/blog"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/data.xml "0.0.8"]
    [clojusc/env-ini "0.3.0-SNAPSHOT"]
    [clojusc/rfc5322 "0.3.0-SNAPSHOT"]
    [clojusc/trifl "0.1.0-SNAPSHOT"]
    [clojusc/twig "0.3.1-SNAPSHOT"]
    [ring/ring-core "1.6.0-RC1"]
    [dragon "0.1.0-SNAPSHOT"]
    [markdown-clj "0.9.97"]
    [org.clojure/clojure "1.8.0"]
    [selmer "1.10.6"]
    [stasis "2.3.0"]]
  :source-paths ["src/clj"]
  :dragon {
    :domain "clozhang.github.io/blog"
    :name "The Clozhang Blog"
    :description "News, Information, & Tutorials for Clojure π-Calculus and the Clozhang Library Collection"
    :dev-port 5097
    :output-dir "docs"
    :posts-path "/archives"
    :feed-count 20
    :cli {
      :log-level :info
      :log-ns [clozhang.blog dragon]}}
  :profiles {
    :uberjar {:aot :all}
    :dev {
      :source-paths ["dev-resources/src"]
      :main clozhang.blog.main
      :aliases {"blog" ^{:doc (str "The Clozhang blog CLI; "
                                   "type `lein blog help` for commands\n")}
                       ["run" "-m" "clozhang.blog.main" "cli"]}
      :repl-options {
        :init-ns clozhang.blog.dev}
      :plugins [
        [lein-simpleton "1.3.0"]]
      :dependencies [
        [http-kit "2.2.0"]
        [leiningen-core "2.7.1"]
        [org.clojure/tools.namespace "0.2.11"]]}
    :test {
      :plugins [
        [lein-ancient "0.6.10"]
        [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
        [lein-bikeshed "0.4.1" :exclusions [org.clojure/tools.namespace]]
        [lein-kibit "0.1.4" :exclusions [org.clojure/clojure]]
        [venantius/yagni "0.1.4"]]}})
