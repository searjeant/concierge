(ns com.eldrix.concierge.config
  (:require [aero.core :refer [read-config]]
            [clojure.java.io :as io]
            [clojure.tools.cli :as cli]
            [mount.core :refer [args defstate]]
            [mount.core :as mount]))

;; TODO(mw): permit choice of config.edn location from mount parameters.
(defstate root
          :start (read-config (io/resource "config.edn") (mount/args)))

(defn https-proxy []
  (select-keys (:https root) [:proxy-host :proxy-port]))

(defn http-proxy []
  (select-keys (:http root) [:proxy-host :proxy-port]))

(defn nadex-connection-pool-size []
  (get-in root [:wales :nadex :connection-pool-size]))

(defn nadex-default-bind-username []
  (get-in root [:wales :nadex :default-bind-username]))

(defn nadex-default-bind-password []
  (get-in root [:wales :nadex :default-bind-password]))

(defn empi-url []
  (get-in root [:wales :empi :url]))

(defn empi-processing-id []
  (get-in root [:wales :empi :processing-id]))

(defn cav-pms-config []
  (get-in root [:wales :cav :pms]))

(defn concierge-connect-config []
  (get-in root [:concierge :connect]))

(defn clods-config []
  (get-in root [:clods]))

(defn hermes-config []
  (get-in root [:hermes]))

(comment
  (mount/start-with-args {:profile :live})
  (mount/stop)
  (https-proxy)
  (System/setProperty "https.proxyHost" (:proxy-host (https-proxy)))
  (System/setProperty "https.proxyPort" (.toString (:proxy-port (https-proxy))))
  (System/getProperty "https.proxyHost")
  (System/getProperty "https.proxyPort"))
