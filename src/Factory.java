//Use this to easily implement creating of parse data
public interface Factory<T> extends Getter {
   T createObject(String[] members);
   String getClassName();
}
