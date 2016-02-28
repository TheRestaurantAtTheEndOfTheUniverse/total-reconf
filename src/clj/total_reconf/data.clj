(ns total-reconf.data)

(def modules
  [{:id "bd0e1770-3e86-4d20-92cf-a093231e555c"
    :name "base"
    :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
    :version "1.0"
    :price 1999.99
    :dependencies [{:modules ["e059f70a-7ba4-4da7-a48d-e712c24fd672"
                              "e059f70a-7ba4-4da7-a48d-e712c24fd672"
                              "275375f5-0fe5-4d4e-97d6-6cf9278436ff"
                              "30afaf7a-8b17-4349-9806-b027600dd75e"]
                     :type :required
                     :operation :at-least-one
                    }
                   ]
    :data-entity-types [{:name "Card"}
                        {:name "Person"}
                        ]
    :link-entity-types [{:name "PERS_has_CARD"
                         :left "Person"
                         :right "Card"}
                        ]
    }
  {:id "e059f70a-7ba4-4da7-a48d-e712c24fd672"
   :name "base"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.1"
   :price 1999.99
   :dependencies [{:modules ["e059f70a-7ba4-4da7-a48d-e712c24fd672"
                             "e059f70a-7ba4-4da7-a48d-e712c24fd672"
                             "275375f5-0fe5-4d4e-97d6-6cf9278436ff"
                             "30afaf7a-8b17-4349-9806-b027600dd75e"]
                   :type :required
                   :operation :at-least-one}
                  ]
    :data-entity-types [{:name "Card"
                         :attributes {:name "Function"
                                      :type :string}}
                        {:name "Person"}
                        ]
    :link-entity-types [{:name "PERS_has_CARD"
                         :left "Person"
                         :right "Card"}
                        ]
   }
  {:id "e059f70a-7ba4-4da7-a48d-e712c24fd672"
   :name "base merkin labels"
   :version "1.0"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :price 399.99
   :labels [{:bundle "xxx"
             :key "name"
             :language "en_EN"
             :value "Bla"}]
   }
  {:id "e059f70a-7ba4-4da7-a48d-e712c24fd672"
   :name "base german labels"
   :version "1.0"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :price 199.99
   }
  {:id "275375f5-0fe5-4d4e-97d6-6cf9278436ff"
   :name "base latin labels"
   :version "1.0"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :price 199.99
   }
  {:id "30afaf7a-8b17-4349-9806-b027600dd75e"
   :name "base english labels"
   :version "1.0"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
    :price 199.99
   }
  {:id "df8c71d7-6139-450c-9e6c-5c35f5eb5fdb"
   :name "Publication management"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.0"
    :price 3999.99
   :dependencies [{:modules ["bd0e1770-3e86-4d20-92cf-a093231e555c"]
                   :type :required
                   :operation :all}
                  ]}
  {:id "cb9f1d8f-c9ec-4803-a346-5d3ad54ee561"
   :name "Publication management"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.1"
    :price 1999.99
   :dependencies [{:modules ["bd0e1770-3e86-4d20-92cf-a093231e555c"]
                   :type :required
                   :operation :all
                   }
                  ]}
  {:id "c1c39e0d-09f3-4bac-bca5-e7780558754c"
   :name "OAIPmh"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.0"
   :price 799.99
   :dependencies [{:modules ["cb9f1d8f-c9ec-4803-a346-5d3ad54ee561"]
                   :type :required
                   :operation :all}
                  ]}
  {:id "f3148e38-4eaa-41dc-93e0-86d9a893b1b8"
   :name "Graduate managment"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.0"
   :price 1250.99
   :dependencies [{:modules ["bd0e1770-3e86-4d20-92cf-a093231e555c"]
                   :type :required
                   :operation :all}
                  ]}
  {:id "48fcd36b-6ecc-4e72-a77f-948fbdf1de81"
   :name "Project management"
   :author {:name "Ingo Kessinger" :email "ingo.parzival.kessinger@googlemail.com"}
   :version "1.0"
   :price 1678
   :dependencies [{:modules ["bd0e1770-3e86-4d20-92cf-a093231e555c"]
                   :type :required
                   :operation :all}
                  ]}
  {:id "ad6b1142-4694-40ab-b9c2-617f49816e0a"
   :name "University of Kent definitions"
   :author {:name "Clark Kent" :email "superman@google.com"}
   :version "1.0"
   :price 0.99
   :constants [{:module "DMS" :name "url" :value "http://kent.avedas.com/dms"}
               {:module "Data integration" :name "url" :value "http://kent.avedas.com/dis"}
               ]
   }
  {:id "5afa7fb9-6fbc-4205-aaad-1364bb568c7e"
   :name "Gotham City College"
   :author {:name "Bruce Wayne" :email "batcave@gotham.com"}
   :version "1.0"
   :price 0.99
   :constants [{:module "DMS" :name "url" :value "http://bat.man/dms"}
               {:module "Data integration" :name "url" :value "http://bat.man/dis"}
               {:module "Publication import" :name "WOS key" :value "12bn345"}
               ]
   }
  
]
)

(defn module-by-id [uuid]
  (filter #(= (:id %1) uuid) modules)
)

