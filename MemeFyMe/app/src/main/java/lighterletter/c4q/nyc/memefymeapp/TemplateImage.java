package lighterletter.c4q.nyc.memefymeapp;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class TemplateImage {

    @DatabaseField(generatedId = true)
    private int id; // For database

    @DatabaseField
    private int image;

    public TemplateImage() {
        // ORMLite requires this
    }

    public TemplateImage(int image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
