(ns total-reconf.schema
  (:require [schema.core :as s]))


(def c-attribute 
  {:name s/Str
   :data-type (s/enum :string :text :boolean :number :cgv :date)
   (s/optional-key :choice-group) s/Str
   }
)

(def c-dataentitytype
  {:name s/Str
   :max-status s/Int
   (s/optional-key :type-selector) s/Str
   (s/optional-key :attributes) [c-attribute]})

(def c-linkentitytype
  {:name s/Str
   :left s/Str
   :right s/Str
   (s/optional-key :tree) s/Bool
   (s/optional-key :attributes) [c-attribute]})


(def author 
  {:name s/Str
   :email s/Str})

(def dependency
  {:name s/Str
   :version s/Str}
)

(def c-choice-group-value
  {:name s/Str
   :choice-group s/Str}  
)

(def c-choice-group
  {:name s/Str
   (s/optional-key :values) [c-choice-group-value]}
)

(def c-label
  {:language s/Str
   :key s/Str
   :bundle s/Str})

(def c-constant
  { :module s/Str
   :name s/Str
   :type (s/enum :string :integer :locale :boolean)
   :value s/Str
   }
)

(def model 
  {
   :name s/Str
   :version s/Str
   (s/optional-key :dependencies) [dependency]
   :author author
   (s/optional-key :data-entity-types) [c-dataentitytype]
   (s/optional-key :link-entity-types) [c-linkentitytype]
   (s/optional-key :choice-groups) [c-choice-group]
   (s/optional-key :choice-groups-values) [c-choice-group-value]
   (s/optional-key :labels) [c-label]
   (s/optional-key :constants) [c-constant]
})


(s/validate model {
                   :name "base"
                   :version "1.0"
                   :dependencies [{:name "some module" :version "1.0"}
                                  {:name "other module" :version "1.3"}
                                  ]
                   :author {:name "Humbug Zinkel"
                            :email "humbug.zinkel@example.com"}
                   :data-entity-types [
                                       {:name "Publication"
                                        :max-status 5
                                        :attributes [
                                                     {:name "Title"
                                                      :data-type :string}
                                                     {:name "Publication type"
                                                      :data-type :cgv
                                                      :choice-group "type of publication"}
                                                     ]}
                                       {:name "Card"
                                        :max-status 3
                                        :attributes [
                                                     {:name "Function"
                                                      :data-type :string}
                                                     ]}
                                       ]

                   :link-entity-types [
                                       {:name "PUBL_has_CARD"
                                        :left "Publication"
                                        :right "Card"}
                                       ]
                   }            
)

