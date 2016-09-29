(ns coffee-table.model
  (:require [schema.core :as s]))

(def Rating
  "Numeric score for various visit factors"
  (s/enum 1 2 3 4 5))

(def Address
  "Location information"
  {:db/id s/Int
   :address/address1 s/Str
   (s/optional-key :address/address2) s/Str
   :address/city s/Str
   :address/region s/Str
   :address/country s/Str})

(def Visit
  "Schema for coffee table visits"
  {:db/id s/Int
   :visit/name s/Str
   :visit/date s/Inst
   (s/optional-key :visit/address) Address
   (s/optional-key :visit/espresso-machine) s/Str
   (s/optional-key :visit/grinder) s/Str
   (s/optional-key :visit/roast) s/Str
   :visit/beverage-ordered s/Str
   :visit/beverage-rating Rating
   (s/optional-key :visit/beverage-notes) s/Str
   (s/optional-key :visit/service-rating) Rating
   (s/optional-key :visit/service-notes) s/Str
   (s/optional-key :visit/ambience-rating) Rating
   (s/optional-key :visit/ambience-notes) s/Str
   (s/optional-key :visit/other-notes) s/Str})

(def Summary
  "Schema for coffee table summaries"
  {:db/id s/Int
   :visit/name s/Str
   :visit/date s/Inst
   :visit/beverage-rating s/Int})
