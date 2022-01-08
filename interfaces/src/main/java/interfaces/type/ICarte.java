package interfaces.type;

import java.util.Map;

public interface ICarte {
    Map<String, String> getChainage();

    void setChainage(Map<String, String> chainage);

    String getNom();

    void setNom(String nom);

    String getType();

    void setType(String type);

    Map<String, Integer> getCout();

    void setCout(Map<String, Integer> cout);

    int getAge();

    void setAge(int age);

    Map<String, String> getEffet();

    void setEffet(Map<String, String> effet);
}
