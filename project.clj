(defn get-banner
  []
  (str
    (slurp "resources/text/repl-banner.txt")
    (slurp "resources/text/repl-loading.txt")))

(defn get-prompt
  [ns]
  (str "\u001B[35m[\u001B[34m"
       ns
       "\u001B[35m]\u001B[33m λ\u001B[m=> "))

(defproject clozhang/blog "0.1.0-SNAPSHOT"
  :description "The Clozhang Blog"
  :url "https://clozhang.github.io/blog"
  :scm {
    :name "git"
    :url "https://github.com/clozhang/blog"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :exclusions [org.clojure/clojure]
  :dependencies [
    [clojusc/trifl "0.3.0-SNAPSHOT"]
    [dragon "0.5.0-SNAPSHOT"]
    [org.clojure/clojure "1.8.0"]
    [org.clojure/data.xml "0.0.8"]]
  :source-paths ["src/clj"]
  :profiles {
    :dragon {
      :domain "clozhang.github.io/blog"
      :name "The Clozhang Blog"
      :description "News, Information, & Tutorials for Clojure π-Calculus and the Clozhang Library Collection"
      :dev-port 5097
      :output-dir "docs"
      :base-path "/blog"
      :posts-path "/blog/archives"
      :feed-count 20
      :cli {
        :log-level :info
        :log-nss [clozhang.blog dragon]}
      :workflow {
       :storage :db}}
    :ubercompile {:aot :all}
    :dev {
      :source-paths ["dev-resources/src"]
      :main clozhang.blog.main
      :repl-options {
        :init-ns clozhang.blog.dev
        :prompt ~get-prompt}
      :dependencies [
        [http-kit "2.2.0"]
        [leiningen-core "2.7.1"]
        [org.clojure/tools.namespace "0.2.11"]]}
    :test {
      :plugins [
        [lein-ancient "0.6.15"]
        [jonase/eastwood "0.2.5"]
        [lein-bikeshed "0.5.0"]
        [lein-kibit "0.1.6"]
        [venantius/yagni "0.1.4"]]}}
  :aliases {
    "blog"
      ^{:doc (str "The Clozhang blog CLI; type `lein blog help` for "
                  "commands\n")}
      ["run" "-m" "clozhang.blog.main" "cli"]
    "check-deps"
      ["with-profile" "+test" "ancient" "check" ":all"]})
