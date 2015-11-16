(ns cluelog.logger
  (:use compojure.core
        [ring.util.response :only (response)])

  (:require [clojure.data.json :as json]
            [compojure.response :as response]
            [com.ashafa.clutch :as clutch]
            [ring.util.response :only (response)]))

(def cloudant
  (str ((( ((json/read-str (System/getenv "VCAP_SERVICES") ) "cloudantNoSQLDB" )0) "credentials") "url") "/logs/"))


(defn add-event [content]
     (clutch/put-document cloudant content))


(defn get-session-id [req]
  (:value ((:cookies req) "ring-session")))


(defn get-client-ip [req]
  (if-let [ips (get-in req [:headers "x-forwarded-for"])]
    (-> ips (clojure.string/split #",") first)
    (:remote-addr req)))

(defn log-event [r]
  (let [session (:session r)
        count (:count session 0)
        session (assoc session :count (inc count))
        event (-> r :params :event)
        session-id (get-session-id r)
        ]
    (add-event {:user session-id,
                :event event,
                :user-agent ((:headers r) "user-agent")
                :time (quot (System/currentTimeMillis) 1000)
                :count count
                :remote-addr (get-client-ip r)
                } )
       (->
         (response "OK")
         (assoc :session session))))
