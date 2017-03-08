package models.idps.signicat;

import java.util.List;

/**
 * Created by luirib on 11/02/17.
 */
public class JsonWebKey
{
    private List<Key> keys;

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }
}
