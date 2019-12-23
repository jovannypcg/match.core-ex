(ns pattern-matching.core
  (:require [clojure.core.match :refer [match]]))

(defn ex-one [n]
  (match n
         1 :one
         2 :two
         3 :three
         :else :infinity))

(defn ex-two [coll]
  (match coll
         [_ false true true] :1
         [false true _ _] :2
         [_ _ false true] :3
         [_ true true true] :4
         :else :nan))

(defn ex-three [coords]
  (match coords
         [[0 0] [0 1]] :init
         [[_ _] [9 3]] :finish))

;; ---------- Sequences --------

(defn ex-four [trigger & flags]
  (println flags)
  (match flags
         ([_ false true true] :seq) :1
         ([false true _ _] :seq) :2
         ([_ _ false true] :seq) :3
         ([_ true true true] :seq) :4
         :else :nan))

;; ---------- Maps ------------

(defn ex-five [opts]
  (match opts
         {:a _ :b 2} :1
         {:a 1 :b _} :2
         {:c 3 :d _ :e 4} :3))

;; ------------ Binding ---------

(defn ex-six [notes]
  (match notes
         [2 3 a] [:1 a]
         [3 4 b] [:2 b]))

;; ------------ Or patterns -------

(defn ex-seven [args]
  (match args
         [1 (:or 2 3) 4] :1
         [6 7 (:or 9 8 10) :2]))

;; ---------- Guards ------------

(defn ex-eight [args]
  (match args
         [(_ :guard odd?) (_ :guard #(odd? %))] :1
         [(_ :guard (fn [n] (odd? n)))] :2))

(defn fibonacci [n]
  (match n
         (_ :guard #(<= % 0)) 0
         1 1
         _ (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))

(defn greet [names]
  (match (into [] (re-seq #"\w+" names))
         [nickname] {:nickname nickname}
         [first-name last-name] {:first-name first-name :last-name last-name}
         [& all-names] (str (count all-names) " names is too damn high!")))
