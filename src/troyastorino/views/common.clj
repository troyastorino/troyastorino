(ns troyastorino.views.common
  (:use [net.cgrand.enlive-html]))

(defn template-path [file]
  "Returns the path to a file in the templates folder."
  (str "troyastorino/views/templates/" file))

(deftemplate page (template-path "layout.html")
  [body & {:keys [title] :or {title "Troy Astorino's Website"}}]
  [:title] (content title)
  [:#main] (content body))