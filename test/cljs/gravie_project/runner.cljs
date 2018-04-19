(ns gravie-project.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [gravie-project.core-test]))

(doo-tests 'gravie-project.core-test)
