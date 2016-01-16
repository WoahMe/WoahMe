/*package woahme.teamwork.com.woahme.Storage;

import com.orm.SugarRecord;

public class SqliteStorage <T extends SugarRecord> {
    final Class<T> typeParameterClass;

    public SqliteStorage(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void save(T entity) {
        entity.save();
    }

    public void delete(T entity) {
        entity.delete();
    }

    public T findById(int id) {
        return T.findById(typeParameterClass, id);
    }

    public void update(T newEntity, int id) {
        T old = T.findById(typeParameterClass, id);
        old = newEntity;
        old.save();
    }
}
*/