(ns coffee-table.test.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [coffee-table.test.component.MobileSummaryItem]))

(doo-tests 'coffee-table.test.component.MobileSummaryItem)
