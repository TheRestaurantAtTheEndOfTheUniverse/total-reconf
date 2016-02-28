(ns total-reconf.model)


(defn- compatible? 
  ([val1 val2]
  (if (and val1 val2)
    (= val1 val2)
    true))
  ([key map1 map2]
   (compatible? (get map1 key)
                (get map2 key)))
)

(defn- all-compatible [keys source1 source2]
  (reduce #(and %1 %2)
          true
          (map #(compatible? % source1 source2) keys))
)

(defn merge-all [keys source1 source2]
  (reduce #(assoc %1 %2 (or (get source1 %2)
                            (get source2 %2)))
          {}
          keys)
)

(defn attribute-compatible?[attr1 attr2]
  (and (compatible? :data-type attr1 attr2)
       (if (= (or (:data-type attr1) (:data-type attr1)) :choice-group)
         (compatible? (:choice-group attr1) (:choice-group attr2))
         true)
))

(defn sub-check 
  ([source1 source2 group-key sub-key check-fn]
  (let [entries (group-by group-key (concat (get source1 sub-key)
                                            (get source2 sub-key)))]
         (reduce (fn[prev group-entry]
                   (let [values (val group-entry)]
                     (if (= (count values) 1)
                       prev
                       (and prev (check-fn (first values)
                                           (second values))))))
                 true
                 entries)))
  ([source1 source2 sub-key check-fn]
   (sub-check source1 source2 :name sub-key check-fn)))


(defn sub-merge
  ([source1 source2 group-key sub-key merge-fn]
  (let [entries (group-by group-key (concat (get source1 sub-key)
                                           (get source2 sub-key)))]
    (reduce (fn[prev group-entry]
              (let [values (val group-entry)]
                (if (= (count values) 1)
                  (conj prev (first values))
                  (conj prev (merge-fn (first values)
                                              (second values))))))
            []
            entries)
    ))
  ([source1 source2 sub-key merge-fn]
   (sub-merge source1 source2 :name sub-key merge-fn)))

(defn- attributes-compatible?
  [source1 source2]
  (sub-check source1 source2 :attributes attribute-compatible?)
)

(defn data-entity-type-compatible?[det1 det2]
    (and (compatible? :name det1 det2)
         (compatible? :max-status det1 det2)
         (compatible? :type-selector det1 det2)
         (attributes-compatible? det1 det2)))

(defn link-entity-type-compatible?[let1 let2]
    (and (compatible? :name let1 let2)
         (compatible? :left let1 let2)
         (compatible? :right let1 let2)
         (compatible? :tree let1 let2)
         (attributes-compatible? let1 let2)))

(defn merge-attribute[attr1 attr2]
  (assoc (select-keys attr1 [:name])
         :data-type (or (:data-type attr1)
                        (:data-type attr2))
         :choice-group (or (:choice-group attr1)
                         (:choice-group attr2))))

(defn merge-attributes[data-object1 data-object2]
  (let [attributes (group-by :name (concat (:attributes data-object1)
                                           (:attributes data-object2)))]
    (reduce (fn[prev attr-entry]
              (let [attrs (val attr-entry)]
                (if (= (count attrs) 1)
                  (conj prev (first attrs))
                  (conj prev (merge-attribute (first attrs)
                                              (second attrs))))))
            []
            attributes)
    ))

(defn merge-data-entity-type [det1 det2]
  (assoc (select-keys det1 [:name])
         :max-status (or (:max-status det1) (:max-status det2))
         :type-selector (or (:type-selector det1) (:type-selector det2))
         :attributes (merge-attributes det1 det2))             
)

(defn merge-link-entity-type [let1 let2]
  (assoc (select-keys let1 [:name])
         :left (or (:left let1) (:left let2))
         :right (or (:right let1) (:right let2))
         :attributes (merge-attributes let1 let2))             
)

(defn choice-group-value-compatible? [cgv1 cgv2]
  (all-compatible [:name :value :choice-group] cgv1 cgv2)
)

(defn merge-choice-group-value [cgv1 cgv2]
  (merge-all [:name :value :choice-group] cgv1 cgv2))

(defn choice-group-compatible? [cg1 cg2]
    (and (compatible? (:name cg1) (:name cg2))
         (sub-check cg1 cg2 :values choice-group-value-compatible?)))

(defn merge-choice-group [cg1 cg2]
  (assoc (select-keys cg1 [:name])
         :values (sub-merge cg1 cg2 :values merge-choice-group-value)))

(defn label-compatible?[l1 l2]
  (all-compatible [:language :key :bundle :value] l1 l2)
)

(defn merge-label[l1 l2]
  (merge-all [:language :key :bundle :value] l1 l2)
)

(defn constant-compatible?[c1 c2]
  (all-compatible [:name :module :type :value] c1 c2))

(defn module-compatible? [module1 module2]
  (and (sub-check module1 module2 :data-entity-types :name data-entity-type-compatible?)
       (sub-check module1 module2 :link-entity-types :name link-entity-type-compatible?)
       (sub-check module1 module2 :choice-groups choice-group-compatible?)
       (sub-check module1 module2 :choice-group-values choice-group-value-compatible?)
       (sub-check module1 module2 #(select-keys % [:name :module]) 
                  :constants constant-compatible?)
       (sub-check module1 module2 #(select-keys % [:language :key :bundle]) 
                  :labels label-compatible?)
))


