(ns multis.events
  (:require
   [re-frame.core :as re-frame]
   [multis.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::update-wallet
 (fn [db [_ value]]
   (assoc db :wallet value)))

(re-frame/reg-event-db
 ::update-balance
 (fn [db [_ value]]
   (assoc db :balance value)))

(re-frame/reg-event-db
 ::add-transaction
 (fn [db [_ value]]
   (let [wallet (:wallet db)
         transactions (get db :transactions [])
         update-transactions (conj transactions {:wallet "Test wallet" :wallet_name wallet :price value :eth-value (divide (js/parseInt value) 100000)})]
     (assoc db :transactions update-transactions))))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))
