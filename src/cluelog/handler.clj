(ns cluelog.handler
  (:use compojure.core)
  (:use cluelog.logger)

  (:require [compojure.handler :as handler]
            [compojure.route :as route]
             ring.middleware.session
            [ring.util.response :only (response)]
            ))

(defroutes app-routes

  (GET "/log/:event"
       [:as request]
       (cluelog.logger/log-event request))

  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

