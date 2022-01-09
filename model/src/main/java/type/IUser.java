package type;

import java.util.List;

public interface IUser {
    @Override
    String toString();

    String getPseudo();

    String getPassword();

    void setPassword(String password);

    void setPseudo(String pseudo);

    void setAmis(List<IUser> amis);

    List<IUser> getAmis();
}
