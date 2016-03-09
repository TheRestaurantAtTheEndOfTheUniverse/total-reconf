(ns total-reconf.model-test
  (:require [total-reconf.model :refer :all]
            [midje.sweet :refer :all]))

(fact "Check attribute compatibility" 
 (attribute-compatible? {:name "a" :data-type :string}
                  {:name "a" :data-type :string}
                  ) => true

 (attribute-compatible? {:name "a" :data-type :number}
                  {:name "a" :data-type :string}
                  ) => false

 (attribute-compatible? {:name "a" :data-type :string}
                  {:name "b" :data-type :string}
                  ) => true

 (attribute-compatible? {:name "a" :data-type :choice-group :choice-group "b"}
                  {:name "a" :data-type :choice-group}
                  ) => true

 (attribute-compatible? {:name "a" :data-type :choice-group :choice-group "b"}
                  {:name "a" :data-type :choice-group :choice-group "b"}
                  ) => true

 (attribute-compatible? {:name "a" :data-type :choice-group}
                  {:name "a" :data-type :choice-group :choice-group "b"}
                  ) => true

 (attribute-compatible? {:name "a" :data-type :choice-group :choice-group "b"}
                  {:name "a" :data-type :choice-group :choice-group "c"}
                  ) => false
)

(fact "Merging attributes"
      (merge-attribute {:name "a" :data-type :string}
                       {:name "a" :data-type :string}
                       ) => {:name "a" :data-type :string :choice-group nil}
      (merge-attribute {:name "a"}
                       {:name "a" :data-type :string}
                       ) => {:name "a" :data-type :string :choice-group nil}
      (merge-attribute {:name "a" :data-type :string}
                       {:name "a"}
                       ) => {:name "a" :data-type :string :choice-group nil}
      (merge-attribute {:name "a" :data-type :choice-group}
                       {:name "a" :data-type :choice-group :choice-group "b"}
                       ) => {:name "a" :data-type :choice-group :choice-group "b"}
      (merge-attribute {:name "a" :data-type :choice-group}
                       {:name "a" :data-type :choice-group :choice-group "b"}
                       ) => {:name "a" :data-type :choice-group :choice-group "b"}
)

(fact "Checking data entity types"
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               }
                              {:name "a"
                               :max-status 5
                               })
      => true
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               }
                              {:name "a"
                               :max-status 4
                               })
      => false
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :type-selector "b"
                               }
                              {:name "a"
                               :max-status 5
                               :type-selector "b"
                               })
      => true
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :type-selector "b"
                               }
                              {:name "a"
                               :max-status 5
                               })
      => true
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :type-selector "b"
                               }
                              {:name "a"
                               :max-status 5
                               :type-selector "c"
                               })
      => false
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]})
      => true
      (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :max-status 5
                               :attributes [{:name "b" :data-type :number}]})
      => true
     (data-entity-type-compatible? {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :number}]})
      => false
)


(fact "Merging data entity types"
      (merge-data-entity-type {:name "a"
                               :max-status 5
                               }
                              {:name "a"
                               :max-status 5
                               })
      => {:name "a"
          :max-status 5
          :attributes []
          :type-selector nil
          }
      (merge-data-entity-type {:name "a"
                               :max-status 5
                               :type-selector "b"
                               }
                              {:name "a"
                               :max-status 5
                               })
      => {:name "a"
          :max-status 5
          :type-selector "b"
          :attributes []
          }
      (merge-data-entity-type {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]})
      => {:name "a"
          :max-status 5
          :type-selector nil
          :attributes [{:name "a" :data-type :string :choice-group nil}]}                             
      (merge-data-entity-type {:name "a"
                               :max-status 5
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :max-status 5
                               :attributes [{:name "b" :data-type :number}]})
      => {:name "a"
          :max-status 5
          :type-selector nil
          :attributes [{:name "a" :data-type :string}
                       {:name "b" :data-type :number}]}                             
 )


(fact "Merging link entity types"
      (merge-link-entity-type {:name "a"
                               :left "b"
                               :right "c"
                               }
                              {:name "a"
                               :left "b"
                               :right "c"
                               })
      => {:name "a"
          :left "b"
          :right "c"
          :attributes []
          }
      (merge-link-entity-type {:name "a"
                               :left "b"
                               }
                              {:name "a"
                               :left "b"
                               :right "c"
                               })
      => {:name "a"
          :left "b"
          :right "c"
          :attributes []
          }
      (merge-link-entity-type {:name "a"
                               :right "c"
                               }
                              {:name "a"
                               :left "b"
                               :right "c"
                               })
      => {:name "a"
          :left "b"
          :right "c"
          :attributes []
          }
      (merge-link-entity-type {:name "a"
                               :left "b"
                               :right "c"
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :left "b"
                               :right "c"
                               :attributes [{:name "a" :data-type :string}]})
      => {:name "a"
          :left "b"
          :right "c"
          :attributes [{:name "a" :data-type :string :choice-group nil}]}                             
      (merge-link-entity-type {:name "a"
                               :left "b"
                               :right "c"
                               :attributes [{:name "a" :data-type :string}]}
                              {:name "a"
                               :left "b"
                               :right "c"
                               :attributes [{:name "b" :data-type :number}]})
      => {:name "a"
          :left "b"
          :right "c"
          :attributes [{:name "a" :data-type :string}
                       {:name "b" :data-type :number}]}                             
 )

(fact "Constants"
      (constant-compatible? {:name "a" :module "m" :type :string :value "v"}
                            {:name "a" :module "m" :type :string :value "v"}
                            ) => true
      (constant-compatible? {:name "a" :module "m" :type :string :value "v1"}
                            {:name "a" :module "m" :type :string :value "v2"}
                            ) => false
      (constant-compatible? {:name "a" :module "m" :type :string :value "v"}
                            {:name "b" :module "m" :type :string :value "v"}
                            ) => false
      (constant-compatible? {:name "a" :module "m" :type :string :value "v"}
                            {:name "a" :module "m" :type :integer :value "v"}
                            ) => false

)

(fact "Choice groups compatibility"
      (choice-group-value-compatible? {:name "a" :value "b"} {:name "a" :value "b"}) => true
      (choice-group-value-compatible? {:name "a" :value "b"} {:name "a" :value "c"}) => false
      (choice-group-compatible? {:name "cg" :values [{:name "a" :value "b"}]} 
                                {:name "cg" :values [{:name "a" :value "b"}]}) => true 
      (choice-group-compatible? {:name "cg" :values [{:name "a" :value "b"}]} 
                                {:name "cg" :values [{:name "a" :value "c"}]}) => false
      (choice-group-compatible? {:name "cg" :values [{:name "a" :value "b"}]} 
                                {:name "cg" :values [{:name "x" :value "c"}]}) => true 
 )

(fact "Choice group merging"
      (merge-choice-group-value {:name "a" :value "b"} {:name "a" :value "b"}) 
                                => {:name "a" :value "b" :choice-group nil}
      (merge-choice-group {:name "a" :values [{:name "a" :value "b"}]}
                          {:name "a" :values [{:name "x" :value "y"}]})
                          => {:name "a" :values [{:name "a" :value "b"}
                                                 {:name "x" :value "y"}]}                          
)

