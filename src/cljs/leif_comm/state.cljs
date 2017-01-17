(ns leif-comm.state
  (:require [reagent.core :as reagent :refer [atom]]))


;;; -------------------------
;;; Application state

(defonce name-atom (atom ""))
(defonce email-atom (atom ""))
(defonce password-atom (atom ""))
(defonce chat-log (atom ""))
(defonce sendtextarea-atom (atom ""))
(defonce connected-peers-atom (atom {}))

;;; -------------------------
;;; Helper functions

(defn append! [atom user message]
  (swap! atom str (str user ": " message "\n")))
