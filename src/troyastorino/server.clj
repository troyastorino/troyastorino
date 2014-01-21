(ns troyastorino.server
  (:require [compojure.route :as route]
            [noir.util.middleware :as middleware])
  (:use [compojure.core :only [GET]]
        [troyastorino.views.views :only [intro-page resource-page]]))

(def public-routes
  [(route/resources "/")])

(def app-routes
  [(GET "/" [] (intro-page "welcome"))
   (GET "/purpose" [] (intro-page "purpose" :title "purpose"))
   (GET "/projects" [] (intro-page "projects" :title "projects"))
   (GET "/projects/:name" [name] (resource-page "projects" name))
   (GET "/thoughts" [] (intro-page "thoughts" :title "thoughts"))
   (GET "/thoughts/:name" [name] (resource-page "thoughts" name))
   (GET "/tidbits" [] (intro-page "tidbits" :title "tidbits"))
   (GET "/tidbits/:name" [name] (resource-page "tidbits" name))])


(def app
  (middleware/app-handler
   (concat public-routes app-routes
           [(route/not-found "Not Found")])))
