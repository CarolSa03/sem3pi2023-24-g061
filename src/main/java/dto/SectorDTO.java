package dto;

public class SectorDTO {

    private Integer id;

    public SectorDTO(Integer id) {
        setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}