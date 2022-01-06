package mongodbService.src;

public abstract class Objectbd {
    public String _id;
    public String Collection_name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCollection_name() {
        return Collection_name;
    }

    public void setCollection_name(String collection_name) {
        Collection_name = collection_name;
    }
}
