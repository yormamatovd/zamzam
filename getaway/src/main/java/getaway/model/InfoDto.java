package getaway.model;

public class InfoDto {
    private  Long id;

    private  String name;

    private  String surname;

    private  String photoId;

    private  String type;

    public InfoDto(Long id, String name, String surname, String photoId, String type) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.photoId = photoId;
        this.type = type;
    }

    public InfoDto() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getType() {
        return type;
    }
}
