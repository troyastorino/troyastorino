(ns troyastorino.views.views
  (:use [troyastorino.views.common :only [page template-path]]
        [markdown :only [md-to-html-string]]
        [net.cgrand.enlive-html]
        [clojure.java.io :only [file]])
  (:require [clojure.string :as string]))

(defn- markdown-path 
  "Path to the root of where markdown files are (markdown files hold the text for the site)."
  [page-name]
  (str "resources/" page-name ".md"))

(defn reformat-paragraphs
  "Takes a string representing a markdown file and changes all the paragraphs from the format Emacs fill mode would make them (without spaces at the end of lines within a paragraph) and turns it into a single line"
  [md-str]
  (string/replace md-str #"([^\s])\n([^\s])" "$1 $2"))

(defn- md-to-html 
  "Takes a markdown file specified by path and converts it to HTML nodes"
  [path]
  (html-snippet (md-to-html-string (reformat-paragraphs (slurp path)))))

(defn wrap-images
  "Takes a set of nodes and wraps every image in a container div with class 'img-container'"
  [md-nodes]
  (transform md-nodes [:img] (wrap :div {:class "img-container"})))

(defn file-list 
  "Returns a seq of Files in folder"
  [folder]
  (let [folder-path (fn [f] (string/join "/" (butlast (string/split (str f) #"/"))))]
    (filter (fn [x] (and (. x isFile) (= (folder-path x) folder))) (rest (file-seq (file folder))))))

(defn file-name 
  "Given a file object, gets the file name without an extension"
  [f]
  (-> f
      str
      (string/split #"/")
      last
      (string/split #"\.")
      first))

(defn human-friendly-title
  "Takes a string, turns underscores and hyphens into spaces, and capitalizes the words"
  [s]
  (let [words (-> s
                 (string/replace #"[_\-]" " ")
                 (string/split #" "))] 
    (->> words
         (map string/capitalize)
         (string/join " "))))

(defn html-links
  [page-name]
  (let [fs (file-list (str "resources/" page-name))
        links-list (snippet (template-path "listing.html") [:ul] [files]
                            [:li] (clone-for [f files] [:a] (let [name (file-name f)]
                                                             (do->
                                                              (content (human-friendly-title name))
                                                              (set-attr :href (str page-name "/" name))))))]
    (links-list fs)))

(defn intro-page 
  "Takes a markdown file name for an intro page and returns HTML nodes"
  [page-name & options]
  (apply page (concat (md-to-html (markdown-path page-name))
                      (html-links page-name)) options))

(defn resource-page [resource name]
  (page (wrap-images (md-to-html (str "resources/" resource "/" name ".md")))
        :title (str "projects - " (human-friendly-title name))))

;; (defpage "/" []
;;   (intro-page "welcome"))

;; (defpage "/purpose" []
;;   (intro-page "purpose" :title "purpose"))

;; (defpage "/projects" []
;;   (intro-page "projects" :title "projects"))

;; (defpage "/projects/:name" {:keys [name]}
;;   (page (wrap-images (md-to-html (str "resources/projects/" name ".md")))
;;         :title (str "projects - " (human-friendly-title name))))

;; (defpage "/thoughts" []
;;   (intro-page "thoughts" :title "thoughts"))

;; (defpage "/thoughts/:name" {:keys [name]}
;;   (page (wrap-images (md-to-html (str "resources/thoughts/" name ".md")))
;;         :title (str "thoughts - " (human-friendly-title name))))

;; (defpage "/tidbits" []
;;   (intro-page "tidbits" :title "tidbits"))

;; (defpage "/tidbits/:name" {:keys [name]}
;;   (page (wrap-images (md-to-html (str "resources/tidbits/" name ".md")))
;;         :title (str "tidbits - " (human-friendly-title name))
;;         :stylesheets ["/lib/prettify/prettify.css"]
;;         :scripts ["//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"
;;                   "/lib/prettify/prettify.js"
;;                   "/lib/prettify/lang-clj.js"
;;                   "/lib/prettify/lang-lisp.js"
;;                   "/js/pretty-code.js"]))
