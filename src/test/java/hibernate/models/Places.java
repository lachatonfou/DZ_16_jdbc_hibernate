package hibernate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "places")
public class Places {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "`row`")
    private int row;
    @Column(name = "place_num")
    private int place_num;
    @Column(name = "`name`")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace_num() {
        return place_num;
    }

    public void setPlace_num(int place_num) {
        this.place_num = place_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Places{" +
                "id=" + id +
                ", row=" + row +
                ", place_num=" + place_num +
                ", name='" + name + '\'' +
                '}';
    }
}
