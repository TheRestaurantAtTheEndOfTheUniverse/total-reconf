(ns total-reconf.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [total-reconf.schema :as schema]
            [total-reconf.data :as data]
            [schema.core :as s]))

(s/defschema model-info-schema
  schema/module-info
)


(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Total Reconf"
                    :description "Configuration API for Converis"}
             :tags [{:name "api", :description "some apis"}]}}}

    (context "/total-reconf" []
      :tags ["api"]

      (GET "/models" []
        :return [model-info-schema]
        :summary "Get a list of available modules"
        (ok (vec (map #(select-keys %1 [:id :name :version :author :price :dependencies]) data/modules))
            ))
      (GET "/isvalid" []
        :return s/Bool
        :summary "Check if a set of modules is valid"
        (ok true))

      )))
