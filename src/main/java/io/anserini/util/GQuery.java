package io.anserini.util;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.anserini.util.RocchioFeatureVector;
import io.anserini.util.Stopper;



/**
 * a fairly rich representation of a query (or query-like) object.  at a minimum, it will typically contain a
 * name some text.
 *
 * @author Miles Efron
 *
 */
public class GQuery {
    private String name;
    private String text;
    private Map<String,String> metadata;
    private FeatureVector featureVector;

    public GQuery() {
        metadata = new HashMap<String,String>();
    }
    public String getTitle() {
        return name;
    }
    public String getText() {
        return text;
    }
    public void setTitle(String name) {
        this.name = name;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getMetadata(String key) {
        return metadata.get(key);
    }
    public void setMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public void applyStopper(Stopper stopper) {
        FeatureVector temp = new FeatureVector(null);
        Iterator<String> it = featureVector.iterator();
        while(it.hasNext()) {
            String feature = it.next();
            if(stopper.isStopWord(feature))
                continue;
            temp.addTerm(feature, featureVector.getFeatureWeight(feature));
        }
        featureVector = temp;
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        if(name != null)
            b.append(name + ": ");
        if(text != null)
            b.append(text + "\n");
        if(featureVector != null)
            b.append(featureVector);

        if(metadata != null) {
            Iterator<String> it = metadata.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next();
                String value = metadata.get(key);
                b.append(key + ":: " + value + "\n");
            }
        }
        return b.toString();
    }
    public FeatureVector getFeatureVector() {
        return featureVector;
    }
    public void setFeatureVector(FeatureVector featureVector) {
        this.featureVector = featureVector;
    }

}