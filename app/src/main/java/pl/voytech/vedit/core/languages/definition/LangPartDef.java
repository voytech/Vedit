package pl.voytech.vedit.core.languages.definition;

import pl.voytech.vedit.core.EditorBuffer;

/**
 * Created by USER on 2016-11-03.
 */

public abstract class LangPartDef<O,I> {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract O instance(EditorBuffer buffer);

}
