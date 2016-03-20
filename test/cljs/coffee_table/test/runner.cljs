(ns coffee-table.test.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [coffee-table.test.component.MobileSummaryItem]
            [coffee-table.test.component.MobileSummaryList]))

(doo-tests 'coffee-table.test.component.MobileSummaryItem
           'coffee-table.test.component.MobileSummaryList)
