(ns coffee-table.state)

(defonce app-state
  {:app/editing false
   :app/buffer {}
   :app/mode :list})
