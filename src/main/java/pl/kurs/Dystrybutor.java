package pl.kurs;

public class Dystrybutor {
    private String id;

    public Dystrybutor(String id) {
        this.id = id;
    }

    public void zatankuj(){
        System.out.println("Tankowanie paliwa");
    }

    @Override
    public String toString() {
        return "Dystrybutor{" +
                "id='" + id + '\'' +
                '}';
    }
}
