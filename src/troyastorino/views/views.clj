(ns troyastorino.views.views
  (:use [troyastorino.views.common :only [page template-path]]
        [noir.core :only [defpage]]
        [markdown :only [md-to-html-string]]
        [net.cgrand.enlive-html]
        [clojure.java.io :only [file]])
  (:require [clojure.string :as string]))

(defn- markdown-path [page-name]
  "Path to the root of where markdown files are (markdown files hold the text for the site)."
  (str "resources/" page-name ".md"))

(defn- md-to-html [path]
  "Takes a markdown file specified by path and converts it to HTML nodes"
  (html-snippet (md-to-html-string (slurp path))))

(defn file-list [folder]
  "Returns a seq of Files in folder"
  (let [folder-path (fn [f] (string/join "/" (butlast (string/split (str f) #"/"))))]
    (filter (fn [x] (and (. x isFile) (= (folder-path x) folder))) (rest (file-seq (file folder))))))

(defn file-name [f]
  "Given a file object, gets the file name without an extension"
  (-> f
      str
      (string/split #"/")
      last
      (string/split #"\.")
      first))

(defn human-friendly-title [s]
  "Takes a string, turns underscores and hyphens into spaces, and capitalizes the words"
  (let [words (-> s
                 (string/replace #"[_\-]" " ")
                 (string/split #" "))] 
    (->> words
         (map string/capitalize)
         (string/join " "))))

(defn html-links [page-name]
  (let [fs (file-list (str "resources/" page-name))
        links-list (snippet (template-path "listing.html") [:ul] [files]
                            [:li] (clone-for [f files] [:a] (let [name (file-name f)]
                                                             (do->
                                                              (content (human-friendly-title name))
                                                              (set-attr :href (str page-name "/" name))))))]
    (links-list fs)))

(defn- intro-page [page-name & options]
  "Takes a markdown file name for an intro page and returns HTML nodes"
  (apply page (concat (md-to-html (markdown-path page-name))
                      (html-links page-name)) options))

(defpage "/" []
  (intro-page "welcome"))

(defpage "/purpose" []
  (intro-page "purpose" :title "purpose"))

(defpage "/projects" []
  (intro-page "projects" :title "projects"))

(defpage "/projects/:name" {:keys [name]}
  (page (md-to-html (str "resources/projects/" name ".md"))
        :title (str "projects - " (human-friendly-title name))))

(defpage "/thoughts" []
  (intro-page "thoughts" :title "thoughts"))

(defpage "/thoughts/:name" {:keys [name]}
  (page (md-to-html (str "resources/thoughts/" name ".md"))
        :title (str "thoughts - " (human-friendly-title name))))

(defpage "/tidbits" []
  (intro-page "tidbits" :title "tidbits"))

(defpage "/tidbits/:name" {:keys [name]}
  (page (md-to-html (str "resources/tidbits/" name ".md"))
        :title (str "tidbits - " (human-friendly-title name))
        :stylesheets ["/lib/prettify/prettify.css"]
        :scripts ["//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"
                  "/lib/prettify/prettify.js"
                  "/lib/prettify/lang-clj.js"
                  "/lib/prettify/lang-lisp.js"
                  "/js/pretty-code.js"]))
