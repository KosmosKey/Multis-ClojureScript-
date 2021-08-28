(ns multis.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::wallet
 (fn [db]
   (:wallet db)))

(re-frame/reg-sub
 ::balance
 (fn [db]
   (:balance db)))

(re-frame/reg-sub
 ::transactions
 (fn [db _]
   (:transactions db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))
