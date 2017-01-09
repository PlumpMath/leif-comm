(ns leif-comm.pages
  (:require [reagent.core :as reagent :refer [atom]]
            [accountant.core :as accountant]
            [leif-comm.state :as state]
            [leif-comm.webrtc :as webrtc]
            [leif-comm.server-comms :as server-comms]))


(defn atom-field [value placeholder type]
[:input {:type type
         :value @value
         :placeholder placeholder
         :on-change #(reset! value (-> % .-target .-value))}])

(defn atom-textarea [id value disabled]
  [:textarea {:id id
              :disabled disabled
              :value @value
              :on-change #(reset! value (-> % .-target .-value))}])


(defn home []
  [:div [:h2 "Welcome to WebRTClojure!"]
   [:div [:a {:href "/about"} "About"]]
   [:div [:a {:href "/register"} "Register"]]
   [atom-field state/name-atom "Nickname" "text"]
   [:input {:type "button" :value "Start" :on-click
            #(do (server-comms/anonymous-login! @state/name-atom)
              (accountant/navigate! "/chat"))}]])

(defn about []
  [:div [:h2 "About leif-comm"]
   [:div [:a {:href "/"} "Go to the home page"]]])

(defn registry []
  [:div [:h2 "Welcome!"]
   [:h2 @server-comms/registry-result]
   [:h5 "How you want to be reached:"]
   [atom-field state/email-atom "Email" "text"]
   [:h5 "Your secret passphrase:"]
   [atom-field state/password-atom "Password" "password"]
   [:input {:type "button" :value "Register" :on-click
            #(server-comms/register! @state/email-atom @state/password-atom)}]])

(defn chat []
  [:div {:id :chat}
   [:h2 "Chat room"]
   [:a {:href "/"} "Back"]
   [atom-textarea :received state/recvtextarea-atom true]
   [atom-textarea :send state/sendtextarea-atom false]
   [:input {:id :send-btn :type "button" :value "Send" :on-click
            #(do (server-comms/send-message! @state/sendtextarea-atom)
                 (state/append! state/recvtextarea-atom @state/name-atom @state/sendtextarea-atom)
                 (reset! state/sendtextarea-atom ""))}]])
