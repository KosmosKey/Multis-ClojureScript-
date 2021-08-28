(ns multis.views
  (:require
   [re-frame.core :as re-frame]
   [multis.subs :as subs]
   [multis.events :as events]))


(defn dashboardChildren [wallet, address, balance, eth-value]
  [:div
   {:style
    {:width "100%"
     :display "flex"
     :font-weight "bold"
     :justify-content "space-between"
     :align-items "center"
     :background "#282A2A"
     :padding "15px"
     :border-raidus "10px"
     :margin "25px 0"
     :color "#fff"}}

   [:p wallet]
   [:p address]
   [:img {:src "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Ethereum-icon-purple.svg/1200px-Ethereum-icon-purple.svg.png" :style {:width "50px"}}]
   [:p "$" + balance]
   [:p eth-value]])

(defn dashboard []
  (let [transactions (re-frame/subscribe [::subs/transactions])]
    [:div

     {:style
      {:width "80%"
       :display "flex"
       :flex-direction "column"
       :align-items "center"
       :margin-top "5rem"
       :color "#fff"}}
     [:h1 "Overview Transactions Dashboard"]

     [:div
      {:style
       {:display "flex"
        :align-items "center"
        :justify-content "space-between"
        :width "100%"
        :margin-top "3rem"
        :font-weight "bold"}}

      [:p "Wallet Name"]
      [:p "Address"]
      [:p "Network"]
      [:p "Price"]
      [:p "Eth"]]
     (for [{:keys [wallet wallet_name price eth-value]} @transactions]
       (dashboardChildren wallet wallet_name price eth-value))]))


(defn input-control []
  (let [balance (re-frame/subscribe [::subs/balance])]

    [:form
     {:on-submit (fn [e]
                   (.preventDefault e)
                   #(re-frame/dispatch [::events/add-transaction @balance]))
      :style
      {:display "flex"
       :width "100%"
       :flex-direction "column"}}
     [:input
      {:type "text" :placeholder "Type in USD value"
       :value @balance
       :on-change #(re-frame/dispatch [::events/update-balance (-> % .-target .-value)])
       :style
       {:width "100%"
        :background "#202020"
        :border "1px solid #fff"
        :padding "10px"
        :margin "10px 0"
        :outline-width "0"
        :color "#fff"}}]
     [:input
      {:type "text" :placeholder "Etherium Value"
       :value (divide (js/parseInt @balance) 100000)
       :disabled true
       :style
       {:width "100%"
        :background "#202020"
        :border "1px solid #fff"
        :padding "10px"
        :margin "10px 0"
        :outline-width "0"
        :color "#fff"}}]
     [:button
      {:type "submit"
       :on-click #(re-frame/dispatch [::events/add-transaction @balance])
       :style
       {:width "100%"
        :background "#202020"
        :border "1px solid #fff"
        :padding "10px"
        :margin "10px 0"
        :outline-width "0"
        :color "#fff"}}
      "Exchange Crypto"]]))

(defn form-control []
  [:div
   {:style
    {:width "100%"
     :max-width "750px"
     :color "#fff"
     :background "#282A2A"
     :padding "15px"
     :display "flex"
     :flex-direction "column"
     :align-items "center"}}
   [:h3 "Exchange Crypto"]
   [:p "1 USD = 0.00001 ETH (Example)"]
   [input-control]])

(defn main-panel []
  (let [wallet (re-frame/subscribe [::subs/wallet])]
    [:div
     {:style
      {:background "#202020"
       :min-height "100vh"}}
     [:nav
      {:style
       {:width "100%"
        :background "#1C1C1C"
        :display "flex"
        :justify-content "center"
        :padding "15px"}}
      [:button
       {:on-click #(re-frame/dispatch [::events/update-wallet "Wallet: 0x0000000x00000000"])
        :style
        {:background "transparent"
         :color "#fff"
         :border "1px solid #fff"
         :border-radius "5px"
         :padding "10px 35px"
         :outline-width "0"}}
       @wallet]]
     [:div
      {:style
       {:display "flex"
        :justify-content "center"}}
      [:h1
       {:style
        {:color "#fff"
         :margin "25px 0"}}
       "Test for Multis (Clojurescript/Reframe)"]]
     [:div
      {:style
       {:display "flex"
        :flex-direction "column"
        :margin "25px 0"
        :align-items "center"}}
      [form-control]
      [dashboard]]]))


