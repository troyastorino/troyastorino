(ns troyastorino.views.common
  (:use [net.cgrand.enlive-html]))

(defn template-path [file]
  "Returns the path to a file in the templates folder."
  (str "troyastorino/views/templates/" file))

(defn add-attrs [attr list]
  "Transformation of node that adds clones of the node differing only by changing the attribute value attr to the items in the list."
  (fn [node]
    (let [new-stylesheet-nodes
          ((clone-for [item list]
                      (set-attr attr item)) node)]
      (cons node new-stylesheet-nodes))))

(deftemplate page (template-path "layout.html")
  [body & {:keys [title stylesheets scripts] :or {title "Troy Astorino's Website"
                                                  stylesheets []
                                                  scripts []}}]
  [:title] (content title)
  [:#main] (content body)
  [[:link (attr= :href "/css/style.css")]] (add-attrs :href stylesheets)
  [[:script (attr= :type "text/javascript")]] (add-attrs :src scripts))