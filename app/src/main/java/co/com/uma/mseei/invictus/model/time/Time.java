package co.com.uma.mseei.invictus.model.time;

import java.time.format.DateTimeFormatter;

/**
 * Time is a interface definition class used to define a period of time.
 * @author Sandra Marcela López Torres
 * @version 0.1, 2023/07/02
 */
public interface Time {

    void setCurrent(String... FromTo);

    void setNext();

    void setPrevious();

    String toString(DateTimeFormatter formatter);

    String[] toStringArray(DateTimeFormatter formatter);
}
